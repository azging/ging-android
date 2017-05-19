package com.azging.ging.view;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.AuthCodeBean;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.UserBean;
import com.azging.ging.utils.Log;
import com.azging.ging.utils.net.JsonCallBack;
import com.azging.ging.utils.net.WebUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseMainActivity implements IActivity {


    @Nullable @BindView(R.id.text) TextView a;

    private WebUtils webUtils;


    @Override
    public int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        webUtils = new WebUtils(this);
    }

    @OnClick({R.id.text, R.id.button})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.text:
                LoginActivity.startActivity(this);

                break;
            case R.id.button:

                break;
        }
    }
}
