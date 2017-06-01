package com.azging.ging.customui.photoview;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.azging.ging.R;
import com.azging.ging.bean.PhotoUri;
import com.azging.ging.customui.dynamiclayout.DynamicLayoutItem;
import com.azging.ging.utils.ImageLoader;

/**
 * 图片显示控件
 *
 */
public class TourPicBlockItem extends DynamicLayoutItem {

    private View.OnClickListener mOnClickListener;
    private ImageView imageView;
    private PhotoUri photoUri;
    private boolean isCenterInside = false;


    public TourPicBlockItem(Context context, int columnCount) {
        super(context,columnCount);
    }

    @Override
    protected void setViewData(View view) {
        imageView = (ImageView) view.findViewById(R.id.image);
        if (isCenterInside){
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        final int imageSize = (screenWidth - 2* mContext.getResources().getDimensionPixelSize(R.dimen.padding_tour_pic_block))/mColumnCount
                - 2*mContext.getResources().getDimensionPixelSize(R.dimen.padding_tour_pic_item);
        params.width = imageSize;
        params.height = imageSize;

        if (photoUri!= null){
            ImageLoader.getInstance().displayImage(mContext,photoUri.getUriStr(),imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_add_pic);
        }
        imageView.setOnClickListener(mOnClickListener);
        imageView.setOnTouchListener(new ImageTouchListener());
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        if (imageView != null){
            imageView.setOnClickListener(onClickListener);
        }
    }

    public void setPhotoUri(PhotoUri photoUri) {
        this.photoUri = photoUri;
        if (imageView != null && photoUri != null){
            if (photoUri.getType() == 1){
                ImageLoader.getInstance().displayImage(mContext,"file://"+photoUri.getUriStr(),imageView);
            } else {
                ImageLoader.getInstance().displayImage(mContext, photoUri.getUriStr(),imageView);
            }
        }
    }

    public void setCenterInside(boolean centerInside){
        isCenterInside = centerInside;
    }
}
