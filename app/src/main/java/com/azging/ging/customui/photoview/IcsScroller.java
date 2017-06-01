package com.azging.ging.customui.photoview;

import android.annotation.TargetApi;
import android.content.Context;

/**
 * Created by GG on 2017/5/31.
 */


@TargetApi(14)
public class IcsScroller extends GingerScroller {

    public IcsScroller(Context context) {
        super(context);
    }

    @Override
    public boolean computeScrollOffset() {
        return mScroller.computeScrollOffset();
    }

}