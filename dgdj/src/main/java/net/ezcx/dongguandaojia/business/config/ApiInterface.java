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

package net.ezcx.dongguandaojia.business.config;

public class ApiInterface {

//    public static final String HOST_URL = "http://www.dgdjwh.com";//
    public static final String HOST_URL = "http://dgdjwh.ezcx.net";//


    public static final String USER_SIGNUP = "/api/user/signup";//注册
    public static final String USER_SIGNIN = "/api/user/signin";//登录
    public static final String USER_VERIFYCODE = "/api/user/verifycode";//获取验证码
    public static final String USER_SIGNOUT = "/api/user/signout";//登出
    public static final String FEEDBACK = "/api/user/feedback";//意见反馈     (加了未成功)
    public static final String USER_PROFILE = "/api/user/profile";//获取个人资料
    public static final String USER_CHANGE_AVATAR = "/api/user/avatar";//修改头像
    public static final String USER_THREE_LOGIN = "/api/user/threelogin";//第三方登录      (QQ成功)
    public static final String USER_CHANGE_PROFILE = "/api/user/changemess";//修改个人资料
    public static final String USER_CHANGE_PASSWORD = "/api/user/editsignin";//忘记密码(修改密码)
    public static final String MY_ORDER_LIST = "/api/orderlist/employeeorder";//我的订单列表
    public static final String MY_ORDER_INFO = "/api/orderlist/orderdetails";//我的订单详情
    public static final String USER_BALANCE = "api/user/changemess";
    public static final String MESSAGE_SYSLIST = "/message/syslist";
    public static final String PUSH_SWITCH = "/push/switch";
    public static final String MESSAGE_UNREAD_COUNT = "/message/unread-count";
    public static final String MESSAGE_LIST = "/message/list";
    public static final String WITHDRAW_MONEY = "/withdraw/money";
    public static final String ORDERLIST_PUBLISHED = "/orderlist/published";
    public static final String USER_LIST = "/user/list";
    public static final String ORDER_PAY = "/order/pay";
    public static final String COMMENT_SEND = "/comment/send";
    public static final String SERVICETYPE_LIST = "/servicetype/list";
    public static final String USER_VALIDCODE = "/user/validcode";
    public static final String ORDER_INFO = "/order/info";
    public static final String USER_INVITE_CODE = "/user/invite-code";
    public static final String USER_CERTIFY = "/user/certify";
    public static final String REPORT = "/report";
    public static final String SERVICECATEGORY_LIST = "/servicecategory/list";
    public static final String MYSERVICE_LIST = "/myservice/list";
    public static final String MYSERVICE_MODIFY = "/myservice/modify";
    public static final String COMMENT_LIST = "/comment/list";
    public static final String WITHDRAW_LIST = "/withdraw/list";
    public static final String USER_APPLY_SERVICE = "/user/apply-service";
    public static final String ORDER_PUBLISH = "/order/publish";
    public static final String ORDER_CONFIRM_PAY = "/order/confirm-pay";
    public static final String ORDERLIST_AROUND = "/orderlist/around";
    public static final String ORDERLIST_RECEIVED = "/orderlist/received";
    public static final String LOCATION_INFO = "/location/info";
    public static final String MESSAGE_READ = "/message/read";
    public static final String PUSH_UPDATE = "/push/update";
    public static final String ORDER_HISTORY = "/order/history";
    public static final String ORDER_CANCEL = "/order/cancel";
    public static final String ORDER_WORK_DONE = "/order/work-done";
    public static final String ORDER_ACCEPT = "/order/accept";

}