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

package net.ezcx.dongguandaojia.business.Model.order;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.activeandroid.util.Log;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.O2OMobile.O2OMobileAppConst;
import com.insthub.O2OMobile.Protocol.messagelistRequest;
import com.insthub.O2OMobile.Protocol.messagelistResponse;
import com.insthub.O2OMobile.Protocol.messagesyslistRequest;
import com.insthub.O2OMobile.Protocol.messagesyslistResponse;
import com.insthub.O2OMobile.SESSION;

import net.ezcx.dongguandaojia.business.bean.SCHEDULE;
import net.ezcx.dongguandaojia.business.bean.order.ORDERS;
import net.ezcx.dongguandaojia.business.bean.order.myorder_infoRequest;
import net.ezcx.dongguandaojia.business.bean.order.myorder_info_real_Response;
import net.ezcx.dongguandaojia.business.bean.order.myorder_listRequest;
import net.ezcx.dongguandaojia.business.bean.order.myorder_listResponse;
import net.ezcx.dongguandaojia.business.config.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyOderListModel extends BaseModel {

    public static final int NUMPERPAGE = 10;
    public static ArrayList<ORDERS> ORDERSList = new ArrayList<ORDERS>();
    public static ArrayList<ORDERS> ORDERSList2 = new ArrayList<ORDERS>();

    public MyOderListModel(Context context) {
        super(context);
    }

    /**
     * 订单列表
     *
     * @param page
     */
    public void getMyOrderList(int page, final int statuss,final int flag) {
        myorder_listRequest request = new myorder_listRequest();
        request.uid = SESSION.getInstance().uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MyOderListModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        myorder_listResponse response = new myorder_listResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            if (flag == 0) {
                                if(statuss == 1){
                                    ORDERSList.clear();
                                }else if(statuss == 2){
                                    ORDERSList2.clear();
                                }
                            }

                            if(statuss == 1){
                                for(int i = 0;i<response.orders.size();i++){
                                    ORDERSList.add(response.orders.get(i));
                                }
                            }else if(statuss == 2){
                                for(int i = 0;i<response.orders.size();i++){
                                    ORDERSList2.add(response.orders.get(i));
                                }
                            }
                            MyOderListModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            MyOderListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }
                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject requestJson = request.toJson();
            params.put("json", requestJson.toString());
            params.put("page", page);
            params.put("status", statuss);
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.MY_ORDER_LIST).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }

    /**
     * 订单列表
     *
     * @param page
     * @param status
     */
    public void getMyOrderList2(int page, int status,final int flag) {
        myorder_listRequest request = new myorder_listRequest();
        request.uid = SESSION.getInstance().uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MyOderListModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        myorder_listResponse response = new myorder_listResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            if (flag == 0) {
                                ORDERSList2.clear();
                            }
                            //ORDERSList = response.orders;
                            for(int i = 0;i<response.orders.size();i++){
                                ORDERSList2.add(response.orders.get(i));
                            }
                            MyOderListModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            MyOderListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }
                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject requestJson = request.toJson();
            params.put("json", requestJson.toString());
            params.put("page", page);
            params.put("status", status);
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.MY_ORDER_LIST).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }



    /**
     * 订单详情
     */
    public void getOrderInfo(int order_id) {
        myorder_infoRequest request = new myorder_infoRequest();
        request.order_id = order_id;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MyOderListModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        myorder_info_real_Response response = new myorder_info_real_Response();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            MyOderListModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            MyOderListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }
                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject requestJson = request.toJson();
            params.put("json", requestJson.toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.MY_ORDER_INFO).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }

    public void getListMore() {
        messagelistRequest request = new messagelistRequest();
        request.uid = SESSION.getInstance().uid;
        request.sid = SESSION.getInstance().sid;
        request.ver = O2OMobileAppConst.VERSION_CODE;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MyOderListModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        messagelistResponse response = new messagelistResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {

                            MyOderListModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            MyOderListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }
                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject requestJson = request.toJson();
            requestJson.remove("by_id");
            params.put("json", requestJson.toString());
        } catch (JSONException e) {

        }
        if (isSendingMessage(ApiInterface.MESSAGE_LIST)) {
            return;
        }
        cb.url(ApiInterface.MESSAGE_LIST).type(JSONObject.class).params(params);
        ajax(cb);
    }


    public void getMessageSysListMore() {
        messagesyslistRequest request = new messagesyslistRequest();
        request.uid = SESSION.getInstance().uid;
        request.sid = SESSION.getInstance().sid;
        request.ver = O2OMobileAppConst.VERSION_CODE;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MyOderListModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        messagesyslistResponse response = new messagesyslistResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            MyOderListModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            MyOderListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }
                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject requestJson = request.toJson();
            requestJson.remove("by_id");
            params.put("json", requestJson.toString());
        } catch (JSONException e) {

        }
        if (isSendingMessage(ApiInterface.MESSAGE_SYSLIST)) {
            return;
        }
        cb.url(ApiInterface.MESSAGE_SYSLIST).type(JSONObject.class).params(params);
        ajax(cb);
    }
}
