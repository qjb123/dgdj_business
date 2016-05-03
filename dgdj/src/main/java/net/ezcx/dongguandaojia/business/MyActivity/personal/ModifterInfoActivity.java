package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;

import net.ezcx.dongguandaojia.business.Model.UserModel;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.user.userchange_profileResponse;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.receiver.ReceiveType;
import net.ezcx.dongguandaojia.business.utils.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifterInfoActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {


    private String value;
    private String title;
    private EditText et_modifterInfo;
    private ImageView ivDelete;

    // 判断手机格式是否正确
    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifter_info);
        setData();
        setTitle(title);

    }

    private void setData() {
        et_modifterInfo = (EditText) findViewById(R.id.et_modifterInfo);
        ivDelete = (ImageView) findViewById(R.id.ivDelete);
        ivDelete.setOnClickListener(this);
        value = getIntent().getStringExtra("value.key");
        title = getIntent().getStringExtra("title.key");
        et_modifterInfo.setText(value);
        et_modifterInfo.requestFocus();
        //监听编辑框中字数的改变
        et_modifterInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_modifterInfo.getText())) {
                    ivDelete.setVisibility(View.INVISIBLE);
                } else {
                    ivDelete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(et_modifterInfo.getText())) {
                    ivDelete.setVisibility(View.INVISIBLE);
                } else {
                    ivDelete.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 网络返回数据
     *
     * @param url
     * @param jo
     * @param status
     * @throws JSONException
     */
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        Intent dataIntent = new Intent();
        userchange_profileResponse response = new userchange_profileResponse();
        response.fromJson(jo);
        if (url.endsWith(ApiInterface.USER_CHANGE_PROFILE)) {
            if (title.equals("修改昵称")) {
                if(response.succeed==1) {
                    ToastView toast = new ToastView(ModifterInfoActivity.this, getString(R.string.edit_nickname_success));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    PreferenceUtil.setEdit("nickname", response.user.nickname, ModifterInfoActivity.this);

                    //发出广播
                    dataIntent.setAction(ReceiveType.UPDATEDATA);
                    sendBroadcast(dataIntent);

                    Intent intent = new Intent();
                    intent.putExtra("name", response.user.nickname);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            } else if (title.equals("修改籍贯")) {
                if(response.succeed==1) {
                    ToastView toast = new ToastView(ModifterInfoActivity.this, getString(R.string.edit_origin));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    PreferenceUtil.setEdit("origin", response.user.origin, ModifterInfoActivity.this);
                    //发出广播
                    dataIntent.setAction(ReceiveType.UPDATEDATA);
                    sendBroadcast(dataIntent);
                    Intent intent = new Intent();
                    intent.putExtra("origin", response.user.origin);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            } else if (title.equals("修改年龄")) {
                if(response.succeed==1) {
                    ToastView toast = new ToastView(ModifterInfoActivity.this, getString(R.string.edit_age));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    PreferenceUtil.setEdit("age", response.user.age, ModifterInfoActivity.this);
                    //发出广播
                    dataIntent.setAction(ReceiveType.UPDATEDATA);
                    sendBroadcast(dataIntent);
                    Intent intent = new Intent();
                    intent.putExtra("age", response.user.age);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            } else if (title.equals("工作经验")) {
                if(response.succeed==1) {
                    ToastView toast = new ToastView(ModifterInfoActivity.this, getString(R.string.edit_all));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    PreferenceUtil.setEdit("worktime", response.user.worktime, ModifterInfoActivity.this);
                    //发出广播
                    dataIntent.setAction(ReceiveType.UPDATEDATA);
                    sendBroadcast(dataIntent);
                    Intent intent = new Intent();
                    intent.putExtra("worktime", response.user.worktime);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }else if (title.equals("修改电话")) {
                if(response.succeed==1) {
                    ToastView toast = new ToastView(ModifterInfoActivity.this, getString(R.string.edit_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    PreferenceUtil.setEdit("mobile", response.user.mobile, ModifterInfoActivity.this);
                    //发出广播
                    dataIntent.setAction(ReceiveType.UPDATEDATA);
                    sendBroadcast(dataIntent);
                    Intent intent = new Intent();
                    intent.putExtra("phone", response.user.mobile);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }else if (title.equals("技能")) {
                if(response.succeed==1) {
                    ToastView toast = new ToastView(ModifterInfoActivity.this, getString(R.string.edit_all));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    PreferenceUtil.setEdit("skill", response.user.skill, ModifterInfoActivity.this);
                    //发出广播
                    dataIntent.setAction(ReceiveType.UPDATEDATA);
                    sendBroadcast(dataIntent);
                    Intent intent = new Intent();
                    intent.putExtra("skill", response.user.skill);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            } else if (title.equals("修改住址")) {
                if(response.succeed==1) {
                    ToastView toast = new ToastView(ModifterInfoActivity.this, getString(R.string.addressl_edit_success));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    PreferenceUtil.setEdit("address", response.user.address, ModifterInfoActivity.this);
                    //发出广播
                    dataIntent.setAction(ReceiveType.UPDATEDATA);
                    sendBroadcast(dataIntent);
                    Intent intent = new Intent();
                    intent.putExtra("address", response.user.address);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }else if (title.equals("服务范围")) {
                if(response.succeed==1) {
                    ToastView toast = new ToastView(ModifterInfoActivity.this, getString(R.string.edit_all));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    PreferenceUtil.setEdit("service_area", response.user.service_area, ModifterInfoActivity.this);
                    //发出广播
                    dataIntent.setAction(ReceiveType.UPDATEDATA);
                    sendBroadcast(dataIntent);
                    Intent intent = new Intent();
                    intent.putExtra("service_area", response.user.service_area);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }else if (title.equals("常驻商圈")) {
                if(response.succeed==1) {
                    ToastView toast = new ToastView(ModifterInfoActivity.this, getString(R.string.edit_all));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    PreferenceUtil.setEdit("businee_circle", response.user.businee_circle, ModifterInfoActivity.this);
                    //发出广播
                    dataIntent.setAction(ReceiveType.UPDATEDATA);
                    sendBroadcast(dataIntent);
                    Intent intent = new Intent();
                    intent.putExtra("businee_circle", response.user.businee_circle);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }else if (title.equals("服务时间")) {
                if(response.succeed==1) {
                    ToastView toast = new ToastView(ModifterInfoActivity.this, getString(R.string.edit_all));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    PreferenceUtil.setEdit("service_time", response.user.service_time, ModifterInfoActivity.this);
                    //发出广播
                    dataIntent.setAction(ReceiveType.UPDATEDATA);
                    sendBroadcast(dataIntent);
                    Intent intent = new Intent();
                    intent.putExtra("service_time", response.user.service_time);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        et_modifterInfo.setText("");
        ivDelete.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSave:
                // TODO: 2016/3/24 处理点击事件
                UserModel userModel = new UserModel(ModifterInfoActivity.this);
                userModel.addResponseListener(this);
                if (title.equals("修改昵称")) {
                    userModel.changeNickname(et_modifterInfo.getText().toString(),"1"); //修改昵称网络请求
                } else if (title.equals("修改籍贯")) {
                    userModel.changeNickname(et_modifterInfo.getText().toString(),"3");//修改电话网络请求
                }else if (title.equals("修改年龄")) {
                    userModel.changeNickname(et_modifterInfo.getText().toString(),"4");//修改电话网络请求
                } else if (title.equals("工作经验")) {
                    userModel.changeNickname(et_modifterInfo.getText().toString(),"5");//修改电话网络请求
                } else if (title.equals("修改电话")) {
                    if(!isMobileNO(et_modifterInfo.getText().toString())){
                        Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    }else{
                        userModel.changeNickname(et_modifterInfo.getText().toString(),"6");//修改住址网络请求
                    }
                }else if (title.equals("技能")) {
                    userModel.changeNickname(et_modifterInfo.getText().toString(),"7");//修改住址网络请求
                }else if (title.equals("修改住址")) {
                    userModel.changeNickname(et_modifterInfo.getText().toString(),"8");//修改住址网络请求
                }else if (title.equals("服务范围")) {
                    userModel.changeNickname(et_modifterInfo.getText().toString(),"9");//修改住址网络请求
                }else if (title.equals("常驻商圈")) {
                    userModel.changeNickname(et_modifterInfo.getText().toString(),"10");//修改住址网络请求
                }else if (title.equals("服务时间")) {
                    userModel.changeNickname(et_modifterInfo.getText().toString(),"11");//修改住址网络请求
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
