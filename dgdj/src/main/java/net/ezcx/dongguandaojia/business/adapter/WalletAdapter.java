package net.ezcx.dongguandaojia.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.wallet.WALLET;

import java.util.List;

/**
 * Created by 安卓 on 2016/3/30.
 */
public class WalletAdapter extends BaseAdapter {
    private Context context;
    private List<WALLET> list;
    private LayoutInflater mInflater;

    public WalletAdapter(Context context, List<WALLET> list) {
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
            convertView = mInflater.inflate(R.layout.list_wallet_item, null);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.tvAppliance = (TextView) convertView.findViewById(R.id.tvAppliance);
            viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tvMoney);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        WALLET wallet = list.get(position);
        viewHolder.tvDate.setText(wallet.date);
        viewHolder.tvAppliance.setText(wallet.appliance_type);
        viewHolder.tvUserName.setText(wallet.user_name);
        viewHolder.tvMoney.setText(wallet.money);
        return convertView;
    }

    class ViewHolder {
        TextView tvUserName;//用户
        TextView tvDate;//时间
        TextView tvAppliance;//家电类型
        TextView tvMoney;//金额
    }

}
