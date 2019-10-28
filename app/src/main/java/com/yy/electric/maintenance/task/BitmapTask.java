package com.yy.electric.maintenance.task;


import android.graphics.Bitmap;

import com.yy.electric.maintenance.ElectricApplication;
import com.yy.electric.maintenance.api.Callback;
import com.yy.electric.maintenance.api.HttpApi;
import com.yy.electric.maintenance.api.ITask;
import com.yy.electric.maintenance.base.IRequest;
import com.yy.electric.maintenance.util.LogUtil;
import com.yy.electric.maintenance.util.ThreadUtil;

public class BitmapTask implements ITask<Bitmap> {

  private static final String TAG = "BitmapTask";
  public static int DEFAULT_LENGTH = 400;

  @Override
  public void startTask(final IRequest request, final Callback<Bitmap> c) {
    ThreadUtil.startRunnable(new Runnable() {
      @Override
      public void run() {
        LogUtil.d(TAG, "startTask() --- loadBitmap begin");
        Bitmap bmp = HttpApi.requestBitmap(request);
        LogUtil.d(TAG, "startTask() --- loadBitmap end, bmp=" + bmp);
        ElectricApplication.sHandler.postData(c, bmp);
      }
    });
  }

  @Override
  public void startTask(String url, Callback<Bitmap> c) {
    //
  }

}
