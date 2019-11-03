package com.yy.electric.maintenance.feature.video.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yy.electric.maintenance.R;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.Holder> {

  private List<VideoListInfo.Row> mList;
  private int type;
  private OnItemClickListener listener;

  public static final int TYPE_GIRD = 0;
  public static final int TYPE_LIST = 1;

  public VideoListAdapter(OnItemClickListener listener) {
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
            .inflate(R.layout.item_rv_video_list, viewGroup, false);
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
  }

  public void addList(List<VideoListInfo.Row> rows) {
    mList.clear();
    if (rows != null) {
      mList.addAll(rows);
    }
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }

  static class Holder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    Holder(@NonNull View itemView) {
      super(itemView);
      mTitle = itemView.findViewById(R.id.tv_video_list_item);
    }
  }

  public interface OnItemClickListener{

    void onItemClick(VideoListInfo.Row row);
  }
}
