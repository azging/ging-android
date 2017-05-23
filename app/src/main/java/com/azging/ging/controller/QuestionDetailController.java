package com.azging.ging.controller;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.bean.QuestionBean;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.bean.UserBean;
import com.azging.ging.utils.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GG on 2017/5/23.
 */

public class QuestionDetailController extends BaseController {


    class ViewHolder {
        @BindView(R.id.user_avatar) ImageView userAvatar;
        @BindView(R.id.user_nick) TextView userNick;
        @BindView(R.id.cost_level) TextView costLevel;
        @BindView(R.id.user_info) LinearLayout userInfo;
        @BindView(R.id.question_title) TextView questionTitle;
        @BindView(R.id.question_detail) TextView questionDetail;
        @BindView(R.id.question_img1) ImageView questionImg1;
        @BindView(R.id.question_img2) ImageView questionImg2;
        @BindView(R.id.question_img3) ImageView questionImg3;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private ViewHolder viewHolder;

    public QuestionDetailController(Context context, View view) {
        super(context, view);
        setViewHolder(new ViewHolder(view));

    }

    public void wrapData(QuestionWrapper item) {
        QuestionBean questionBean = item.getQuestion();
        UserBean userBean = item.getCreateUserWrapper().getUser();
        ImageLoader.getInstance().displayImage(mContext, userBean.getThumbAvatarUrl(), viewHolder.userAvatar);
        viewHolder.userNick.setText(userBean.getNick());
        viewHolder.costLevel.setText(questionBean.getCostType());
        viewHolder.questionTitle.setText(questionBean.getTitle());
        viewHolder.questionDetail.setText(questionBean.getDescription());

        if (questionBean.getThumbPhotoUrls() != null) {
            if (questionBean.getThumbPhotoUrls().size() > 0)
                ImageLoader.getInstance().displayImage(mContext, questionBean.getThumbPhotoUrls().get(0), viewHolder.questionImg1);
            if (questionBean.getThumbPhotoUrls().size() > 1)
                ImageLoader.getInstance().displayImage(mContext, questionBean.getThumbPhotoUrls().get(1), viewHolder.questionImg2);
            if (questionBean.getThumbPhotoUrls().size() > 2)
                ImageLoader.getInstance().displayImage(mContext, questionBean.getThumbPhotoUrls().get(2), viewHolder.questionImg3);
        }
    }

    public ViewHolder getViewHolder() {
        return viewHolder;
    }

    public void setViewHolder(ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

}
