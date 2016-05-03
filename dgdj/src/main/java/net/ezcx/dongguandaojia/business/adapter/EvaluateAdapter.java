package net.ezcx.dongguandaojia.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.EVALUATE;

import java.util.List;

/**
 * Created by 安卓 on 2016/3/30.
 */
public class EvaluateAdapter extends BaseAdapter {
    private Context context;
    private List<EVALUATE> list;
    private LayoutInflater mInflater;

    public EvaluateAdapter(Context context, List<EVALUATE> list) {
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
            convertView = mInflater.inflate(R.layout.list_evaluate_item, null);
            viewHolder.tvNickname = (TextView) convertView.findViewById(R.id.tvNickname);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.tvEvaluateContent = (TextView) convertView.findViewById(R.id.tvEvaluateContent);
            viewHolder.tvServerDesc = (TextView) convertView.findViewById(R.id.tvServerDesc);
            viewHolder.tvMyReply = (TextView) convertView.findViewById(R.id.tvMyReply);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        EVALUATE evaluate = list.get(position);
        viewHolder.tvNickname.setText(evaluate.nickname);
        viewHolder.tvDate.setText(evaluate.date);
        viewHolder.tvEvaluateContent.setText(evaluate.evaluate_content);
        viewHolder.tvServerDesc.setText(evaluate.server_desc);
        viewHolder.tvMyReply.setText(evaluate.my_reply);

        return convertView;
    }

    class ViewHolder {
        TextView tvNickname;//会员名称
        TextView tvDate;//日期
        TextView tvEvaluateContent;//评价内容
        TextView tvServerDesc;//服务描述
        TextView tvMyReply;//我的回复
    }

}
