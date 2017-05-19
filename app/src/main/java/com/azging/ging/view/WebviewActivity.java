package com.azging.ging.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by GG on 2017/5/19.
 */

public class WebviewActivity extends BaseMainActivity implements IActivity{

    public static void startActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }
    private WebView mWebView;

    private String mTitle;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = (WebView)findViewById(R.id.webview);

        mTitle = this.getIntent().getStringExtra("title");
        mUrl = this.getIntent().getStringExtra("url");
        if(mUrl!=null){
            if(TextUtils.isEmpty(mTitle)) mTitle = getResources().getString(R.string.browse_web);
            setTitle(mTitle);
            WebSettings settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            mWebView.addJavascriptInterface(new JsInteration(), "android_control");
            mWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    if(progress==100) {
                        findViewById(R.id.progressbar_loading).setVisibility(View.INVISIBLE);
                    }
                }

            });
            mWebView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url)
                { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边

                    if (url.contains("cn.duckr.android")) {
                        Intent intent;
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }

                    view.loadUrl(url);
                    return true;
                }
            });
            mWebView.loadUrl(mUrl);
        }else{
            finish();
        }
    }


    @Override
    public int initView() {
        return R.layout.activity_webview;

    }

    @Override
    public void initData() {

    }

    private String successFunc = "";

    public class JsInteration {

        @JavascriptInterface
        public void shareWebPage(String json) {
            try {
                JSONArray ja = new JSONArray(json);
                final String title = ja.getString(0);
                final String content = ja.getString(1);
                final String shareUrl = ja.getString(2);
                final String shareImgUrl = ja.getString(3);
                if(ja.length()>4){
                    successFunc = ja.getString(4);
                }

//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        SocialShareBoard ssb = SocialShareBoard.getInstance();
//                        ssb.setSingletonInstance(WebviewActivity.this,SocialShareBoard.TYPE_OTHER,title,shareUrl,shareImgUrl,content,new SocialShareController.ShareCallback() {
//                            @Override
//                            public void onSuccess() {
//                                if(!successFunc.isEmpty())
//                                    mWebView.loadUrl("javascript:"+successFunc+"()");
//                            }
//                        });
//                        ssb.showAtLocation(WebviewActivity.this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//                    }
//                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
