package com.yy.electric.maintenance.feature.launcher.mime.modify.info;


import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;
import com.yy.electric.maintenance.feature.launcher.mime.modify.info.data.ModifyAvatarResult;
import com.yy.electric.maintenance.feature.launcher.mime.modify.info.data.ModifyInfoData;

class ModifyInfoContract {

  interface Presenter extends BasePresenter {

    void modifyDetailInfo();

    void modifyAvatar();

  }

  interface View extends BaseView<Presenter> {

    void updateModifyInfo(ModifyInfoData data);

    void updateAvatarResult(ModifyAvatarResult result);

  }

}
