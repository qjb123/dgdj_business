
package net.ezcx.dongguandaojia.business.bean.order;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Table(name = "userchange_avatarResponse")
public class myorder_listResponse extends DataBaseModel {

    @Column(name = "succeed")
    public int succeed;

    @Column(name = "error_code")
    public int error_code;

    public ArrayList<ORDERS> orders = new ArrayList<ORDERS>();

    @Column(name = "error_desc")
    public String error_desc;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.succeed = jsonObject.optInt("succeed");

        this.error_code = jsonObject.optInt("error_code");

        subItemArray = jsonObject.optJSONArray("orders");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.optJSONObject(i);
                ORDERS subItem = new ORDERS();
                subItem.fromJson(subItemObject);
                this.orders.add(subItem);
            }
        }
        this.error_desc = jsonObject.optString("error_desc");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("succeed", succeed);
        localItemObject.put("error_code", error_code);
        localItemObject.put("error_desc", error_desc);
        for (int i = 0; i < orders.size(); i++) {
            ORDERS itemData = orders.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("orders", itemJSONArray);
        return localItemObject;
    }

}
