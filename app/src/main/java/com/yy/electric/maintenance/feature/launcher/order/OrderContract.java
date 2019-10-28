package com.yy.electric.maintenance.feature.launcher.order;


import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;
import com.yy.electric.maintenance.feature.launcher.order.data.OrderDealData;
import com.yy.electric.maintenance.feature.launcher.order.data.OrderDetailData;
import com.yy.electric.maintenance.feature.launcher.order.data.OverOrderResult;

class OrderContract {

  interface Presenter extends BasePresenter {

    void loadOrderData();

    void dealOrder();

    void loadDetailInfo(OrderDetailRequest request);

    void overOrder(OverOrderRequest request);

  }

  interface View<T> extends BaseView<Presenter> {

    void updateOrderData(T t);

    void updateDetailInfo(OrderDetailData data);

    void updateOrderDealData(OrderDealData data);

    void overOrderResult(OverOrderResult result);

  }

}
