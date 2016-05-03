package net.ezcx.dongguandaojia.business.MyActivity.Schedule;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;

import net.ezcx.dongguandaojia.business.R;

public class AddScheduleActivity extends BaseActivity {

    private AppCompatTextView tvTitleName;
    private AppCompatTextView tvFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        setActionBar();
    }

    /**
     * 设置标题栏
     */
    private void setActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            ActionBar.LayoutParams params = new Toolbar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_add_schedule_title, null);
            getSupportActionBar().setCustomView(view, params);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            tvTitleName = (AppCompatTextView) findViewById(R.id.tv_title_name);
            AppCompatImageView ivTitleBack = (AppCompatImageView) findViewById(R.id.ivBack);
            tvFinish = (AppCompatTextView) findViewById(R.id.tvFinish);
            OnTopClickListener clickListener = new OnTopClickListener();
            ivTitleBack.setOnClickListener(clickListener);
            tvFinish.setOnClickListener(clickListener);
        }
    }

    /**
     * Toolbar上两个图片按钮的点击事件
     */
    class OnTopClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivBack:
                    finish();
                    break;
                case R.id.tvFinish:
                    Toast.makeText(AddScheduleActivity.this, "完成", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }
}
