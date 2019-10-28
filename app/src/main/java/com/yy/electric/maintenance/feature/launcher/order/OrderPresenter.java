package com.yy.electric.maintenance.feature.launcher.order;

import com.yy.electric.maintenance.api.Callback;
import com.yy.electric.maintenance.api.ITask;
import com.yy.electric.maintenance.feature.launcher.order.data.OrderData;
import com.yy.electric.maintenance.feature.launcher.order.data.OrderDealData;
import com.yy.electric.maintenance.feature.launcher.order.data.OrderDetailData;
import com.yy.electric.maintenance.feature.launcher.order.data.OverOrderResult;
import com.yy.electric.maintenance.task.RequestDataTask;
import com.yy.electric.maintenance.util.LogUtil;

public class OrderPresenter implements OrderContract.Presenter {
  private static final String TAG = "GrabPresenter";
  private OrderContract.View<OrderData> view;
  private OrderRequest orderRequest;
  private OrderDealRequest dealRequest;
  private OverOrderRequest mOverOrderRequest;

  private ITask<OrderData> task;
  private ITask<OrderDealData> dealTask;
  private ITask<OverOrderResult> overOrderTask;

  OrderPresenter(OrderContract.View<OrderData> view, OrderRequest orderRequest,
                 OrderDealRequest dealRequest,
                 OverOrderRequest overOrderRequest) {
    this.view = view;
    this.orderRequest = orderRequest;
    this.dealRequest = dealRequest;
    mOverOrderRequest = overOrderRequest;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    loadOrderData();
  }

  @Override
  public void loadOrderData() {
    if (view == null || orderRequest == null) {
      LogUtil.e(TAG, "loadOrderData() --- view is null");
      return;
    }
    if (task == null) {
      task = new RequestDataTask<>(OrderData.class);
    }
    LogUtil.d(TAG, "loadOrderData() --- orderRequest = " + orderRequest.toString());
    task.startTask(orderRequest, new Callback<OrderData>() {

      @Override
      public void result(OrderData data) {
        view.updateOrderData(data);
      }
    });
  }

  @Override
  public void dealOrder() {

    if (view == null || dealRequest == null) {
      LogUtil.e(TAG, "dealOrder() --- view is null");
      return;
    }
    if (dealTask == null) {
      dealTask = new RequestDataTask<>(OrderDealData.class);
    }
    LogUtil.d(TAG, "dealOrder() --- map = " + dealRequest.toString());
    dealTask.startTask(dealRequest, new Callback<OrderDealData>() {

      @Override
      public void result(OrderDealData data) {
        view.updateOrderDealData(data);
      }
    });
  }

  @Override
  public void loadDetailInfo(OrderDetailRequest request) {
    if (task == null) {
      task = new RequestDataTask<>(OrderData.class);
    }
    LogUtil.d(TAG, "loadDetailInfo() --- map = " + request.toString());
    new RequestDataTask<>
            (OrderDetailData.class).startTask(request, new Callback<OrderDetailData>() {
      @Override
      public void result(OrderDetailData orderDetailData) {
        view.updateDetailInfo(orderDetailData);
      }
    });
  }

  @Override
  public void overOrder(OverOrderRequest request) {
    if (overOrderTask == null) {
      overOrderTask = new RequestDataTask<>(OverOrderResult.class);
    }
    LogUtil.d(TAG, "overOrder() --- map = " + request.toString());
    new RequestDataTask<>
            (OverOrderResult.class).startTask(request, new Callback<OverOrderResult>() {
      @Override
      public void result(OverOrderResult result) {
        view.overOrderResult(result);
      }
    });
  }
}
