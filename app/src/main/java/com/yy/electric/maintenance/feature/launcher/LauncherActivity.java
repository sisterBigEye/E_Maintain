package com.yy.electric.maintenance.feature.launcher;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.base.BaseActivity;
import com.yy.electric.maintenance.base.BaseFragment;
import com.yy.electric.maintenance.feature.launcher.grab.GrabFragment;
import com.yy.electric.maintenance.feature.launcher.mime.MimeFragment;
import com.yy.electric.maintenance.feature.launcher.order.WorkOrderFragment;
import com.yy.electric.maintenance.feature.launcher.report.ReportFragment;
import com.yy.electric.maintenance.util.LogUtil;

import java.util.ArrayList;

public class LauncherActivity extends BaseActivity implements View.OnClickListener {

  private static final String TAG = "LauncherActivity";

  private BaseFragment mGrabFragment;
  private BaseFragment mWorkOrderFragment;
  private BaseFragment mReportFragment;
  private BaseFragment mMimeFragment;

  private int mFragmentPage = 0;
  public static final int PAGE_ORDER = 0;
  public static final int PAGE_GRAB = 1;
  public static final int PAGE_REPORT = 2;
  public static final int PAGE_MIME = 3;

  FragmentManager mManager;

  private View mGrabBtn;
  private View mWorkOrderBtn;
  private View mReportBtn;
  private View mMimeBtn;

  private ArrayList<View> mVList;

  private BaseFragment mCurrentFragment;
  private ArrayList<BaseFragment> mFragments;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // 设置 NavigationBar 的颜色
    getWindow().setNavigationBarColor(Color.BLACK);
    init();
    initView();
    LogUtil.d(TAG, "onCreate()" + this);
  }

  private void init() {
    mFragments = new ArrayList<>();

    mWorkOrderFragment = new WorkOrderFragment();
    mFragments.add(mWorkOrderFragment);

    mGrabFragment = new GrabFragment();
    mFragments.add(mGrabFragment);

    mReportFragment = new ReportFragment();
    mFragments.add(mReportFragment);

    mMimeFragment = new MimeFragment();
    mFragments.add(mMimeFragment);

    mCurrentFragment = mWorkOrderFragment;
  }

  private void initView() {
    mVList = new ArrayList<>();

    mWorkOrderBtn = findViewById(R.id.btn_work_order_launcher);
    mWorkOrderBtn.setOnClickListener(this);
    mVList.add(mWorkOrderBtn);

    mGrabBtn = findViewById(R.id.btn_grab_launcher);
    mGrabBtn.setOnClickListener(this);
    mVList.add(mGrabBtn);

    mReportBtn = findViewById(R.id.btn_report_launcher);
    mReportBtn.setOnClickListener(this);
    mVList.add(mReportBtn);

    mMimeBtn = findViewById(R.id.btn_mime_launcher);
    mMimeBtn.setOnClickListener(this);
    mVList.add(mMimeBtn);
    notifyIconState(0);

    mManager = getSupportFragmentManager();
    FragmentTransaction transaction = mManager.beginTransaction();
    for (Fragment fragment : mFragments) {
      transaction.add(R.id.fgt_launcher, fragment).hide(fragment);
    }
    transaction.show(mCurrentFragment);
    transaction.commit();
    mCurrentFragment.startTask();
  }

  @Override
  protected int layoutId() {
    return R.layout.activity_launcher;
  }

  @Override
  protected String toolBarTitle() {
    return "";
  }

  @Override
  public void onClick(View v) {
    BaseFragment fragment = null;
    int selectPage;
    switch (v.getId()) {

      case R.id.btn_work_order_launcher:
        selectPage = PAGE_ORDER;
        break;

      case R.id.btn_grab_launcher:
        selectPage = PAGE_GRAB;
        break;

      case R.id.btn_report_launcher:
        selectPage = PAGE_REPORT;
        break;

      case R.id.btn_mime_launcher:
        selectPage = PAGE_MIME;
        break;

      default:
        selectPage = mFragmentPage;
        break;
    }
    if (selectPage == mFragmentPage) {
      return;
    }
    fragment = mFragments.get(selectPage);
    if (fragment == null) {
      return;
    }
    mFragmentPage = selectPage;
    LogUtil.d(TAG, "onClick() --- mFragmentPage=" + mFragmentPage
            + ", mCurrentFragment=" + mCurrentFragment
            + ", fragment=" + fragment);
    FragmentTransaction transaction = mManager.beginTransaction();
    transaction.hide(mCurrentFragment).show(fragment).commit();
    notifyIconState(mFragmentPage);
    mCurrentFragment.stopTask();
    fragment.startTask();
    mCurrentFragment = fragment;

  }

  private void notifyIconState(int index) {
    for (int i = 0; i < mVList.size(); i++) {
      View v = mVList.get(i);
      if (v == null) {
        continue;
      }
      if (i == index) {
        v.setBackgroundColor(getResources().getColor(R.color.launcher_btn_select));
        continue;
      }
      v.setBackgroundColor(getResources().getColor(R.color.launcher_btn));
    }
  }

  @Override
  protected int getStatusBarColor() {
    return R.color.colorPrimary;
  }
}
