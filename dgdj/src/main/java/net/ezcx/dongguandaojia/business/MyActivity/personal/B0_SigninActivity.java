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

package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.activeandroid.util.Log;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;

import net.ezcx.dongguandaojia.business.Activity.SlidingActivity;
import net.ezcx.dongguandaojia.business.Application.MessageConstant;

import net.ezcx.dongguandaojia.business.Model.UserModel;
import net.ezcx.dongguandaojia.business.MyActivity.HomeActivity;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.bean.user.usersigninResponse;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.utils.KeyBoardUtils;
import net.ezcx.dongguandaojia.business.utils.PreferenceUtil;

import com.insthub.O2OMobile.SESSION;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 登录页面
 */
public class B0_SigninActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {
    public static final String MOBILE = "MOBILE";
    private static final String TAG = "B0_SigninActivity";
    private EditText mMobile;
    private EditText mPassword;
    private TextView mSignup;
    private Button mLogin;
    private UserModel mUserModel;
    private TextView tv_forgetPassw;
    private ImageView iv_qq;
    private ImageView iv_weibo;
    private ImageView iv_weixin;
    private UMShareAPI mShareAPI;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b0_signin);
        initView();
        EventBus.getDefault().register(this);
        mShareAPI = UMShareAPI.get(this);//初始化shareAPI
    }

    private void initView() {
        iv_qq = (ImageView) findViewById(R.id.iv_qq);
        iv_weixin = (ImageView) findViewById(R.id.iv_weixin);
        iv_weibo = (ImageView) findViewById(R.id.iv_weibo);
        mMobile = (EditText) findViewById(R.id.et_mobile);
        mPassword = (EditText) findViewById(R.id.et_password);
        mSignup = (TextView) findViewById(R.id.tv_signup);
        tv_forgetPassw = (TextView) findViewById(R.id.tv_forgetPassw);
//        mSignup.getPaint().setFlTextViewags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        mMobile = (EditText) findViewById(R.id.et_mobile);
        mLogin = (Button) findViewById(R.id.btn_login);
        mSignup.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        tv_forgetPassw.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
        iv_weibo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String mobile = mMobile.getText().toString();
        String password = mPassword.getText().toString();

        switch (v.getId()) {
            case R.id.tv_signup://跳转至注册页面
                Intent intent = new Intent(this, B1_SignupActivity.class);
                startActivity(intent);
                KeyBoardUtils.closeInput(this, mMobile);
                break;
            case R.id.tv_forgetPassw://跳转至忘记密码页面
                Intent intent2 = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intent2);
                KeyBoardUtils.closeInput(this, mMobile);
                break;
            case R.id.iv_qq://跳转至QQ登录
                doOauth(SHARE_MEDIA.QQ);
                break;
            case R.id.iv_weibo://跳转至微博登录
                doOauth(SHARE_MEDIA.SINA);
                break;
            case R.id.iv_weixin://跳转至微信登录
                doOauth(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.btn_login://登录
                if ("".equals(mobile)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mMobile.requestFocus();
                } else if ("".equals(password)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_password));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mPassword.requestFocus();
                } else if (mobile.length() < 11) {
                    ToastView toast = new ToastView(this, getString(R.string.wrong_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mMobile.requestFocus();
                } else if (password.length() < 6 || password.length() > 20) {
                    ToastView toast = new ToastView(this, getString(R.string.please_enter_correct_password_format));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mPassword.requestFocus();
                } else {
                    KeyBoardUtils.closeInput(this, mPassword);
                    mUserModel = new UserModel(B0_SigninActivity.this);
                    mUserModel.addResponseListener(this);
                    mUserModel.login(mobile, password);//登录请求
                }
                break;
        }
    }

    /**
     * 第三方登录授权
     *
     * @param platform
     */
    private void doOauth(SHARE_MEDIA platform) {
        UMAuthListener umAuthListener = new UMAuthListener() {

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                if (platform.equals(SHARE_MEDIA.QQ)) {
                    String uid = data.get("uid");
                    String access_token = data.get("access_token");
                    userModel = new UserModel(B0_SigninActivity.this);
                    userModel.addResponseListener(B0_SigninActivity.this);
                    userModel.threeLogin(uid, access_token, 1);//第三方登录网络请求
                    Toast.makeText(B0_SigninActivity.this, "QQ登录", Toast.LENGTH_SHORT).show();
                } else if (platform.equals(SHARE_MEDIA.SINA)) {
                    String uid = data.get("uid");
                    String access_token = data.get("access_token");
                    userModel = new UserModel(B0_SigninActivity.this);
                    userModel.addResponseListener(B0_SigninActivity.this);
                    userModel.threeLogin(uid, access_token, 2);
                    Toast.makeText(B0_SigninActivity.this, "微博登录", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
            }
        };
        mShareAPI.doOauthVerify(this, platform, umAuthListener);

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
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        if (url.endsWith(ApiInterface.USER_SIGNIN)) {
            usersigninResponse usersigninResponse = new usersigninResponse();
            usersigninResponse.fromJson(jo);
            if (usersigninResponse.succeed == 1) {
                PreferenceUtil.setEdit("comment_count", usersigninResponse.user.comment_count, B0_SigninActivity.this);
                PreferenceUtil.setEdit("id", usersigninResponse.user.id+"", B0_SigninActivity.this);
                PreferenceUtil.setEdit("thumb", usersigninResponse.user.avatar.thumb, B0_SigninActivity.this);
                PreferenceUtil.setEdit("mobile_phone", usersigninResponse.user.mobile_phone, B0_SigninActivity.this);
                PreferenceUtil.setEdit("user_group", usersigninResponse.user.user_group+"", B0_SigninActivity.this);
                PreferenceUtil.setEdit("nickname", usersigninResponse.user.nickname, B0_SigninActivity.this);
                PreferenceUtil.setEdit("age", usersigninResponse.user.age, B0_SigninActivity.this);
                PreferenceUtil.setEdit("comment_goodrate", usersigninResponse.user.comment_goodrate, B0_SigninActivity.this);
                PreferenceUtil.setEdit("gender", usersigninResponse.user.gender+"", B0_SigninActivity.this);
                PreferenceUtil.setEdit("service_area", usersigninResponse.user.service_area, B0_SigninActivity.this);
                PreferenceUtil.setEdit("origin", usersigninResponse.user.origin, B0_SigninActivity.this);
                PreferenceUtil.setEdit("service_time", usersigninResponse.user.service_time, B0_SigninActivity.this);
                PreferenceUtil.setEdit("skill", usersigninResponse.user.skill, B0_SigninActivity.this);
                PreferenceUtil.setEdit("address", usersigninResponse.user.address, B0_SigninActivity.this);
                PreferenceUtil.setEdit("worktime", usersigninResponse.user.worktime, B0_SigninActivity.this);
                Message msg = new Message();
                msg.what = MessageConstant.SIGN_IN_SUCCESS;
                EventBus.getDefault().post(msg);
                Intent intent = new Intent(B0_SigninActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                ToastView toast = new ToastView(this, getString(R.string.mobile_phone_or_password_error));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                mPassword.requestFocus();
            }

        } else if (url.endsWith(ApiInterface.USER_THREE_LOGIN)) {
            usersigninResponse usersigninResponse = new usersigninResponse();
            usersigninResponse.fromJson(jo);
            if (usersigninResponse.succeed == 1) {
                PreferenceUtil.setEdit("comment_count", usersigninResponse.user.comment_count, B0_SigninActivity.this);
                PreferenceUtil.setEdit("id", usersigninResponse.user.id+"", B0_SigninActivity.this);
                PreferenceUtil.setEdit("thumb", usersigninResponse.user.avatar.thumb, B0_SigninActivity.this);
                PreferenceUtil.setEdit("mobile_phone", usersigninResponse.user.mobile_phone, B0_SigninActivity.this);
                PreferenceUtil.setEdit("user_group", usersigninResponse.user.user_group+"", B0_SigninActivity.this);
                PreferenceUtil.setEdit("nickname", usersigninResponse.user.nickname, B0_SigninActivity.this);
                PreferenceUtil.setEdit("age", usersigninResponse.user.age, B0_SigninActivity.this);
                PreferenceUtil.setEdit("comment_goodrate", usersigninResponse.user.comment_goodrate, B0_SigninActivity.this);
                PreferenceUtil.setEdit("gender", usersigninResponse.user.gender+"", B0_SigninActivity.this);
                PreferenceUtil.setEdit("service_area", usersigninResponse.user.service_area, B0_SigninActivity.this);
                PreferenceUtil.setEdit("origin", usersigninResponse.user.origin, B0_SigninActivity.this);
                PreferenceUtil.setEdit("service_time", usersigninResponse.user.service_time, B0_SigninActivity.this);
                PreferenceUtil.setEdit("skill", usersigninResponse.user.skill, B0_SigninActivity.this);
                PreferenceUtil.setEdit("address", usersigninResponse.user.address, B0_SigninActivity.this);
                PreferenceUtil.setEdit("worktime", usersigninResponse.user.worktime, B0_SigninActivity.this);
                Intent intent = new Intent(B0_SigninActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

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

    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                ToastView toast = new ToastView(getApplicationContext(), getString(R.string.exit_again));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                handler.sendEmptyMessageDelayed(0, 3000);
                return true;
            } else {
                if (SESSION.getInstance().uid == 0) {
                    finish();
                } else {
                    Intent intent = new Intent(this, SlidingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

}
