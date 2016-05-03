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

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.insthub.O2OMobile.O2OMobileAppConst;
import com.insthub.O2OMobile.Protocol.messagelistRequest;
import com.insthub.O2OMobile.Protocol.messagelistResponse;
import com.insthub.O2OMobile.Protocol.messagesyslistRequest;
import com.insthub.O2OMobile.Protocol.messagesyslistResponse;
import com.insthub.O2OMobile.SESSION;

import net.ezcx.dongguandaojia.business.bean.SCHEDULE;
import net.ezcx.dongguandaojia.business.config.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleListModel extends BaseModel {

    public static final int NUMPERPAGE = 10;
    public ArrayList<SCHEDULE> SCHEDULEList = new ArrayList<SCHEDULE>();

    public ScheduleListModel(Context context) {
        super(context);
    }


    public void getList() {
        messagelistRequest request = new messagelistRequest();
        request.uid = SESSION.getInstance().uid;
        request.sid = SESSION.getInstance().sid;
        request.ver = O2OMobileAppConst.VERSION_CODE;
        request.by_no = 1;
        request.count = NUMPERPAGE;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ScheduleListModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        messagelistResponse response = new messagelistResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ;
                            ScheduleListModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            ScheduleListModel.this.callback(url, response.error_code, response.error_desc);
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
                    ScheduleListModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        messagelistResponse response = new messagelistResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {

                            ScheduleListModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            ScheduleListModel.this.callback(url, response.error_code, response.error_desc);
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
                    ScheduleListModel.this.callback(this, url, jo, status);
                    if (null != jo) {
                        messagesyslistResponse response = new messagesyslistResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ScheduleListModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            ScheduleListModel.this.callback(url, response.error_code, response.error_desc);
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
