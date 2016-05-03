package net.ezcx.dongguandaojia.business.MyActivity.order;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;

import net.ezcx.dongguandaojia.business.Model.order.MyOderListModel;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.adapter.OrderInfoAdapter;
import net.ezcx.dongguandaojia.business.bean.order.ORDERSINFO;
import net.ezcx.dongguandaojia.business.bean.order.myorder_info_real_Response;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.view.NestListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderInfoActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {

    @Bind(R.id.tvaddress)
    TextView tvaddress;
    @Bind(R.id.tvconsignee)
    TextView tvconsignee;
    @Bind(R.id.tvmobile)
    TextView tvmobile;
    @Bind(R.id.tvservice_name)
    TextView tvserviceName;
    @Bind(R.id.tvappointment_time)
    TextView tvappointmentTime;
    @Bind(R.id.tvtotal)
    TextView tvtotal;
    @Bind(R.id.tvpay_status)
    TextView tvpayStatus;
    @Bind(R.id.nlv_order_info)
    NestListView nlvOrderInfo;
    @Bind(R.id.btn_start_service)
    Button btnStartService;
    private MyOderListModel myOderListModel;
    public static final String ORDER_ID_KEY = "orderId.key";
    List<ORDERSINFO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        ButterKnife.bind(this);
        initData();
    }

    //初始化数据
    private void initData() {
        int order_id = getIntent().getIntExtra(ORDER_ID_KEY, 0);
        myOderListModel = new MyOderListModel(this);
        myOderListModel.addResponseListener(this);
        myOderListModel.getOrderInfo(order_id);//发送获取订单详情的网络请求
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.MY_ORDER_INFO)) {
            myorder_info_real_Response response = new myorder_info_real_Response();
            response.fromJson(jo);
            if (response.succeed == 1) {
                list = response.order_details.orderservice;
                OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(this, list);
                nlvOrderInfo.setAdapter(orderInfoAdapter);
                tvmobile.setText(response.order_details.mobile);
                tvappointmentTime.setText(response.order_details.appointment_time);
                tvaddress.setText(response.order_details.address);
                tvserviceName.setText(response.order_details.service_name);
                tvconsignee.setText(response.order_details.consignee);
                tvtotal.setText(response.order_details.total);

                if (response.order_details.order_status == 0) {
                    btnStartService.setText("客户发单");
                    tvpayStatus.setText("未支付");
                }  if (response.order_details.order_status == 1) {
                    btnStartService.setText("已确认接单");
                    tvpayStatus.setText("未支付");
                }  if (response.order_details.order_status == 2) {
                    btnStartService.setText("工作完成");
                    tvpayStatus.setText("未支付");
                    btnStartService.setBackgroundColor(Color.parseColor("#9a9a9a"));
                    btnStartService.setFocusable(false);
                }  if (response.order_details.order_status == 3) {
                    btnStartService.setText("已付款 ");
                    tvpayStatus.setText("已支付");
                    btnStartService.setBackgroundColor(Color.parseColor("#9a9a9a"));
                    btnStartService.setFocusable(false);
                }  if (response.order_details.order_status == 4) {
                    btnStartService.setText("付款已确认");
                    tvpayStatus.setText("已支付");
                    btnStartService.setBackgroundColor(Color.parseColor("#9a9a9a"));
                    btnStartService.setFocusable(false);
                }  if (response.order_details.order_status == 5) {
                    btnStartService.setText("订单结束");
                    tvpayStatus.setText("已支付");
                    btnStartService.setBackgroundColor(Color.parseColor("#9a9a9a"));
                    btnStartService.setFocusable(false);
                }  if (response.order_details.order_status == 6) {
                    btnStartService.setText("订单取消");
                    tvpayStatus.setText("未支付");
                    btnStartService.setBackgroundColor(Color.parseColor("#9a9a9a"));
                    btnStartService.setFocusable(false);
                } else {
                    btnStartService.setText("客户发单");
                    tvpayStatus.setText("未支付");
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
