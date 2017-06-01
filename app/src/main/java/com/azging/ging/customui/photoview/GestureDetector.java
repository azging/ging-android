package com.azging.ging.customui.photoview;

import android.view.MotionEvent;

/**
 * Created by GG on 2017/5/31.
 */

public interface GestureDetector {

    boolean onTouchEvent(MotionEvent ev);

    boolean isScaling();

    boolean isDragging();

    void setOnGestureListener(OnGestureListener listener);

}
