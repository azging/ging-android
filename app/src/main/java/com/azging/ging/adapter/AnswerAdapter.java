package com.azging.ging.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.azging.ging.R;
import com.azging.ging.bean.AnswerWrapper;
import com.azging.ging.controller.AnswerDetailController;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by GG on 2017/5/23.
 */

public class AnswerAdapter extends BaseQuickAdapter<AnswerWrapper, BaseViewHolder> {
    public AnswerAdapter(@Nullable List<AnswerWrapper> data) {
        super(R.layout.item_answer_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnswerWrapper item) {
        AnswerDetailController controller = new AnswerDetailController(mContext, helper.itemView);
        controller.wrapData(item);
    }
}
