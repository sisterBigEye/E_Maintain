package com.yy.electric.maintenance.task;

import com.yy.electric.maintenance.ElectricApplication;
import com.yy.electric.maintenance.api.Callback;
import com.yy.electric.maintenance.api.HttpApi;
import com.yy.electric.maintenance.api.ITask;
import com.yy.electric.maintenance.base.IRequest;
import com.yy.electric.maintenance.util.LogUtil;
import com.yy.electric.maintenance.util.ThreadUtil;

public class RequestDataTask<T> implements ITask<T> {

  private static final String TAG = "RequestTask";

  private Class<T> clazz;

  public RequestDataTask(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public void startTask(final IRequest request, final Callback<T> c) {
    LogUtil.d(TAG, "startTask()");
    ThreadUtil.startRunnable(new Runnable() {
      @Override
      public void run() {
        T t = HttpApi.requestData(request, clazz);
        ElectricApplication.sHandler.postData(c, t);
      }
    });

  }

  @Override
  public void startTask(final String url, final Callback<T> c) {
    LogUtil.d(TAG, "startTask() url=" + url);
    ThreadUtil.startRunnable(new Runnable() {
      @Override
      public void run() {
        T t = HttpApi.requestData(url, clazz);
        ElectricApplication.sHandler.postData(c, t);
      }
    });
  }

}
