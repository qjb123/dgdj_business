package net.ezcx.dongguandaojia.business.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.datebase.City;


@SuppressLint("DefaultLocale")
public class ItemBeanAdapter extends BaseAdapter implements SectionIndexer {
	private Context ct;
	private List<City> data;

	public ItemBeanAdapter(Context ct, List<City> datas) {
		this.ct = ct;
		this.data = datas;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 *
	 * @param list
	 */
	public void updateListView(List<City> list) {
		this.data = list;
		notifyDataSetChanged();
	}

	public void remove(City user){
		this.data.remove(user);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(ct).inflate(
					R.layout.select_city_item, null);
			viewHolder = new ViewHolder();
			viewHolder.group_title = (TextView) convertView.findViewById(R.id.group_title);
			viewHolder.column_title = (TextView) convertView
					.findViewById(R.id.column_title);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		City cityBean = data.get(position);
		final String name = cityBean.getCity();
		final String avatar = cityBean.getFirstPY();


		viewHolder.column_title.setText(name);

		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.group_title.setVisibility(View.VISIBLE);
			viewHolder.group_title.setText(cityBean.getFirstPY());
		} else {
			viewHolder.group_title.setVisibility(View.GONE);
		}

		return convertView;
	}

	static class ViewHolder {
		TextView group_title;// 首字母提示

		TextView column_title;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return data.get(position).getFirstPY().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@SuppressLint("DefaultLocale")
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = data.get(i).getFirstPY();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section){
				return i;
			}
		}

		return -1;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

}