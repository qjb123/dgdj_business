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

package com.insthub.O2OMobile.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import net.ezcx.dongguandaojia.business.Activity.D1_OrderActivity;
import net.ezcx.dongguandaojia.business.Activity.SlidingActivity;
import com.insthub.O2OMobile.Adapter.E0_PublishedOrderListAdapter;
import net.ezcx.dongguandaojia.business.Application.MessageConstant;
import com.insthub.O2OMobile.Model.PublishedOrderListModel;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import com.insthub.O2OMobile.Protocol.ORDER_INFO;
import com.insthub.O2OMobile.Protocol.orderlistpublishedResponse;
import net.ezcx.dongguandaojia.business.R;

import org.json.JSONException;
import org.json.JSONObject;

public class E0_PublishedOrdersFragment extends Fragment implements BusinessResponse, IXListViewListener {
    private TextView mUndoneOrderTextView;
    private TextView mDoneOrderTextView;
    private XListView mUnFinishedListView;
    private XListView mFinishedListView;
    private ImageView mEmptyView;

    private E0_PublishedOrderListAdapter mUndoListAdapter;
    private E0_PublishedOrderListAdapter mDoneListAdapter;
    private PublishedOrderListModel mOrderListModel;

   private ImageView mBackButton;

    public static int UNDONE_ORDER = 0;
    public static int DONE_ORDER = 1;
    private int mCurrentState = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View mainView = inflater.inflate(R.layout.e0_published_orders,null);
        mainView.setOnClickListener(null);
        mUndoneOrderTextView = (TextView)mainView.findViewById(R.id.e0_undone_order);
        mDoneOrderTextView = (TextView)mainView.findViewById(R.id.e0_done_order);
        mUnFinishedListView = (XListView)mainView.findViewById(R.id.e0_orderlist_undone);
        mUnFinishedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position - 1 < mOrderListModel.publicUnfinishedOrderList.size()) {
                    ORDER_INFO order_info = mOrderListModel.publicUnfinishedOrderList.get(position - 1);
                    Intent intent = new Intent(getActivity(), D1_OrderActivity.class);
                    intent.putExtra(D1_OrderActivity.ORDER_ID, order_info.id);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }

            }
        });
        mUnFinishedListView.setPullRefreshEnable(true);
        mUnFinishedListView.setPullLoadEnable(true);
        mUnFinishedListView.setXListViewListener(this, 1);

        mFinishedListView = (XListView)mainView.findViewById(R.id.e0_orderlist_done);
        mFinishedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position - 1 < mOrderListModel.publicFinishedOrderList.size()) {
                    ORDER_INFO order_info = mOrderListModel.publicFinishedOrderList.get(position - 1);
                    Intent intent = new Intent(getActivity(), D1_OrderActivity.class);
                    intent.putExtra(D1_OrderActivity.ORDER_ID, order_info.id);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }

            }
        });
        mFinishedListView.setPullRefreshEnable(true);
        mFinishedListView.setPullLoadEnable(true);
        mFinishedListView.setXListViewListener(this, 2);

        mBackButton = (ImageView)mainView.findViewById(R.id.home_menu);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SlidingActivity) getActivity()).showLeft();
            }
        });

        mOrderListModel = new PublishedOrderListModel(getActivity());

        mOrderListModel.addResponseListener(this);
        mOrderListModel.loadCacheUnFinished();

        if(mOrderListModel.publicUnfinishedOrderList!=null&& mOrderListModel.publicUnfinishedOrderList.size()>0){
            mUndoListAdapter = new E0_PublishedOrderListAdapter(getActivity(), mOrderListModel.publicUnfinishedOrderList);
            mUnFinishedListView.setAdapter(mUndoListAdapter);
            mUnFinishedListView.loadMoreHide();
        }

        mOrderListModel.fetchPreUnfinished();

        mUndoneOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmptyView.setVisibility(View.GONE);
                mUnFinishedListView.setVisibility(View.VISIBLE);
                mFinishedListView.setVisibility(View.GONE);
                mCurrentState = UNDONE_ORDER;
                mUndoneOrderTextView.setTextColor(Color.WHITE);
                mUndoneOrderTextView.setBackgroundResource(R.drawable.e0_nav_left_selected);
                mDoneOrderTextView.setTextColor(getResources().getColor(R.color.select_item));
                mDoneOrderTextView.setBackgroundResource(R.drawable.e0_nav_right_normal);
                if (null == mUndoListAdapter) {
                    if (mOrderListModel.publicUnfinishedOrderList != null && mOrderListModel.publicUnfinishedOrderList.size() > 0) {
                        mOrderListModel.loadCacheUnFinished();
                        mUndoListAdapter = new E0_PublishedOrderListAdapter(getActivity(), mOrderListModel.publicUnfinishedOrderList);
                        mUnFinishedListView.setAdapter(mUndoListAdapter);
                        mUnFinishedListView.loadMoreHide();
                    }
                    mOrderListModel.fetchPreUnfinished();
                }
            }
        });

        mDoneOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmptyView.setVisibility(View.GONE);
                mFinishedListView.setVisibility(View.VISIBLE);
                mUnFinishedListView.setVisibility(View.GONE);
                mCurrentState = DONE_ORDER;

                mUndoneOrderTextView.setTextColor(getResources().getColor(R.color.select_item));
                mUndoneOrderTextView.setBackgroundResource(R.drawable.e0_nav_left_normal);
                mDoneOrderTextView.setTextColor(Color.WHITE);
                mDoneOrderTextView.setBackgroundResource(R.drawable.e0_nav_right_selected);

                if (null == mDoneListAdapter) {
                    if (mOrderListModel.publicFinishedOrderList != null && mOrderListModel.publicFinishedOrderList.size() > 0) {
                        mOrderListModel.loadCacheFinished();
                        mDoneListAdapter = new E0_PublishedOrderListAdapter(getActivity(), mOrderListModel.publicFinishedOrderList);
                        mFinishedListView.setAdapter(mDoneListAdapter);
                        mFinishedListView.loadMoreHide();
                    }

                    mOrderListModel.fetchPrefinished();
                }
            }
        });
        mEmptyView = (ImageView)mainView.findViewById(R.id.e0_empty_view);
        mEmptyView.setVisibility(View.GONE);
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentState == UNDONE_ORDER) {
                    mOrderListModel.fetchPreUnfinished();
                } else {
                    mOrderListModel.fetchPrefinished();
                }
            }
        });
        if (!EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().register(this);
        }
        return mainView;
    }
    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        if (EventBus.getDefault().isregister(this))
        {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {
    	mUnFinishedListView.stopRefresh();
    	mUnFinishedListView.stopLoadMore();
    	mFinishedListView.stopRefresh();
    	mFinishedListView.stopLoadMore();
        if(url.endsWith(ApiInterface.ORDERLIST_PUBLISHED)) {
            if (mCurrentState == UNDONE_ORDER) {
                if (jo != null) {
                    mUnFinishedListView.loadMoreShow();
                    mEmptyView.setVisibility(View.GONE);
                    mUnFinishedListView.setVisibility(View.VISIBLE);
                    if (null == mUndoListAdapter) {
                        mUndoListAdapter = new E0_PublishedOrderListAdapter(getActivity(), mOrderListModel.publicUnfinishedOrderList);
                        mUnFinishedListView.setAdapter(mUndoListAdapter);
                    } else {
                        mUndoListAdapter.notifyDataSetChanged();
                    }

                    orderlistpublishedResponse response = new orderlistpublishedResponse();
                    response.fromJson(jo);
                    if (0 == response.more) {
                        mUnFinishedListView.stopLoadMore();
                        mUnFinishedListView.setPullLoadEnable(false);
                    }
                    else
                    {
                        mUnFinishedListView.stopLoadMore();
                        mUnFinishedListView.setPullLoadEnable(true);
                    }
                } else {
                    if (mOrderListModel.publicUnfinishedOrderList.size() == 0) {
                        mEmptyView.setVisibility(View.VISIBLE);
                        mUnFinishedListView.setVisibility(View.GONE);
                    }
                }
            } else {
                if (null != jo)
                {
                    mFinishedListView.loadMoreShow();
                    mEmptyView.setVisibility(View.GONE);
                    mFinishedListView.setVisibility(View.VISIBLE);
                    if (null == mDoneListAdapter)
                    {
                        mDoneListAdapter = new E0_PublishedOrderListAdapter(getActivity(), mOrderListModel.publicFinishedOrderList);
                        mFinishedListView.setAdapter(mDoneListAdapter);
                    }
                    else
                    {
                        mDoneListAdapter.notifyDataSetChanged();
                    }

                    orderlistpublishedResponse response = new orderlistpublishedResponse();
                    response.fromJson(jo);
                    if (0 == response.more)
                    {
                        mFinishedListView.stopLoadMore();
                        mFinishedListView.setPullLoadEnable(false);
                    }
                    else
                    {
                        mFinishedListView.stopLoadMore();
                        mFinishedListView.setPullLoadEnable(true);
                    }
                }
                else
                {
                    if (mOrderListModel.publicFinishedOrderList.size() == 0)
                    {
                        mEmptyView.setVisibility(View.VISIBLE);
                        mFinishedListView.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    public void onRefresh(int id) {
        if (id == 1)
        {
            mOrderListModel.fetchPreUnfinished();
        }
        else
        {
            mOrderListModel.fetchPrefinished();
        }
    }

    @Override
    public void onLoadMore(int id)
    {
        if (id == 1)
        {
            mOrderListModel.fetchNextUnfinished();
        }
        else
        {
            mOrderListModel.fetchNextfinished();
        }

    }

    public void onEvent(Object event) {
        Message message = (Message) event;
        if (message.what == MessageConstant.SIGN_IN_SUCCESS) {
            if (mCurrentState == UNDONE_ORDER)
            {
                mOrderListModel.fetchPreUnfinished();
            }
            else
            {
                mOrderListModel.fetchPrefinished();
            }
        }
    }
}
