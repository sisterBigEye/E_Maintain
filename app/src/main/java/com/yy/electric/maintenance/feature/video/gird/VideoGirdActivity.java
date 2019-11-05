package com.yy.electric.maintenance.feature.video.gird;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.base.BaseActivity;
import com.yy.electric.maintenance.feature.video.detail.VideoControlActivity;
import com.yy.electric.maintenance.feature.video.detail.VideoDetailManager;
import com.yy.electric.maintenance.feature.video.list.VideoListActivity;
import com.yy.electric.maintenance.util.LogUtil;
import com.yy.electric.maintenance.util.ToastUtil;

public class VideoGirdActivity extends BaseActivity implements VideoGirdContract.View<VideoListInfo>, VideoGirdAdapter.OnItemClickListener, View.OnClickListener {

  private static final String TAG = "VideoGirdActivity";
  public static final String INTENT_EXTRA_KEY_VIDEO_LIST = "videoList";
  private VideoGirdRequest mRequest;
  private VideoGirdContract.Presenter presenter;
  private VideoGirdAdapter mAdapter;
  private TextView mHeadTv;
  private Intent mDetailIntent;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();
  }

  private void init() {
    mHeadTv = findViewById(R.id.tv_head_video_gird);
    mHeadTv.setOnClickListener(this);
    mAdapter = new VideoGirdAdapter(this);
    RecyclerView view = findViewById(R.id.rv_content_video_gird);
    GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
    view.setLayoutManager(layoutManager);
    view.setAdapter(mAdapter);

    handleIntentData();
  }

  @Override
  protected int layoutId() {
    return R.layout.activity_video_gird;
  }

  @Override
  protected String toolBarTitle() {
    return "视频";
  }

  private void handleIntentData() {
    Intent intent = getIntent();
    String useName = intent.getStringExtra(INTENT_EXTRA_KEY_VIDEO_LIST);
    if (useName == null) {
      LogUtil.d(TAG, "handleIntentData() useName is null");
      ToastUtil.toast("无效的用户");
      finish();
      return;
    }
    mRequest = new VideoGirdRequest();
    mRequest.url = "Baoding_VideoThing/Services/SelectCarmeraVideoByUsername?";
    mRequest.username = useName;
    VideoDetailManager.getInstance().setVideoUserName(useName);
    presenter = new VideoGirdPresenter(this, mRequest);
    presenter.start();

  }

  @Override
  public void videoListResult(VideoListInfo videoListInfo) {
    LogUtil.d(TAG, "videoListResult() videoListInfo=" + videoListInfo);
    if (videoListInfo == null) {
      mHeadTv.setVisibility(View.INVISIBLE);
      return;
    }
    if (videoListInfo.rows.size() > VideoGirdAdapter.MAX_SIZE) {
      mHeadTv.setVisibility(View.VISIBLE);
    } else {
      mHeadTv.setVisibility(View.INVISIBLE);
    }
    VideoDetailManager.getInstance().setVideoInfoList(videoListInfo.rows);
    mAdapter.addList(videoListInfo.rows);
  }

  @Override
  public void setPersonal(VideoGirdContract.Presenter p) {
    this.presenter = p;
  }

  @Override
  protected int getStatusBarColor() {
    return R.color.colorPrimary;
  }

  @Override
  public void onItemClick(VideoListInfo.Row row) {
    LogUtil.d(TAG, "onItemClick() row=" + row);
    if (row == null) {
      return;
    }
    VideoDetailManager.getInstance().setVideoDetailInfo(row);
    if (mDetailIntent == null) {
      mDetailIntent = new Intent(this, VideoControlActivity.class);
    }
    startActivity(mDetailIntent);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_head_video_gird:
        startActivity(new Intent(this, VideoListActivity.class));
        break;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mAdapter != null) {
      mAdapter.destroy();
    }
  }
}
