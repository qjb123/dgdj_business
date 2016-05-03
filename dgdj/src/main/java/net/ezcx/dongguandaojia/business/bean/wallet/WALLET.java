
package net.ezcx.dongguandaojia.business.bean.wallet;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "WALLET")
public class WALLET extends DataBaseModel {

    @Column(name = "date")
    public String date;

    @Column(name = "user_name")
    public String user_name;
    @Column(name = "appliance_type")
    public String appliance_type;//家电类型
    @Column(name = "describe")
    public String describe;
    @Column(name = "money")
    public String money;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.date = jsonObject.optString("date");

        this.user_name = jsonObject.optString("user_name");
        this.money = jsonObject.optString("money");

        this.appliance_type = jsonObject.optString("appliance_type");
        this.describe = jsonObject.optString("describe");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("date", date);
        localItemObject.put("user_name", user_name);
        localItemObject.put("appliance_type", appliance_type);
        localItemObject.put("describe", describe);
        localItemObject.put("money", money);

        return localItemObject;
    }

}
