package com.yy.electric.maintenance.feature.video.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.base.BaseActivity;
import com.yy.electric.maintenance.feature.video.gird.VideoListInfo;
import com.yy.electric.maintenance.util.LogUtil;
import com.yy.electric.maintenance.util.ToastUtil;

public class VideoControlActivity extends BaseActivity implements View.OnClickListener, VideoContract.View {

  private static final String TAG = "VideoControlActivity";
  private WebView mVideoWv;
  private View mControlV;
  private String mRawUrl;
  private String mPlaybackUrl;
  private String mCurrentCameraType;
  private String lastUrl = "";

  private VideoPresenter mPresenter;
  private VideoControlRequest mControlRequest;
  private VideoChannelRequest mChannelRequest;
  private VideoPlaybackRequest mVideoPlaybackRequest;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initView();
    init();
  }

  private void init() {

    VideoListInfo.Row row = VideoDetailManager.getInstance().getVideoDetailInfo();
    if (row == null || TextUtils.isEmpty(row.videourl)) {
      finish();
      return;
    }
    mControlRequest = new VideoControlRequest();
    mControlRequest.speed = 0;
    mControlRequest.url = "Baoding_VideoThing/Services/CameraControll?";

    mChannelRequest = new VideoChannelRequest();
    mChannelRequest.url = "Baoding_VideoThing/Services/SelectVideoDserialAndChannelNoByspecificname?";

    mVideoPlaybackRequest = new VideoPlaybackRequest();
    mVideoPlaybackRequest.url = "Baoding_VideoThing/Services/VideoPlayBack?";
    mPresenter = new VideoPresenter(this, mControlRequest, mChannelRequest, mVideoPlaybackRequest);

    mRawUrl = row.videourl;
    mCurrentCameraType = row.cameratype;
    if (mCurrentCameraType == null || !mCurrentCameraType.equals("球机")) {
      mControlV.setVisibility(View.INVISIBLE);
    } else {
      mControlV.setVisibility(View.VISIBLE);
    }
    mChannelRequest.specificname = row.specificname;
    mPresenter.start();
    playWebView(mRawUrl);
  }

  private void initView() {
    mControlV = findViewById(R.id.ll_control_video);
    mVideoWv = findViewById(R.id.wv_video);

    findViewById(R.id.btn_live_video).setOnClickListener(this);
    findViewById(R.id.btn_playback_video).setOnClickListener(this);

    findViewById(R.id.iv_up_video).setOnClickListener(this);
    findViewById(R.id.iv_down_video).setOnClickListener(this);
    findViewById(R.id.iv_left_video).setOnClickListener(this);
    findViewById(R.id.iv_right_video).setOnClickListener(this);

    findViewById(R.id.btn_enlarge_video).setOnClickListener(this);
    findViewById(R.id.btn_narrow_video).setOnClickListener(this);
  }

  @Override
  protected int layoutId() {
    return R.layout.activity_video_detail;
  }

  @Override
  protected String toolBarTitle() {
    return "";
  }

  @Override
  protected boolean isHideStateBar() {
    return true;
  }

  public void playWebView(String url) {
    LogUtil.d(TAG, "playWebView() url=" + url);
    if (url == null || TextUtils.isEmpty(url)) {
      return;
    }

    mVideoWv.reload();

    mVideoWv.getSettings().setJavaScriptEnabled(true);

    //mVideoWv.getSettings().setPluginsEnabled(true);

    //mVideoWv.getSettings().setPluginState(WebSettings.PluginState.ON);

    mVideoWv.setVisibility(View.VISIBLE);

    mVideoWv.getSettings().setUseWideViewPort(true);

    mVideoWv.loadUrl(url);

    lastUrl = url;

  }

  @Override
  public void onClick(View v) {
    boolean isControl = false;
    switch (v.getId()) {
      case R.id.btn_live_video:
        ToastUtil.toast("直播");
        if (mCurrentCameraType != null && mCurrentCameraType.equals("球机")) {
          mControlV.setVisibility(View.VISIBLE);
        }
        playWebView(mRawUrl);
        break;
      case R.id.btn_playback_video:
        if (TextUtils.isEmpty(mRawUrl)) {
          ToastUtil.toast("请先选择一个播放地址");
          return;
        }
        startActivityForResult(new Intent(this, TimePickActivity.class), TimePickActivity.REQUEST_CODE);
        break;

      case R.id.iv_up_video:
        ToastUtil.toast("上");
        isControl = true;
        mControlRequest.direction = 0;
        break;

      case R.id.iv_down_video:
        ToastUtil.toast("下");
        isControl = true;
        mControlRequest.direction = 1;
        break;

      case R.id.iv_left_video:
        ToastUtil.toast("左");
        isControl = true;
        mControlRequest.direction = 2;
        break;

      case R.id.iv_right_video:
        ToastUtil.toast("右");
        isControl = true;
        mControlRequest.direction = 3;
        break;

      case R.id.btn_enlarge_video:
        ToastUtil.toast("放大");
        isControl = true;
        mControlRequest.direction = 8;
        break;

      case R.id.btn_narrow_video:
        ToastUtil.toast("缩小");
        isControl = true;
        mControlRequest.direction = 9;
        break;

      default:
        break;
    }

    if (isControl) {
      LogUtil.d(TAG, "onClick() mControlRequest=" + mControlRequest);
      mPresenter.controlVideo();
    }

  }

  @Override
  public void channelResult(VideoChannel channel) {
    LogUtil.d(TAG, "channelResult() channel=" + channel);
    if (channel == null) {
      return;
    }
    for (VideoChannel.Row row : channel.rows) {
      if (row == null) {
        continue;
      }
      mControlRequest.channelNo = row.channelNo;
      mControlRequest.deviceSerial = row.deviceSerial;
    }
  }

  @Override
  public void controlVideoResult(VideoControlResult result) {

  }

  @Override
  public void playbackResult(VideoPlaybackResult result) {
    mPlaybackUrl = null;
    LogUtil.d(TAG, "playbackResult() result=" + result);
    if (result == null || result.rows == null) {
      ToastUtil.toast("回放失败，没有获取到回放地址");
      return;
    }
    for (VideoPlaybackResult.Row row : result.rows) {
      if (row == null || TextUtils.isEmpty(row.result)) {
        continue;
      }
      mPlaybackUrl = row.result;
      break;
    }
    if (TextUtils.isEmpty(mPlaybackUrl)) {
      ToastUtil.toast("回放失败，没有获取到回放地址");
      return;
    }
    mControlV.setVisibility(View.INVISIBLE);
    ToastUtil.toast("开始回放");
    playWebView(mPlaybackUrl);
  }

  @Override
  public void setPersonal(VideoContract.Presenter p) {

  }

  public void stop() {
    LogUtil.d(TAG, "stop()");
    if (mVideoWv != null) {
      mVideoWv.stopLoading();
      mVideoWv.destroy();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    stop();

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == TimePickActivity.REQUEST_CODE && resultCode == TimePickActivity.RESULT_CODE) {
      long startTime = data.getLongExtra(TimePickActivity.EXTRA_KEY_START_TIME, 0);
      long endTime = data.getLongExtra(TimePickActivity.EXTRA_KEY_END_TIME, 0);
      mVideoPlaybackRequest.startTime = startTime - 1000 * 100;
      mVideoPlaybackRequest.endTime = endTime;
      mVideoPlaybackRequest.videourl = mRawUrl;
      LogUtil.d(TAG, "onActivityResult() mVideoPlaybackRequest=" + mVideoPlaybackRequest);
      mPresenter.playbackRequest();
      ToastUtil.toast("准备开始回放");
    }

  }
}
