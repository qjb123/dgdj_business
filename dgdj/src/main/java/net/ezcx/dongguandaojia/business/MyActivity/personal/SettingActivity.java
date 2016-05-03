package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.MyDialog;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.insthub.O2OMobile.O2OMobileAppConst;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import com.insthub.O2OMobile.SESSION;

import net.ezcx.dongguandaojia.business.Application.MessageConstant;
import net.ezcx.dongguandaojia.business.Model.UserBalanceModel;
import net.ezcx.dongguandaojia.business.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {

    private LinearLayout ll_aboutUs;
    private LinearLayout llFeedback;
    private LinearLayout ll_contact_customer;
    private LinearLayout ll_version_update;
    private Button btn_logout;
    private UserBalanceModel mUserBalance;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        mShared = getSharedPreferences(O2OMobileAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        mUserBalance = new UserBalanceModel(this);
        mUserBalance.addResponseListener(this);
    }

    private void initView() {
        ll_aboutUs = (LinearLayout) findViewById(R.id.ll_aboutUs);
        llFeedback = (LinearLayout) findViewById(R.id.llFeedback);
        ll_contact_customer = (LinearLayout) findViewById(R.id.ll_contact_customer);
        ll_version_update = (LinearLayout) findViewById(R.id.ll_version_update);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        ll_aboutUs.setOnClickListener(this);
        llFeedback.setOnClickListener(this);
        ll_contact_customer.setOnClickListener(this);
        ll_version_update.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_aboutUs://关于到家
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.llFeedback://意见反馈
                Intent intent2 = new Intent(this, FeedbackActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_contact_customer://联系客服
                Intent intent3 = new Intent(this, Contact_Customer_Activity.class);
                startActivity(intent3);
                break;
            case R.id.ll_version_update://版本更新

                break;
            case R.id.btn_logout://登出
                final MyDialog myDialog = new MyDialog(SettingActivity.this, "是否注销该账号?");
                myDialog.show();
                myDialog.positive.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mUserBalance.signout();//发送登出账号的请求
                        myDialog.dismiss();
                    }
                });
                myDialog.negative.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }

                });
                break;
        }
    }

    /**
     * 网络请求返回数据
     *
     * @param url
     * @param jo
     * @param status
     * @throws JSONException
     */
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.USER_SIGNOUT)) {
            mEditor.putBoolean("isLogin", false);
            mEditor.putString("user", "");
            mEditor.putInt("uid", 0);
            mEditor.putString("sid", "");
            mEditor.commit();
            SESSION.getInstance().uid = mShared.getInt("uid", 0);
            SESSION.getInstance().sid = mShared.getString("sid", "");
            ToastView toast = new ToastView(SettingActivity.this, getString(R.string.logout_success));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Message msg = new Message();
            msg.what = MessageConstant.LOGINOUT;
            EventBus.getDefault().post(msg);
            Intent intent = new Intent(SettingActivity.this, B0_SigninActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
