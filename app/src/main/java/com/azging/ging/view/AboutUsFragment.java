package com.azging.ging.view;

import com.azging.ging.R;
import com.azging.ging.base.BaseFragment;

/**
 * Created by GG on 2017/5/23.
 */

public class AboutUsFragment extends BaseFragment {

    public static AboutUsFragment startFragment() {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_about_us;
    }

    @Override
    public void initView() {

    }
}
