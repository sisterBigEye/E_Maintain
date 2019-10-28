package com.yy.electric.maintenance.feature.launcher.report;

import com.yy.electric.maintenance.api.Callback;
import com.yy.electric.maintenance.api.ITask;
import com.yy.electric.maintenance.feature.launcher.report.data.ReportDetailData;
import com.yy.electric.maintenance.feature.launcher.report.data.ReportTypeData;
import com.yy.electric.maintenance.task.RequestDataTask;
import com.yy.electric.maintenance.util.LogUtil;

public class ReportPresenter implements ReportContract.Presenter {
  private static final String TAG = "ReportPresenter";
  private ReportContract.View<ReportTypeData> view;
  private ReportTypeRequest request;
  private ITask<ReportTypeData> task;
  private ReportDetailRequest detailRequest;

  ReportPresenter(ReportContract.View<ReportTypeData> view, ReportTypeRequest request, ReportDetailRequest detailRequest) {
    this.view = view;
    this.request = request;
    this.detailRequest = detailRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadReportType();
    loadDetailInfo();
  }

  @Override
  public void loadReportType() {
    if (view == null || request == null) {
      LogUtil.e(TAG, "loadReportType() --- view is null");
      return;
    }
    if (task == null) {
      task = new RequestDataTask<>(ReportTypeData.class);
    }
    LogUtil.d(TAG, "loadReportType() --- map = " + request.toString());
    task.startTask(request.url, new Callback<ReportTypeData>() {

      @Override
      public void result(ReportTypeData data) {
        view.updateReportType(data);
      }
    });
  }

  @Override
  public void loadDetailInfo() {
    LogUtil.d(TAG, "loadDetailInfo() --- map = " + detailRequest.toString());
    new RequestDataTask<>
            (ReportDetailData.class).startTask(detailRequest, new Callback<ReportDetailData>() {
      @Override
      public void result(ReportDetailData data) {
        view.updateDetailInfo(data);
      }
    });
  }
}
