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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "userchange_profileRequest")
public class userchange_profileRequest extends DataBaseModel {



    @Column(name = "uid")
    public int uid;

    @Column(name = "worktime")//工作经验
    public String worktime;

    @Column(name = "age")//年龄
    public String age;

    @Column(name = "skill")//技能
    public String skill;

    @Column(name = "service_time")//服务时间
    public String service_time;

    @Column(name = "service_area")//服务范围
    public String service_area;

    @Column(name = "businee_circle")//常驻商圈
    public String businee_circle;

    @Column(name = "origin")//籍贯
    public String origin;

    @Column(name = "address")
    public String address;

    @Column(name = "nickname")
    public String nickname;

    @Column(name = "gender")//性别
    public String gender;

    @Column(name = "mobile")
    public String mobile;



    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("uid", uid);
        localItemObject.put("worktime", worktime);
        localItemObject.put("age", age);
        localItemObject.put("skill", skill);
        localItemObject.put("service_time", service_time);
        localItemObject.put("service_area", service_area);
        localItemObject.put("businee_circle", businee_circle);
        localItemObject.put("origin", origin);
        localItemObject.put("address", address);
        localItemObject.put("nickname", nickname);
        localItemObject.put("gender", gender);
        localItemObject.put("mobile", mobile);

        return localItemObject;
    }

}
