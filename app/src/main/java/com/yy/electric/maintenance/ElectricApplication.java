package com.yy.electric.maintenance;

import android.app.Application;

import com.yy.electric.maintenance.util.PxUtil;
import com.yy.electric.maintenance.util.SharedPreferencesUtil;
import com.yy.electric.maintenance.util.ToastUtil;

public class ElectricApplication extends Application {

  private static final String TAG = "ElectricApplication_";
  public static TaskHandler sHandler;

  @Override
  public void onCreate() {
    super.onCreate();
    sHandler = new TaskHandler(getMainLooper());
    ToastUtil.init(this);
    PxUtil.init(this);
    SharedPreferencesUtil.init(this);
  }
}
