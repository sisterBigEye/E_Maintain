package com.yy.electric.maintenance.feature.launcher.report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yy.electric.maintenance.R;
import com.yy.electric.maintenance.feature.launcher.report.data.ReportDetailData;
import com.yy.electric.maintenance.util.TimeUtil;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends BaseAdapter {

  public List<ReportDetailData.Row> mInfoList;
  private DateFormat mDateFormat = DateFormat.getDateTimeInstance();

  public ReportAdapter() {
    mInfoList = new ArrayList<>();
  }

  public void updateData(List<ReportDetailData.Row> list) {
    mInfoList.clear();
    addDataList(list);
  }

  public void addDataList(List<ReportDetailData.Row> list) {
    mInfoList.clear();
    ;
    if (list == null || list.size() == 0) {
      return;
    }
    mInfoList.addAll(list);
    notifyDataSetChanged();
  }

  public void addData(ReportDetailData.Row info) {
    if (info != null) {
      mInfoList.add(info);
    }
  }

  @Override
  public int getCount() {
    return mInfoList.size();
  }

  @Override
  public ReportDetailData.Row getItem(int position) {
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
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_report, parent, false);
      holder = new Holder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (Holder) convertView.getTag();
    }
    ReportDetailData.Row info = mInfoList.get(position);
    if (info != null) {
      holder.mTitleTv.setText(info.repairsn);
      holder.mUserTv.setText(info.usersource);
      holder.mDeviceNameTv.setText(info.devicesource);
      if (info.createtime <= 0) {
        holder.mCreateTimeTv.setText("");
      } else {
        holder.mCreateTimeTv.setText(TimeUtil.getDateTime(info.createtime));
      }
      if (info.managefinishtime <= 0) {
        holder.mOverTimeTv.setText("");
      } else {
        holder.mOverTimeTv.setText(TimeUtil.getDateTime(info.managefinishtime));
      }
    }

    return convertView;
  }

  private static class Holder {
    private TextView mTitleTv;
    private TextView mUserTv;
    private TextView mDeviceNameTv;
    private TextView mCreateTimeTv;
    private TextView mOverTimeTv;

    public Holder(View v) {
      mTitleTv = v.findViewById(R.id.tv_title_report_item);
      mUserTv = v.findViewById(R.id.tv_user_report_item);
      mDeviceNameTv = v.findViewById(R.id.tv_device_name_report_item);
      mCreateTimeTv = v.findViewById(R.id.tv_create_time_report_item);
      mOverTimeTv = v.findViewById(R.id.tv_over_time_report_item);

    }
  }

  public void clear() {
    mInfoList.clear();
    notifyDataSetChanged();
  }
}
