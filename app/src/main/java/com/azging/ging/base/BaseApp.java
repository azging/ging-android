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

import com.azging.ging.R;
import com.azging.ging.bean.ActivityBean;
import com.azging.ging.net.NetChangeObserver;
import com.azging.ging.net.NetWorkUtil;
import com.azging.ging.net.NetworkStateReceiver;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.Log;

import butterknife.ButterKnife;
import butterknife.Unbinder;


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
//        ShareSDK.initSDK(this);
        Log.i("", "onCreate: ");
        initNetState();

        registerActivityLifecycleCallbacks(this);
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

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityBean bean = activity.getIntent().getParcelableExtra("ActivityBean");
        bean.getUnbinder().unbind();
        AppManager.getAppManager().finishActivity();
    }
}
