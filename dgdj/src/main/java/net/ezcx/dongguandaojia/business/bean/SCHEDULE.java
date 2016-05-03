
package net.ezcx.dongguandaojia.business.bean;

import android.util.Log;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "SCHEDULE")
public class SCHEDULE extends DataBaseModel {

    @Column(name = "date")
    public String date;

    @Column(name = "address")
    public String address;
    @Column(name = "appliance_type")
    public String appliance_type;//家电类型
    @Column(name = "describe")
    public String describe;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.date = jsonObject.optString("date");

        this.address = jsonObject.optString("address");

        this.appliance_type = jsonObject.optString("appliance_type");
        this.describe = jsonObject.optString("describe");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("date", date);
        localItemObject.put("address", address);
        localItemObject.put("appliance_type", appliance_type);
        localItemObject.put("describe", describe);

        return localItemObject;
    }

}
