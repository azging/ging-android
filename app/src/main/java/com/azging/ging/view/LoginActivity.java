package com.azging.ging.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.AuthCodeBean;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.UserBean;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.Log;
import com.azging.ging.utils.PrefConstants;
import com.azging.ging.utils.SharedPreferencesHelper;
import com.azging.ging.utils.ToastUtil;
import com.azging.ging.net.JsonCallBack;
import com.azging.ging.net.WebUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseMainActivity implements IActivity {

    @BindView(R.id.header_back) ImageView headerBack;
    @BindView(R.id.header_title) TextView headerTitle;
    @BindView(R.id.header_more) ImageView headerMore;
    @BindView(R.id.header_view) RelativeLayout headerView;
    @BindView(R.id.ed_phone) AppCompatEditText edPhone;
    @BindView(R.id.send_code) TextView sendCode;
    @BindView(R.id.phone_view) LinearLayout phoneView;
    @BindView(R.id.ed_password) AppCompatEditText edPassword;
    @BindView(R.id.password_view) LinearLayout passwordView;
    @BindView(R.id.prompt) TextView prompt;
    @BindView(R.id.login_btn) TextView loginBtn;
    @BindView(R.id.weixin_login) LinearLayout weixinLogin;
    @BindView(R.id.agreement) TextView agreement;

    private WebUtils webUtils;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int initView() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        webUtils = new WebUtils(this);


        headerView.setBackgroundResource(R.color.floralwhite);
        headerBack.setVisibility(View.GONE);
        headerMore.setVisibility(View.GONE);
        headerTitle.setText(R.string.login);

    }

    @OnClick({R.id.header_back, R.id.send_code, R.id.login_btn, R.id.weixin_login})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.header_back:

                break;
            case R.id.send_code:
                webUtils.sentAuthcode("MainActivity", edPhone.getText().toString(), new JsonCallBack<GingResponse<AuthCodeBean>>() {
                    @Override
                    public void onSuccess(GingResponse<AuthCodeBean> authCodeBeanGingResponse, Call call, Response response) {
                        super.onSuccess(authCodeBeanGingResponse, call, response);
                        ToastUtil.showShort("发送成功");
                    }
                });
                break;
            case R.id.login_btn:
                webUtils.phoneLogin("MainActivity", edPhone.getText().toString(), edPassword.getText().toString(), new JsonCallBack<GingResponse<UserBean>>() {
                    @Override
                    public void onSuccess(GingResponse<UserBean> userBeanGingResponse, Call call, Response response) {
                        super.onSuccess(userBeanGingResponse, call, response);
                        ToastUtil.showShort("登录成功");
                        if (userBeanGingResponse.Data != null) {
                            SharedPreferencesHelper.getInstance(AppManager.getAppManager().currentActivity())
                                    .putStringValue(PrefConstants.KEY_CURRENT_USER, userBeanGingResponse.Data.toString());
                            Log.printJSON("user su", userBeanGingResponse.Data.toString());
                        }
                    }
                });
                break;
            case R.id.weixin_login:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            ToastUtil.showLong("授权成功");
            Log.printMap(data);
            webUtils.wxLogin("WXLogin", data.get("uid"), data.get("name"), data.get("iconurl"), getGender(data.get("gender")), new JsonCallBack<GingResponse<UserBean>>() {
                @Override
                public void onSuccess(GingResponse<UserBean> userBeanGingResponse, Call call, Response response) {
                    if (userBeanGingResponse.Data != null) {
                        SharedPreferencesHelper.getInstance(AppManager.getAppManager().currentActivity())
                                .putStringValue(PrefConstants.KEY_CURRENT_USER, userBeanGingResponse.Data.toString());
                        Log.printJSON("user su", userBeanGingResponse.Data.toString());
                    }
                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.showLong("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.showLong("取消授权");
        }
    };

    public int getGender(String gender) {
        if ("男".equals(gender))
            return 1;
        else if ("女".equals(gender))
            return 2;
        return 0;
    }
}
