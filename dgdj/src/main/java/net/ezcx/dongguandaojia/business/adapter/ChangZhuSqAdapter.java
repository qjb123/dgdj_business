package net.ezcx.dongguandaojia.business.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import net.ezcx.dongguandaojia.business.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/28 0028.
 */
public class ChangZhuSqAdapter extends BaseAdapter {
    Context context;
    private String images[];
    /**
     * 定义一个选中的集合
     */
    private List<Integer> seleteds;

    public ChangZhuSqAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
        seleteds = new ArrayList<Integer>();
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 添加到选中集合
     * @param position
     */
    public void addSelected(int position) {
        seleteds.add(position);
    }
    /**
     * 移除集合
     * @param position
     */
    public void removeSelected(Integer position) {
        if (seleteds.contains(position)) {
            seleteds.remove(position);
        }
    }
    public boolean isItemSelected(int position) {
        return seleteds.contains(position) ? true : false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context, R.layout.activity_czsq_list_item, null);
        Button button = (Button) convertView.findViewById(R.id.image);
        button.setText(images[position]);
        //button.setBackgroundResource(R.drawable.image_search_button);
        if (seleteds.contains(position)) {
            button.setBackgroundResource(R.drawable.image_search_button_choose);
            button.setTextColor(Color.parseColor("#ffffff"));
        }else{
            button.setBackgroundResource(R.drawable.image_search_button);
            button.setTextColor(Color.parseColor("#9a9a9a"));
        }
        return convertView;
    }
}
