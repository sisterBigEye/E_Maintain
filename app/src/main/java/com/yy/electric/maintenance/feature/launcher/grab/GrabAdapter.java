package com.yy.electric.maintenance.feature.launcher.grab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.feature.launcher.grab.data.GrabInfoData;
import com.yy.electric.maintenance.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class GrabAdapter extends BaseAdapter {

  public List<GrabInfoData.Row> mInfoList;

  public GrabAdapter() {
    mInfoList = new ArrayList<>();
  }

  public void updateData(List<GrabInfoData.Row> list) {
    mInfoList.clear();
    addDataList(list);
  }

  public void addDataList(List<GrabInfoData.Row> list) {
    mInfoList.clear();
    ;
    if (list == null || list.size() == 0) {
      return;
    }
    mInfoList.addAll(list);
    notifyDataSetChanged();
  }

  public void addData(GrabInfoData.Row info) {
    if (info != null) {
      mInfoList.add(info);
    }
  }

  @Override
  public int getCount() {
    return mInfoList.size();
  }

  @Override
  public GrabInfoData.Row getItem(int position) {
    return mInfoList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Holder holder;
    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_grab, parent, false);
      holder = new Holder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (Holder) convertView.getTag();
    }
    GrabInfoData.Row info = mInfoList.get(position);
    if (info != null) {
      if (info.isSelect) {
        holder.mItem.setBackgroundColor(parent.getContext().getResources().getColor(R.color.gray));
      } else {
        holder.mItem.setBackground(null);
      }
      holder.mTitleTv.setText(info.repairsn);
      String time = "创单时间: ";
      if (info.createtime > 0) {
        time += TimeUtil.getDateTime(info.createtime);
      }
      holder.mTimeTv.setText(time);
      String content = "用户: " + info.usersource + "    设备: " + info.devicesource;
      holder.mContentTv.setText(content);
    }
    return convertView;
  }

  private static class Holder {
    private TextView mTitleTv;
    private TextView mTimeTv;
    private TextView mContentTv;
    private View mItem;

    public Holder(View v) {
      mItem = v.findViewById(R.id.v_item_event_item);
      mTitleTv= v.findViewById(R.id.tv_user_repairsn_item);
      mContentTv = v.findViewById(R.id.tv_user_device_item);
      mTimeTv = v.findViewById(R.id.tv_order_create_time_item);

    }
  }

  public void clear() {
    mInfoList.clear();
    notifyDataSetChanged();
  }
}
