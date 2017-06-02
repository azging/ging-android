package com.azging.ging.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.azging.ging.bean.AnswerWrapper;
import com.azging.ging.bean.AnswerWrapperListBean;
import com.azging.ging.bean.AuthCodeBean;
import com.azging.ging.bean.BalanceBean;
import com.azging.ging.bean.CreateUserWrapper;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.IsPassBean;
import com.azging.ging.bean.OrderDataBean;
import com.azging.ging.bean.QiniuTokenBean;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.bean.QuestionWrapperListBean;
import com.azging.ging.bean.UserBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

import static com.azging.ging.utils.Utils.Bitmap2Bytes;

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
     * 我的提问列表
     *
     * @param key
     * @param StatusType 问题状态：0为全部，1为未解决，2为已解决
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
     * 我的回答列表
     *
     * @param key
     * @param StatusType 回答状态：0为全部，1为被采纳获得红包
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

    /**
     * 上传图片到七牛
     *
     * @param pic
     * @param tag
     * @param callBack
     */

    public void qiniuUploadPic(final Bitmap pic, final String tag, String type, final QiniuComplete callBack) {
        JsonCallBack<GingResponse<QiniuTokenBean>> callback = new JsonCallBack<GingResponse<QiniuTokenBean>>() {
            @Override
            public void onSuccess(GingResponse<QiniuTokenBean> qiniuTokenBeanGingResponse, Call call, Response response) {
                super.onSuccess(qiniuTokenBeanGingResponse, call, response);
//                    JSONObject dataObj = new JSONObject(s);
//                    Log.e("sssss", s);
//                    String token = dataObj.optString("UpToken");
//                    String pubkey_name = dataObj.optString("PhotoKey");
                String token = qiniuTokenBeanGingResponse.Data.getUpToken();
                String pubkey_name = qiniuTokenBeanGingResponse.Data.getPhotoKey();


                byte[] uploadPic_bytes = Bitmap2Bytes(pic);
                qiniuUploadData(uploadPic_bytes, token, pubkey_name, tag, callBack);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callBack.Compplete(null, null, null, tag);
            }
        };


//        StringCallback callback = new StringCallback() {
//            @Override
//            public void onSuccess(String s, Call call, Response response) {
//                try {
//                    JSONObject dataObj = new JSONObject(s);
//                    Log.e("sssss", s);
//                    String token = dataObj.optString("UpToken");
//                    String pubkey_name = dataObj.optString("PhotoKey");
//                    byte[] uploadPic_bytes = Bitmap2Bytes(pic);
//                    qiniuUploadData(uploadPic_bytes, token, pubkey_name, tag, callBack);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    callBack.Compplete(null, null, null, tag);
//                }
//            }
//
//            @Override
//            public void onError(Call call, Response response, Exception e) {
//                super.onError(call, response, e);
//                callBack.Compplete(null, null, null, tag);
//            }
//        };


        OkGo.post(WebUrls.getUrl(WebUrls.qiniu_uptoken))
                .params("Type", type)
                .execute(callback);
    }

    /**
     * 上传图片到七牛
     *
     * @param uploadPic_bytes
     * @param token
     * @param pubkey_name
     * @param tag
     * @param callBack
     */
    private void qiniuUploadData(byte[] uploadPic_bytes, String token, String pubkey_name,
                                 final String tag, final QiniuComplete callBack) {
        UploadManager uploadManager = new UploadManager();
        if (uploadPic_bytes != null) {
            uploadManager.put(uploadPic_bytes, pubkey_name,
                    token, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            callBack.Compplete(key, info, response, tag);
                        }
                    }, null);
        }
    }

    /**
     * 新建订单
     *
     * @param quid        问题对外标识
     * @param auid        回答对外标识
     * @param amount      支付金额
     * @param paymentType 付款方式：0未知，1为余额，2为微信
     * @param tradeType   交易类型：0未知，1为提问支付，2为采纳答案，3为提现
     * @param callBack
     */
    public void addOrder(String quid, String auid, double amount, int paymentType, int tradeType, JsonCallBack<GingResponse<OrderDataBean>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.add_order))
                .tag(mContext)
                .params("Quid", quid)
                .params("Auid", auid)
                .params("Amount", amount)
                .params("PaymentType", paymentType)
                .params("TradeType", tradeType)
                .execute(callBack);
    }

    /**
     * 用户信息修改
     *
     * @param key       保存返回数据的 key
     * @param nick      昵称
     * @param avatarUrl 头像
     * @param gender    性别
     * @param callBack
     */
    public void userInfoUpdate(String key, String nick, String avatarUrl, int gender, JsonCallBack<GingResponse<CreateUserWrapper>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.user_info_update))
                .tag(mContext)
                .cacheKey(key)
                .params("Nick", nick)
                .params("AvatarUrl", avatarUrl)
                .params("Gender", gender)
                .execute(callBack);
    }

    /**
     * 提交反馈意见
     *
     * @param content
     * @param callback
     */
    public void addFeedback(String content, JsonCallBack<GingResponse<IsPassBean>> callback) {
        OkGo.post(WebUrls.getUrl(WebUrls.add_feedback))
                .params("Content", content)
                .execute(callback);
    }

    /**
     * 添加答案
     *
     * @param key
     * @param quid     问题唯一标识
     * @param content  回答的内容
     * @param type     0为文字回答
     * @param callBack
     */
    public void addAnswer(String key, String quid, String content, int type, JsonCallBack<GingResponse<AnswerWrapper>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.add_answer))
                .tag(mContext)
                .cacheKey(key)
                .params("Quid", quid)
                .params("Content", content)
                .params("Type", type)
                .execute(callBack);
    }

    /**
     * 挑选最佳答案
     *
     * @param key
     * @param quid     问题唯一标识
     * @param auid     回答唯一标识
     * @param callBack
     */
    public void adoptAnswer(String key, String quid, String auid, JsonCallBack<GingResponse<IsPassBean>> callBack) {
        OkGo.post(WebUrls.getUrl(WebUrls.adopt_answer))
                .tag(mContext)
                .cacheKey(key)
                .params("Quid", quid)
                .params("Auid", auid)
                .execute(callBack);
    }

    /**
     * 获取用户余额
     *
     * @param callBack
     */
    public void balance(JsonCallBack<GingResponse<BalanceBean>> callBack) {
        OkGo.get(WebUrls.getUrl(WebUrls.balance))
                .execute(callBack);
    }
}
