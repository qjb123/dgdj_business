package net.ezcx.dongguandaojia.business.bean.order;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 安卓 on 2016/4/5.
 */
@Table(name = "ORDERSINFO")
public class ORDERSINFO extends DataBaseModel {

    @Column(name = "number")//订单数量
    public int number;
    @Column(name = "attr_name")//家电类型
    public String attr_name;
    @Column(name = "attr_value")//故障原因
    public String attr_value;
    @Column(name = "price")//价格
    public String price;
    @Column(name = "imgurl")//图片
    public String imgurl;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        this.price = jsonObject.optString("price");
        this.number = jsonObject.optInt("number");
        this.imgurl = jsonObject.optString("imgurl");
        this.attr_name = jsonObject.optString("attr_name");
        this.attr_value = jsonObject.optString("attr_value");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("price", price);
        localItemObject.put("number", number);
        localItemObject.put("imgurl", imgurl);
        localItemObject.put("attr_name", attr_name);
        localItemObject.put("attr_value", attr_value);

        return localItemObject;
    }

}
