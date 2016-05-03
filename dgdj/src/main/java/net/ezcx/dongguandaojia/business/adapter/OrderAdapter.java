package net.ezcx.dongguandaojia.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.ezcx.dongguandaojia.business.Application.O2OMobile;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.order.ORDERS;
import net.ezcx.dongguandaojia.business.config.ApiInterface;

import java.util.List;

/**
 * Created by 安卓 on 2016/3/30.
 */
public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<ORDERS> list;
    private LayoutInflater mInflater;
    protected ImageLoader mImageLoader = ImageLoader.getInstance();

    public OrderAdapter(Context context, List<ORDERS> list) {
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
            convertView = mInflater.inflate(R.layout.list_order_item, null);
            viewHolder.tvorder_sn = (TextView) convertView.findViewById(R.id.tvorder_sn);
            viewHolder.tvservice_name = (TextView) convertView.findViewById(R.id.tvservice_name);
            viewHolder.tvappointment_time = (TextView) convertView.findViewById(R.id.tvappointment_time);
            viewHolder.tvaddress = (TextView) convertView.findViewById(R.id.tvaddress);
            viewHolder.tvorder_status = (TextView) convertView.findViewById(R.id.tvorder_status);
            viewHolder.ivimgurl = (ImageView) convertView.findViewById(R.id.ivimgurl);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ORDERS order = list.get(position);
        viewHolder.tvservice_name.setText(order.service_name);
        viewHolder.tvorder_sn.setText(order.order_sn);
        viewHolder.tvappointment_time.setText(order.appointment_time);
        viewHolder.tvaddress.setText(order.address);

        DisplayImageOptions options = new DisplayImageOptions.Builder()//
                .cacheInMemory(true)//
                .cacheOnDisk(true)//
                .build();
        ImageLoader.getInstance().displayImage(ApiInterface.HOST_URL+order.imgurl, viewHolder.ivimgurl, options);
        if (order.order_status == 0) {
            viewHolder.tvorder_status.setText("客户发单");
        }  if (order.order_status == 1) {
            viewHolder.tvorder_status.setText("已确认接单");
        }  if (order.order_status == 2) {
            viewHolder.tvorder_status.setText("工作完成");
        }  if (order.order_status == 3) {
            viewHolder.tvorder_status.setText("已付款 ");
        }  if (order.order_status == 4) {
            viewHolder.tvorder_status.setText("付款已确认");
        }  if (order.order_status == 5) {
            viewHolder.tvorder_status.setText("订单结束");
        }  if (order.order_status == 6) {
            viewHolder.tvorder_status.setText("订单取消");
        } else {
            viewHolder.tvorder_status.setText("客户发单");
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvorder_sn;//订单编号
        TextView tvservice_name;//服务类型
//        TextView tvfailure_cause;//故障原因
        TextView tvappointment_time;//服务时间
        TextView tvaddress;//服务地址
        TextView tvorder_status;//服务状态
        ImageView ivimgurl;//图片
    }

}
