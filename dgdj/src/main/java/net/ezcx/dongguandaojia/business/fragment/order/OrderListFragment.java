package net.ezcx.dongguandaojia.business.fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.insthub.O2OMobile.Protocol.userverifycodeResponse;

import net.ezcx.dongguandaojia.business.Model.order.MyOderListModel;
import net.ezcx.dongguandaojia.business.MyActivity.order.OrderInfoActivity;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.adapter.OrderAdapter;
import net.ezcx.dongguandaojia.business.base.BaseFragment;
import net.ezcx.dongguandaojia.business.bean.order.ORDERS;
import net.ezcx.dongguandaojia.business.bean.order.myorder_listResponse;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.utils.TimeCountUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单列表页面
 */
public class OrderListFragment extends BaseFragment implements IXListViewListener, BusinessResponse, View.OnClickListener {
    public static final String FRAGMENT_TYPE_KEY = "fragmentType.key";
    @Bind(R.id.lvMyOrder)
    XListView lvMyOrder;
    private int fragmentType = 0;//判断是第几个fragment
    List<ORDERS> list;
    private OrderAdapter orderAdapter;
    private MyOderListModel myOderListModel;
    int page = 1;//起始页

    public static OrderListFragment newInstance(int fragmentType) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE_KEY, fragmentType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            //根据fragmentType判断是第几个fragment,继而发送不同的网络请求
            this.fragmentType = getArguments().getInt(FRAGMENT_TYPE_KEY);
        }
        //初始化listview的数据
//        ORDERS order = new ORDERS();
//        list = new ArrayList<>();
//        for (int i = 0; i <= 9; i++) {
//            order.appliance_type = "家电维修";
//            order.failure_cause = "冰箱不制冷";
//            order.appointment_time = "2015.2.3 9:00-18:00";
//            order.adress = "深圳龙华新区展涛大厦江湖救急开门";
//            list.add(order);
//        }
        lvMyOrder.setXListViewListener(this, 1);
        lvMyOrder.setPullLoadEnable(true);
//        lvMyOrder.setRefreshTime();
//        orderAdapter = new OrderAdapter(getContext(), list);
//        lvMyOrder.setAdapter(orderAdapter);

        //发送获取我的订单列表网络请求
        myOderListModel = new MyOderListModel(getActivity());
        myOderListModel.addResponseListener(this);
        if (fragmentType == 0) {
            myOderListModel.getMyOrderList(page, 1,0);//全部订单
        } else {
            myOderListModel.getMyOrderList(page, 2,0);//完成订单
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        //listview Item监听
        lvMyOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrderInfoActivity.class);
                intent.putExtra(OrderInfoActivity.ORDER_ID_KEY, list.get(position).id);
                startActivity(intent);
            }
        });
    }

    //下拉刷新的回调
    @Override
    public void onRefresh(int id) {
        page = 1;
        if (fragmentType == 0) {
            myOderListModel.getMyOrderList(page, 1,0);//全部订单
        } else {
            myOderListModel.getMyOrderList(page, 2,0);//完成订单
        }
    }

    //加载回调
    @Override
    public void onLoadMore(int id) {
        page++;
        if (fragmentType == 0) {
            myOderListModel.getMyOrderList(page, 1,1);//全部订单
        } else {
            myOderListModel.getMyOrderList(page, 2,1);//完成订单
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //网络请求返回数据
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.MY_ORDER_LIST)) {
            myorder_listResponse response = new myorder_listResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                if (fragmentType == 0) {
                    list = MyOderListModel.ORDERSList;
                }else  if (fragmentType == 1) {
                    list = MyOderListModel.ORDERSList2;
                }
                orderAdapter = new OrderAdapter(getActivity(), list);
                lvMyOrder.setAdapter(orderAdapter);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
