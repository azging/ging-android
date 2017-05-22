package com.azging.ging.adapter;

import android.support.annotation.Nullable;

import com.azging.ging.R;
import com.azging.ging.bean.QuestionWrapper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by GG on 2017/5/22.
 */

public class NewQuestionAdapter extends BaseQuickAdapter<QuestionWrapper, BaseViewHolder> {


    public NewQuestionAdapter(@Nullable List<QuestionWrapper> data) {
        super(R.layout.item_question_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionWrapper item) {

    }

}
