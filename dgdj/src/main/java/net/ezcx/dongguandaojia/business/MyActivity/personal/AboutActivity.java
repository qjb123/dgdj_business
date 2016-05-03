package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;

import net.ezcx.dongguandaojia.business.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity implements BusinessResponse {


    @Bind(R.id.tvVersionCode)
    TextView tvVersionCode;
    @Bind(R.id.llUpdate)
    LinearLayout llUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }

}
