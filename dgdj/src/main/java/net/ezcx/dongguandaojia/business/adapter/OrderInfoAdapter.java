package net.ezcx.dongguandaojia.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.activeandroid.util.Log;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


import net.ezcx.dongguandaojia.business.Application.O2OMobile;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.order.ORDERSINFO;
import net.ezcx.dongguandaojia.business.config.ApiInterface;

import java.util.List;

/**
 * Created by 安卓 on 2016/3/30.
 */
public class OrderInfoAdapter extends BaseAdapter {
    private Context context;
    private List<ORDERSINFO> list;
    private LayoutInflater mInflater;
    protected ImageLoader mImageLoader = ImageLoader.getInstance();

    public OrderInfoAdapter(Context context, List<ORDERSINFO> list) {
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
            convertView = mInflater.inflate(R.layout.list_order_info_item, null);
            viewHolder.tvattr_name = (TextView) convertView.findViewById(R.id.tvattr_name);
            viewHolder.tvattr_value = (TextView) convertView.findViewById(R.id.tvattr_value);
            viewHolder.tvnumber = (TextView) convertView.findViewById(R.id.tvnumber);
            viewHolder.tvprice = (TextView) convertView.findViewById(R.id.tvprice);
           viewHolder.ivimgurl = (ImageView) convertView.findViewById(R.id.ivimgurl);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ORDERSINFO order = list.get(position);
        viewHolder.tvattr_name.setText(order.attr_name);
        viewHolder.tvattr_value.setText(order.attr_value);
        viewHolder.tvnumber.setText(order.number + "");
        viewHolder.tvprice.setText("¥" + order.price);

        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .cacheInMemory(true)//
                .cacheOnDisk(true)//
                .build();
        ImageLoader.getInstance().displayImage(order.imgurl, viewHolder.ivimgurl, options);

        return convertView;
    }

    class ViewHolder {
        TextView tvattr_name;//家电类型
        TextView tvnumber;//数量
        TextView tvprice;//价格
        TextView tvattr_value;//故障原因
        ImageView ivimgurl;//图片
    }

}
