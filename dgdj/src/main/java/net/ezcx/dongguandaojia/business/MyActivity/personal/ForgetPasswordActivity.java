package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.O2OMobile.Protocol.userverifycodeResponse;

import net.ezcx.dongguandaojia.business.Model.UserModel;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.user.userforget_passwordResponse;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.utils.KeyBoardUtils;
import net.ezcx.dongguandaojia.business.utils.TimeCountUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 忘记密码界面
 */
public class ForgetPasswordActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {

    private EditText et_mobile;
    private EditText et_verify_code;
    private EditText et_password;
    private EditText et_recommmend_code;
    private String mobile;
    private String verify_code;
    private String password;
    private String recommmend_code;
    private Button btn_get_verify_code_again;
    private Button btn_finish;
    private UserModel userModel;
    private TimeCountUtils mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
    }

    private void initView() {
        btn_get_verify_code_again = (Button) findViewById(R.id.btn_get_verify_code_again);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_get_verify_code_again.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_verify_code = (EditText) findViewById(R.id.et_verify_code);
        et_password = (EditText) findViewById(R.id.et_password);
        et_recommmend_code = (EditText) findViewById(R.id.et_recommmend_code);
        userModel = new UserModel(this);
        userModel.addResponseListener(this);
    }

    @Override
    public void onClick(View v) {
        mobile = et_mobile.getText().toString();
        verify_code = et_verify_code.getText().toString();
        password = et_password.getText().toString();
        recommmend_code = et_recommmend_code.getText().toString();
        switch (v.getId()) {
            case R.id.btn_get_verify_code_again://获取验证码
                if (TextUtils.isEmpty(mobile)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    et_mobile.requestFocus();
                } else if (mobile.length() < 11) {
                    ToastView toast = new ToastView(this, getString(R.string.wrong_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    et_mobile.requestFocus();
                } else {
                    userModel.getVerifyCode(mobile, "0");//发送获取验证码请求
                    et_verify_code.requestFocus();
                }
                break;
            case R.id.btn_finish://完成
                if ("".equals(mobile)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    et_mobile.requestFocus();
                } else if (mobile.length() < 11) {
                    ToastView toast = new ToastView(this, getString(R.string.wrong_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    et_mobile.requestFocus();
                } else if ("".equals(verify_code)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_verify_code));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    et_verify_code.setText("");
                    et_verify_code.requestFocus();
                } else if ("".equals(password)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_password));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    et_password.requestFocus();
                } else if (password.length() < 6 || password.length() > 16) {
                    ToastView toast = new ToastView(this, getString(R.string.new_password_wrong_format_hint));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    et_password.requestFocus();
                } else if (!recommmend_code.equals(password)) {
                    ToastView toast = new ToastView(this, getString(R.string.two_passwords_differ_hint));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    et_recommmend_code.requestFocus();
                } else {
                    userModel.forgetPassword(mobile, password, recommmend_code, verify_code);//发送忘记密码络请求
                    KeyBoardUtils.closeInput(this, et_mobile);
                }
                break;
        }
    }

    //返回的数据
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.USER_VERIFYCODE)) {
            userverifycodeResponse response = new userverifycodeResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                mTime = new TimeCountUtils(this, btn_get_verify_code_again, 60000, 1000);//构造CountDownTimer对象
                mTime.start();
            } else {
                et_mobile.requestFocus();
            }
        } else if (url.endsWith(ApiInterface.USER_CHANGE_PASSWORD)) {
            userforget_passwordResponse response = new userforget_passwordResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                Intent intent = new Intent(this, B0_SigninActivity.class);
                intent.putExtra(B0_SigninActivity.MOBILE, mobile);
                startActivity(intent);
            } else {
                et_mobile.requestFocus();
            }
        }
    }
}
