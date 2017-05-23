package com.azging.ging.view;

import com.azging.ging.R;
import com.azging.ging.base.BaseFragment;

/**
 * Created by GG on 2017/5/23.
 */

public class FeedbackFragment extends BaseFragment {

    public static FeedbackFragment startFragment() {
        FeedbackFragment feedbackFragment = new FeedbackFragment();
        return feedbackFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_feedback;
    }

    @Override
    public void initView() {

    }
}
