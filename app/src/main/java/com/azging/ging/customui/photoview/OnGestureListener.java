package com.azging.ging.customui.photoview;

/**
 * Created by GG on 2017/5/31.
 */

public interface OnGestureListener {

    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX,
                 float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);

}
