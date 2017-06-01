package com.azging.ging.customui.photoview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.azging.ging.R;
import com.azging.ging.bean.PhotoUri;
import com.azging.ging.customui.dynamiclayout.DynamicLayout;
import com.azging.ging.utils.PrefConstants;
import com.azging.ging.view.PhotoWallActivity;
import com.azging.ging.view.PublishQuestionActivity;
import com.azging.ging.view.ViewTourPicActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 图片显示控件
 *
 */
public class TourPicBlock extends DynamicLayout {

    private TourPicBlockItem addItem;
    private static final String KEY_ADD = "add";
    private int limit;
    private boolean enableTakePhoto;
    private Fragment fragment=null;

    public TourPicBlock(Context context, LinearLayout container, int limit, int columnCount) {
        super(context, container,columnCount);
        this.limit = limit;
    }

    public void setFragment(Fragment fragment){
        this.fragment = fragment;
    }

    @Override
    protected void doAfterCreated() {

    }

    @Override
    protected List<String> getDefaultItemKeyList() {
        if (getItemCount() < limit && !mItemKeyList.contains(KEY_ADD)){
            mItemKeyList.add(KEY_ADD);
        }
        return mItemKeyList;
    }

    @Override
    protected void buildBlockItemMap() {
        mItemMap = new HashMap<>();
        addItem = new TourPicBlockItem(mContext,mColumnCount);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext instanceof PublishQuestionActivity){
                    MobclickAgent.onEvent(mContext, "PublishTourPic_AddImage");
                }
                Intent intent = new Intent(mContext, PhotoWallActivity.class);
                intent.putExtra(PhotoWallActivity.KEY_AMOUNT_LIMIT, limit);
                intent.putExtra(PhotoWallActivity.KEY_ENABLE_TAKE_PHOTO, enableTakePhoto);
                String[] selected = new String[getItemCount()];
                for(int i = 0; i< getItemCount(); i++){
                    if (KEY_ADD != mItemKeyList.get(i)){
                        selected[i] = mItemKeyList.get(i);
                    }
                }
                intent.putExtra(PhotoWallActivity.KEY_SELECTED_LIST, selected);
                if (fragment==null)
                    ((Activity)mContext).startActivityForResult(intent, PrefConstants.GO_TO_PHOTO);
                else
                    fragment.startActivityForResult(intent, PrefConstants.GO_TO_PHOTO);
            }
        });
        addItem.setCenterInside(true);
        mItemMap.put(KEY_ADD, addItem);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_tour_pic_block;
    }

    @Override
    protected int getColumnCount() {
        return mColumnCount;
    }

    @Override
    protected int getPrebuildItemCount() {
        return mColumnCount;
    }

    public void addPic(final PhotoUri photoUri){
        String key = photoUri.getUriStr();
        if (mItemMap.containsKey(key)){
            return ;
        }
        while (mItemKeyList.contains(KEY_ADD)){
            mItemKeyList.remove(KEY_ADD);
        }
        if (getItemCount() < limit){
            TourPicBlockItem item = new TourPicBlockItem(mContext,mColumnCount);
            item.setPhotoUri(photoUri);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewTourPicActivity.startViewTourPicActivity((Activity)mContext,fragment,photoUri.getUriStr(), true);
                }
            });
            mItemMap.put(key, item);
            mItemKeyList.add(key);
        }
        if (getItemCount() < limit){
            mItemKeyList.add(KEY_ADD);
        }
    }

    public void removePic(PhotoUri photoUri){
        if (photoUri == null)
            return ;
        String key = photoUri.getUriStr();
        removePath(key);
    }

    private void removePath(String key) {
        mItemMap.remove(key);
        mItemKeyList.remove(key);
    }

    public void clearPicList(){
        List<String> list = new ArrayList<>();
        list.addAll(mItemKeyList);
        for(String key : list){
            if (!KEY_ADD.equals(key))
                removePath(key);
        }
    }

    public void setEnableTakePhoto(boolean enableTakePhoto) {
        this.enableTakePhoto = enableTakePhoto;
    }
}
