package com.azging.ging.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMainActivity implements IActivity {

    @BindView(R.id.header_back) ImageView headerBack;
    @BindView(R.id.header_title) TextView headerTitle;
    @BindView(R.id.header_more) ImageView headerMore;
    @BindView(R.id.header_view) ConstraintLayout headerView;


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
        headerView.setBackgroundResource(R.color.floralwhite);
        headerBack.setVisibility(View.GONE);
        headerMore.setVisibility(View.GONE);
        headerTitle.setText(R.string.login);

    }

    @OnClick({R.id.header_back, R.id.header_title, R.id.header_more})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.header_back:

                break;
            case R.id.header_title:

                break;
            case R.id.header_more:

                break;
        }
    }
}
