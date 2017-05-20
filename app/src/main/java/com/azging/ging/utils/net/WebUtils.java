package com.azging.ging.utils.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.azging.ging.bean.AuthCodeBean;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.UserBean;
import com.azging.ging.utils.Log;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by GG on 2017/5/19.
 */

public class WebUtils {
    private Context mContext;

    public WebUtils(Context context) {
        mContext = context;
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
     * 打印成功请求信息
     *
     * @param data
     * @param call
     * @param response
     * @param <T>
     */

    public <T> void handleResponse(T data, Call call, Response response) {
        StringBuilder sb;
        if (call != null) {
            Log.e("请求成功  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());

            Headers requestHeadersString = call.request().headers();
            Set<String> requestNames = requestHeadersString.names();
            sb = new StringBuilder();
            for (String name : requestNames) {
                sb.append(name).append(" ： ").append(requestHeadersString.get(name)).append("\n");
            }
            Log.e(sb.toString());
        }
        if (data != null) {
            if (data instanceof String) {
                Log.printJSON((String) data);
            } else if (data instanceof List) {
                sb = new StringBuilder();
                List list = (List) data;
                for (Object obj : list) {
                    sb.append(obj.toString()).append("\n");
                }
                Log.e(sb.toString());
            } else if (data instanceof Set) {
                sb = new StringBuilder();
                Set set = (Set) data;
                for (Object obj : set) {
                    sb.append(obj.toString()).append("\n");
                }
                Log.e(sb.toString());
            } else if (data instanceof Map) {
                sb = new StringBuilder();
                Map map = (Map) data;
                Set keySet = map.keySet();
                for (Object key : keySet) {
                    sb.append(key.toString()).append(" ： ").append(map.get(key)).append("\n");
                }
                Log.e(sb.toString());
            } else if (data instanceof File) {
                File file = (File) data;
                Log.e("数据内容即为文件内容\n下载文件路径：" + file.getAbsolutePath());
            } else if (data instanceof Bitmap) {
                Log.e("图片的内容即为数据");
            } else {
                Log.printJSON(data.toString());
            }
        }

        if (response != null) {
            Headers responseHeadersString = response.headers();
            Set<String> names = responseHeadersString.names();
            sb = new StringBuilder();
            sb.append("url ： ").append(response.request().url()).append("\n\n");
            sb.append("stateCode ： ").append(response.code()).append("\n");
            for (String name : names) {
                sb.append(name).append(" ： ").append(responseHeadersString.get(name)).append("\n");
            }
            Log.e(sb.toString());
        }
    }

    /**
     * 打印错误请求信息
     *
     * @param call
     * @param response
     */

    public void handleError(Call call, Response response) {
        StringBuilder sb;
        if (call != null) {
            Log.e("请求失败  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());

            Headers requestHeadersString = call.request().headers();
            Set<String> requestNames = requestHeadersString.names();
            sb = new StringBuilder();
            for (String name : requestNames) {
                sb.append(name).append(" ： ").append(requestHeadersString.get(name)).append("\n");
            }
            Log.e(sb.toString());
        }

        if (response != null) {
            Headers responseHeadersString = response.headers();
            Set<String> names = responseHeadersString.names();
            sb = new StringBuilder();
            sb.append("stateCode ： ").append(response.code()).append("\n");
            for (String name : names) {
                sb.append(name).append(" ： ").append(responseHeadersString.get(name)).append("\n");
            }
            Log.e(sb.toString());
        }
    }
}
