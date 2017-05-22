package com.azging.ging.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.net.JsonCallBack;
import com.azging.ging.net.WebUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class PublishQuestionActivity extends BaseMainActivity implements IActivity {


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PublishQuestionActivity.class);
        context.startActivity(intent);
    }

    @Nullable @BindView(R.id.header_back) ImageView headerBack;
    @Nullable @BindView(R.id.header_title) TextView headerTitle;
    @Nullable @BindView(R.id.header_more) ImageView headerMore;
    @Nullable @BindView(R.id.header_view) RelativeLayout headerView;
    @Nullable @BindView(R.id.ed_title) AppCompatEditText edTitle;
    @Nullable @BindView(R.id.title_view) LinearLayout titleView;
    @Nullable @BindView(R.id.publish_describe) TextView publishDescribe;
    @Nullable @BindView(R.id.ed_describe) AppCompatEditText edDescribe;
    @Nullable @BindView(R.id.add_img1) ImageView addImg1;
    @Nullable @BindView(R.id.add_img2) ImageView addImg2;
    @Nullable @BindView(R.id.add_img3) ImageView addImg3;
    @Nullable @BindView(R.id.add_img_view) LinearLayout addImgView;
    @Nullable @BindView(R.id.length_limit) TextView lengthLimit;
    @Nullable @BindView(R.id.reward_view) View rewardView;
    @Nullable @BindView(R.id.anonymous_view) View anonymousView;
    @Nullable @BindView(R.id.publish_btn) View publishBtn;

    private WebUtils webUtils;
    private float rewardF;
    private int anonymousI;

    @Override
    public int initView() {
        return R.layout.activity_publish_question;
    }

    @Override
    public void initData() {
        webUtils = new WebUtils(this);
    }


    @OnClick({R.id.header_back, R.id.add_img1, R.id.add_img2, R.id.add_img3, R.id.reward_view, R.id.anonymous_view, R.id.publish_btn})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.header_back:

                break;

            case R.id.add_img1:

                break;
            case R.id.add_img2:

                break;
            case R.id.add_img3:

                break;
            case R.id.reward_view:

                break;
            case R.id.anonymous_view:

                break;
            case R.id.publish_btn:
                webUtils.publishQuestion("PublishQuestion", edTitle.getText().toString(), edDescribe.getText().toString(), "", rewardF, anonymousI, new JsonCallBack<GingResponse<QuestionWrapper>>() {
                    @Override
                    public void onSuccess(GingResponse<QuestionWrapper> questionWrapperGingResponse, Call call, Response response) {
                        super.onSuccess(questionWrapperGingResponse, call, response);
                    }
                });
                break;
        }
    }

}
