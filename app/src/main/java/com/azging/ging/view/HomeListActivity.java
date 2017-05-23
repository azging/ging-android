package com.azging.ging.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeListActivity extends BaseMainActivity implements IActivity {

    private static final String KEY_TYPE = "TYPE";

    public static final int TYPE_QUESTION = 1;
    public static final int TYPE_ANSWER = 2;
    public static final int TYPE_WALLET = 3;
    public static final int TYPE_FEEDBAK = 4;
    public static final int TYPE_ABOUT_US = 5;
    @BindView(R.id.header_back) ImageView mHeaderBack;
    @BindView(R.id.header_title) TextView mHeaderTitle;
    @BindView(R.id.header_more) ImageView mHeaderMore;
    @BindView(R.id.header_view) RelativeLayout mHeaderView;
    @BindView(R.id.fragment) LinearLayout mFragment;

    private int mType;

    public static void startActivity(Context context, int type) {
        Intent intent = new Intent(context, HomeListActivity.class);
        intent.putExtra(KEY_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int initView() {
        return R.layout.activity_home_list;
    }

    @Override
    public void initData() {
        mType = getIntent().getIntExtra(KEY_TYPE, 0);
        switch (mType) {
            case TYPE_QUESTION:

                break;
            case TYPE_ANSWER:

                break;
            case TYPE_WALLET:

                break;
            case TYPE_FEEDBAK:

                break;
            case TYPE_ABOUT_US:

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
