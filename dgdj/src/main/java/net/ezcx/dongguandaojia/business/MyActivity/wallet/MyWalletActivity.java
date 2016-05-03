package net.ezcx.dongguandaojia.business.MyActivity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.adapter.WalletAdapter;
import net.ezcx.dongguandaojia.business.bean.wallet.WALLET;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyWalletActivity extends BaseActivity implements BusinessResponse, IXListViewListener {

    @Bind(R.id.lvMyWallet)
    XListView lvMyWallet;
    List<WALLET> list;
    @Bind(R.id.llGetMoney)
    LinearLayout llGetMoney;
    @Bind(R.id.llRecharge)
    LinearLayout llRecharge;
    private AppCompatTextView tvTitleName;
    private AppCompatTextView tvFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallett);
        ButterKnife.bind(this);
        setActionBar();
        initData();
        initView();
    }

    /**
     * 设置标题栏
     */
    private void setActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            ActionBar.LayoutParams params = new Toolbar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_add_schedule_title, null);
            getSupportActionBar().setCustomView(view, params);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            tvTitleName = (AppCompatTextView) findViewById(R.id.tv_title_name);
            AppCompatImageView ivTitleBack = (AppCompatImageView) findViewById(R.id.ivBack);
            tvFinish = (AppCompatTextView) findViewById(R.id.tvFinish);
            OnTopClickListener clickListener = new OnTopClickListener();
            ivTitleBack.setOnClickListener(clickListener);
            //tvFinish.setOnClickListener(clickListener);
            tvTitleName.setText("我的钱包");
            //tvFinish.setText("订单明细");
        }
    }

    private void initView() {
        //listview的item监听
        lvMyWallet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MyWalletActivity.this, position + "点击", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        WALLET wallet = new WALLET();
        list = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            wallet.date = "2016-02-25";
            wallet.user_name = "Dineal";
            wallet.appliance_type = "维修家电-冰箱";
            wallet.money = "666.66";
            list.add(wallet);
        }
        lvMyWallet.setXListViewListener(this, 1);
        lvMyWallet.setPullLoadEnable(true);
        lvMyWallet.setRefreshTime();
        WalletAdapter walletAdapter = new WalletAdapter(this, list);
        lvMyWallet.setAdapter(walletAdapter);
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

    @OnClick({R.id.llGetMoney, R.id.llRecharge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llGetMoney://提现
                Intent intent = new Intent(MyWalletActivity.this, GetMoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.llRecharge://充值
                Intent intent2 = new Intent(MyWalletActivity.this, RechargeActivity.class);
                startActivity(intent2);
                break;
        }
    }

    /**
     * Toolbar上两个图片按钮的点击事件
     */
    class OnTopClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivBack:
                    finish();
                    break;
                case R.id.tvFinish:
                    Intent intent = new Intent(MyWalletActivity.this, OrderDetailActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }
}
