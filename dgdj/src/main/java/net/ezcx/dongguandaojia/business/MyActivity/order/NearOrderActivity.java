package net.ezcx.dongguandaojia.business.MyActivity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.adapter.NearOrderAdapter;
import net.ezcx.dongguandaojia.business.bean.order.NEARORDERS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NearOrderActivity extends BaseActivity implements BusinessResponse, IXListViewListener {

    @Bind(R.id.lvNearOrder)
    XListView lvNearOrder;
    List<NEARORDERS> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_order);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    //初始化listview数据
    private void initData() {
        NEARORDERS nearorders = new NEARORDERS();
        list = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            nearorders.name = "王先生";
            nearorders.need_position = "保姆";
            nearorders.money = "4000";
            nearorders.distance = "21km";
            nearorders.time = "25分钟";
            list.add(nearorders);
        }
        lvNearOrder.setXListViewListener(this, 1);
        lvNearOrder.setPullLoadEnable(true);
        lvNearOrder.setRefreshTime();
        NearOrderAdapter adapter = new NearOrderAdapter(this, list);
        lvNearOrder.setAdapter(adapter);
    }

    //初始化监听
    private void initView() {  //listview的item监听
        lvNearOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NearOrderActivity.this, NearOrderInfoActivity.class);
                startActivity(intent);
            }
        });
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
