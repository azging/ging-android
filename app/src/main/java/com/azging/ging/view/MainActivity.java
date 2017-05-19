package com.azging.ging.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseActivity;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.AuthCodeBean;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.UserBean;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.Log;
import com.azging.ging.utils.ProgressDialogHelper;
import com.azging.ging.utils.ToastUtil;
import com.azging.ging.utils.net.JsonCallBack;
import com.azging.ging.utils.net.WebUitls;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseMainActivity implements IActivity {


    @Nullable @BindView(R.id.text) TextView a;

    private WebUitls webUitls;


    @Override
    public int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        webUitls = new WebUitls(this);
    }

    @OnClick({R.id.text, R.id.button})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.text:
                webUitls.sentAuthcode("MainActivity", "17600209342", new JsonCallBack<GingResponse<AuthCodeBean>>() {
                    @Override
                    public void onSuccess(GingResponse<AuthCodeBean> authCodeBeanGingResponse, Call call, Response response) {
                        super.onSuccess(authCodeBeanGingResponse, call, response);
                        Log.printJSON("main", authCodeBeanGingResponse.toString());
                        Log.printJSON("user su", authCodeBeanGingResponse.Data.toString());
                    }
                });
//                webUitls.sentAuthcode("MainActivity", "17600209234", new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        Log.w("main", s);
//                    }
//                });

                break;
            case R.id.button:
                webUitls.phoneLogin("MainActivity", "17600209337", "278901", new JsonCallBack<GingResponse<UserBean>>() {
                    @Override
                    public void onSuccess(GingResponse<UserBean> userBeanGingResponse, Call call, Response response) {
                        super.onSuccess(userBeanGingResponse, call, response);
                        Log.w("phone su", userBeanGingResponse.toString());
                        if (userBeanGingResponse.Data != null)
                            Log.printJSON("user su", userBeanGingResponse.Data.toString());
                    }
                });
                break;
        }
    }
}
