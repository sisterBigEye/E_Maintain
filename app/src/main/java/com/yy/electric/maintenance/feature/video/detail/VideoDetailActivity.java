package com.yy.electric.maintenance.feature.video.detail;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.base.BaseActivity;
import com.yy.electric.maintenance.util.LogUtil;
import com.yy.electric.maintenance.util.ToastUtil;

import java.io.IOException;

public class VideoDetailActivity extends BaseActivity implements View.OnClickListener {

  private static final String TAG = "VideoDetailActivity";

  private SurfaceView mVideoView;
  private MediaPlayer mPlayer;
  private SurfaceHolder mHolder;
  private TextView mBackTv;
  private TextView mTitleTv;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();
    initView();
  }

  private void init() {

  }

  private void initView() {
    mTitleTv = findViewById(R.id.tv_title_video_detail);
    mTitleTv.setText(VideoDetailManager.getInstance().getVideoDetailInfo().specificname);

    mBackTv = findViewById(R.id.tv_back_video_detail);
    mBackTv.setOnClickListener(this);

    mVideoView = findViewById(R.id.sv_video_detail);
    SurfaceHolder holder = mVideoView.getHolder();
    holder.addCallback(mCallback);
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

  public void play(String url) {
    LogUtil.d(TAG, "play() url=" + url);
    if (url == null || TextUtils.isEmpty(url)) {
      return;
    }
    if (mPlayer != null) {
      mPlayer.release();
    }
    mPlayer = new MediaPlayer();
    try {
      mPlayer.setDataSource(this, Uri.parse(url));
      mPlayer.prepare();
      mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
          mPlayer.setDisplay(mHolder);
          mPlayer.start();
          //mPlayer.setLooping(true);
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
      LogUtil.e(TAG, "notifyVideoInfo() play error", e);
      ToastUtil.toast("播放失败");
    }
  }


  private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
      mHolder = holder;
      String url = VideoDetailManager.getInstance().getVideoDetailInfo().videourl;
      LogUtil.d(TAG, "surfaceCreated(), url=" + url);
      play(url);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      LogUtil.d(TAG, "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
      LogUtil.d(TAG, "surfaceDestroyed()");
    }
  };

  public void stop() {
    if (mPlayer != null) {
      mPlayer.stop();
      mPlayer.release();
      mPlayer = null;
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (mVideoView != null) {
      mVideoView.setVisibility(View.INVISIBLE);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (mVideoView != null) {
      mVideoView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    stop();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_back_video_detail:
        finish();
        break;
    }
  }
}
