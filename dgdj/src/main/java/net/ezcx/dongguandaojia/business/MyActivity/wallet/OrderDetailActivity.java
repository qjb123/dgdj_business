package net.ezcx.dongguandaojia.business.MyActivity.wallet;

import android.os.Bundle;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.adapter.OrderDetailAdapter;
import net.ezcx.dongguandaojia.business.adapter.WalletAdapter;
import net.ezcx.dongguandaojia.business.bean.wallet.ORDERDETAIL;
import net.ezcx.dongguandaojia.business.bean.wallet.WALLET;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity implements BusinessResponse, IXListViewListener {

    @Bind(R.id.lvOrderDetail)
    XListView lvOrderDetail;
    List<ORDERDETAIL> list;
    private OrderDetailAdapter orderDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ORDERDETAIL orderdetail = new ORDERDETAIL();
        list = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            orderdetail.commission = "¥50";
            orderdetail.total = "¥550";
            orderdetail.appliance_type = "维修家电";
            orderdetail.money = "¥500";
            list.add(orderdetail);
        }
        lvOrderDetail.setXListViewListener(this, 1);
        lvOrderDetail.setPullLoadEnable(true);
        lvOrderDetail.setRefreshTime();
        orderDetailAdapter = new OrderDetailAdapter(this, list);
        lvOrderDetail.setAdapter(orderDetailAdapter);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }

    @Override
    public void onRefresh(int id) {

    }

    @Override
    public void onLoadMore(int id) {

    }
}
