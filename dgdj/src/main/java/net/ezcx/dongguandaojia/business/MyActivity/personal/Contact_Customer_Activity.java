package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;

import net.ezcx.dongguandaojia.business.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact_Customer_Activity extends BaseActivity implements BusinessResponse, View.OnClickListener {

    private LinearLayout ll_erweima;
    private LinearLayout ll_call_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__customer_);
        initView();
    }

    private void initView() {
        ll_erweima = (LinearLayout) findViewById(R.id.ll_erweima);
        ll_call_phone = (LinearLayout) findViewById(R.id.ll_call_phone);
        ll_erweima.setOnClickListener(this);
        ll_call_phone.setOnClickListener(this);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_erweima://二维码
                break;
            case R.id.ll_call_phone://打电话
                break;
        }
    }
}
