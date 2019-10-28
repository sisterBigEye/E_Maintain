package com.yy.electric.maintenance.feature.launcher.mime.modify.password;


import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;
import com.yy.electric.maintenance.feature.launcher.mime.modify.password.data.ModifyPasswordData;

class ModifyPasswordContract {

  interface Presenter extends BasePresenter {

    void modifyPassword();

  }

  interface View extends BaseView<Presenter> {

    void updatePassword(ModifyPasswordData data);

  }

}
