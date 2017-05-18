package com.azging.ging.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.IActivity;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.Log;
import com.azging.ging.utils.ProgressDialogHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IActivity {


    @Nullable @BindView(R.id.text) TextView a;


    @Override
    public int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.text, R.id.button})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.text:
                break;
            case R.id.button:
                LoginActivity.startActivity(this);
                break;
        }
    }
}
