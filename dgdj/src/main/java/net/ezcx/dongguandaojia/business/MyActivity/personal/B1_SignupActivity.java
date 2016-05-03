package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;

import net.ezcx.dongguandaojia.business.Application.MessageConstant;

import net.ezcx.dongguandaojia.business.Model.UserModel;

import net.ezcx.dongguandaojia.business.config.ApiInterface;

import net.ezcx.dongguandaojia.business.bean.user.usersignupResponse;

import com.insthub.O2OMobile.Protocol.userverifycodeResponse;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.utils.KeyBoardUtils;
import net.ezcx.dongguandaojia.business.utils.PreferenceUtil;
import net.ezcx.dongguandaojia.business.utils.TimeCountUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 注册页面
 */
public class B1_SignupActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {
    private EditText mMobile;//手机号
    private EditText mVerifyCode;//验证码
    private EditText et_password;//密码
    private EditText et_invite_code;//邀请码
    private Button mGetVerifyCodeAgain;//获取验证码
    private Button btn_finish;//完成注册
    private UserModel mUserModel;
    private TimeCountUtils mTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.b1_signup_verify);
        initView();
    }

    private void initView() {
        mMobile = (EditText) findViewById(R.id.et_mobile);
        mVerifyCode = (EditText) findViewById(R.id.et_verify_code);
        et_password = (EditText) findViewById(R.id.et_password);
        et_invite_code = (EditText) findViewById(R.id.et_invite_code);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        mGetVerifyCodeAgain = (Button) findViewById(R.id.btn_get_verify_code_again);
        btn_finish.setOnClickListener(this);
        mGetVerifyCodeAgain.setOnClickListener(this);
        mUserModel = new UserModel(this);
        mUserModel.addResponseListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        String mobile = mMobile.getText().toString();
        String verify_code = mVerifyCode.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String invite_code = et_invite_code.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_finish://完成注册按钮
                if ("".equals(mobile)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mMobile.requestFocus();
                } else if (mobile.length() < 11) {
                    ToastView toast = new ToastView(this, getString(R.string.wrong_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mMobile.requestFocus();
                } else if ("".equals(verify_code)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_verify_code));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mVerifyCode.setText("");
                    mVerifyCode.requestFocus();
                } else if ("".equals(password)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_password));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    et_password.requestFocus();
                } else {
                    mUserModel.signup(mobile, password, verify_code, invite_code);//发送注册网络请求
                    KeyBoardUtils.closeInput(this, mMobile);
                }
                break;
            case R.id.btn_get_verify_code_again://获取验证码
                if ("".equals(mobile)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mMobile.requestFocus();
                } else if (mobile.length() < 11) {
                    ToastView toast = new ToastView(this, getString(R.string.wrong_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mMobile.requestFocus();
                } else {
                    mUserModel.getVerifyCode(mobile, "1");//发送获取验证码请求
                    mVerifyCode.requestFocus();
                }

                break;
        }

    }

    /**
     * 数据返回
     *
     * @param url
     * @param jo
     * @param status
     * @throws JSONException
     */
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.USER_VERIFYCODE)) {
            userverifycodeResponse response = new userverifycodeResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                mTime = new TimeCountUtils(this, mGetVerifyCodeAgain, 60000, 1000);//构造CountDownTimer对象
                mTime.start();
            } else {
                mMobile.requestFocus();
            }
        } else if (url.endsWith(ApiInterface.USER_SIGNUP)) {
            usersignupResponse response = new usersignupResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {

                PreferenceUtil.setEdit("mobile_phone", response.user.mobile_phone,B1_SignupActivity.this);

                Intent intent = new Intent(this, B0_SigninActivity.class);
                intent.putExtra(B0_SigninActivity.MOBILE, mMobile.getText().toString());
                startActivity(intent);
            } else {
                mMobile.requestFocus();
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(Object event) {
        Message message = (Message) event;
        if (message.what == MessageConstant.SIGN_UP_SUCCESS) {
            finish();
        }
    }
}
