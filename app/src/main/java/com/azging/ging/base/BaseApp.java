package com.azging.ging.base;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.azging.ging.BuildConfig;
import com.azging.ging.R;
import com.azging.ging.bean.ActivityBean;
import com.azging.ging.utils.net.NetChangeObserver;
import com.azging.ging.utils.net.NetWorkUtil;
import com.azging.ging.utils.net.NetworkStateReceiver;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.Log;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.handler.WeixinPreferences;
import com.umeng.socialize.media.WeiXinShareContent;

import java.util.logging.Level;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.internal.platform.Platform;


/**
 * 项目名称：ProjectFunction
 * <p>
 * 1>初始化全局配置,第三方的sdk
 * 2>初始化第三方的SDK
 * 3>初始化通用框架
 * 全局异常处理
 * 友盟统计有全局异常的处理
 */

public class BaseApp extends Application implements Application.ActivityLifecycleCallbacks {
    public static BaseApp app;

    private NetChangeObserver observer = new NetChangeObserver() {

        @Override
        public void onConnect(NetWorkUtil.NetType type) {
            super.onConnect(type);

            //表示已连接
            switch (type) {
                case NETWORK_WIFI:
                    //表示wifi
                    break;
                default:
                    Toast.makeText(BaseApp.this, "2/3/4G网络已连接", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

        @Override
        public void onDisConnect() {
            super.onDisConnect();
            //表示网络已断开
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        //init umeng config
        if (BuildConfig.DEBUG) {
            MobclickAgent.setDebugMode(true);
            MobclickAgent.setCatchUncaughtExceptions(false);
        } else {
            MobclickAgent.setDebugMode(false);
            MobclickAgent.setCatchUncaughtExceptions(true);
        }
        MobclickAgent.openActivityDurationTrack(false);
//		MobclickAgent.updateOnlineConfig(this);

        UMShareAPI.get(this);
        Log.i("", "onCreate: ");
        initNetState();
        initOkGo();
        registerActivityLifecycleCallbacks(this);

    }

    {
        PlatformConfig.setWeixin("", "");
    }

    private void initOkGo() {
        //必须调用初始化
        OkGo.init(this);

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()

                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
//              .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
                    .setCertificates();                              //方法一：信任所有证书,不安全有风险
//              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
//              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//               .setHostnameVerifier(new SafeHostnameVerifier())

            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

            //这两行同上，不需要就不要加入
//                    .addCommonHeaders(headers)  //设置全局公共头
//                    .addCommonParams(params);   //设置全局公共参数

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initNetState() {
        //动态注册网络监听
        NetworkStateReceiver.registerNetworkStateReceiver(this);

        //注册网络变化的观察者
        NetworkStateReceiver.registerObserver(observer);
    }

    /**
     * 程序结束的位置 注销监听
     */
    public void unRegisterNetworkStateReceiver() {
        NetworkStateReceiver.unRegisterNetworkStateReceiver(this);
        if (observer != null) {
            NetworkStateReceiver.removeRegisterObserver(observer);
        }

    }


    @Override
    public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
        if (activity instanceof IActivity) {
            activity.setContentView(((IActivity) activity).initView());
            ActivityBean bean = new ActivityBean();
            Unbinder unbinder = ButterKnife.bind(activity);
            bean.setUnbinder(unbinder);
            activity.getIntent().putExtra("ActivityBean", bean);
            ((IActivity) activity).initData();
        }


        if (activity.findViewById(R.id.toolbar) != null) {
            if (activity instanceof AppCompatActivity) {
                ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
                ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
                    activity.getActionBar().setDisplayShowTitleEnabled(false);
                }
            }
        }
        if (activity.findViewById(R.id.toolbar_title) != null) {
            ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
        }
        if (activity.findViewById(R.id.toolbar_back) != null) {
            activity.findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }

        AppManager.getAppManager().addActivity(activity);

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        MobclickAgent.onPageStart(activity.getClass().getName());
        MobclickAgent.onResume(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        MobclickAgent.onPageEnd(activity.getClass().getName());
        MobclickAgent.onPause(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof IActivity) {
            ActivityBean bean = activity.getIntent().getParcelableExtra("ActivityBean");
            bean.getUnbinder().unbind();
            AppManager.getAppManager().finishActivity();
        }
    }


}
