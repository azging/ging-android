package com.azging.ging.net;

/**
 * Created by GG on 2017/5/19.
 */

public class WebUrls {

    private static String baseUrl_offline = "http://www.azging.com/dev";

    private static String baseUrl_online = "http://www.azging.com/ging";

    static String getUrl(String s) {
        return baseUrl_offline + s;
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
    static String question_detail = "/api/v1/question/detail/";

    //根据quid获取回答列表
    static String answer_list = "/api/v1/question/answer/list/";

    //最新问题列表
    static String new_question_list = "/api/v1/question/list/new/";

    //热门问题列表
    static String hot_question_list = "/api/v1/question/list/hot/";

    //附近问题列表
    static String nearby_question_list = "/api/v1/question/list/nearby/";

    //用户退出登录
    static String logout = "/api/v1/user/logout/";

    //我的提问列表
    static String user_question_list = "/api/v1/user/question/list/";

    //我的回答列表
    static String user_answer_list = "/api/v1/user/answer/list/";

    //新建订单
    static String add_order = "/api/v1/wallet/order/add/";

    //用户信息修改
    static String user_info_update="/api/v1/user/info/update/";

    //提交反馈意见
    static String add_feedback="/api/v1/app/feedback/add/";

    //获取七牛上传的 token
    static String qiniu_uptoken="/api/v1/util/qiniu/uptoken/";

    //添加回答
    static String add_answer="/api/v1/question/answer/add/";
}
