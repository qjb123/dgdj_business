package net.ezcx.dongguandaojia.business.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import net.ezcx.dongguandaojia.business.MyActivity.order.OrderInfoActivity;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.adapter.EvaluateAdapter;
import net.ezcx.dongguandaojia.business.base.BaseFragment;
import net.ezcx.dongguandaojia.business.bean.EVALUATE;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 评价列表页面
 */
public class EvaluateListFragment extends BaseFragment implements IXListViewListener, BusinessResponse, View.OnClickListener {
    public static final String FRAGMENT_TYPE_KEY = "fragmentType.key";
    @Bind(R.id.lvMyEvaluate)
    XListView lvMyEvaluate;
    private int fragmentType = 0;//判断是第几个fragment
    List<EVALUATE> list;
    private EvaluateAdapter evaluateAdapter;

    public static EvaluateListFragment newInstance(int fragmentType) {
        EvaluateListFragment fragment = new EvaluateListFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_TYPE_KEY, fragmentType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evaluate_list, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            //根据fragmentType判断是第几个fragment,继而发送不同的网络请求
            this.fragmentType = getArguments().getInt(FRAGMENT_TYPE_KEY);
        }
        //初始化listview的数据
        EVALUATE evaluate = new EVALUATE();
        list = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            evaluate.nickname = "yang 'sir";
            evaluate.evaluate_content = "阿姨非常细心,也很负责,我们非常满意!!!谢谢阿姨聚会喝酒就会晋级赛近段时间";
            evaluate.date = "2015-02-03";
            evaluate.server_desc = "购买张阿姨保姆服务9个月";
            evaluate.my_reply = "非常感谢您喜欢我的服务~~~";
            list.add(evaluate);
        }
        lvMyEvaluate.setXListViewListener(this, 1);
        lvMyEvaluate.setPullLoadEnable(true);
        lvMyEvaluate.setRefreshTime();
        evaluateAdapter = new EvaluateAdapter(getActivity(), list);
        lvMyEvaluate.setAdapter(evaluateAdapter);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        //listview Item监听
        lvMyEvaluate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrderInfoActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    //下拉刷新的回调
    @Override
    public void onRefresh(int id) {

    }

    //加载回调
    @Override
    public void onLoadMore(int id) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //网络请求返回数据
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }

    @Override
    public void onClick(View v) {

    }
}
