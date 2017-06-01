package com.azging.ging.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.CreateUserWrapper;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.UserBean;
import com.azging.ging.net.JsonCallBack;
import com.azging.ging.net.QiniuComplete;
import com.azging.ging.net.WebUtils;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.AsyncDownTaskNoPop;
import com.azging.ging.utils.FileUtils;
import com.azging.ging.utils.GsonUtil;
import com.azging.ging.utils.ImageLoader;
import com.azging.ging.utils.ImageUtils;
import com.azging.ging.utils.Log;
import com.azging.ging.utils.PrefConstants;
import com.azging.ging.utils.ProgressDialogHelper;
import com.azging.ging.utils.QiniuImageType;
import com.azging.ging.utils.SharedPreferencesHelper;
import com.azging.ging.utils.ToastUtil;
import com.azging.ging.utils.Utils;
import com.azging.ging.utils.constants.LocalBroadcastHelper;
import com.azging.ging.utils.crop.Crop;
import com.qiniu.android.http.ResponseInfo;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by GG on 2017/6/1.
 */

public class UserInfoActivity extends BaseMainActivity implements IActivity {
    private String TAG = "UserInfoActivity";
    private static final String KEY_USERINFOACTIVITY = "USERINFOACTIVITY";

    @BindView(R.id.user_avatar) ImageView mUserAvatar;
    @BindView(R.id.user_nick) TextView mUserNick;
    @BindView(R.id.user_gender) TextView mUserGender;
    @BindView(R.id.user_save) TextView mUserSave;
    @BindView(R.id.change_user_avatar) LinearLayout mChangeUserAvatar;

    private Uri avatarBitmapUri = null;
    private Bitmap mAvatarBitmap = null;
    private boolean isModified = false;
    private WebUtils mWebUtils;
    private UserBean mUserBean;
    private ProgressDialogHelper mProgressDialogHelper;
    private EditText dialogTextEdit;


    public static void startActivity(Context context, UserBean userBean) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(KEY_USERINFOACTIVITY, userBean);
        context.startActivity(intent);
    }

    @Override
    public int initView() {
        return R.layout.activity_user_info;
    }

    public void initData() {
        mWebUtils = new WebUtils(this);
        mProgressDialogHelper = ProgressDialogHelper.getInstance();
        mProgressDialogHelper.initProgressDialog(this);
        mUserBean = (UserBean) getIntent().getSerializableExtra(KEY_USERINFOACTIVITY);

        mUserNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mUserBean.setNick(s.toString());
                isModified = true;
            }
        });

        mUserNick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showEditDialog("输入昵称", mUserBean.getNick(), R.string.edit_nick, new InputCallback() {

                    @Override
                    public void resultCallback(String str) {
                        if (!TextUtils.isEmpty(str)) {
                            if (str.length() > 10) {
                                ToastUtil.showShort(UserInfoActivity.this, R.string.nick_limit);
                                return;
                            }
                            isModified = true;
                            mUserBean.setNick(str);
                            mUserNick.setText(str);
                        }
                    }
                });
            }
        });

        ImageLoader.getInstance().displayImage(this, mUserBean.getThumbAvatarUrl(), mUserAvatar);
        mUserNick.setText(mUserBean.getNick());
        if (mUserBean.getGender() == 0)
            mUserGender.setText("未知");
        else if (mUserBean.getGender() == 1)
            mUserGender.setText("男");
        else if (mUserBean.getGender() == 2)
            mUserGender.setText("女");
    }

    @OnClick({R.id.change_user_avatar, R.id.user_gender, R.id.user_save})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.change_user_avatar:
                avatarBitmapUri = FileUtils.getImageFilePathWithFile();
                Utils.showPictureSelectorDialog(UserInfoActivity.this, avatarBitmapUri);
                break;
            case R.id.user_gender:
                new AlertDialog.Builder(UserInfoActivity.this).setItems(R.array.user_gender, new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which + 1 == 1) {
                                    mUserBean.setGender(1);
                                    mUserGender.setText("男");
                                } else if (which + 1 == 2) {
                                    mUserBean.setGender(2);
                                    mUserGender.setText("女");
                                }
                                isModified = true;
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.user_save:
                checkModified();
                break;
        }
    }

    private interface InputCallback {
        public void resultCallback(String str);
    }

    private void showEditDialog(String editHint, String editText, int title, final InputCallback inputcallback) {
        View layout = getLayoutInflater().inflate(R.layout.dialog_input_text, (ViewGroup)
                findViewById(R.id.dialog_input_root));
        dialogTextEdit = (EditText) layout.findViewById(R.id.dialog_input_edit);
        dialogTextEdit.setHint(editHint);

        dialogTextEdit.setText(editText);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(layout)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = dialogTextEdit.getText().toString();
                        inputcallback.resultCallback(str);
                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        checkModified();
    }

    private void checkModified() {
        if (isModified) {
            Utils.showConfirmCancelHintDialog(this, "是否保存修改的信息?", new DialogInterface
                    .OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onSubmitClick();
                }
            }, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AppManager.getAppManager().finishActivity();
                }
            });
        } else
            AppManager.getAppManager().finishActivity();
    }

    private void onSubmitClick() {
        mProgressDialogHelper.showProgressDialog();
        if (mAvatarBitmap != null && !mAvatarBitmap.isRecycled()) {
            mWebUtils.qiniuUploadPic(mAvatarBitmap, QiniuImageType.AVATAR, "", new
                    QiniuComplete() {

                        @Override
                        public void Compplete(String key, ResponseInfo info, JSONObject response,
                                              String tag) {
                            String url = PrefConstants.QINIU_PIC_PREFIX + key;
                            mUserBean.setAvatarUrl(url);
                            submitInfo();
                        }
                    });
        } else
            submitInfo();
    }

    private void submitInfo() {
        mWebUtils.userInfoUpdate(TAG, mUserBean.getNick(), mUserBean.getAvatarUrl(), mUserBean.getGender(), new JsonCallBack<GingResponse<CreateUserWrapper>>() {
            @Override
            public void onSuccess(GingResponse<CreateUserWrapper> gingResponse, Call call, Response response) {
                super.onSuccess(gingResponse, call, response);
                if (gingResponse.Data != null) {
                    SharedPreferencesHelper.getInstance(AppManager.getAppManager().currentActivity())
                            .putStringValue(PrefConstants.KEY_CURRENT_USER, GsonUtil.jsonToString(gingResponse.Data.getUser()));
                    LocalBroadcastHelper.notifyUserInfoUpdate(LocalBroadcastManager.getInstance(UserInfoActivity.this), gingResponse.Data.getUser());
                    Log.printJSON("user su", gingResponse.toString());
                    AppManager.getAppManager().finishActivity();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PrefConstants.REQUEST_GET_ALBUM) {
            if (resultCode != RESULT_OK)
                return;
            if (data != null) {
                final Uri uri = data.getData();
                isModified = true;
                handleImageUri(uri);
            } else
                ToastUtil.showShort(R.string.no_photo_chosen);
        } else if (requestCode == PrefConstants.REQUEST_TAKE_PICTURE) {
            if (resultCode != RESULT_OK)
                return;
            if (this.avatarBitmapUri == null) {
                ToastUtil.showShort("null error");
                return;
            }
            isModified = true;
            handleImageUri(avatarBitmapUri);
        } else if (requestCode == Crop.REQUEST_CROP) {
            Log.w("crop image received");
            handleCrop(resultCode, data);
        }
    }


    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            Log.w("" + uri);
            mAvatarBitmap = new ImageUtils(this).getBitmapFromUri(uri);
            mUserAvatar.setImageDrawable(null);
            mUserAvatar.setImageURI(uri);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleImageUri(final Uri uri) {
        mProgressDialogHelper.showProgressDialog();
        new AsyncDownTaskNoPop() {
            @Override
            protected String doInBackground(Integer... params) {
                if (uri != null) {
                    try {
                        Bitmap bitmap = new ImageUtils(UserInfoActivity.this).getBitmapFromUri(uri);
                        bitmap = ImageUtils.compressImage(bitmap);
                        String filePath = FileUtils.getImageFilePath();
                        File file = ImageUtils.saveMyBitmap(filePath, bitmap);
                        bitmap.recycle();
                        avatarBitmapUri = Uri.fromFile(file);
//						ImageUtils.doCrop(this,this.avatarBitmapUri);
                    } catch (Exception e) {
                        Log.e("Exception", e.getMessage(), e);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                mProgressDialogHelper.hideProgressDialog();
                isModified = true;
                beginCrop(avatarBitmapUri);
            }
        }.execute(0);
    }

    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(getCacheDir(), "cropped"));
        new Crop(source).output(outputUri).asSquare().start(this);
    }
}
