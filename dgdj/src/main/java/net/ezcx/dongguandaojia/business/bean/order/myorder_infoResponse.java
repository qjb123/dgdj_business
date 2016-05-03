
package net.ezcx.dongguandaojia.business.bean.order;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Table(name = "myorder_infoResponse")
public class myorder_infoResponse extends DataBaseModel {

    @Column(name = "order_sn")//订单号
    public String order_sn;
    @Column(name = "order_status")//订单状态
    public int order_status;
    @Column(name = "appointment_time")//服务时间
    public String appointment_time;
    @Column(name = "service_name")//服务类型
    public String service_name;
    @Column(name = "user_mobile")//接单人号码
    public String user_mobile;
    @Column(name = "name")//接单人名字
    public String name;
    @Column(name = "address")// address:收货人地址
    public String address;
    @Column(name = "mobile")// 收货人电话
    public String mobile;
    @Column(name = "consignee")// 收货人名字
    public String consignee;
    @Column(name = "total")//总价
    public String total;
    public ArrayList<ORDERSINFO> orderservice = new ArrayList<ORDERSINFO>();

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.order_sn = jsonObject.optString("order_sn");
        this.order_status = jsonObject.optInt("order_status");
        this.appointment_time = jsonObject.optString("appointment_time");
        this.service_name = jsonObject.optString("service_name");
        this.user_mobile = jsonObject.optString("user_mobile");
        this.name = jsonObject.optString("name");
        this.address = jsonObject.optString("address");
        this.mobile = jsonObject.optString("mobile");
        this.consignee = jsonObject.optString("consignee");
        this.total = jsonObject.optString("total");

        subItemArray = jsonObject.optJSONArray("orderservice");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.optJSONObject(i);
                ORDERSINFO subItem = new ORDERSINFO();
                subItem.fromJson(subItemObject);
                this.orderservice.add(subItem);
            }
        }
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("order_sn", order_sn);
        localItemObject.put("order_status", order_status);
        localItemObject.put("appointment_time", appointment_time);
        localItemObject.put("service_name", service_name);
        localItemObject.put("user_mobile", user_mobile);
        localItemObject.put("name", name);
        localItemObject.put("address", address);
        localItemObject.put("mobile", mobile);
        localItemObject.put("consignee", consignee);
        localItemObject.put("total", total);
        for (int i = 0; i < orderservice.size(); i++) {
            ORDERSINFO itemData = orderservice.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("orderservice", itemJSONArray);
        return localItemObject;
    }

}
