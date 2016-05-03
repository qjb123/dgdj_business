package com.BeeFramework.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;

import com.BeeFramework.view.MyProgressDialog;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.AQuery;
import com.external.androidquery.callback.AjaxCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.AQUtility;
import com.insthub.O2OMobile.APIErrorCode;

import net.ezcx.dongguandaojia.business.MyActivity.personal.B0_SigninActivity;

import com.insthub.O2OMobile.O2OMobileAppConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BaseModel implements BusinessResponse {

    protected BeeQuery aq;
    protected ArrayList<BusinessResponse> businessResponseArrayList = new ArrayList<BusinessResponse>();
    public ArrayList<BeeCallback> sendingmessageList = new ArrayList<BeeCallback>();
    protected Context mContext;

    public SharedPreferences shared;
    public SharedPreferences.Editor editor;

    public BaseModel() {

    }

    public BaseModel(Context context) {
        aq = new BeeQuery(context);
        mContext = context;
        shared = context.getSharedPreferences(O2OMobileAppConst.USERINFO, 0);
        editor = shared.edit();
    }

    protected void saveCache() {
        return;
    }

    protected void cleanCache() {
        return;
    }

    public void addResponseListener(BusinessResponse listener) {
        if (!businessResponseArrayList.contains(listener)) {
            businessResponseArrayList.add(listener);
        } else {

        }
    }

    public void removeResponseListener(BusinessResponse listener) {
        businessResponseArrayList.remove(listener);
    }

    //公共的错误处理
    public void callback(BeeCallback callback, String url, JSONObject jo, AjaxStatus status) {
        DebugMessageModel.finishSendingMessage(callback);
        finishMessage(callback);
        if (null == jo) {
            ToastView toast = new ToastView(mContext, "网络错误，请检查网络设置");
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

    }

    //公共的错误处理
    public boolean callback(String url, int errorCode, String errorDesc) {
        if (errorCode == APIErrorCode.SESSION_EXPIRE) {
            ToastView toast = new ToastView(mContext, errorDesc);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            editor.putBoolean("isLogin", false);
            editor.commit();
            Intent intent = new Intent(mContext, B0_SigninActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();
            return true;
        } else if (null != errorDesc) {
            ToastView toast = new ToastView(mContext, errorDesc);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return true;
        } else {
            return true;
        }


    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        AQUtility.debug("get remote data: ", jo.toString());
        for (BusinessResponse iterable_element : businessResponseArrayList) {
            iterable_element.OnMessageResponse(url, jo, status);
        }

        if (businessResponseArrayList.size() == 0) {

        }
    }

    public void addMessage(BeeCallback msg) {
        sendingmessageList.add(msg);
    }


    public boolean isSendingMessage(String url) {
        for (int i = 0; i < sendingmessageList.size(); i++) {
            AjaxCallback msg = sendingmessageList.get(i);
            if (msg.getUrl().endsWith(url)) {
                return true;
            }
        }

        return false;
    }

    public void finishMessage(BeeCallback msg) {
        if (sendingmessageList.contains(msg)) {
            sendingmessageList.remove(msg);
        }

    }

    public <K> AQuery ajax(AjaxCallback<K> callback) {
        addMessage((BeeCallback) callback);
        return aq.ajax(callback);
    }

    public <K> AQuery ajaxProgress(AjaxCallback<K> callback) {
        addMessage((BeeCallback) callback);
        MyProgressDialog pro =
                new MyProgressDialog(mContext, "请稍候...");
        return (AQuery) aq.progress(pro.mDialog).
                ajax(callback);

    }

}
