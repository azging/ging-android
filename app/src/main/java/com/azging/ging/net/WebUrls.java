package com.azging.ging.net;

/**
 * Created by GG on 2017/5/19.
 */

public class WebUrls {

    private static String baseUrl = "http://47.93.28.215/ging";

    static String getUrl(String s) {
        return baseUrl + s;
    }

    //设置应用初始化数据
    static String set_config = "/api/v1/app/config/set/";

    //获取验证码
    static String authcode_send = "/api/v1/user/phone/authcode/send/";

    //使用手机号登录或注册
    static String login_phone = "/api/v1/user/login/phone/";

    //微信登录
    static String login_wx = "/api/v1/user/login/social/";

    //发布问题接口
    static String publish_question = "/api/v1/question/publish/";

    //根据quid获取问题   GET
    static String question_detail = "/api/v1/question/detail/{quid}";

    //最新问题列表
    static String new_question_list = "/api/v1/question/list/new/";

    //热门问题列表
    static String hot_question_list = "/api/v1/question/list/hot/";

    //附近问题列表
    static String nearby_question_list = "/api/v1/question/list/nearby/";
}
