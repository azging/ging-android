package com.azging.ging.view;

import com.azging.ging.R;
import com.azging.ging.base.BaseFragment;

/**
 * Created by GG on 2017/5/19.
 */

public class RenameFragment extends BaseFragment {

    public static RenameFragment startFragment() {
        RenameFragment fragment = new RenameFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rename;
    }

    @Override
    public void initView() {

    }
}
