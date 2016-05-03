package net.ezcx.dongguandaojia.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.wallet.ORDERDETAIL;
import net.ezcx.dongguandaojia.business.bean.wallet.ORDERDETAIL;

import java.util.List;

/**
 * Created by 安卓 on 2016/3/30.
 */
public class OrderDetailAdapter extends BaseAdapter {
    private Context context;
    private List<ORDERDETAIL> list;
    private LayoutInflater mInflater;

    public OrderDetailAdapter(Context context, List<ORDERDETAIL> list) {
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
            convertView = mInflater.inflate(R.layout.list_order_detail_item, null);
            viewHolder.tvCommission = (TextView) convertView.findViewById(R.id.tvCommission);
            viewHolder.tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);
            viewHolder.tvAppliance = (TextView) convertView.findViewById(R.id.tvAppliance);
            viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tvMoney);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ORDERDETAIL orderdetail = list.get(position);
        viewHolder.tvCommission.setText(orderdetail.commission);
        viewHolder.tvAppliance.setText(orderdetail.appliance_type);
        viewHolder.tvTotal.setText(orderdetail.total);
        viewHolder.tvMoney.setText(orderdetail.money);
        return convertView;
    }

    class ViewHolder {
        TextView tvAppliance;//服务项目
        TextView tvCommission;//提成
        TextView tvTotal;//总计
        TextView tvMoney;//金额
    }

}
