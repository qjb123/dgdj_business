package net.ezcx.dongguandaojia.business.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import net.ezcx.dongguandaojia.business.MyActivity.Schedule.AddScheduleActivity;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.adapter.ScheduleAdapter;
import net.ezcx.dongguandaojia.business.base.BaseFragment;
import net.ezcx.dongguandaojia.business.bean.SCHEDULE;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ScheduleFragment extends BaseFragment implements IXListViewListener {
    @Bind(R.id.calendarView)
    MaterialCalendarView calendarView; //日历控件
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    @Bind(R.id.lvSchedule)
    XListView lvSchedule;
    @Bind(R.id.ivAddCalendar)
    AppCompatImageView ivAddCalendar;
    private ScheduleAdapter scheduleAdapter;
    List<SCHEDULE> list;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {//初始化listview的数据
        SCHEDULE schedule = new SCHEDULE();
        list = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {

            schedule.date = "10:23";
            schedule.address = "罗湖区";
            schedule.appliance_type = "冰箱";
            list.add(schedule);
        }
        lvSchedule.setXListViewListener(this, 1);
        lvSchedule.setPullLoadEnable(true);
        lvSchedule.setRefreshTime();
        scheduleAdapter = new ScheduleAdapter(getActivity(), list);
        lvSchedule.setAdapter(scheduleAdapter);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        //日历控件的监听
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (date != null) {
                    Toast.makeText(getActivity(), FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //listview的item监听
        lvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), position + "点击", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh(int id) {

    }

    @Override
    public void onLoadMore(int id) {

    }

    @OnClick({R.id.ivBack, R.id.ivAddCalendar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAddCalendar:
                Intent intent2 = new Intent(getBaseActivity(), AddScheduleActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
