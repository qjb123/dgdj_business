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

package net.ezcx.dongguandaojia.business.Model;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.O2OMobile.APIErrorCode;

import net.ezcx.dongguandaojia.business.Application.O2OMobile;

import com.insthub.O2OMobile.Model.PushModel;
import com.insthub.O2OMobile.O2OMobileAppConst;

import net.ezcx.dongguandaojia.business.bean.user.userchange_passwordRequest;
import net.ezcx.dongguandaojia.business.bean.user.userchange_profileRequest;
import net.ezcx.dongguandaojia.business.bean.user.userchange_profileResponse;
import net.ezcx.dongguandaojia.business.bean.user.userforget_passwordRequest;
import net.ezcx.dongguandaojia.business.bean.user.userthreeloginRequest;
import net.ezcx.dongguandaojia.business.config.ApiInterface;

import com.insthub.O2OMobile.Protocol.CONFIG;
import com.insthub.O2OMobile.Protocol.ENUM_USER_GENDER;
import com.insthub.O2OMobile.Protocol.LOCATION;

import net.ezcx.dongguandaojia.business.bean.user.USER;

import com.insthub.O2OMobile.Protocol.usercertifyRequest;
import com.insthub.O2OMobile.Protocol.usercertifyResponse;
import com.insthub.O2OMobile.Protocol.userchange_passwordResponse;
import com.insthub.O2OMobile.Protocol.userinvite_codeRequest;
import com.insthub.O2OMobile.Protocol.userinvite_codeResponse;

import net.ezcx.dongguandaojia.business.bean.user.usersigninRequest;
import net.ezcx.dongguandaojia.business.bean.user.usersigninResponse;

import net.ezcx.dongguandaojia.business.bean.user.usersignupRequest;

import net.ezcx.dongguandaojia.business.bean.user.usersignupResponse;
import net.ezcx.dongguandaojia.business.utils.PreferenceUtil;

import com.insthub.O2OMobile.Protocol.userverifycodeRequest;
import com.insthub.O2OMobile.Protocol.userverifycodeResponse;
import com.insthub.O2OMobile.SESSION;
import com.insthub.O2OMobile.Utils.LocationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UserModel extends BaseModel {
    private Context context;
    public String invite_code;

    public UserModel(Context context) {
        super(context);
        this.context = context;

    }

    /**
     * 登录网络请求
     *
     * @param mobile
     * @param password
     */
    public void login(String mobile, String password) {

        usersigninRequest request = new usersigninRequest();
        request.mobile_phone = mobile;
        request.password = password;
        request.UUID = O2OMobile.getDeviceId(context);
        request.platform = "android";
        request.role = 1;
        LOCATION location = new LOCATION();
        location.lat = LocationManager.getLatitude();
        location.lon = LocationManager.getLongitude();
        request.location = location;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);
                    Log.e("获取登录信息",jo+"");
                    if (null != jo) {
                        usersigninResponse usersigninResponse = new usersigninResponse();
                        usersigninResponse.fromJson(jo);
                        if (usersigninResponse.succeed == 1) {
                            USER user = usersigninResponse.user;
                            CONFIG config = usersigninResponse.config;
                            int push = config.push;
                            if (push == 1) {
                                editor.putBoolean("push", true);
                            } else {
                                editor.putBoolean("push", false);
                            }
                            editor.putBoolean("isLogin", true);
                            editor.putString("user", user.toJson().toString());
                            editor.putInt("uid", usersigninResponse.user.id);
                            editor.putString("sid", usersigninResponse.sid);
                            editor.putString("nickename", user.nickname);
                            editor.commit();
                            SESSION.getInstance().uid = usersigninResponse.user.id;
                            SESSION.getInstance().sid = usersigninResponse.sid;

                            PushModel pushModel = new PushModel(context);
                            pushModel.update();
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if (usersigninResponse.error_code == APIErrorCode.MOBILE_NOT_EXIST || usersigninResponse.error_code == APIErrorCode.ERROR_PASSWORD) {
                                UserModel.this.OnMessageResponse(url, jo, status);
                            } else {
                                UserModel.this.callback(url, usersigninResponse.error_code, usersigninResponse.error_desc);
                            }

                        }

                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
        }
        //传url和参数
        cb.url(ApiInterface.USER_SIGNIN).type(JSONObject.class).
                params(params);
        ajaxProgress(cb);//发送网络请求

    }

    /**
     * 第三方登录网络请求
     */
    public void threeLogin(String uid, String access_token, int loginsign) {

        userthreeloginRequest request = new userthreeloginRequest();
        request.usid = uid;
        request.access_token = access_token;
        request.platform = "android";
        request.appid = "1105215561";
        request.loginsign = loginsign;
        request.role = 1;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        usersigninResponse usersigninResponse = new usersigninResponse();
                        usersigninResponse.fromJson(jo);
                        if (usersigninResponse.succeed == 1) {
                            USER user = usersigninResponse.user;
                            CONFIG config = usersigninResponse.config;
                            int push = config.push;
                            if (push == 1) {
                                editor.putBoolean("push", true);
                            } else {
                                editor.putBoolean("push", false);
                            }
                            editor.putBoolean("isLogin", true);
                            editor.putString("user", user.toJson().toString());
                            editor.putInt("uid", usersigninResponse.user.id);
                            editor.putString("sid", usersigninResponse.sid);
                            editor.putString("nickename", user.nickname);
                            editor.commit();
                            SESSION.getInstance().uid = usersigninResponse.user.id;
                            SESSION.getInstance().sid = usersigninResponse.sid;

                            PushModel pushModel = new PushModel(context);
                            pushModel.update();
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if (usersigninResponse.error_code == APIErrorCode.MOBILE_NOT_EXIST || usersigninResponse.error_code == APIErrorCode.ERROR_PASSWORD) {
                                UserModel.this.OnMessageResponse(url, jo, status);
                            } else {
                                UserModel.this.callback(url, usersigninResponse.error_code, usersigninResponse.error_desc);
                            }

                        }

                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
        }
        //传url和参数
        cb.url(ApiInterface.USER_THREE_LOGIN).type(JSONObject.class).
                params(params);
        ajaxProgress(cb);//发送网络请求

    }

    /**
     * 获取验证码
     *
     * @param mobile
     */
    public void getVerifyCode(String mobile, String verify_type) {
        userverifycodeRequest request = new userverifycodeRequest();
        request.mobile_phone = mobile;
        request.verify_type = verify_type;
        request.ver = O2OMobileAppConst.VERSION_CODE;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        userverifycodeResponse response = new userverifycodeResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            UserModel.this.callback(url, response.error_code, response.error_desc);
                            if (response.error_code == APIErrorCode.MOBILE_EXIST) {
                                UserModel.this.OnMessageResponse(url, jo, status);
                            }
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.USER_VERIFYCODE).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }

   /* public void valid_verifycode(String mobile, String verifycode) {
        uservalidcodeRequest request = new uservalidcodeRequest();
        request.mobile_phone = mobile;
        request.verify_code = verifycode;
        request.ver = O2OMobileAppConst.VERSION_CODE;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        uservalidcodeResponse response = new uservalidcodeResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            UserModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.USER_VALIDCODE).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
*/

    /**
     * 注册网络请求
     *
     * @param mobile
     * @param password
     * @param verify_code
     * @param invite_code
     */

    public void signup(String mobile, String password, String verify_code, String invite_code) {
        usersignupRequest request = new usersignupRequest();
        request.mobile_phone = mobile;
        request.verify_code = verify_code;
        request.invite_code = invite_code;
        request.password = password;
        request.platform = "android";
        request.role = 1;
//        LOCATION location = new LOCATION();
//        location.lat = LocationManager.getLatitude();
//        location.lon = LocationManager.getLongitude();
//        request.location = location;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        usersignupResponse response = new usersignupResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            USER user = response.user;
                            CONFIG config = response.config;
                            int push = config.push;
                            if (push == 1) {
                                editor.putBoolean("push", true);
                            } else {
                                editor.putBoolean("push", false);
                            }
                            editor.putBoolean("isLogin", true);
                            editor.putString("user", user.toJson().toString());
                            editor.putInt("uid", response.user.id);
                            editor.putString("sid", response.sid);
                            editor.commit();
                            SESSION.getInstance().uid = response.user.id;
                            SESSION.getInstance().sid = response.sid;
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if (response.error_code == APIErrorCode.NICKNAME_EXIST) {

                                UserModel.this.OnMessageResponse(url, jo, status);
                            }
                            UserModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.USER_SIGNUP).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    /**
     * 修改资料
     *
     * @param nickname
     */
    public void changeNickname(String nickname,String type) {

        userchange_profileRequest request = new userchange_profileRequest();
        if("1".equals(type)){//昵称
            request.nickname = nickname;
        }else if("2".equals(type)){    //性别
            if ("男".equals(nickname)) {
                request.gender = "0";
            } else {
                request.gender = "1";
            }
        }else  if("3".equals(type)){//籍贯
            request.origin = nickname;
        }else  if("4".equals(type)){//年龄
            request.age = nickname;
        }else  if("5".equals(type)){//工作经验
            request.worktime = nickname;
        }else  if("6".equals(type)){//联系方式
            request.mobile = nickname;
        }else  if("7".equals(type)){//技能
            request.skill = nickname;
        }else  if("8".equals(type)){//家庭地址
            request.address = nickname;
        }else  if("9".equals(type)){//服务范围
            request.service_area = nickname;
        }else  if("10".equals(type)){//常驻商圈
            request.businee_circle = nickname;
        }else  if("11".equals(type)){//服务时间
            request.service_time = nickname;
        }
        request.uid = SESSION.getInstance().uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);

                    Log.e("获取修改信息", jo + "");

                    if (null != jo) {
                        userchange_profileResponse response = new userchange_profileResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            USER user = response.user;
                            editor.putString("user", user.toJson().toString());
                            editor.commit();
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            UserModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }
        Log.e("获取传递参数信息", params + "");
        cb.url(ApiInterface.USER_CHANGE_PROFILE).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }

   /* *//**
     * 修改简介,职业,住址,性别
     *
     * @param signture
     *//*
    public void changeSignature(String signture) {
        userchange_profileRequest request = new userchange_profileRequest();
        request.signature = signture;
        request.profession = signture;
        request.address = signture;
        if ("男".equals(signture)) {
            request.gender = 0;
        } else {
            request.gender = 1;
        }
        request.uid = SESSION.getInstance().uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        userchange_profileResponse response = new userchange_profileResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            USER user = response.user;
                            editor.putString("user", user.toJson().toString());
                            editor.commit();
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            UserModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.USER_CHANGE_PROFILE).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }

    *//**
     * 修改电话号码
     *
     * @param mobile_phone
     *//*
    public void changeMobilePhone(String mobile_phone) {
        userchange_profileRequest request = new userchange_profileRequest();
        request.mobile_phone = mobile_phone;
        request.uid = SESSION.getInstance().uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        userchange_profileResponse response = new userchange_profileResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            USER user = response.user;
                            editor.putString("user", user.toJson().toString());
                            editor.commit();
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            UserModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.USER_CHANGE_PROFILE).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }*/

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param password_again
     */
    public void changePassword(String oldPassword, String newPassword, String password_again) {
        userchange_passwordRequest request = new userchange_passwordRequest();
        request.password_old = oldPassword;
        request.password_new = newPassword;
        request.password_again = password_again;
        request.uid = SESSION.getInstance().uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        userchange_profileResponse response = new userchange_profileResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if (response.error_code == APIErrorCode.ERROR_PASSWORD) {
                                UserModel.this.OnMessageResponse(url, jo, status);
                            }
                            UserModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.USER_CHANGE_PROFILE).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }

    /**
     * 忘记密码
     */
    public void forgetPassword(String mobile_phone, String password_n, String password_a, String verify_code) {
        userforget_passwordRequest request = new userforget_passwordRequest();
        request.mobile_phone = mobile_phone;
        request.password_n = password_n;
        request.password_a = password_a;
        request.verify_code = verify_code;
     /*   request.sid = SESSION.getInstance().sid;
        request.uid = SESSION.getInstance().uid;
        request.ver = O2OMobileAppConst.VERSION_CODE;*/
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        userchange_passwordResponse response = new userchange_passwordResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if (response.error_code == APIErrorCode.ERROR_PASSWORD) {
                                UserModel.this.OnMessageResponse(url, jo, status);
                            }
                            UserModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.USER_CHANGE_PASSWORD).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }

    public void getInviteCode() {
        userinvite_codeRequest request = new userinvite_codeRequest();
        request.sid = SESSION.getInstance().sid;
        request.uid = SESSION.getInstance().uid;
        request.ver = O2OMobileAppConst.VERSION_CODE;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        userinvite_codeResponse response = new userinvite_codeResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            invite_code = response.invite_code;
                            editor.putString("invitecode_" + SESSION.getInstance().uid, invite_code);
                            editor.commit();
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            UserModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.USER_INVITE_CODE).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }

    public void certify(String name, String identity, String bankId, ENUM_USER_GENDER gender, File avatar) {
        usercertifyRequest request = new usercertifyRequest();
        request.name = name;
        request.identity_card = identity;
        request.bankcard = bankId;

        request.sid = SESSION.getInstance().sid;
        request.uid = SESSION.getInstance().uid;
        request.gender = gender.value();
        request.ver = O2OMobileAppConst.VERSION_CODE;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    UserModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        usercertifyResponse response = new usercertifyResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            UserModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            UserModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put("json", request.toJson().toString());
            if (null != avatar) {
                params.put("avatar", avatar);
            }

        } catch (JSONException e) {

        }

        cb.url(ApiInterface.USER_CERTIFY).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }
}

