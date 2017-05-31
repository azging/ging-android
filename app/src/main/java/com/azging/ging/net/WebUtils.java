package com.azging.ging.net;

import android.content.Context;

import com.azging.ging.bean.AnswerWrapperListBean;
import com.azging.ging.bean.AuthCodeBean;
import com.azging.ging.bean.CreateUserWrapper;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.bean.QuestionWrapperListBean;
import com.azging.ging.bean.UserBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

/**
 * Created by GG on 2017/5/19.
 */

public class WebUtils extends WebBase {
    private Context mContext;

    public WebUtils(Context context) {
        mContext = context;
    }


    public void setConfig(JsonCallBack callback) {
        OkGo.post(WebUrls.getUrl(WebUrls.set_config))
                .tag(mContext)
                .cacheKey("BaseApp")
                .execute(callback);
    }


    /**
     * 发送验证码
     *
     * @param key
     * @param telephone
     * @param callback
     */

    public void sentAuthcode(String key, String telephone, JsonCallBack<GingResponse<AuthCodeBean>> callback) {
        OkGo.post(WebUrls.getUrl(WebUrls.authcode_send))
                .tag(mContext)
                .cacheKey(key)
                .cacheMode(CacheMode.NO_CACHE)
                .params("Telephone", telephone)
                .execute(callback);
    }

    /**
     * 手机号 验证码登录
     *
     * @param key
     * @param telephone
     * @param authcode
     * @param callback
     */

    public void phoneLogin(String key, String telephone, String authcode, JsonCallBack<GingResponse<CreateUserWrapper>> callback) {
        OkGo.post(WebUrls.getUrl(WebUrls.login_phone))
                .tag(mContext)
                .cacheKey(key)
                .cacheMode(CacheMode.NO_CACHE)
                .params("Telephone", telephone)
                .params("AuthCode", authcode)
                .execute(callback);
    }

    /**
     * 微信登录
     *
     * @param key
     * @param wechatId  微信id
     * @param nick      微信昵称
     * @param avatarUrl 微信头像的url
     * @param gender    性别
     * @param callBack
     */

    public void wxLogin(String key, String wechatId, String nick, String avatarUrl, int gender, JsonCallBack<GingResponse<UserBean>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.login_wx))
                .tag(mContext)
                .cacheKey(key)
                .cacheMode(CacheMode.NO_CACHE)
                .params("WechatId", wechatId)
                .params("Nick", nick)
                .params("AvatarUrl", avatarUrl)
                .params("Gender", gender)
                .execute(callBack);
    }

    /**
     * 发布问题
     *
     * @param key
     * @param Title
     * @param Description
     * @param PhotoUrls
     * @param Reward
     * @param IsAnonymous
     * @param callBack
     */

    public void publishQuestion(String key, String Title, String Description, String PhotoUrls, float Reward, int IsAnonymous, JsonCallBack<GingResponse<QuestionWrapper>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.publish_question))
                .tag(mContext)
                .cacheKey(key)
                .params("Title", Title)
                .params("Description", Description)
                .params("PhotoUrls", PhotoUrls)
                .params("Reward", Reward)
                .params("IsAnonymous", IsAnonymous)
                .execute(callBack);
    }

    /**
     * 获取最新问题列表
     *
     * @param key
     * @param orderStr
     * @param callBack
     */

    public void newQuestionList(String key, String orderStr, JsonCallBack<GingResponse<QuestionWrapperListBean>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.new_question_list))
                .tag(mContext)
                .cacheKey(key)
                .params("OrderStr", orderStr)
                .execute(callBack);
    }

    /**
     * 获取热门问题列表
     *
     * @param key
     * @param orderStr
     * @param callBack
     */

    public void hotQuestionList(String key, String orderStr, JsonCallBack<GingResponse<QuestionWrapperListBean>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.hot_question_list))
                .tag(mContext)
                .cacheKey(key)
                .params("OrderStr", orderStr)
                .execute(callBack);
    }

    /**
     * 获取附近问题列表
     *
     * @param key
     * @param orderStr
     * @param callBack
     */

    public void nearbyQuestionList(String key, String orderStr, JsonCallBack<GingResponse<QuestionWrapperListBean>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.nearby_question_list))
                .tag(mContext)
                .cacheKey(key)
                .params("OrderStr", orderStr)
                .execute(callBack);
    }

    /**
     * 根据quid获取问题的回答列表
     *
     * @param key
     * @param Quid
     * @param orderStr
     * @param callBack
     */

    public void answerList(String key, String Quid, String orderStr, JsonCallBack<GingResponse<AnswerWrapperListBean>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.answer_list))
                .tag(mContext)
                .cacheKey(key)
                .params("Quid", Quid)
                .params("OrderStr", orderStr)
                .execute(callBack);

    }

    /**
     * 根据quid获取问题详情   GET方法
     *
     * @param key
     * @param quid
     * @param callBack
     */
    public void questionDetail(String key, String quid, JsonCallBack<GingResponse<QuestionWrapper>> callBack) {
        OkGo.get(WebUrls.getUrl(WebUrls.question_detail) + quid)
                .tag(mContext)
                .cacheKey(key)
                .execute(callBack);

    }

    /**
     * 用户退出登录
     *
     * @param key
     * @param callBack
     */
    public void logout(String key, StringCallback callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.logout))
                .tag(mContext)
                .cacheKey(key)
                .execute(callBack);
    }

    /**
     * 我的提问列表   StatusType  问题状态：0为全部，1为未解决，2为已解决
     *
     * @param key
     * @param StatusType
     * @param OrderStr
     * @param callBack
     */
    public void userQuestionList(String key, int StatusType, String OrderStr, JsonCallBack<GingResponse<QuestionWrapperListBean>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.user_question_list))
                .tag(mContext)
                .cacheKey(key)
                .params("StatusType", StatusType)
                .params("OrderStr", OrderStr)
                .execute(callBack);
    }

    /**
     * 我的回答列表    StatusType   回答状态：0为全部，1为被采纳获得红包
     *
     * @param key
     * @param StatusType
     * @param OrderStr
     * @param callBack
     */
    public void userAnswerList(String key, int StatusType, String OrderStr, JsonCallBack<GingResponse<QuestionWrapperListBean>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.user_answer_list))
                .tag(mContext)
                .cacheKey(key)
                .params("StatusType", StatusType)
                .params("OrderStr", OrderStr)
                .execute(callBack);
    }
}
