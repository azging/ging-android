package com.azging.ging.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseApp;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.bean.UserBean;
import com.azging.ging.customui.photoview.PhotoView;
import com.azging.ging.customui.photoview.PhotoViewAttacher;
import com.azging.ging.utils.DownLoadImageService;
import com.azging.ging.utils.FileUtils;
import com.azging.ging.utils.PrefConstants;
import com.azging.ging.utils.ToastUtil;
import com.azging.ging.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

import java.io.File;
import java.util.Arrays;

/**
 * Created by GG on 2017/5/31.
 */

public class ViewTourPicActivity extends BaseMainActivity {

    public static final String KEY_PATH = "path";
    public static final String KEY_ENABLE_DELETE = "enable_delete";
    public static final String KEY_ENABLE_SAVE = "enable_save";
    public static final String KEY_IMG_LIST = "image_list";
    public static final String KEY_INDEX = "index";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PHOTO_WRAPPER = "photo_wrapper";
    public static final String KEY_INVITE_WRAPPER = "invite_wrapper";
    public static final String TYPE_DEFAULT = "default"; // 只查看图片，不删除，不举报，如查看Activ图片
    public static final String TYPE_DELETE = "delete";
    public static final String TYPE_PHOTO = "photo";
    public static final String TYPE_INVITE = "invite";

    private String mImagePath;
    private boolean enableDelete;
    private boolean enableSave;
    private ViewPager mViewPager;
    private String[] mImageList;
    private String mType;
    private UserBean createUser;
    private TextView mTitleView;
    private View rightMenu;
    private int mIndex = 0;
    private boolean[] downloadStatus;
    private Handler mHandler;
    private Context mContext;
    private LocalBroadcastManager localBroadcastManager;

    public static void startViewTourPicActivity(Activity activity, Fragment fragment, String path, boolean enableDelete) {
        Intent intent = new Intent(activity, ViewTourPicActivity.class);
        intent.putExtra(KEY_PATH, path);
        intent.putExtra(KEY_TYPE, TYPE_DELETE);
        intent.putExtra(KEY_ENABLE_DELETE, enableDelete);
        if (fragment != null) {
            fragment.startActivityForResult(intent, PrefConstants.REQUEST_VIEW_TOUR_PIC);
        } else {
            activity.startActivityForResult(intent, PrefConstants.REQUEST_VIEW_TOUR_PIC);
            activity.overridePendingTransition(R.anim.activity_stand_still, R.anim.activity_stand_still);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        mContext = this;
        mHandler = new Handler();
        mImagePath = intent.getStringExtra(KEY_PATH);
        mImageList = intent.getStringArrayExtra(KEY_IMG_LIST);
        mType = intent.getStringExtra(KEY_TYPE);
        if (mImageList == null) {
            if (mImagePath == null) {
                finish();
                return;
            }
            mImageList = new String[]{mImagePath};
        }
        downloadStatus = new boolean[mImageList.length];
        Arrays.fill(downloadStatus, false);
        mIndex = intent.getIntExtra(KEY_INDEX, 0);
        enableDelete = intent.getBooleanExtra(KEY_ENABLE_DELETE, false);
        enableSave = intent.getBooleanExtra(KEY_ENABLE_SAVE, false);
        localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        setContentView(R.layout.activity_view_tour_pic);
        setupViews();
    }

    private void setupViews() {
        mTitleView = (TextView) findViewById(R.id.title);
        updateTitle();
        rightMenu = findViewById(R.id.menu_img_area);
        ImageView rightImageView = (ImageView) findViewById(R.id.menu_img);
        if (TYPE_DELETE.equals(mType)) {
            rightImageView.setImageResource(R.drawable.ic_action_delete);
            rightMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(R.string.hint)
                            .setMessage(R.string.delete_pic_hint)
                            .setNegativeButton(R.string.cancel, null)
                            .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setResult(RESULT_OK, getIntent());
                                    finish();
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            });
        } else if (TYPE_PHOTO.equals(mType) || TYPE_INVITE.equals(mType)) {
            rightImageView.setImageResource(R.drawable.icon_more);
            rightMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int arrId = R.array.others_photo_options;
                    if (createUser.equals(BaseApp.app.getCurrentUser())) {
                        arrId = R.array.my_photo_options;
                    }
                    Utils.showSimpleItemArrDialog(ViewTourPicActivity.this, 0, arrId, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0: // 保存到手机
                                    savePic();
                                    break;
                                case 1: // 自己 - 删除， 别人 - 举报

                                    break;
                            }
                        }
                    });
                }
            });
        } else {
            rightMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.showSimpleItemArrDialog(ViewTourPicActivity.this, 0, R.array.pic_options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0: // 保存到手机
                                    savePic();
                                    break;
                            }
                        }
                    });
                }
            });
        }
        findViewById(R.id.left_img_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new ImagePagerAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mIndex = position;
                updateTitle();
                if (!downloadStatus[position]) {
                    rightMenu.setEnabled(false);
                }
            }
        });
        mViewPager.setCurrentItem(mIndex);
    }

    private void updateTitle() {
        mTitleView.setText("" + (mIndex + 1) + "/" + mImageList.length);
    }

    private void savePic() {
        if (!Utils.isLoggedIn()) {
            ToastUtil.showShort(ViewTourPicActivity.this, R.string.login_download_pic);
            LoginActivity.startActivity(this);
            return;
        }
//        NewUser user = DuckrApp.getInstance().getCurrentUser();
//        if (user.getCoins() < 200) {
//            ComFuncs.myToast(ViewTourPicActivity.this, "积分低于200无法下载图片");
//            return;
//        }
        String url = mImageList[mIndex];

        onDownLoad(url);

    }

    private void onDownLoad(String url) {
        DownLoadImageService service = new DownLoadImageService(getApplicationContext(), url,
                new DownLoadImageService.ImageDownLoadCallBack() {

                    @Override
                    public void onDownLoadSuccess(File file) {
                        final File imageFile = file;
//        final File imageFile = ImageLoader.getInstance().getDiskCache().get(url);
                        if (imageFile == null || imageFile.exists()) {
                            new Thread() {
                                @Override
                                public void run() {
                                    final File destFile = new File(FileUtils.getImageFilePath());
                                    final boolean success = FileUtils.copy(imageFile, destFile);
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (success) {
                                                ToastUtil.showShort(getBaseContext(), R.string.save_image_success);
                                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(destFile)));
                                            } else {
                                                ToastUtil.showShort(getBaseContext(), R.string.net_work_error);
                                            }
                                        }
                                    });
                                }
                            }.start();
                        }

                    }

                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap) {
                        // 在这里执行图片保存方法
//                        Message message = new Message();
//                        message.what = MSG_VISIBLE;
//                        handler.sendMessageDelayed(message, delayTime);
                    }

                    @Override
                    public void onDownLoadFailed() {
                        // 图片保存失败

                        ToastUtil.showShort(getBaseContext(), R.string.net_work_error);

//                        Message message = new Message();
//                        message.what = MSG_ERROR;
//                        handler.sendMessageDelayed(message, delayTime);
                    }
                });
        //启动图片下载线程
        new Thread(service).start();
    }


    class ImagePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageList.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            FrameLayout frameLayout = new FrameLayout(ViewTourPicActivity.this);
            final ProgressBar progressBar = new ProgressBar(ViewTourPicActivity.this);
            final PhotoView photoView = new PhotoView(ViewTourPicActivity.this);
            FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayout.addView(photoView, params1);
            FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            frameLayout.addView(progressBar, params2);
            String path = mImageList[position];

            Glide.with(mContext).load(path).into(new ImageViewTarget<GlideDrawable>(photoView) {
                @Override
                protected void setResource(GlideDrawable resource) {
                    downloadStatus[position] = true;
                    if (position == mIndex) {
                        rightMenu.setEnabled(true);
                    }
                    photoView.setImageDrawable(resource);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadStarted(Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

//            Glide.with(mContext).load(path).into(new Target<GlideDrawable>() {
//                @Override
//                public void onLoadStarted(Drawable placeholder) {
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onLoadFailed(Exception e, Drawable errorDrawable) {
//
//                }
//
//                @Override
//                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                    downloadStatus[position] = true;
//                    if (position == mIndex) {
//                        rightMenu.setEnabled(true);
//                    }
//                    photoView.setImageDrawable(resource);
//                    progressBar.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onLoadCleared(Drawable placeholder) {
//
//                }
//
//                @Override
//                public void getSize(SizeReadyCallback cb) {
//
//                }
//
//                @Override
//                public void setRequest(Request request) {
//
//                }
//
//                @Override
//                public Request getRequest() {
//                    return null;
//                }
//
//                @Override
//                public void onStart() {
//
//                }
//
//                @Override
//                public void onStop() {
//
//                }
//
//                @Override
//                public void onDestroy() {
//
//                }
//            });

//            ImageLoader.getInstance().loadImage(path, new ImageLoadingListener() {
//                @Override
//                public void onLoadingStarted(String s, View view) {
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onLoadingFailed(String s, View view, FailReason failReason) {
//                }
//
//                @Override
//                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//                    downloadStatus[position] = true;
//                    if (position == mIndex) {
//                        rightMenu.setEnabled(true);
//                    }
//                    photoView.setImageBitmap(bitmap);
//                    progressBar.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onLoadingCancelled(String s, View view) {
//
//                }
//            });
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }

                @Override
                public void onOutsidePhotoTap() {
                    finish();
                }
            });
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(frameLayout, params);
            return frameLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
