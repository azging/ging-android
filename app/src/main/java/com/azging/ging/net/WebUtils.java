package com.azging.ging.net;

import android.content.Context;

import com.azging.ging.bean.AuthCodeBean;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.bean.QuestionWrapperListBean;
import com.azging.ging.bean.UserBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

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

    public void phoneLogin(String key, String telephone, String authcode, JsonCallBack<GingResponse<UserBean>> callback) {
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

    public void newQuestionList(String key, String orderStr, JsonCallBack<GingResponse<QuestionWrapperListBean>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.new_question_list))
                .tag(mContext)
                .cacheKey(key)
                .params("OrderStr",orderStr)
                .execute(callBack);
    }


}
