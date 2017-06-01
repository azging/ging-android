package com.azging.ging.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.azging.ging.R;
import com.azging.ging.utils.FileUtils;
import com.azging.ging.utils.ImageLoader;
import com.azging.ging.utils.PrefConstants;
import com.azging.ging.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GG on 2017/5/31.
 */

public class PhotoWallAdapter extends BaseAdapter {


    private static final int TYPE_TAKE_PHOTO = 1;
    private static final int TYPE_PIC = 0;

    private Context context;
    private ArrayList<String> imagePathList = null;
    private List<String> selectedList;

    private int maxSize = 1;
    private int[] selectedIcons = new int[]{R.drawable.selected_1, R.drawable.selected_2, R.drawable.selected_3, R.drawable.selected_4,
            R.drawable.selected_5, R.drawable.selected_6, R.drawable.selected_7, R.drawable.selected_8, R.drawable.selected_9};
    private boolean enableTakePhoto;
    private Uri createTourPicUri;

    public PhotoWallAdapter(Context context, ArrayList<String> imagePathList, int limit, boolean enableTakePhoto) {
        this.context = context;
        this.imagePathList = imagePathList;
        this.maxSize = limit;
        this.enableTakePhoto = enableTakePhoto;
    }

    public void setSelectedList(List<String> list){
        selectedList = list;
    }

    public String[] exportSelectedArray(){
        if (selectedList == null || selectedList.size() == 0)
            return null;
        String[] array = new String[selectedList.size()];
        for(int i = 0; i<selectedList.size(); i++){
            array[i] = selectedList.get(i);
        }
        return array;
    }

    @Override
    public int getCount() {
        int count =  imagePathList == null ? 0 : imagePathList.size();
        if (enableTakePhoto){
            count ++;
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        if (enableTakePhoto){
            position--;
        }
        if (position < 0){
            return null;
        }
        return imagePathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"NewApi", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String filePath = "file://" + getItem(position);
        final int type = getItemViewType(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.image_picker_photo_wall_item, null);
            holder = new ViewHolder();

            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.photo_wall_item_photo);
            holder.checkBox = (ImageView) convertView
                    .findViewById(R.id.photo_wall_item_cb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (type == TYPE_TAKE_PHOTO){
            holder.checkBox.setVisibility(View.GONE);
            holder.imageView.clearColorFilter();
            holder.imageView.setImageResource(R.drawable.image_camera);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.imageView.setBackgroundResource(R.color.process_date_gray);
            holder.imageView.setTag(filePath);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // tag的key必须使用id的方式定义以保证唯一，否则会出现IllegalArgumentException.

            int index = selectedList.indexOf(filePath);
            if (index == -1) {
                holder.imageView.setColorFilter(null);
                holder.checkBox.setImageResource(R.drawable.img_check_normal);
            } else {
                holder.imageView.setColorFilter(ContextCompat.getColor(context,R.color.image_checked_bg));
                holder.checkBox.setImageResource(getSelectedIconId(index));
            }
            if (!filePath.equals(holder.imageView.getTag())) {
                ImageLoader.getInstance().displayImage(context,filePath,holder.imageView);
//				ImageLoader.getInstance().displayImage(filePath, holder.imageView);
            }
            holder.imageView.setTag(filePath);
        }
        View.OnClickListener itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == TYPE_TAKE_PHOTO) {
                    if (selectedList.size() >= maxSize){
                        ToastUtil.showShort(context, "最多可添加" + maxSize + "张图片。");
                    } else {
                        createTourPicUri = FileUtils.getImageFileUriForTakePhoto();
                        if (!FileUtils.checkSDCard()) {
                            ToastUtil.showShort(context, R.string.sdcard_not_connected);
                            return;
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, createTourPicUri);
                        ((Activity)context).startActivityForResult(intent, PrefConstants.REQUEST_TAKE_PICTURE);
                    }
                } else {
                    holder.checkBox.setSelected(true);
                    int index = selectedList.indexOf(filePath);
                    if (index == -1) {
                        //set item selected
                        if (selectedList.size() >= maxSize){
                           ToastUtil.showShort(context, "最多可添加" + maxSize + "张图片。");
                        } else {
                            selectedList.add(filePath);
                        }
                    } else {
                        //set item unselected
                        selectedList.remove(filePath);
                    }
                    notifyDataSetChanged();
                }
            }
        };
        holder.imageView.setOnClickListener(itemListener);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (enableTakePhoto && position == 0){
            return TYPE_TAKE_PHOTO;
        }
        return TYPE_PIC;
    }

    private int getSelectedIconId(int index){
        if (maxSize == 1){
            return R.drawable.choose;
        } else {
            return selectedIcons[index];
        }
    }

    public List<String> getSelectedList(){
        return selectedList;
    }

    public Uri getCreateTourPicUri(){
        return createTourPicUri;
    }

    private class ViewHolder {
        ImageView imageView;
        ImageView checkBox;
    }
}
