
package net.ezcx.dongguandaojia.business.bean;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;
import com.insthub.O2OMobile.Protocol.MY_CERTIFICATION;
import com.insthub.O2OMobile.Protocol.PHOTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Table(name = "EVALUATE")
public class EVALUATE extends DataBaseModel {

    @Column(name = "evaluate_content")//评价内容
    public String evaluate_content;
    @Column(name = "date")//日期
    public String date;
    @Column(name = "nickname")//姓名
    public String nickname;
    @Column(name = "server_desc")//服务描述
    public String server_desc;
    @Column(name = "my_reply")//我的回复
    public String my_reply;


    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.nickname = jsonObject.optString("nickname");
        this.date = jsonObject.optString("date");
        this.evaluate_content = jsonObject.optString("evaluate_content");
        this.server_desc = jsonObject.optString("server_desc");
        this.my_reply = jsonObject.optString("my_reply");


        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("nickname", nickname);
        localItemObject.put("date", date);
        localItemObject.put("evaluate_content", evaluate_content);
        localItemObject.put("server_desc", server_desc);
        localItemObject.put("my_reply", my_reply);

        return localItemObject;
    }

}
