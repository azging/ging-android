package com.azging.ging.view;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseApp;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.utils.AppManager;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeListActivity extends BaseMainActivity implements IActivity {

    private static final String KEY_TYPE = "TYPE";

    public static final int TYPE_QUESTION = 1;
    public static final int TYPE_ANSWER = 2;
    public static final int TYPE_WALLET = 3;
    public static final int TYPE_FEEDBACK = 4;
    public static final int TYPE_ABOUT_US = 5;
    @BindView(R.id.header_left) RelativeLayout mHeaderLeft;
    @BindView(R.id.header_right) RelativeLayout mHeaderRight;
    @BindView(R.id.header_back) ImageView mHeaderBack;
    @BindView(R.id.header_title) TextView mHeaderTitle;
    @BindView(R.id.header_more) ImageView mHeaderMore;
    @BindView(R.id.header_complete) TextView mHeaderComplete;
    @BindView(R.id.header_view) RelativeLayout mHeaderView;
    @BindView(R.id.fragment) LinearLayout mFragment;

    private int mType;
    FeedbackFragment feedbackFragment;

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

        mHeaderMore.setVisibility(View.GONE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentManager = getSupportFragmentManager();
        switch (mType) {
            case TYPE_QUESTION:
                mHeaderTitle.setText("我的提问");

                MyQuestionFragment questionFragment = (MyQuestionFragment) fragmentManager.findFragmentById(R.id.fragment);
                if (questionFragment == null) {
                    questionFragment = MyQuestionFragment.startFragment(MyQuestionFragment.TYPE_QUESTION);
                    transaction.add(R.id.fragment, questionFragment);
                } else {
                    transaction.add(R.id.fragment, questionFragment);
                }
                break;
            case TYPE_ANSWER:
                mHeaderTitle.setText("我的回答");

                MyQuestionFragment answerFragment = (MyQuestionFragment) fragmentManager.findFragmentById(R.id.fragment);
                if (answerFragment == null) {
                    answerFragment = MyQuestionFragment.startFragment(MyQuestionFragment.TYPE_ANSWER);
                    transaction.add(R.id.fragment, answerFragment);
                } else {
                    transaction.add(R.id.fragment, answerFragment);
                }
                break;
            case TYPE_WALLET:
                mHeaderTitle.setText("我的红包");
                WalletFragment walletFragment = (WalletFragment) fragmentManager.findFragmentById(R.id.fragment);
                if (walletFragment == null) {
                    walletFragment = WalletFragment.startFragment(BaseApp.app.getCurrentUser());
                    transaction.add(R.id.fragment, walletFragment);
                } else {
                    transaction.add(R.id.fragment, walletFragment);
                }

                break;
            case TYPE_FEEDBACK:
                mHeaderTitle.setText("意见反馈");
                mHeaderComplete.setVisibility(View.VISIBLE);
                mHeaderComplete.setText("提交");
                feedbackFragment = (FeedbackFragment) fragmentManager.findFragmentById(R.id.fragment);
                if (feedbackFragment == null) {
                    feedbackFragment = FeedbackFragment.startFragment();
                    transaction.add(R.id.fragment, feedbackFragment);
                } else {
                    transaction.add(R.id.fragment, feedbackFragment);
                }
                break;
            case TYPE_ABOUT_US:
                mHeaderTitle.setText("关于");
                AboutUsFragment aboutUsFragment = (AboutUsFragment) fragmentManager.findFragmentById(R.id.fragment);
                if (aboutUsFragment == null) {
                    aboutUsFragment = AboutUsFragment.startFragment();
                    transaction.add(R.id.fragment, aboutUsFragment);
                } else {
                    transaction.add(R.id.fragment, aboutUsFragment);
                }
                break;
        }
        transaction.commit();
    }

    @OnClick({R.id.header_left, R.id.header_right})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.header_left:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.header_right:
                feedbackFragment.submit();
                break;
        }
    }
}
