package com.yy.electric.maintenance.feature.launcher.grab;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.api.HttpApi;
import com.yy.electric.maintenance.base.BaseFragment;
import com.yy.electric.maintenance.feature.launcher.grab.data.GrabCheckResultData;
import com.yy.electric.maintenance.feature.launcher.grab.data.GrabData;
import com.yy.electric.maintenance.feature.launcher.grab.data.GrabInfoData;
import com.yy.electric.maintenance.util.LogUtil;
import com.yy.electric.maintenance.util.ToastUtil;

import java.lang.ref.WeakReference;

public class GrabFragment extends BaseFragment implements GrabContract.View<GrabInfoData>, View.OnClickListener {

  private static final String TAG = "GrabFragment";
  private static final int PAGE_GRAB_LIST = 0;
  private static final int PAGE_COMMIT = 1;
  private ListView mWarningLv;
  private GrabAdapter mAdapter;
  private GrabInfoRequest grabInforequest;
  private GrabContract.Presenter presenter;
  private GrabInfoData.Row mCurrentClickRow;
  // 抢单按钮
  private Button mGrabBtn;
  private GrabCheckRequest mCheckRequest;
  private GrabRequest mGrabRequest;
  private boolean isLoop;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_grab, container, false);
    }
    return mBaseView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initView();
    init();
  }

  private void init() {
    grabInforequest = new GrabInfoRequest();
    grabInforequest.repairStatus = "未接单";
    grabInforequest.url = "Baoding_ElecKeeper_RepairOrder/Services/SelectDataByStatusFull?";

    mCheckRequest = new GrabCheckRequest();
    mCheckRequest.url = "Baoding_ElecKeeper_RepairOrder/Services/SelectDataByRepairSN?";

    mGrabRequest = new GrabRequest();
    mGrabRequest.url = "Baoding_ElecKeeper_RepairOrder/Services/RecvRepairOrder?";
    presenter = new GrabPresenter(this, grabInforequest, mCheckRequest, mGrabRequest);
  }

  private void initView() {
    mGrabBtn = mBaseView.findViewById(R.id.btn_declaration_grab);
    mGrabBtn.setOnClickListener(this);
    mTitleTv.setText("抢单");
    mWarningLv = mBaseView.findViewById(R.id.lv_warning_grab);
    mAdapter = new GrabAdapter();
    mWarningLv.setAdapter(mAdapter);
    mWarningLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GrabInfoData.Row temp = mAdapter.mInfoList.get(position);
        if (mCurrentClickRow != null) {
          if (mCurrentClickRow == temp) {
            return;
          } else {
            mCurrentClickRow.isSelect = false;
          }
        }
        temp.isSelect = true;
        mCurrentClickRow = temp;
        mAdapter.notifyDataSetChanged();
      }
    });
  }

  private void checkOrder() {
    LogUtil.d(TAG, "checkGrabResult() mCurrentClickRow=" + mCurrentClickRow);
    if (mCurrentClickRow == null) {
      ToastUtil.toast("请先选中单子");
      return;
    }

    mCheckRequest.repairSN = mCurrentClickRow.repairsn;
    if (presenter != null) {
      presenter.checkGrab();
    }
  }

  @Override
  public void updateGrabInfoData(GrabInfoData data) {
    mCurrentClickRow = null;
    if (data == null) {
      mAdapter.clear();
      return;
    }
    mAdapter.addDataList(data.rows);
  }

  @Override
  public void checkGrabResult(GrabCheckResultData data) {
    if (data == null) {
      ToastUtil.toast("抢单失败");
      return;
    }
    String repairstatus = null;

    String exceptiondesc = null;
    String repairSN = mCurrentClickRow.repairsn;
    String userName = HttpApi.getUserName();
    try {
      for (GrabCheckResultData.Row row : data.rows) {
        if (row.repairstatus.equals("未接单")) {
          repairstatus = row.repairstatus;
          exceptiondesc = row.exceptiondesc;
          break;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "checkGrabResult() error", e);
    }
    LogUtil.d(TAG, "checkGrabResult() repairstatus=" + repairstatus);
    if (repairstatus != null) {
      mGrabRequest.repairPersonInput = userName;
      mGrabRequest.repairSN = repairSN;
      mGrabRequest.exceptionDesc = exceptiondesc;
      LogUtil.d(TAG, "checkGrabResult() mGrabRequest=" + mGrabRequest);
      presenter.grabOrder();
    } else {
      ToastUtil.toast("抢单失败");
    }
  }

  @Override
  public void grabResult(GrabData data) {
    LogUtil.d(TAG, "grabResult() GrabData=" + data);
    ToastUtil.toast("抢单成功");
    notifyData();
  }


  @Override
  public void setPersonal(GrabContract.Presenter p) {
    presenter = p;
  }

  @Override
  public void startTask() {
    super.startTask(10 * 1000);
  }

  @Override
  public void updateTask() {
    super.updateTask();
    notifyData();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_declaration_grab:
        checkOrder();
        break;

      default:
        break;
    }
  }

  private void notifyData() {
    if (presenter == null) {
      return;
    }
    presenter.start();
  }

}
