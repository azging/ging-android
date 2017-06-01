package com.azging.ging.view;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.adapter.PhotoAlbumLVAdapter;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.PhotoAlbumLVItem;
import com.azging.ging.utils.FileUtils;
import com.azging.ging.utils.Log;
import com.azging.ging.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import static com.azging.ging.view.PhotoWallActivity.isImage;

/**
 * 分相册查看SD卡所有图片。
 * Created by GG on 2017/5/31.
 */
public class PhotoAlbumActivity extends Activity implements IActivity{

    /**
     * 点击返回时，回到相册页面
     */
    private void backAction() {
        Intent intent = new Intent(this, PhotoWallActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * 获取目录中图片的个数。
     */
    private int getImageCount(File folder) {
        int count = 0;
        File[] files = folder.listFiles();
        if(files == null) {
            finish();
            return 0;
        }
        for (File file : files) {
            if (isImage(file.getName())) {
                count++;
            }
        }

        return count;
    }

    /**
     * 获取目录中最新的一张图片的绝对路径。
     */
    private String getFirstImagePath(File folder) {
        File[] files = folder.listFiles();
        if(files == null) {
            finish();
            return null;
        }
        for (int i = files.length - 1; i >= 0; i--) {
            File file = files[i];
            if (isImage(file.getName())) {
                return file.getAbsolutePath();
            }
        }

        return null;
    }

    /**
     * 使用ContentProvider读取SD卡所有图片。
     */
    private ArrayList<PhotoAlbumLVItem> getImagePathsByContentProvider() {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;

        ContentResolver mContentResolver = getContentResolver();

        // 只查询jpg和png的图片
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);

        ArrayList<PhotoAlbumLVItem> list = null;
        if (cursor != null) {
            if (cursor.moveToLast()) {
                //路径缓存，防止多次扫描同一目录
                HashSet<String> cachePath = new HashSet<>();
                list = new ArrayList<>();

                while (true) {
                    // 获取图片的路径
                    String imagePath = cursor.getString(0);
                    File parentFile = new File(imagePath).getParentFile();
//                    String parentPath = parentFile.getPath();
                    String parentPath = new File(imagePath).getParent();

                    //不扫描重复路径
                    if (!cachePath.contains(parentPath)) {
                        list.add(new PhotoAlbumLVItem(parentPath, getImageCount(parentFile),
                                getFirstImagePath(parentFile)));
                        cachePath.add(parentPath);
                    }

                    if (!cursor.moveToPrevious()) {
                        break;
                    }
                }
            }

            cursor.close();
        }

        return list;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //动画
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    public int initView() {
        return R.layout.image_picker_photo_album;
    }

    @Override
    public void initData() {

        if (!FileUtils.isSDcardOK()) {
            ToastUtil.showShort(this, "SD卡不可用。");
            return;
        }

        Intent t = getIntent();
        if (!t.hasExtra("latest_count")) {
            Log.e("Missing latest_count info!");
            return;
        }

        TextView titleTV = (TextView) findViewById(R.id.topbar_title_tv);
        titleTV.setText(R.string.select_album);

        Button cancelBtn = (Button) findViewById(R.id.topbar_right_btn);
        cancelBtn.setText(R.string.cancel);
        cancelBtn.setVisibility(View.VISIBLE);

        ListView listView = (ListView) findViewById(R.id.select_img_listView);

//        //第一种方式：使用file
//        File rootFile = new File(Utility.getSDcardRoot());
//        //屏蔽/mnt/sdcard/DCIM/.thumbnails目录
//        String ignorePath = rootFile + File.separator + "DCIM" + File.separator + ".thumbnails";
//        getImagePathsByFile(rootFile, ignorePath);

        //第二种方式：使用ContentProvider。（效率更高）
        final ArrayList<PhotoAlbumLVItem> list = new ArrayList<>();
        //“最近照片”
        list.add(new PhotoAlbumLVItem(getResources().getString(R.string.latest_image),
                t.getIntExtra("latest_count", -1), t.getStringExtra("latest_first_img")));
        //相册
        list.addAll(getImagePathsByContentProvider());

        PhotoAlbumLVAdapter adapter = new PhotoAlbumLVAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                String path;
                //第一行为“最近照片”
                if (position == 0) {
                    path = PhotoWallActivity.PATH_LATEST;
                } else {
                    path = list.get(position).getPathName();
//                    if (!path.startsWith("file://")){
//                        path = Uri.fromFile(new File(path)).toString();
//                    }
                }
                intent.putExtra("folderPath", path);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消，回到主页面
                finish();
            }
        });
        setResult(RESULT_CANCELED);
    }

    //    /**
//     * 使用File读取SD卡所有图片。
//     */
//    private void getImagePathsByFile(File file, String ignorePath) {
//        if (file.isFile()) {
//            File parentFile = file.getParentFile();
//            String parentFilePath = parentFile.getAbsolutePath();
//
//            if (cachePath.contains(parentFilePath)) {
//                return;
//            }
//
//            if (isImage(file.getName())) {
//                list.add(new SelectImgGVItem(parentFilePath, getImageCount(parentFile),
//                        getFirstImagePath(parentFile)));
//                cachePath.add(parentFilePath);
//            }
//        } else {
//            String absolutePath = file.getAbsolutePath();
//            //屏蔽文件夹
//            if (absolutePath.equals(ignorePath)) {
//                return;
//            }
//
//            //不读取缩略图
//            if (absolutePath.contains("thumb")) {
//                return;
//            }
//
//            //不读取层级超过5的
//            if (Utility.countMatches(absolutePath, File.separator) > 5) {
//                return;
//            }
//
//            //不读取路径包含.的和隐藏文件
//            if (file.getName().contains(".")) {
//                return;
//            }
//
//            File[] childFiles = file.listFiles();
//            if (childFiles != null) {
//                for (File childFile : childFiles) {
//                    getImagePathsByFile(childFile, ignorePath);
//                }
//            }
//        }
//    }
}
