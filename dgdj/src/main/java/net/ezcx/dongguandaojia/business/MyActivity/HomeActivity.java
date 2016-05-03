package net.ezcx.dongguandaojia.business.MyActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.base.BaseFragment;
import net.ezcx.dongguandaojia.business.fragment.HomeFragment;
import net.ezcx.dongguandaojia.business.fragment.MeFragment;
import net.ezcx.dongguandaojia.business.fragment.order.OrderFragment;
import net.ezcx.dongguandaojia.business.fragment.ScheduleFragment;
import net.ezcx.dongguandaojia.business.view.TabButton;
import net.ezcx.dongguandaojia.business.view.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页
 */
public class HomeActivity extends BaseActivity {
    public static final String FRAGMENT_INDEX_KEY = "fragmentIndex.key";
    private static final String TAG = "MainActivity";
    @Bind(R.id.appBar)
    Toolbar toolbar;
    @Bind(R.id.tabHomepage)
    TabButton tabHomepage;
    @Bind(R.id.tabSchedule)
    TabButton tabSchedule;
    @Bind(R.id.tabOrder)
    TabButton tabOrder;
    @Bind(R.id.tabMe)
    TabButton tabMe;
    long lastBackTime = 0;
    private TabLayout tabLayout;
    /**
     * 当前页码
     */
    private int currentIndex;
    private FragmentManager fragmentManager;
    /**
     * 界面中选项卡的Fragments
     */
    private List<BaseFragment> fragmentList = new ArrayList<>();
    /**
     * 选项卡id
     */
    private int[] buttonId = {R.id.tabHomepage, R.id.tabSchedule, R.id.tabOrder, R.id.tabMe};
    /**
     * 要显示的Framgent的class
     */
    private Class[] fragmentClasses = {HomeFragment.class, ScheduleFragment.class, OrderFragment.class,
            MeFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setActionBar();
        Log.d(TAG, "onCreate");
        initFragment();
    }

    /**
     * 设置标题栏
     */
    private void setActionBar() {
        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            getSupportActionBar().setHomeButtonEnabled(false);
//            ActionBar.LayoutParams params = new Toolbar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
//                    ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
//            View view = LayoutInflater.from(this).inflate(R.layout.layout_main_title, null);
//            getSupportActionBar().setCustomView(view, params);
//            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//            tvTitleName = (AppCompatTextView) findViewById(R.id.tv_title_name);
//            AppCompatImageView ivTitleBack = (AppCompatImageView) findViewById(R.id.ivBack);
//            tvTitleNearOrder = (AppCompatTextView) findViewById(R.id.tvNearOrder);
//            ivTitleAddCalendar = (AppCompatImageView) findViewById(R.id.ivAddCalendar);
//            OnTopClickListener clickListener = new OnTopClickListener();
//            ivTitleBack.setOnClickListener(clickListener);
//            tvTitleNearOrder.setOnClickListener(clickListener);
//            ivTitleAddCalendar.setOnClickListener(clickListener);
            getSupportActionBar().hide();
        }
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        // 初始tab项
        currentIndex = 0;
        tabLayout = new TabLayout(R.color.theme_color, R.color.theme_color).addBtn(tabHomepage,
                tabSchedule, tabOrder, tabMe);
        tabHomepage.init(R.drawable.icon_home2, R.drawable.icon_home, getString(R.string.tab_home), true,
                this);
        tabSchedule.init(R.drawable.icon_schedule, R.drawable.icon_schedule2,
                getString(R.string.tab_schedule), false, this);
        tabOrder.init(R.drawable.icon_order, R.drawable.icon_order2,
                getString(R.string.tab_order), false, this);
        tabMe.init(R.drawable.icon_me, R.drawable.icon_me2, getString(R.string.tab_me), false,
                this);
        tabLayout.selectBtn(buttonId[currentIndex]);
        // 初始化Fragment
        fragmentList.clear();
        // 添加Fragment
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        try {
            for (int i = 0; i < fragmentClasses.length; i++) {
                BaseFragment tempFragment = (BaseFragment) fragmentManager
                        .findFragmentByTag(fragmentClasses[i].getSimpleName());
                Bundle bundle = new Bundle();
                bundle.putInt(FRAGMENT_INDEX_KEY, i);
                if (tempFragment != null) {
                    fragmentList.add(tempFragment);
                } else {
                    tempFragment = (BaseFragment) fragmentClasses[i].newInstance();
                    tempFragment.setArguments(bundle);
                    fragmentList.add(tempFragment);
                    transaction.add(R.id.flContent, fragmentList.get(i),
                            fragmentList.get(i).getClass().getSimpleName());
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        transaction.commit();
        showFragment(currentIndex);
    }

    /**
     * 展示Fragment
     *
     * @param currentIndex 要展示第几个Fragment
     */
    private void showFragment(int currentIndex) {
        tabLayout.selectBtn(buttonId[currentIndex]);
        FragmentTransaction t = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            BaseFragment fragment = fragmentList.get(i);
            BaseFragment tempFragment = (BaseFragment) fragmentManager
                    .findFragmentByTag(fragment.getClass().getSimpleName());
            if (i == currentIndex) {
                if (tempFragment != null) {
                    t.show(tempFragment);
                    tempFragment.callFragment(false);
                } else {
                    t.show(fragment);
                    fragment.callFragment(false);
                }
            } else {
                if (tempFragment != null) {
                    t.hide(tempFragment);
                } else {
                    t.hide(fragment);
                }
            }
        }
        t.commit();
    }

    /**
     * 应用结束时调用
     */
    private void doFinish() {
        finish();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy is called");
        doFinish();
        super.onDestroy();
    }

    /**
     * 按钮的点击事件
     *
     * @param view view
     */
    @OnClick(value = {R.id.tabHomepage, R.id.tabSchedule, R.id.tabOrder, R.id.tabMe})
    public void btnOnClick(View view) {
        int tempIndex = currentIndex;
        switch (view.getId()) {
            case R.id.tabHomepage:
                currentIndex = 0;
                break;
            case R.id.tabSchedule:
                currentIndex = 1;
                break;
            case R.id.tabOrder:
                currentIndex = 2;
                break;
            case R.id.tabMe:
                currentIndex = 3;
                break;
        }
        if (tempIndex != currentIndex) {
            showFragment(currentIndex);
        }
    }

    /**
     * 获得当前选中的选项卡的名称
     *
     * @return 当前选中的选项卡的名称
     */
    public String getFragmentTitle() {
        return tabLayout.getSelectedButton().getTabName();
    }

    /**
     * 获得当前为第几个选项卡
     *
     * @return 第几个选项卡
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackTime < 2000) {
            // MainActivity.this.finish();
            doFinish();
        } else {
            Toast.makeText(this, "再次点击，确认退出", Toast.LENGTH_SHORT).show();
        }
        lastBackTime = currentTime;
    }
}
