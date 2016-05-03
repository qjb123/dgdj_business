
package net.ezcx.dongguandaojia.business.bean.order;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "myorder_info_real_Response")
public class myorder_info_real_Response extends DataBaseModel {
    @Column(name = "error_code")
    public int error_code;

    @Column(name = "error_desc")
    public String error_desc;
    @Column(name = "succeed")
    public int succeed;
    @Column(name = "time_usage")
    public String time_usage;
    @Column(name = "order_details")
    public myorder_infoResponse order_details;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }


        this.succeed = jsonObject.optInt("succeed");
        this.time_usage = jsonObject.optString("time_usage");
        myorder_infoResponse subItem = new myorder_infoResponse();
        subItem.fromJson(jsonObject.optJSONObject("order_details"));
        this.order_details = subItem;

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();

        localItemObject.put("succeed", succeed);
        localItemObject.put("time_usage", time_usage);
        localItemObject.put("order_details", order_details.toJson().toString());

        return localItemObject;
    }

}
