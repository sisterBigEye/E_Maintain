package com.yy.electric.maintenance.feature.launcher.mime.modify.password;

import com.yy.electric.maintenance.api.Callback;
import com.yy.electric.maintenance.api.ITask;
import com.yy.electric.maintenance.feature.launcher.mime.modify.password.data.ModifyPasswordData;
import com.yy.electric.maintenance.task.RequestDataTask;
import com.yy.electric.maintenance.util.LogUtil;

public class ModifyPasswordPresenter implements ModifyPasswordContract.Presenter {
  private static final String TAG = "ModifyPasswordPresenter";
  private ModifyPasswordContract.View view;
  private ModifyPasswordRequest request;
  private ITask<ModifyPasswordData> task;

  ModifyPasswordPresenter(ModifyPasswordContract.View view, ModifyPasswordRequest request) {
    this.view = view;
    this.request = request;
    this.view.setPersonal(this);
  }

  @Override
  public void start() {
    modifyPassword();
  }

  @Override
  public void modifyPassword() {
    if (view == null || request == null) {
      LogUtil.e(TAG, "modifyPassword() --- view is null");
      return;
    }
    if (task == null) {
      task = new RequestDataTask<>(ModifyPasswordData.class);
    }
    LogUtil.d(TAG, "modifyPassword() --- map = " + request.toString());
    task.startTask(request, new Callback<ModifyPasswordData>() {

      @Override
      public void result(ModifyPasswordData data) {
        view.updatePassword(data);
      }
    });
  }
}
