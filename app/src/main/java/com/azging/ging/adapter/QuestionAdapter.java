package com.azging.ging.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.azging.ging.R;
import com.azging.ging.bean.QuestionBean;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.bean.UserBean;
import com.azging.ging.controller.QuestionDetailController;
import com.azging.ging.utils.ImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by GG on 2017/5/22.
 */

public class QuestionAdapter extends BaseQuickAdapter<QuestionWrapper, BaseViewHolder> {


    public QuestionAdapter(@Nullable List<QuestionWrapper> data) {
        super(R.layout.item_question_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionWrapper item) {
        QuestionDetailController questionDetailController = new QuestionDetailController(mContext, helper.itemView);
        questionDetailController.wrapData(item);

//        QuestionBean questionBean = item.getQuestion();
//        UserBean userBean = item.getCreateUserWrapper().getUser();
//        ImageLoader.getInstance().displayImage(mContext, userBean.getThumbAvatarUrl(), (ImageView) helper.getView(R.id.user_avatar));
//        helper.setText(R.id.user_nick, userBean.getNick())
//                .setText(R.id.cost_level, questionBean.getCostType())
//                .setText(R.id.question_title, questionBean.getTitle())
//                .setText(R.id.question_detail, questionBean.getDescription());
//        if (questionBean.getThumbPhotoUrls() != null) {
//            if (questionBean.getThumbPhotoUrls().size() > 0)
//                ImageLoader.getInstance().displayImage(mContext, questionBean.getThumbPhotoUrls().get(0), (ImageView) helper.getView(R.id.question_img1));
//            if (questionBean.getThumbPhotoUrls().size() > 1)
//                ImageLoader.getInstance().displayImage(mContext, questionBean.getThumbPhotoUrls().get(1), (ImageView) helper.getView(R.id.question_img2));
//            if (questionBean.getThumbPhotoUrls().size() > 2)
//                ImageLoader.getInstance().displayImage(mContext, questionBean.getThumbPhotoUrls().get(2), (ImageView) helper.getView(R.id.question_img3));
//        }

    }

}
