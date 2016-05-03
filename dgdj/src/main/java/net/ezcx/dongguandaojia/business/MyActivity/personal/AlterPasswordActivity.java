package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;

import net.ezcx.dongguandaojia.business.Model.UserModel;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.bean.user.userchange_profileResponse;
import net.ezcx.dongguandaojia.business.config.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlterPasswordActivity extends BaseActivity implements BusinessResponse {

    @Bind(R.id.et_oldPassw)
    EditText etOldPassw;
    @Bind(R.id.et_newPassw)
    EditText etNewPassw;
    @Bind(R.id.et_againPassw)
    EditText etAgainPassw;
    @Bind(R.id.btn_finish)
    Button btnFinish;
    private UserModel userModel;
    private String oldPassw;
    private String newPassw;
    private String againPassw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_password);
        ButterKnife.bind(this);

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.USER_CHANGE_PROFILE)) {
            userchange_profileResponse response = new userchange_profileResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                ToastView toast = new ToastView(this, getString(R.string.change_password_success));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent intent = new Intent(AlterPasswordActivity.this, B0_SigninActivity.class);
                startActivity(intent);
            }

        }
    }

    @OnClick(R.id.btn_finish)
    public void onClick() {
        oldPassw = etOldPassw.getText().toString();
        newPassw = etNewPassw.getText().toString();
        againPassw = etAgainPassw.getText().toString();
        if ("".equals(oldPassw)) {
            ToastView toast = new ToastView(AlterPasswordActivity.this, getString(R.string.input_password));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            etOldPassw.requestFocus();
            etOldPassw.setText("");
        } else if (oldPassw.length() < 6 || oldPassw.length() > 20) {
            ToastView toast = new ToastView(AlterPasswordActivity.this, getString(R.string.password_wrong_format_hint));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            etOldPassw.requestFocus();
        } else if ("".equals(newPassw)) {
            ToastView toast = new ToastView(AlterPasswordActivity.this, getString(R.string.input_new_password));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            etNewPassw.requestFocus();
            etNewPassw.setText("");
        } else if (newPassw.length() < 6 || newPassw.length() > 20) {
            ToastView toast = new ToastView(AlterPasswordActivity.this, getString(R.string.new_password_wrong_format_hint));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            etNewPassw.requestFocus();
        } else if (!newPassw.equals(againPassw)) {
            ToastView toast = new ToastView(AlterPasswordActivity.this, getString(R.string.two_passwords_differ_hint));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            etAgainPassw.requestFocus();
        } else {
            userModel = new UserModel(AlterPasswordActivity.this);
            userModel.addResponseListener(this);
            userModel.changePassword(oldPassw, newPassw, againPassw);//修改密码网络请求
        }
    }
}
