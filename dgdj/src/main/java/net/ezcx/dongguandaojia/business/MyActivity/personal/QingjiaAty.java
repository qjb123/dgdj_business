package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;

import net.ezcx.dongguandaojia.business.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class QingjiaAty extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.appBar)
    Toolbar appBar;
    @Bind(R.id.qinjia_name)
    EditText qinjiaName;
    @Bind(R.id.qinjia_starttime)
    TextView qinjiaStarttime;
    @Bind(R.id.qinjia_endtime)
    TextView qinjiaEndtime;
    @Bind(R.id.qinjia_content)
    EditText qinjiaContent;
    @Bind(R.id.qingjia_button)
    Button qingjiaButton;

    //private TimePickerView tpvServiceTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_qingjia);
        ButterKnife.bind(this);

        qinjiaStarttime.setOnClickListener(this);
        qinjiaEndtime.setOnClickListener(this);
        qingjiaButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {

    }
}
