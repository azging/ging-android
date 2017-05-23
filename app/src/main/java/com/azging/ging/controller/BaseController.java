package com.azging.ging.controller;

import android.content.Context;
import android.view.View;

/**
 * Created by GG on 2017/5/23.
 */

public abstract class BaseController {

    protected Context mContext;
    protected View mView;

    public BaseController(Context context, View view) {
        mContext = context;
        mView = view;
    }

    public View getView() {
        return mView;
    }

}

