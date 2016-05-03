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

package net.ezcx.dongguandaojia.business.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.O2OMobile.O2OMobileAppConst;
import net.ezcx.dongguandaojia.business.Model.UserModel;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.bean.user.USER;
import net.ezcx.dongguandaojia.business.R;

import org.json.JSONException;
import org.json.JSONObject;

public class C4_EditIntroActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {
    private TextView mTitle;
    private ImageView mBack;
    private ImageView mTopViewRightImage;
    private Button mSave;
    private SharedPreferences mShared;
    private EditText mBrief;
    private UserModel mUserModel;
    private TextView mTextNum;
    private int mTotalSize = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c4_edit_intro);
        mTitle = (TextView) findViewById(R.id.top_view_title);
        mTitle.setText(getString(R.string.edit_brief));
        mBack = (ImageView) findViewById(R.id.top_view_back);
        mBrief = (EditText) findViewById(R.id.et_brief);
        mTextNum = (TextView) findViewById(R.id.text_num);
        mSave = (Button) findViewById(R.id.btn_save);
        mTopViewRightImage = (ImageView) findViewById(R.id.top_view_right_image);
        mTopViewRightImage.setVisibility(View.INVISIBLE);
        mShared = getSharedPreferences(O2OMobileAppConst.USERINFO, 0);
        String userStr = mShared.getString("user", "");
        try {
            if (userStr != null) {
                JSONObject userJson = new JSONObject(userStr);
                USER user = new USER();
                user.fromJson(userJson);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mUserModel = new UserModel(this);
        mUserModel.addResponseListener(this);
        mSave = (Button) findViewById(R.id.btn_save);
        mBack.setOnClickListener(this);
        mSave.setOnClickListener(this);
        //监听字数变化
        mBrief.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTextNum.setText(mTotalSize - mBrief.getText().length() + "");

            }
        });
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if(url.endsWith(ApiInterface.USER_CHANGE_PROFILE)){
            ToastView toast = new ToastView(C4_EditIntroActivity.this, getString(R.string.brief_edit_success));
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        String brief = mBrief.getText().toString().trim();
        switch (v.getId()) {
            case R.id.top_view_back:
                finish();
                break;
            case R.id.btn_save:
                if ("".equals(brief)) {
                    ToastView toast = new ToastView(C4_EditIntroActivity.this, getString(R.string.brief_wrong_format_hint));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mBrief.setText("");
                    mBrief.requestFocus();
                    break;
                }else{
//                    mUserModel.changeBrief(brief);
                }
        }
    }
}