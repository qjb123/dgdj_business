package net.ezcx.dongguandaojia.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.SCHEDULE;

import java.util.List;

/**
 * Created by 安卓 on 2016/3/30.
 */
public class ScheduleAdapter extends BaseAdapter {
    private Context context;
    private List<SCHEDULE> list;
    private LayoutInflater mInflater;

    public ScheduleAdapter(Context context, List<SCHEDULE> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_schedule_item, null);
            viewHolder.ivTip = (ImageView) convertView.findViewById(R.id.ivTip);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.tvAppliance = (TextView) convertView.findViewById(R.id.tvAppliance);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SCHEDULE SCHEDULE = list.get(position);
        viewHolder.tvDate.setText(SCHEDULE.date);
        viewHolder.tvAppliance.setText(SCHEDULE.appliance_type);
        viewHolder.tvAddress.setText(SCHEDULE.address);
        return convertView;
    }

    class ViewHolder {
        ImageView ivTip;//提示圆点
        TextView tvDate;//时间
        TextView tvAppliance;//家电类型
        TextView tvAddress;//地址
    }

}
