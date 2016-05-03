package net.ezcx.dongguandaojia.business.bean.order;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 安卓 on 2016/4/5.
 */
@Table(name = "NEARORDERS")
public class NEARORDERS extends DataBaseModel {
    @Column(name = "name")//姓名
    public String name;
    @Column(name = "need_position")//需求职位
    public String need_position;
    @Column(name = "money")//金额
    public String money;
    @Column(name = "distance")//距离
    public String distance;
    @Column(name = "time")//时间
    public String time;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.name = jsonObject.optString("name");

        this.need_position = jsonObject.optString("need_position");

        this.money = jsonObject.optString("money");
        this.distance = jsonObject.optString("distance");
        this.time = jsonObject.optString("time");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("name", name);
        localItemObject.put("need_position", need_position);
        localItemObject.put("money", money);
        localItemObject.put("distance", distance);
        localItemObject.put("time", time);

        return localItemObject;
    }

}
