package net.ezcx.dongguandaojia.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.order.NEARORDERS;

import java.util.List;

/**
 * Created by 安卓 on 2016/3/30.
 */
public class NearOrderAdapter extends BaseAdapter {
    private Context context;
    private List<NEARORDERS> list;
    private LayoutInflater mInflater;

    public NearOrderAdapter(Context context, List<NEARORDERS> list) {
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
            convertView = mInflater.inflate(R.layout.list_near_order_item, null);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvPosition = (TextView) convertView.findViewById(R.id.tvPosition);
            viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tvMoney);
            viewHolder.tvDistance = (TextView) convertView.findViewById(R.id.tvDistance);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NEARORDERS order = list.get(position);
        viewHolder.tvName.setText(order.name);
        viewHolder.tvPosition.setText(order.need_position);
        viewHolder.tvMoney.setText(order.money);
        viewHolder.tvDistance.setText(order.distance);
        viewHolder.tvTime.setText(order.time);
        return convertView;
    }

    class ViewHolder {
        TextView tvName;//姓名
        TextView tvPosition;//职位
        TextView tvMoney;//金额
        TextView tvDistance;//距离
        TextView tvTime;//时间
    }

}
