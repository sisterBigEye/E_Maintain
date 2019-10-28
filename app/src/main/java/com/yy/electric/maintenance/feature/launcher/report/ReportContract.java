package com.yy.electric.maintenance.feature.launcher.report;


import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;
import com.yy.electric.maintenance.feature.launcher.report.data.ReportDetailData;

class ReportContract {

  interface Presenter extends BasePresenter {

    void loadReportType();

    void loadDetailInfo();

  }

  interface View<T> extends BaseView<Presenter> {

    void updateReportType(T t);

    void updateDetailInfo(ReportDetailData data);

  }

}
