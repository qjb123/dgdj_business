package com.BeeFramework.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.BeeFramework.model.ActivityManagerModel;
import com.BeeFramework.model.BusinessMessage;
import net.ezcx.dongguandaojia.business.R;

import org.json.JSONException;

import java.util.List;

@SuppressLint("NewApi")
public class BaseActivity extends AppCompatActivity implements Handler.Callback {
    public Handler mHandler;

    public BaseActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mHandler = new Handler(this);
        ActivityManagerModel.addLiveActivity(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setToolBar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setToolBar();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
       setToolBar();
    }

    private void setToolBar(){//ToolBar在这里已经设置过了,如果不需要返回按钮才需要重新设置,你不用在你的Activity中重新弄,只要在清单文件里在Acitivity中设置Label属性就可以了.
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.appBar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
            }
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityManagerModel.addVisibleActivity(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        ActivityManagerModel.removeVisibleActivity(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManagerModel.addForegroundActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManagerModel.removeForegroundActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerModel.removeLiveActivity(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    public void OnMessageResponse(BusinessMessage response)
            throws JSONException {
        if (response.messageState == BusinessMessage.FAILURE_MESSAGE) {
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
