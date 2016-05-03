package net.ezcx.dongguandaojia.business.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

import net.ezcx.dongguandaojia.business.R;

/**
 * 倒计时工具类
 */
public class TimeCountUtils extends CountDownTimer {
    private Button button;
    private Context context;

    public TimeCountUtils(Context context, Button button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        this.button = button;
        this.context = context;
    }

    @Override
    public void onFinish() {//计时完毕时触发
        button.setText(context.getString(R.string.get_verify_code_again));
        button.setClickable(true);
    }

    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        button.setClickable(false);
        button.setText(millisUntilFinished / 1000 + context.getString(R.string.resend_after));
    }
}
