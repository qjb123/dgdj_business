package net.ezcx.dongguandaojia.business.fragment.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.external.maxwin.view.IXListViewListener;

import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.base.BaseFragment;
import net.ezcx.dongguandaojia.business.fragment.order.OrderListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderFragment extends BaseFragment implements IXListViewListener {
    @Bind(android.R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.vpOrder)
    ViewPager vpOrder;

    private List<OrderListFragment> orderListFragments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        for (int i = 0; i < 2; i++) {
            orderListFragments.add(OrderListFragment.newInstance(i));
        }
    }

    @Override
    public void initEvent() {
        vpOrder.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                orderListFragments.get(position).callFragment(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpOrder.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {//这个没报错,你点进方法看看就知道,这个也是v4包提供的
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int position) {
                return orderListFragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.title_all_order);
                    case 1:
                        return getString(R.string.title_finish_order);
                    default:
                        return "";
                }
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
            }
        });
        tabs.setupWithViewPager(vpOrder);
        orderListFragments.get(0).callFragment(false);
    }

    @Override
    public void onRefresh(int id) {

    }

    @Override
    public void onLoadMore(int id) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
