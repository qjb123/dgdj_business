package net.ezcx.dongguandaojia.business.MyActivity.personal;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.BeeFramework.activity.BaseActivity;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.fragment.EvaluateListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyEvaluateActivity extends BaseActivity {

    @Bind(R.id.tabs_evaluate)
    TabLayout tabsEvaluate;
    @Bind(R.id.vpEvaluate)
    ViewPager vpEvaluate;
    private List<EvaluateListFragment> evaluateListFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluate);
        ButterKnife.bind(this);
        initView();//初始化fragment
        initEvent();
    }

    public void initView() {
        for (int i = 0; i < 4; i++) {
            evaluateListFragments.add(EvaluateListFragment.newInstance(i));
        }
    }

    public void initEvent() {
        vpEvaluate.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                evaluateListFragments.get(position).callFragment(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpEvaluate.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Fragment getItem(int position) {
                return evaluateListFragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.all_evaluate);
                    case 1:
                        return getString(R.string.good_evaluate);
                    case 2:
                        return getString(R.string.middle_evaluate);
                    case 3:
                        return getString(R.string.bad_evaluate);
                    default:
                        return "";
                }
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
            }
        });
        tabsEvaluate.setupWithViewPager(vpEvaluate);
        evaluateListFragments.get(0).callFragment(false);
    }
}
