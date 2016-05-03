package net.ezcx.dongguandaojia.business.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.sina.weibo.sdk.utils.LogUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class MyJPushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();

        if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())){
            System.out.println("收到了通知");
            receivingNotification(context,bundle);
            //发出广播
            Intent dataIntent = new Intent();
            dataIntent.setAction(ReceiveType.NOTIFICATION);
            context.sendBroadcast(dataIntent);
        }
    }

    private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        LogUtil.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        LogUtil.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtil.d(TAG, "extras : " + extras);
    }

}
