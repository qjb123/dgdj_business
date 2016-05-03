package net.ezcx.dongguandaojia.business.bean.order;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 安卓 on 2016/4/5.
 */
@Table(name = "ORDERS")
public class ORDERS extends DataBaseModel {
    @Column(name = "_id")//订单id
    public int id;
    @Column(name = "failure_cause")//故障原因
    public String failure_cause;
    @Column(name = "order_sn")//订单号
    public String order_sn;
    @Column(name = "order_status")//订单状态
    public int order_status;
    @Column(name = "appointment_time")//服务时间
    public String appointment_time;
    @Column(name = "service_name")//服务类型
    public String service_name;
    @Column(name = "address")//服务地址
    public String address;
    @Column(name = "imgurl")//图片
    public String imgurl;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.failure_cause = jsonObject.optString("failure_cause");

        this.appointment_time = jsonObject.optString("appointment_time");
        this.id = jsonObject.optInt("id");
        this.service_name = jsonObject.optString("service_name");
        this.address = jsonObject.optString("address");
        this.order_sn = jsonObject.optString("order_sn");
        this.order_status = jsonObject.optInt("order_status");
        this.imgurl = jsonObject.optString("imgurl");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("failure_cause", failure_cause);
        localItemObject.put("appointment_time", appointment_time);
        localItemObject.put("service_name", service_name);
        localItemObject.put("address", address);
        localItemObject.put("order_sn", order_sn);
        localItemObject.put("order_status", order_status);
        localItemObject.put("imgurl", imgurl);
        localItemObject.put("id", id);

        return localItemObject;
    }

}
