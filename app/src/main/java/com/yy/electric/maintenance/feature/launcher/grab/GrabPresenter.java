package com.yy.electric.maintenance.feature.launcher.grab;

import com.yy.electric.maintenance.api.Callback;
import com.yy.electric.maintenance.api.ITask;
import com.yy.electric.maintenance.feature.launcher.grab.data.GrabCheckResultData;
import com.yy.electric.maintenance.feature.launcher.grab.data.GrabData;
import com.yy.electric.maintenance.feature.launcher.grab.data.GrabInfoData;
import com.yy.electric.maintenance.task.RequestDataTask;
import com.yy.electric.maintenance.util.LogUtil;

public class GrabPresenter implements GrabContract.Presenter {
  private static final String TAG = "GrabPresenter";
  private GrabContract.View<GrabInfoData> view;

  private GrabInfoRequest grabInfoRequest;
  private GrabCheckRequest mCheckRequest;
  private GrabRequest mGrabRequest;

  private ITask<GrabInfoData> grabInfoTask;
  private ITask<GrabCheckResultData> mCheckTask;
  private ITask<GrabData> mGrabTask;

  GrabPresenter(GrabContract.View<GrabInfoData> view, GrabInfoRequest grabInfoRequest,
                GrabCheckRequest checkRequest,
                GrabRequest grabRequest) {
    this.view = view;
    this.grabInfoRequest = grabInfoRequest;
    this.mCheckRequest = checkRequest;
    mGrabRequest = grabRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadGrabInfoData();
  }

  @Override
  public void loadGrabInfoData() {
    if (view == null || grabInfoRequest == null) {
      LogUtil.e(TAG, "loadGrabInfoData() --- view is null");
      return;
    }
    if (grabInfoTask == null) {
      grabInfoTask = new RequestDataTask<>(GrabInfoData.class);
    }
    LogUtil.d(TAG, "loadGrabInfoData() --- map = " + grabInfoRequest.toString());
    grabInfoTask.startTask(grabInfoRequest, new Callback<GrabInfoData>() {

      @Override
      public void result(GrabInfoData data) {
        view.updateGrabInfoData(data);
      }
    });
  }

  @Override
  public void checkGrab() {
    if (view == null || mCheckRequest == null) {
      LogUtil.e(TAG, "checkGrab() --- view is null");
      return;
    }
    if (mCheckTask == null) {
      mCheckTask = new RequestDataTask<>(GrabCheckResultData.class);
    }
    LogUtil.d(TAG, "checkGrab() --- map = " + mCheckRequest.toString());
    mCheckTask.startTask(mCheckRequest, new Callback<GrabCheckResultData>() {

      @Override
      public void result(GrabCheckResultData data) {
        view.checkGrabResult(data);
      }
    });
  }

  @Override
  public void grabOrder() {
    if (view == null || mGrabRequest == null) {
      LogUtil.e(TAG, "grabOrder() --- view is null");
      return;
    }
    if (mGrabTask == null) {
      mGrabTask = new RequestDataTask<>(GrabData.class);
    }
    LogUtil.d(TAG, "grabOrder() --- map = " + mGrabRequest.toString());
    mGrabTask.startTask(mGrabRequest, new Callback<GrabData>() {

      @Override
      public void result(GrabData data) {
        view.grabResult(data);
      }
    });
  }
}
