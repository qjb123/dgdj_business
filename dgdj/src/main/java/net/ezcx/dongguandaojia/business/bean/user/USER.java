
package net.ezcx.dongguandaojia.business.bean.user;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;
import com.insthub.O2OMobile.Protocol.MY_CERTIFICATION;
import com.insthub.O2OMobile.Protocol.PHOTO;

@Table(name = "USER")
public class USER extends DataBaseModel {

    @Column(name = "comment_count")
    public String comment_count;

    @Column(name = "USER_id")
    public int id;

    @Column(name = "mobile_phone")
    public String mobile_phone;

    @Column(name = "user_group")
    public int user_group;

    @Column(name = "nickname")
    public String nickname;

    @Column(name = "age")
    public String age;

    @Column(name = "joined_at")
    public String joined_at;

    @Column(name = "comment_goodrate")
    public String comment_goodrate;

    @Column(name = "gender")
    public int gender;

    @Column(name = "avatar")
    public PHOTO avatar;

    @Column(name = "origin")//籍贯
    public String origin;

    @Column(name = "mobile")
    public String mobile;

    public ArrayList<MY_CERTIFICATION> my_certification = new ArrayList<MY_CERTIFICATION>();

    @Column(name = "profession ")
    public String profession;

    @Column(name = "current_service_price")
    public String current_service_price;

    @Column(name = "signature")
    public String signature;

    @Column(name = "address")
    public String address;

    @Column(name = "worktime")//工作经验
    public String worktime;

    @Column(name = "skill")//技能
    public String skill;

    @Column(name = "service_time")//服务时间
    public String service_time;

    @Column(name = "service_area")//服务范围
    public String service_area;

    @Column(name = "businee_circle")//常驻商圈
    public String businee_circle;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.service_time = jsonObject.optString("service_time");
        this.service_area = jsonObject.optString("service_area");
        this.businee_circle = jsonObject.optString("businee_circle");

        this.profession = jsonObject.optString("profession");

        this.user_group = jsonObject.optInt("user_group");

        this.nickname = jsonObject.optString("nickname");
        this.age = jsonObject.optString("age");
        this.comment_goodrate = jsonObject.optString("comment_goodrate");
        subItemArray = jsonObject.optJSONArray("my_certification");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                MY_CERTIFICATION subItem = new MY_CERTIFICATION();
                subItem.fromJson(subItemObject);
                this.my_certification.add(subItem);
            }
        }


        this.profession = jsonObject.optString("profession ");
        PHOTO avatar = new PHOTO();
        avatar.fromJson(jsonObject.optJSONObject("avatar"));
        this.avatar = avatar;

        this.id = jsonObject.optInt("id");

        this.mobile_phone = jsonObject.optString("mobile_phone");

        this.current_service_price = jsonObject.optString("current_service_price");

        this.joined_at = jsonObject.optString("joined_at");

        this.gender = jsonObject.optInt("gender");

        this.origin = jsonObject.optString("origin");
        this.worktime = jsonObject.optString("worktime");

        this.mobile = jsonObject.optString("mobile");

        this.skill = jsonObject.optString("skill");

        this.signature = jsonObject.optString("signature");

        this.address = jsonObject.optString("address");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("comment_count", comment_count);
        localItemObject.put("user_group", user_group);
        localItemObject.put("nickname", nickname);
        for (int i = 0; i < my_certification.size(); i++) {
            MY_CERTIFICATION itemData = my_certification.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("my_certification", itemJSONArray);
        localItemObject.put("profession ", profession);
        if (null != avatar) {
            localItemObject.put("avatar", avatar.toJson());
        }
        localItemObject.put("id", id);
        localItemObject.put("mobile_phone", mobile_phone);
        localItemObject.put("current_service_price", current_service_price);
        localItemObject.put("joined_at", joined_at);
        localItemObject.put("gender", gender);

        localItemObject.put("origin", origin);
        localItemObject.put("worktime", worktime);

        localItemObject.put("mobile", mobile);
        localItemObject.put("skill", skill);

        localItemObject.put("signature", signature);
        localItemObject.put("address", address);

        localItemObject.put("service_time", service_time);
        localItemObject.put("service_area", service_area);
        localItemObject.put("businee_circle", businee_circle);
        return localItemObject;
    }

}
