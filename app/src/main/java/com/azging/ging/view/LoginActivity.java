package com.azging.ging.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.azging.ging.utils.net.JsonCallBack;
import com.azging.ging.utils.net.WebUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
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
                    }
                });
                break;
            case R.id.login_btn:
                webUtils.phoneLogin("MainActivity", edPhone.getText().toString(), edPassword.getText().toString(), new JsonCallBack<GingResponse<UserBean>>() {
                    @Override
                    public void onSuccess(GingResponse<UserBean> userBeanGingResponse, Call call, Response response) {
                        super.onSuccess(userBeanGingResponse, call, response);
                        if (userBeanGingResponse.Data != null) {
                            SharedPreferencesHelper.getInstance(AppManager.getAppManager().currentActivity())
                                    .putStringValue(PrefConstants.KEY_CURRENT_USER, userBeanGingResponse.Data.toString());
                            Log.printJSON("user su", userBeanGingResponse.Data.toString());
                        }
                    }
                });
                break;
            case R.id.weixin_login:

                break;
        }
    }

}
