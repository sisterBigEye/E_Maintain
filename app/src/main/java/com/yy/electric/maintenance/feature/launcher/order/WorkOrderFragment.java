package com.yy.electric.maintenance.feature.launcher.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.api.HttpApi;
import com.yy.electric.maintenance.base.BaseFragment;
import com.yy.electric.maintenance.feature.launcher.order.data.OrderData;
import com.yy.electric.maintenance.feature.launcher.order.data.OrderDealData;
import com.yy.electric.maintenance.feature.launcher.order.data.OrderDetailData;
import com.yy.electric.maintenance.feature.launcher.order.data.OverOrderResult;
import com.yy.electric.maintenance.feature.video.gird.VideoGirdActivity;
import com.yy.electric.maintenance.util.LogUtil;
import com.yy.electric.maintenance.util.ToastUtil;

public class WorkOrderFragment extends BaseFragment implements OrderContract.View<OrderData>, View.OnClickListener {

  private static final String TAG = "WorkOrderFragment";
  private static final int PAGE_ORDER_LIST = 0;
  private static final int PAGE_DETAIL_INFO = 1;
  private ListView mWarningLv;
  private OrderAdapter mAdapter;
  private OrderRequest orderRequest;
  private OrderDealRequest dealRequest;
  private OverOrderRequest mOverOrderRequest;

  private OrderContract.Presenter presenter;
  private View mListPage;
  private View mDetailInfoPage;
  private OrderData.Row mCurrentClickRow;
  private Button mDetailBtn;
  private Button mCameraBtn;

  private TextView mOrderDescTv;
  private EditText mHandleResultEt;
  private EditText mRemarkEt;

  private Intent mVideoIntent;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mBaseView == null) {
      mBaseView = inflater.inflate(R.layout.fragment_work_order, container, false);
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
    orderRequest = new OrderRequest();
    orderRequest.user = HttpApi.getUserName();
    orderRequest.url = "Baoding_ElecKeeper_RepairOrder/Services/SelectDataByRecvUser?";

    dealRequest = new OrderDealRequest();
    dealRequest.url = "Baoding_ElecKeeper_RepairOrder/Services/HandleRepairOrder?";

    mOverOrderRequest = new OverOrderRequest();
    mOverOrderRequest.url = "Baoding_ElecKeeper_RepairOrder/Services/RepairFinishRepairOrder?";
    presenter = new OrderPresenter(this, orderRequest, dealRequest, mOverOrderRequest);
    presenter.start();
  }

  private void initView() {
    mOrderDescTv = mBaseView.findViewById(R.id.tv_order_desc_order);
    mHandleResultEt = mBaseView.findViewById(R.id.et_handleResult_order);
    mRemarkEt = mBaseView.findViewById(R.id.et_remark_order);

    mBaseView.findViewById(R.id.btn_deal_order).setOnClickListener(this);
    mDetailBtn = mBaseView.findViewById(R.id.btn_check_order);
    mDetailBtn.setOnClickListener(this);
    mCameraBtn = mBaseView.findViewById(R.id.btn_camera_order);
    mCameraBtn.setOnClickListener(this);
    mListPage = mBaseView.findViewById(R.id.ll_first_page_order);
    mDetailInfoPage = mBaseView.findViewById(R.id.ll_second_page_order);
    mTitleTv.setText("我的工单");
    mBackIv.setOnClickListener(this);
    mBaseView.findViewById(R.id.btn_over_order).setOnClickListener(this);
    mWarningLv = mBaseView.findViewById(R.id.lv_warning_order);
    mAdapter = new OrderAdapter();
    mWarningLv.setAdapter(mAdapter);
    mWarningLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderData.Row temp = mAdapter.mInfoList.get(position);
        if (mCurrentClickRow != null) {
          if (mCurrentClickRow == temp) {
            return;
          } else {
            mCurrentClickRow.isSelect = false;
          }
        }
        temp.isSelect = true;
        mCurrentClickRow = temp;
        handlerUi();
      }
    });
  }

  private void showDetail(String repairsn) {
    if (repairsn == null || TextUtils.isEmpty(repairsn)) {
      return;
    }
    OrderDetailRequest request = new OrderDetailRequest();
    request.url = "Baoding_ElecKeeper_RepairOrder/Services/SelectDataByRepairSN?";
    request.repairSN = repairsn;
    presenter.loadDetailInfo(request);
  }

  @Override
  public void updateOrderData(OrderData orderData) {
    LogUtil.w(TAG, "updateOrderData() orderData=" + orderData);
    mCameraBtn.setVisibility(View.GONE);
    mCurrentClickRow = null;
    if (orderData == null) {
      mAdapter.clear();
      return;
    }
    mAdapter.addDataList(orderData.rows);
  }

  @Override
  public void updateDetailInfo(OrderDetailData data) {
    if (data == null) {
      ToastUtil.toast("获取数据为空");
      LogUtil.w(TAG, "updateDetailInfo() data is null");
      return;
    }
    OrderDetailData.Row row = null;
    try {
      for (OrderDetailData.Row temp : data.rows) {
        if (temp != null) {
          row = temp;
          break;
        }
      }
    } catch (Exception e) {
      LogUtil.e(TAG, "updateDetailInfo() error", e);
    }
    if (row == null) {
      LogUtil.w(TAG, "updateDetailInfo() row is null");
      return;
    }
    LogUtil.d(TAG, "updateDetailInfo() row=" + row);
    mOrderDescTv.setText(row.exceptiondesc);
    mHandleResultEt.setText(row.handleresult);
    mRemarkEt.setText(row.remarks);
  }

  @Override
  public void updateOrderDealData(OrderDealData data) {
    LogUtil.d(TAG, "updateOrderDealData() data=" + data);
    if (data == null) {
      ToastUtil.toast("工单处理失败");
      return;
    }
    for (OrderDealData.Row row : data.rows) {
      if (row.result == null) {
        continue;
      }
      if (row.result.equals("成功")) {
        presenter.start();
        ToastUtil.toast("工单已处理");
        return;
      }
    }
    ToastUtil.toast("工单处理失败");

  }

  @Override
  public void overOrderResult(OverOrderResult result) {
    pageTurning(PAGE_ORDER_LIST);
    LogUtil.d(TAG, "overOrderResult() result=" + result);
    if (result == null) {
      ToastUtil.toast("结单失败");
      return;
    }
    for (OverOrderResult.Row row : result.rows) {
      if (row == null) {
        continue;
      }
      if (row.result == null || TextUtils.isEmpty(row.result)) {
        continue;
      }
      if (row.result.equals("成功")) {
        ToastUtil.toast("结单成功");
        presenter.start();
        return;
      }
    }
  }

  @Override
  public void setPersonal(OrderContract.Presenter p) {
    presenter = p;
  }

  @Override
  public void startTask(long updateTime) {
    super.startTask(60 * 1000);
  }

  @Override
  protected void updateTask() {
    super.updateTask();
    LogUtil.d(TAG, "updateTask() presenter=" + presenter);
    if (presenter != null) {
      presenter.start();
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_back:
        pageTurning(PAGE_ORDER_LIST);
        break;

      case R.id.btn_deal_order:
        dealOrder();
        break;

      case R.id.btn_check_order:
        pageTurning(PAGE_DETAIL_INFO);
        break;

      case R.id.btn_over_order:
        if (mCurrentClickRow != null) {
          mOverOrderRequest.repairSN = mCurrentClickRow.repairsn;
        }
        mOverOrderRequest.handleResult = mHandleResultEt.getText().toString().trim();
        mOverOrderRequest.remarks = mRemarkEt.getText().toString().trim();
        presenter.overOrder(mOverOrderRequest);
        break;

      case R.id.btn_camera_order:
        if (mCurrentClickRow == null) {
          LogUtil.w(TAG, "onClick() camera, CurrentClickRow is null");
          return;
        }
        if (mVideoIntent == null) {
          mVideoIntent = new Intent(getActivity(), VideoGirdActivity.class);
        }
        mVideoIntent.putExtra(VideoGirdActivity.INTENT_EXTRA_KEY_VIDEO_LIST, mCurrentClickRow.usersource);
        startActivity(mVideoIntent);
        break;


      default:
        break;
    }
  }

  private void pageTurning(int page) {
    switch (page) {
      case PAGE_ORDER_LIST:
        mTitleTv.setText("工单状态");
        mListPage.setVisibility(View.VISIBLE);
        mDetailInfoPage.setVisibility(View.GONE);
        mBackIv.setVisibility(View.INVISIBLE);
        break;
      case PAGE_DETAIL_INFO:
        if (mCurrentClickRow == null) {
          ToastUtil.toast("请先选择列表中的一项数据");
          return;
        }

        if (mCurrentClickRow.repairsn == null || TextUtils.isEmpty(mCurrentClickRow.repairsn)) {
          ToastUtil.toast("当前选中的列表存在无效数据");
          return;
        }

        if (!mCurrentClickRow.repairstatus.equals("处理中")) {
          ToastUtil.toast("不在处理中的订单无法结单");
          return;
        }
        mTitleTv.setText("工单信息");
        mListPage.setVisibility(View.GONE);
        mDetailInfoPage.setVisibility(View.VISIBLE);
        mBackIv.setVisibility(View.VISIBLE);
        showDetail(mCurrentClickRow.repairsn);
        break;
      default:
        break;
    }
  }

  private void dealOrder() {
    if (mCurrentClickRow == null) {
      ToastUtil.toast("请先选择列表中的一项数据");
      return;
    }

    if (mCurrentClickRow.repairstatus != null && mCurrentClickRow.repairstatus.equals("已接单")) {
      dealRequest.repairSN = mCurrentClickRow.repairsn;
      presenter.dealOrder();
    } else {
      ToastUtil.toast("工单已处理");
      LogUtil.d(TAG, "updateOrderDealData() 工单已处理");
    }
  }

  private void handlerUi() {
    if (mCurrentClickRow == null) {
      return;
    }
    if (mCurrentClickRow.repairstatus.equals(OrderData.REPAIR_STATUS_PROCESSING)) {
      mCameraBtn.setVisibility(View.VISIBLE);
    } else {
      mCameraBtn.setVisibility(View.GONE);
    }
    mAdapter.notifyDataSetChanged();
  }
}
