package com.yy.electric.maintenance.feature.video.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.base.BaseActivity;
import com.yy.electric.maintenance.feature.video.detail.VideoDetailActivity;
import com.yy.electric.maintenance.feature.video.detail.VideoDetailManager;
import com.yy.electric.maintenance.util.LogUtil;
import com.yy.electric.maintenance.util.ToastUtil;

public class VideoGirdActivity extends BaseActivity implements VideoListContract.View<VideoListInfo>, VideoListAdapter.OnItemClickListener {

  private static final String TAG = "VideoGirdActivity";
  public static final String INTENT_EXTRA_KEY_VIDEO_LIST = "videoList";
  private VideoListRequest mRequest;
  private VideoListContract.Presenter presenter;
  private VideoListAdapter mAdapter;
  private TextView mHeadTv;
  private Intent mDetailIntent;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();
  }

  private void init() {
    mHeadTv = findViewById(R.id.tv_head_video_list);
    mAdapter = new VideoListAdapter(this);
    RecyclerView view = findViewById(R.id.rv_content_video_list);
    GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
    view.setLayoutManager(layoutManager);
    view.setAdapter(mAdapter);

    handleIntentData();
  }

  @Override
  protected int layoutId() {
    return R.layout.activity_video_list;
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
    mRequest = new VideoListRequest();
    mRequest.url = "Baoding_VideoThing/Services/SelectCarmeraVideoByUsername?";
    mRequest.username = useName;
    presenter = new VideoListPresenter(this, mRequest);
    presenter.start();

  }

  @Override
  public void videoListResult(VideoListInfo videoListInfo) {
    LogUtil.d(TAG, "videoListResult() videoListInfo=" + videoListInfo);
    if (videoListInfo == null) {
      mHeadTv.setVisibility(View.INVISIBLE);
      return;
    }
    if (videoListInfo.rows.size() > 4) {
      mHeadTv.setVisibility(View.VISIBLE);
    } else {
      mHeadTv.setVisibility(View.INVISIBLE);
    }
    mAdapter.addList(videoListInfo.rows);
  }

  @Override
  public void setPersonal(VideoListContract.Presenter p) {
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
      mDetailIntent = new Intent(this, VideoDetailActivity.class);
    }
    startActivity(mDetailIntent);
  }
}
