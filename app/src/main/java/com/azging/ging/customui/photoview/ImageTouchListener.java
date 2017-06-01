package com.azging.ging.customui.photoview;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by GG on 2017/5/31.
 */

public class ImageTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v instanceof ImageView){
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                ColorMatrix matrix=new ColorMatrix();
                matrix.setSaturation(0.2f);
                float[] array=matrix.getArray();
                int brightness = 50;
                array[4]=array[9]=array[14]=brightness;
                matrix.set(array);
                ((ImageView) v).setColorFilter(new ColorMatrixColorFilter(matrix));
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL ||
                    event.getAction() == MotionEvent.ACTION_UP){
                ((ImageView) v).clearColorFilter();
            }
        }
        return false;
    }
}
