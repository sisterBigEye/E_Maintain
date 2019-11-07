package com.yy.electric.maintenance.feature.video.gird;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class VideoGirdAdapter extends RecyclerView.Adapter<VideoGirdAdapter.Holder> {

  private static final String TAG = "VideoGirdAdapter";

  private List<VideoListInfo.Row> mList;
  private int type;
  private OnItemClickListener listener;

  public static final int TYPE_GIRD = 0;
  public static final int TYPE_LIST = 1;
  public static final int MAX_SIZE = 4;

  public VideoGirdAdapter(OnItemClickListener listener) {
    mList = new ArrayList<>();
    this.listener = listener;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
    switch (type) {
      case TYPE_LIST:
        break;
      default:
        break;
    }
    View view = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_rv_video_gird, viewGroup, false);
    final Holder holder = new Holder(view);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener != null) {
          listener.onItemClick(mList.get(holder.getAdapterPosition()));
        }
      }
    });
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int i) {
    VideoListInfo.Row row = mList.get(i);
    if (row == null) {
      return;
    }
    holder.mTitle.setText(row.specificname);
    if (TextUtils.isEmpty(row.videourl)) {
      return;
    }
    playWebView(holder.mWeb, row.videourl);
  }

  public void addList(List<VideoListInfo.Row> rows) {
    mList.clear();
    if (rows == null) {
      return;
    }
    for (int i = 0; i < rows.size(); i++) {
      if (i >= MAX_SIZE) {
        break;
      }
      mList.add(rows.get(i));
    }
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  public void destroy() {
  }

  static class Holder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    private WebView mWeb;

    Holder(@NonNull View itemView) {
      super(itemView);
      mTitle = itemView.findViewById(R.id.tv_video_gird_item);
      mWeb = itemView.findViewById(R.id.wv_video_gird_item);
    }
  }

  public interface OnItemClickListener {

    void onItemClick(VideoListInfo.Row row);
  }

  private void playWebView(WebView webView, String url) {
    LogUtil.d(TAG, "playWebView() url=" + url);
    if (url == null || TextUtils.isEmpty(url)) {
      return;
    }
    webView.reload();

    WebSettings settings = webView.getSettings();

    settings.setJavaScriptEnabled(true);

    //settings.setUseWideViewPort(true);

    settings.setLoadWithOverviewMode(true);

    //mVideoWv.getSettings().setPluginsEnabled(true);

    //mVideoWv.getSettings().setPluginState(WebSettings.PluginState.ON);

    webView.setVisibility(View.VISIBLE);

    settings.setUseWideViewPort(true);

    webView.loadUrl(url);


  }
}
