package com.yy.electric.maintenance.feature.video.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.base.BaseActivity;
import com.yy.electric.maintenance.feature.video.detail.VideoControlActivity;
import com.yy.electric.maintenance.feature.video.detail.VideoDetailManager;
import com.yy.electric.maintenance.feature.video.gird.VideoListInfo;
import com.yy.electric.maintenance.util.LogUtil;
import com.yy.electric.maintenance.util.ToastUtil;

import java.util.List;

public class VideoListActivity extends BaseActivity implements
        VideoListContract.View<VideoListInfo>, VideoListAdapter.OnItemClickListener, View.OnClickListener {

  private static final String TAG = "VideoListActivity";
  private VideoListRequest mRequest;
  private VideoListContract.Presenter presenter;
  private VideoListAdapter mAdapter;
  private Intent mDetailIntent;

  private EditText mSearchEt;
  private Button mSearchBtn;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();
  }

  private void init() {
    mSearchEt = findViewById(R.id.et_search_video_list);
    mSearchBtn = findViewById(R.id.btn_search_video_list);
    mSearchBtn.setOnClickListener(this);

    mAdapter = new VideoListAdapter(this);
    RecyclerView view = findViewById(R.id.rv_content_video_list);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    view.setLayoutManager(layoutManager);
    view.setAdapter(mAdapter);
    List<VideoListInfo.Row> list = VideoDetailManager.getInstance().getVideoInfoList();
    mAdapter.addList(list);

    mRequest = new VideoListRequest();
    mRequest.url = "Baoding_VideoThing/Services/SelectVideoByKeyword?";
    mRequest.username = VideoDetailManager.getInstance().getVideoUserName();
    ;
    mRequest.keyword = "";
    presenter = new VideoListPresenter(this, mRequest);
  }

  @Override
  protected int layoutId() {
    return R.layout.activity_video_list;
  }

  @Override
  protected String toolBarTitle() {
    return "所有视频";
  }

  @Override
  public void videoListResult(VideoListInfo videoListInfo) {
    LogUtil.d(TAG, "videoListResult() videoListInfo=" + videoListInfo);
    if (videoListInfo == null) {
      return;
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
      mDetailIntent = new Intent(this, VideoControlActivity.class);
    }
    startActivity(mDetailIntent);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_search_video_list:
        ToastUtil.toast("开始搜索");
        String keyword = mSearchEt.getText().toString().trim();
        mRequest.keyword = keyword;
        presenter.start();
        break;
    }
  }
}
