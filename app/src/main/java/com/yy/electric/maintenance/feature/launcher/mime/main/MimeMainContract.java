package com.yy.electric.maintenance.feature.launcher.mime.main;


import android.graphics.Bitmap;

import com.yy.electric.maintenance.base.BasePresenter;
import com.yy.electric.maintenance.base.BaseView;
import com.yy.electric.maintenance.feature.launcher.mime.main.data.MimeInfoData;
import com.yy.electric.maintenance.feature.launcher.mime.main.data.MimeOrderData;
import com.yy.electric.maintenance.feature.launcher.mime.main.data.MimeAlertQuantityData;

public class MimeMainContract {

  public interface Presenter extends BasePresenter {

    void loadOrderTimes();

    void loadWarrantyTimes();

    void loadMimeInfo();

    void loadBmp();

  }

  public interface View extends BaseView<Presenter> {

    void updateMimeOrderData(MimeOrderData data);

    void updateAlertQuantityData(MimeAlertQuantityData data);

    void updateMimeInfo(MimeInfoData data);

    void showBitmap(Bitmap bitmap);

  }

}
