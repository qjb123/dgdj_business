
package net.ezcx.dongguandaojia.business.bean.wallet;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "ORDERDETAIL")
public class ORDERDETAIL extends DataBaseModel {

    @Column(name = "total")//总计
    public String total;
    @Column(name = "appliance_type")//家电类型(服务项目)
    public String appliance_type;
    @Column(name = "commission")//提成
    public String commission;
    @Column(name = "money")//金额
    public String money;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.total = jsonObject.optString("total");
        this.money = jsonObject.optString("money");
        this.appliance_type = jsonObject.optString("appliance_type");
        this.commission = jsonObject.optString("commission");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("total", total);
        localItemObject.put("appliance_type", appliance_type);
        localItemObject.put("commission", commission);
        localItemObject.put("money", money);

        return localItemObject;
    }

}
