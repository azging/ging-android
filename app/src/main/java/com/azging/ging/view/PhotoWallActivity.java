package com.azging.ging.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.adapter.PhotoWallAdapter;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.utils.AsyncDownTaskNoPop;
import com.azging.ging.utils.FileUtils;
import com.azging.ging.utils.PrefConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GG on 2017/5/31.
 */

public class PhotoWallActivity extends BaseMainActivity implements IActivity{


    public static final String KEY_SELECTED_LIST = "selected_list";
    public static final String KEY_AMOUNT_LIMIT = "amount_limit";
    public static final String KEY_ENABLE_TAKE_PHOTO = "enable_take_photo";
    public static final String PATH_LATEST = "#LATEST#";

    private int mLimit = 1;
    private TextView titleTV;

    private ArrayList<String> list;
    private ArrayList<String> latestList;
    private GridView mPhotoWall;
    private PhotoWallAdapter adapter;
    /**
     * 当前文件夹路径
     */
    private String currentFolder = null;
    /**
     * 当前展示的是否为最近照片
     */
    private boolean isLatest = true;
    private boolean enableTakePhoto = false;

    public static void startActivity(Object object, int requestCode, int amountLimit) {
        startActivity(object, requestCode, amountLimit, true, null);
    }

    public static void startActivity(Object object, int requestCode, int amountLimit, boolean enableTakePhoto, String[] selectedList){
        Activity activity;
        Fragment fragment = null;
        if (object instanceof Activity) {
            activity = (Activity) object;
        } else if (object instanceof Fragment) {
            fragment = (Fragment) object;
            activity = fragment.getActivity();
        } else {
            throw new IllegalArgumentException("Need Activity or Fragment !");
        }
        Intent intent = new Intent(activity, PhotoWallActivity.class);
        intent.putExtra(KEY_AMOUNT_LIMIT, amountLimit);
        intent.putExtra(KEY_ENABLE_TAKE_PHOTO, enableTakePhoto);
        if (selectedList != null) {
            intent.putExtra(KEY_SELECTED_LIST, selectedList);
        }
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 点击返回时，跳转至相册页面
     */
    @SuppressLint("NewApi")
    private void backAction() {
        Intent intent = new Intent(PhotoWallActivity.this, PhotoAlbumActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        if (latestList != null && latestList.size() > 0) {
            intent.putExtra("latest_count", latestList.size());
            intent.putExtra("latest_first_img", latestList.get(0));
        }
        startActivityForResult(intent, PrefConstants.REQUEST_ALBUM_PATH);
        //动画
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }


    /**
     * 根据图片所属文件夹路径，刷新页面
     */
    @SuppressLint("NewApi")
    private void updateView(int code, String folderPath) {
        list.clear();
        //adapter.clearSelectionMap();
        adapter.notifyDataSetChanged();

        if (code == 100) {   //某个相册
            int lastSeparator = folderPath.lastIndexOf(File.separator);
            String folderName = folderPath.substring(lastSeparator + 1);
            titleTV.setText(folderName);
            ArrayList<String> pathList = getAllImagePathsByFolder(folderPath);
            if(pathList!=null) {
                list.addAll(pathList);
            }
            //setAllHashSet();
        } else if (code == 200) {  //最近照片
            titleTV.setText(R.string.latest_image);
            if (latestList == null){
                updateImageList();
            } else {
                list.addAll(latestList);
                adapter.notifyDataSetChanged();
                if (list.size() > 0) {
                    //滚动至顶部
                    mPhotoWall.smoothScrollToPosition(0);
                }
            }
            //setAllHashSet();
        }
    }

    private void updateImageList(){
//        showProgressDialog();
        new AsyncDownTaskNoPop(){

            List<String> arr = null;
            @Override
            protected String doInBackground(Integer... params) {
                arr = getLatestImagePaths(100);
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
//                hideProgressDialog();
                if(arr!=null){
                    latestList = new ArrayList<>();
                    latestList.addAll(arr);
                    list.addAll(latestList);
                    adapter.notifyDataSetChanged();
                    if (list.size() > 0) {
                        //滚动至顶部
                        mPhotoWall.smoothScrollToPosition(0);
                    }
                }
            }

        }.execute(0);
    }


    /**
     * 获取指定路径下的所有图片文件。
     */
    private ArrayList<String> getAllImagePathsByFolder(String folderPath) {
        File folder = new File(folderPath);
        String[] allFileNames = folder.list();
        if (allFileNames == null || allFileNames.length == 0) {
            return null;
        }

        ArrayList<String> imageFilePaths = new ArrayList<>();
        for (int i = allFileNames.length - 1; i >= 0; i--) {
            if (isImage(allFileNames[i])) {
                imageFilePaths.add(folderPath + File.separator + allFileNames[i]);
            }
        }

        return imageFilePaths;
    }

    /**
     * 使用ContentProvider读取SD卡最近图片。
     */
    private ArrayList<String> getLatestImagePaths(int maxCount) {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;

        ContentResolver mContentResolver = getContentResolver();

        // 只查询jpg和png的图片,按最新修改排序
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);

        ArrayList<String> latestImagePaths = null;
        if (cursor != null) {
            //从最新的图片开始读取.
            //当cursor中没有数据时，cursor.moveToLast()将返回false
            if (cursor.moveToLast()) {
                latestImagePaths = new ArrayList<>();
                while (true) {
                    // 获取图片的路径
                    String path = cursor.getString(0);
                    if(FileUtils.isFileExists(path)){
                        long size = FileUtils.getFileSize(path);
                        if(size>PrefConstants.MIN_IMAGE_SIZE)
                            latestImagePaths.add(path);
                    }
                    if (latestImagePaths.size() >= maxCount || !cursor.moveToPrevious()) {
                        break;
                    }
                }
            }
            cursor.close();
        }

        return latestImagePaths;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PrefConstants.REQUEST_TAKE_PICTURE) {
            if (resultCode != Activity.RESULT_OK)
                return;
            Intent intent = getIntent();
//            LogHelper.e("filepath : " + data.getStringExtra(MediaStore.EXTRA_OUTPUT));
            adapter.getSelectedList().add(adapter.getCreateTourPicUri().toString());
            updateImageList();
//            latestList.add(0, adapter.getCreateTourPicUri().toString());
            String[] imageArray = adapter.exportSelectedArray();
            intent.removeExtra(KEY_SELECTED_LIST);
            intent.putExtra(KEY_SELECTED_LIST, imageArray);
            setResult(RESULT_OK, intent);
            finish();
//            DuckrFuncs.startCreateTourPicActivity(this, createTourPicUri, CreateTourPicActivity.TYPE_TAKE_PIC);
        } else {
            if (resultCode == RESULT_OK){
                String folderPath = data.getStringExtra("folderPath");
                if (PATH_LATEST.equals(folderPath)){
                    //“最近照片”
                    if (!isLatest) {
                        updateView(200, null);
                        isLatest = true;
                    }
                } else {
                    //某个相册
                    if (isLatest || (folderPath != null && !folderPath.equals(currentFolder))) {
                        currentFolder = folderPath;
                        updateView(100, currentFolder);
                        isLatest = false;
                    }
                }
            }
        }
    }

    /**判断该文件是否是一个图片。*/
    public static boolean isImage(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }

    @Override
    public int initView() {
        return R.layout.image_picker_photo_wall;
    }

    @Override
    public void initData() {

        if(getIntent().hasExtra(KEY_AMOUNT_LIMIT)){
            mLimit = this.getIntent().getIntExtra(KEY_AMOUNT_LIMIT, 1);
        }
        enableTakePhoto = getIntent().getBooleanExtra(KEY_ENABLE_TAKE_PHOTO, false);
        titleTV = (TextView) findViewById(R.id.topbar_title_tv);
        titleTV.setText(R.string.latest_image);

        Button backBtn = (Button) findViewById(R.id.topbar_left_btn);
        Button confirmBtn = (Button) findViewById(R.id.topbar_right_btn);
        backBtn.setText(R.string.photo_album);
        backBtn.setVisibility(View.VISIBLE);
        confirmBtn.setText(R.string.confirm);
        confirmBtn.setVisibility(View.VISIBLE);

        mPhotoWall = (GridView) findViewById(R.id.photo_wall_grid);
        list = new ArrayList<>();
        List<String> selectedList = new ArrayList<>();
        if (getIntent().hasExtra(KEY_SELECTED_LIST)){
            String[] selected = getIntent().getStringArrayExtra(KEY_SELECTED_LIST);
            if (selected != null){
                for(String str : selected){
                    if (str != null){
                        selectedList.add(str);
                    }
                }
            }
        }
        adapter = new PhotoWallAdapter(this, list, mLimit, enableTakePhoto);
        adapter.setSelectedList(selectedList);
        mPhotoWall.setAdapter(adapter);
        updateImageList();

        //选择照片完成
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择图片完成,回到起始页面
                Intent intent = getIntent();
                String[] imageArray = adapter.exportSelectedArray();
                intent.removeExtra(KEY_SELECTED_LIST);
                intent.putExtra(KEY_SELECTED_LIST, imageArray);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //点击返回，回到选择相册页面
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backAction();
            }
        });

    }
}
