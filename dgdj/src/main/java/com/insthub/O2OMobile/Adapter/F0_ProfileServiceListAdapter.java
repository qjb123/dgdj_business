//
//       _/_/_/                      _/            _/_/_/_/_/
//    _/          _/_/      _/_/    _/  _/              _/      _/_/      _/_/
//   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/              _/      _/    _/  _/    _/
//  _/    _/  _/        _/        _/  _/          _/        _/    _/  _/    _/
//   _/_/_/    _/_/_/    _/_/_/  _/    _/      _/_/_/_/_/    _/_/      _/_/
//
//
//  Copyright (c) 2015-2016, Geek Zoo Studio
//  http://www.geek-zoo.com
//
//
//  Permission is hereby granted, free of charge, to any person obtaining a
//  copy of this software and associated documentation files (the "Software"),
//  to deal in the Software without restriction, including without limitation
//  the rights to use, copy, modify, merge, publish, distribute, sublicense,
//  and/or sell copies of the Software, and to permit persons to whom the
//  Software is furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//  IN THE SOFTWARE.
//

package com.insthub.O2OMobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import net.ezcx.dongguandaojia.business.Application.O2OMobile;
import com.insthub.O2OMobile.Protocol.MY_SERVICE;
import net.ezcx.dongguandaojia.business.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class F0_ProfileServiceListAdapter extends BaseAdapter{
    private Context mContext;
    public List<MY_SERVICE> mList;
    private LayoutInflater mInflater;

    public F0_ProfileServiceListAdapter(Context context, List<MY_SERVICE> list) {

        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.f0_profile_service_list_item, null);
            holder.title= (TextView) convertView.findViewById(R.id.service_title);
            holder.icon= (ImageView) convertView.findViewById(R.id.service_icon);
            holder.price= (TextView) convertView.findViewById(R.id.service_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MY_SERVICE service = mList.get(position);
        holder.title.setText(service.service_type.title);
        ImageLoader.getInstance().displayImage(service.service_type.icon, holder.icon, O2OMobile.options_home);
        if(service.price!=null&&!"".equals(service.price)){
            holder.price.setText(Utils.formatBalance(service.price)+mContext.getString(R.string.yuan_start));
        }
        return convertView;
    }

    class ViewHolder {
        TextView title;
        ImageView icon;
        TextView price;

    }
}
