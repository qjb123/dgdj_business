package com.BeeFramework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.BeeFramework.AppConst;
import com.BeeFramework.adapter.CrashLogAdapter;
import com.BeeFramework.protocol.CrashMessage;
import com.external.maxwin.view.XListView;
import net.ezcx.dongguandaojia.business.R;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CrashLogActivity extends BaseActivity
{
    ArrayList<File> logFilesList = new ArrayList<File>();
    ArrayList<CrashMessage> crashMessageArrayList = new ArrayList<CrashMessage>();

    XListView logListView;
    TextView titleTextView;
    CrashLogAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_log_activity);
        logListView = (XListView)findViewById(R.id.log_list_view);
        titleTextView = (TextView)findViewById(R.id.navigationbar_title);
        titleTextView.setText("崩溃日志分析");

        new Thread() {
            @Override
            public void run() {
                 initLog();
            }
        }.start();

        logListView.setPullLoadEnable(false);
        logListView.setPullRefreshEnable(false);
        logListView.setRefreshTime();

        listAdapter = new CrashLogAdapter(this,crashMessageArrayList);
        logListView.setAdapter(listAdapter);

        logListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id)
             {
             	int size =crashMessageArrayList.size();
                 CrashMessage crashMessage = crashMessageArrayList.get(size-position);
                 Intent it = new Intent(CrashLogActivity.this, CrashLogDetailActivity.class);
                 it.putExtra("crash_time",crashMessage.crashTime);
                 it.putExtra("crash_content",crashMessage.crashContent);
                 startActivity(it);
             }
         });
         
    }


    public void initLog()
    {
        try
        {

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + AppConst.LOG_DIR_PATH;

            getFiles(logFilesList, path);
            for (int i = 0; i < logFilesList.size(); i++)
            {
                File file = logFilesList.get(i);
                try
                {
                    FileInputStream fin = new FileInputStream(file);
                    int length = fin.available();
                    byte[] buffer = new byte[length];
                    fin.read(buffer);
                    String content = EncodingUtils.getString(buffer,"UTF-8");
                    fin.close();
                    String fileName = file.getName();
                    String[] nameArray = fileName.split("\\.");
                    if (nameArray.length > 0)
                    {
                        String intStr = nameArray[0];

                        long timestamp =  Long.parseLong(intStr);
                        Date currentTime = new Date(timestamp);

                        CrashMessage crashMessage = new CrashMessage();

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        String dateString = formatter.format(currentTime);
                        crashMessage.crashTime = dateString;
                        crashMessage.crashContent = content;
                        crashMessageArrayList.add(crashMessage);
                    }

                }
                catch (FileNotFoundException e)
                {

                }
                catch (IOException e2)
                {
                    e2.printStackTrace();
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            handler.sendEmptyMessage(0);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                listAdapter.notifyDataSetChanged();
                break;
            }

        }
    };

    private void getFiles(ArrayList<File> logFilesList, String path)
    {
        File[] allFiles = new File(path).listFiles();
        for (int i = 0; i < allFiles.length; i++)
        {
            File file = allFiles[i];
            if (file.isFile()&&file.getName().contains("txt"))
            {
                logFilesList.add(file);
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        listAdapter.notifyDataSetChanged();
    }
}
