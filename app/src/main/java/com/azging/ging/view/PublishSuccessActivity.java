package com.azging.ging.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.controller.QuestionDetailController;
import com.azging.ging.utils.AppManager;

import butterknife.BindView;
import butterknife.OnClick;

public class PublishSuccessActivity extends BaseMainActivity implements IActivity {

    private static String KEY_QUESTION = "QUESTION";
    @BindView(R.id.header_back) ImageView mHeaderBack;
    @BindView(R.id.header_title) TextView mHeaderTitle;
    @BindView(R.id.header_more) ImageView mHeaderMore;
    @BindView(R.id.header_complete) TextView mHeaderComplete;
    @BindView(R.id.header_view) RelativeLayout mHeaderView;
    @BindView(R.id.root) LinearLayout questionView;
    @BindView(R.id.weixin_share) ImageView mWeixinShare;


    public static void startActivity(Context context, QuestionWrapper questionWrapper) {
        Intent intent = new Intent(context, PublishSuccessActivity.class);
        intent.putExtra(KEY_QUESTION, questionWrapper);
        context.startActivity(intent);
    }

    @Override
    public int initView() {
        return R.layout.activity_publish_success;
    }

    @Override
    public void initData() {
        mHeaderTitle.setText(R.string.publish_success);
        mHeaderMore.setVisibility(View.GONE);
        mHeaderComplete.setVisibility(View.VISIBLE);

        QuestionWrapper questionWrapper = (QuestionWrapper) getIntent().getSerializableExtra(KEY_QUESTION);
        QuestionDetailController controller = new QuestionDetailController(PublishSuccessActivity.this, questionView);
        controller.wrapData(questionWrapper);
    }

    @OnClick({R.id.header_complete, R.id.weixin_share})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.header_complete:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.weixin_share:

                break;
        }
    }
}
