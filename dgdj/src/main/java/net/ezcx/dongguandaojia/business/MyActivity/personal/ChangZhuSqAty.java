package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;

import net.ezcx.dongguandaojia.business.Model.UserModel;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.adapter.ChangZhuSqAdapter;
import net.ezcx.dongguandaojia.business.adapter.ItemBeanAdapter;
import net.ezcx.dongguandaojia.business.adapter.SearchCityAdapter;
import net.ezcx.dongguandaojia.business.bean.user.userchange_profileResponse;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.datebase.City;
import net.ezcx.dongguandaojia.business.datebase.MyCityDBHelper;
import net.ezcx.dongguandaojia.business.receiver.ReceiveType;
import net.ezcx.dongguandaojia.business.utils.PreferenceUtil;
import net.ezcx.dongguandaojia.business.utils.StringUtil;
import net.ezcx.dongguandaojia.business.utils.ToastUtil;
import net.ezcx.dongguandaojia.business.view.ClearEditText;
import net.ezcx.dongguandaojia.business.view.NoScrollGridView;
import net.ezcx.dongguandaojia.business.view.NoScrollListView;
import net.ezcx.dongguandaojia.business.view.testbase.MyLetterSortView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/28 0028.
 */
public class ChangZhuSqAty extends BaseActivity implements BusinessResponse ,View.OnClickListener{

    @Bind(R.id.iv_return)
    TextView ivReturn;
    @Bind(R.id.tv_add_data)
    TextView tvAddData;
    @Bind(R.id.czsq_serch_name)
    ClearEditText mClearEditText;
    @Bind(R.id.zcsq_dqcs)
    TextView zcsqDqcs;
    @Bind(R.id.czsq_gridview)
    GridView czsqGridview;

    private String CityArray[] = { "华强北", "海岸城", "欢乐海岸", "布吉", "龙华", "东门", "华侨城", "南山",
            "西乡", "宝安", "光明城", "福田" }; // 选中热门商圈数组

    private List<String> list = new ArrayList<String>(); // 选中城市集合
    ChangZhuSqAdapter changZhuSqAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czsq);
        ButterKnife.bind(this);
        changZhuSqAdapter = new ChangZhuSqAdapter(getBaseContext(),CityArray);
        czsqGridview.setAdapter(changZhuSqAdapter);

        czsqGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (changZhuSqAdapter.isItemSelected(position)) {
                    changZhuSqAdapter.removeSelected(position);
                    list.remove(CityArray[position]);
                } else {
                    changZhuSqAdapter.addSelected(position);
                    list.add(CityArray[position]);
                }
                changZhuSqAdapter.notifyDataSetChanged();
            }
        });

        setLinstener();
    }
    protected void setLinstener() {
        tvAddData.setOnClickListener(this);
        ivReturn.setOnClickListener(this);
       /* tvAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel = new UserModel(ChangZhuSqAty.this);
                userModel.addResponseListener(this);
               // userModel.changeNickname(et_modifterInfo.getText().toString(),"10");//修改住址网络请求
            }
        });*/


        /*ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

       /* mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //filterData2(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        Intent dataIntent = new Intent();
        userchange_profileResponse response = new userchange_profileResponse();
        response.fromJson(jo);
        if (url.endsWith(ApiInterface.USER_CHANGE_PROFILE)) {
            if(response.succeed==1){
                ToastView toast = new ToastView(ChangZhuSqAty.this, getString(R.string.edit_all));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                PreferenceUtil.setEdit("businee_circle", response.user.businee_circle, ChangZhuSqAty.this);
                //发出广播
                dataIntent.setAction(ReceiveType.UPDATEDATA);
                sendBroadcast(dataIntent);
                Intent intent = new Intent();
                intent.putExtra("businee_circle", response.user.businee_circle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_add_data:
                UserModel userModel = new UserModel(ChangZhuSqAty.this);
                userModel.addResponseListener(this);
                userModel.changeNickname(StringUtil.listToString(list), "10");//修改住址网络请求
                break;
        }
    }
}
