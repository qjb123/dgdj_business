//
//       _/_/_/                      _/            _/_/_/_/_/
//    _/          _/_/      _/_/    _/  _/              _/      _/_/      _/_/
//   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/              _/      _/    _/  _/    _/
//  _/    _/  _/        _/        _/  _/          _/        _/    _/  _/    _/
//   _/_/_/    _/_/_/    _/_/_/  _/    _/      _/_/_/_/_/    _/_/      _/_/
//
//
//  Copyright (c) 2015-2016, Geek Zoo Studio
//  http://www.geek-zoo.com
//
//
//  Permission is hereby granted, free of charge, to any person obtaining a
//  copy of this software and associated documentation files (the "Software"),
//  to deal in the Software without restriction, including without limitation
//  the rights to use, copy, modify, merge, publish, distribute, sublicense,
//  and/or sell copies of the Software, and to permit persons to whom the
//  Software is furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//  IN THE SOFTWARE.
//

package net.ezcx.dongguandaojia.business.bean.user;

import com.external.activeandroid.DataBaseModel;
import com.external.activeandroid.annotation.Column;
import com.external.activeandroid.annotation.Table;
import com.insthub.O2OMobile.Protocol.LOCATION;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "usersigninRequest")
public class usersigninRequest extends DataBaseModel {

    @Column(name = "platform")
    public String platform;

    @Column(name = "mobile_phone")
    public String mobile_phone;

    @Column(name = "location")
    public LOCATION location;

    @Column(name = "UUID")
    public String UUID;

    @Column(name = "role")
    public int role;

    @Column(name = "password")
    public String password;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.platform = jsonObject.optString("platform");

        this.mobile_phone = jsonObject.optString("mobile_phone");
        LOCATION location = new LOCATION();
        location.fromJson(jsonObject.optJSONObject("location"));
        this.location = location;

        this.UUID = jsonObject.optString("UUID");

        this.role = jsonObject.optInt("role");

        this.password = jsonObject.optString("password");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("platform", platform);
        localItemObject.put("mobile_phone", mobile_phone);
        if (null != location) {
            localItemObject.put("location", location.toJson());
        }
        localItemObject.put("UUID", UUID);
        localItemObject.put("role", role);
        localItemObject.put("password", password);
        return localItemObject;
    }

}
