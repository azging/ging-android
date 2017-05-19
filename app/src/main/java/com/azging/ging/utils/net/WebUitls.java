package com.azging.ging.utils.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.azging.ging.bean.AuthCodeBean;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.UserBean;
import com.azging.ging.utils.Log;
import com.lzy.okgo.OkGo;

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

public class WebUitls {
    private Context mContext;

    public WebUitls(Context context) {
        mContext = context;
    }

//    public void sentAuthcode(String key, String telephone, StringCallback callback) {
//        OkGo.post(WebUrls.getUrl(WebUrls.authcode_send))
//                .tag(mContext)
//                .cacheKey(key)
//                .params("Telephone", telephone)
//                .execute(callback);
//    }

    public void sentAuthcode(String key, String telephone, JsonCallBack<GingResponse<AuthCodeBean>> callback) {
        OkGo.post(WebUrls.getUrl(WebUrls.authcode_send))
                .tag(mContext)
                .cacheKey(key)
                .params("Telephone", telephone)
                .execute(callback);
    }

    public void phoneLogin(String key, String telephone, String authcode, JsonCallBack<GingResponse<UserBean>> callback) {
        OkGo.post(WebUrls.getUrl(WebUrls.login_phone))
                .tag(mContext)
                .cacheKey(key)
                .params("Telephone", telephone)
                .params("AuthCode", authcode)
                .execute(callback);
    }




    public  <T> void handleResponse(T data, Call call, Response response) {
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
