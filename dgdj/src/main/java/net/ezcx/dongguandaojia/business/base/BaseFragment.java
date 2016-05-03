package net.ezcx.dongguandaojia.business.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.BeeFramework.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {

    /**
     * 是否已经初始化Fragment
     */
    protected boolean isInitFragment = false;
    private BaseActivity baseActivity;
    /**
     * 是否可以进行初始化操作
     */
    private boolean isCanInit = false;
    /**
     * 是否是从本类调用
     */
    private boolean fromThis = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        baseActivity = (BaseActivity) activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isCanInit = true;
        callFragment(true);
    }

    /**
     * 可能会调用两次，如介意，不要重写
     */
    public synchronized void callFragment(boolean fromThis) {
        if (!fromThis) {
            this.fromThis = fromThis;
        }
        if (isCanInit && !isInitFragment && !this.fromThis) {
            initData();
            initView();
            initEvent();
            isInitFragment = true;
        }
    }

    /**
     * 初始化默认数据
     */
    public abstract void initData();

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 初始化事件（监听）
     */
    public abstract void initEvent();

    /**
     * 获得ZZBActivity
     *
     * @return ZZBActivity
     */
    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

}
