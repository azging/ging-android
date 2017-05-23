package com.azging.ging.controller;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.bean.ActivityBean;
import com.azging.ging.bean.AnswerBean;
import com.azging.ging.bean.AnswerWrapper;
import com.azging.ging.bean.UserBean;
import com.azging.ging.utils.DateUtils;
import com.azging.ging.utils.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GG on 2017/5/23.
 */

public class AnswerDetailController extends BaseController {

    class ViewHolder {

        @BindView(R.id.user_avatar) ImageView userAvatar;
        @BindView(R.id.user_nick) TextView userNick;
        @BindView(R.id.cost_level) TextView costLevel;
        @BindView(R.id.user_info) LinearLayout userInfo;
        @BindView(R.id.answer_detail) TextView answerDetail;
        @BindView(R.id.answer_time) TextView answerTime;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    ViewHolder viewHolder;

    public AnswerDetailController(Context context, View view) {
        super(context, view);
        viewHolder = new ViewHolder(view);
    }

    public void wrapData(AnswerWrapper item) {

        AnswerBean answerBean = item.getAnswer();
        UserBean userBean = item.getCreateUserWrapper().getUser();
        ImageLoader.getInstance().displayImage(mContext, userBean.getThumbAvatarUrl(), viewHolder.userAvatar);
        viewHolder.userNick.setText(userBean.getNick());

        viewHolder.answerDetail.setText(answerBean.getContent());
        viewHolder.answerTime.setText(DateUtils.convertTimestamp(answerBean.getUpdateTime()));
    }
}
