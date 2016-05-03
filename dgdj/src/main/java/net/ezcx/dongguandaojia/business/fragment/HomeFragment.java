package net.ezcx.dongguandaojia.business.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BeeFramework.model.BusinessResponse;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.external.androidquery.callback.AjaxStatus;

import net.ezcx.dongguandaojia.business.Model.UserBalanceModel;
import net.ezcx.dongguandaojia.business.MyActivity.order.NearOrderActivity;
import net.ezcx.dongguandaojia.business.MyActivity.personal.B0_SigninActivity;
import net.ezcx.dongguandaojia.business.MyActivity.personal.PersonalInfoActivity;
import net.ezcx.dongguandaojia.business.R;
import net.ezcx.dongguandaojia.business.base.BaseFragment;
import net.ezcx.dongguandaojia.business.bean.user.USER;
import net.ezcx.dongguandaojia.business.bean.user.userprofileResponse;
import net.ezcx.dongguandaojia.business.bean.user.usersigninResponse;
import net.ezcx.dongguandaojia.business.config.ApiInterface;
import net.ezcx.dongguandaojia.business.utils.PreferenceUtil;
import net.ezcx.dongguandaojia.business.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements BusinessResponse {
    @Bind(R.id.bmapView)
    MapView bmapView;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    @Bind(R.id.tvNearOrder)
    AppCompatTextView tvNearOrder;
    private BaiduMap mBaiduMap;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        automaticLogin();
        return view;

    }

    @Override
    public void initData() {

        initLocation();

    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        bmapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        bmapView.onPause();
    }

    @Override
    public void onDestroyView() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        bmapView.onDestroy();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initLocation() {
        mBaiduMap = bmapView.getMap();
        mLocationClient = new LocationClient(getActivity().getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @OnClick({R.id.tvNearOrder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvNearOrder:
                Intent intent = new Intent(getBaseActivity(), NearOrderActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.USER_PROFILE)){
            userprofileResponse response = new userprofileResponse();
            response.fromJson(jo);
            USER user = response.user;
            if (response.succeed == 1) {
                PreferenceUtil.setEdit("comment_count", user.comment_count, getActivity());
                PreferenceUtil.setEdit("id", user.id+"", getActivity());
                PreferenceUtil.setEdit("thumb",user.avatar.thumb, getActivity());
                PreferenceUtil.setEdit("mobile_phone", user.mobile_phone, getActivity());
                PreferenceUtil.setEdit("user_group", user.user_group+"", getActivity());
                PreferenceUtil.setEdit("nickname", user.nickname, getActivity());
                PreferenceUtil.setEdit("age", user.age, getActivity());
                PreferenceUtil.setEdit("comment_goodrate", user.comment_goodrate, getActivity());
                PreferenceUtil.setEdit("gender", user.gender+"", getActivity());
                PreferenceUtil.setEdit("service_area", user.service_area, getActivity());
                PreferenceUtil.setEdit("origin", user.origin, getActivity());
                PreferenceUtil.setEdit("service_time", user.service_time, getActivity());
                PreferenceUtil.setEdit("skill", user.skill, getActivity());
                PreferenceUtil.setEdit("address", user.address, getActivity());
                PreferenceUtil.setEdit("worktime", user.worktime, getActivity());
            }

        }
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
            if (location == null || bmapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    private UserBalanceModel userBalanceModel;
    /*
    自登陆事件
     */
    private void automaticLogin() {
        String id = PreferenceUtil.getValue("id", getActivity());
        if (StringUtil.isEmpty(id) ) {
        } else {
            userBalanceModel = new UserBalanceModel(getActivity());
            userBalanceModel.addResponseListener(this);
            userBalanceModel.getProfile();
        }

    }



}
