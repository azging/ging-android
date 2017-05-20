package com.azging.ging.utils.net;

/**
 * Created by GG on 2017/5/19.
 */

public class WebUrls {

    private static String baseUrl = "http://47.93.28.215/ging";

    static String getUrl(String s) {
        return baseUrl + s;
    }

    //获取验证码
    static String authcode_send = "/api/v1/user/phone/authcode/send/";

    //使用手机号登录或注册
    static String login_phone = "/api/v1/user/login/phone/";

    static String login_wx="/api/v1/user/login/wechat/";
}
