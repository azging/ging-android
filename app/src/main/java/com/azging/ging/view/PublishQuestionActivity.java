package com.azging.ging.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.PhotoUri;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.OrderDataBean;
import com.azging.ging.bean.QuestionBean;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.bean.WxPrepayBean;
import com.azging.ging.customui.photoview.TourPicBlock;
import com.azging.ging.net.JsonCallBack;
import com.azging.ging.net.QiniuComplete;
import com.azging.ging.net.WebUtils;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.ImageUtils;
import com.azging.ging.utils.Log;
import com.azging.ging.utils.PrefConstants;
import com.azging.ging.utils.ProgressDialogHelper;
import com.azging.ging.utils.QiniuImageType;
import com.azging.ging.utils.ToastUtil;
import com.azging.ging.utils.UIHelper;
import com.azging.ging.utils.Utils;
import com.azging.ging.utils.constants.LocalBroadcastConstants;
import com.azging.ging.utils.constants.LocalBroadcastHelper;
import com.azging.ging.utils.wxpay.WxPay;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qiniu.android.http.ResponseInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.azging.ging.utils.PrefConstants.GO_TO_PHOTO;

public class PublishQuestionActivity extends BaseMainActivity implements IActivity {

    private static final String TAG = "PublishQuestionActivity";

    public static final int TYPE_SELECT_PIC = 1;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PublishQuestionActivity.class);
        context.startActivity(intent);
    }

    @Nullable @BindView(R.id.header_back) ImageView headerBack;
    @Nullable @BindView(R.id.header_title) TextView headerTitle;
    @Nullable @BindView(R.id.header_more) ImageView headerMore;
    @Nullable @BindView(R.id.header_view) RelativeLayout headerView;
    @Nullable @BindView(R.id.ed_title) AppCompatEditText edTitle;
    @Nullable @BindView(R.id.title_view) LinearLayout titleView;
    @Nullable @BindView(R.id.publish_describe) TextView publishDescribe;
    @Nullable @BindView(R.id.ed_describe) AppCompatEditText edDescribe;
    @Nullable @BindView(R.id.add_img_view) LinearLayout addImgView;
    @Nullable @BindView(R.id.length_limit) TextView lengthLimit;
    @Nullable @BindView(R.id.reward_view) View rewardView;
    @Nullable @BindView(R.id.anonymous_view) View anonymousView;
    @Nullable @BindView(R.id.publish_btn) View publishBtn;

    private WebUtils webUtils;
    private float rewardF;
    private boolean isAnonymous;
    private PhotoUri photoUri;
    private TourPicBlock tourPicBlock;
    public ArrayList<PhotoUri> imagePathList = new ArrayList<>();
    private ArrayList<String> imageUriList = new ArrayList<>();
    private int finishUploadCount;
    public List<Bitmap> forRecycledBitmapList = new ArrayList<>();
    private String[] mPicArr = new String[3];
    private ProgressDialogHelper mProgressDialogHelper;
    private String quid;

    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (LocalBroadcastConstants.INTENT_WXPAY_RESULT.equals(intent.getAction())) {
                final boolean result = intent.getBooleanExtra(LocalBroadcastConstants.EXTRA_RESULT, false);
                if (result) {
                    paySuccess();
                } else {
                    ToastUtil.showShort(PublishQuestionActivity.this, R.string.order_false);
                }
            }
        }
    };

    private void paySuccess() {
        if (quid != null && TextUtils.isEmpty(quid))
            webUtils.questionDetail(TAG, quid, new JsonCallBack<GingResponse<QuestionWrapper>>() {
                @Override
                public void onSuccess(GingResponse<QuestionWrapper> questionWrapperGingResponse, Call call, Response response) {
                    super.onSuccess(questionWrapperGingResponse, call, response);
                    if (questionWrapperGingResponse.Data.getQuestion().getStatus() == 2) {
                        ToastUtil.showShort(PublishQuestionActivity.this, R.string.publish_success);
                        PublishSuccessActivity.startActivity(PublishQuestionActivity.this, questionWrapperGingResponse.Data);
                        AppManager.getAppManager().finishActivity();
                    }
                }
            });
    }

    @Override
    public int initView() {
        return R.layout.activity_publish_question;
    }

    @Override
    public void initData() {
        webUtils = new WebUtils(this);


        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        LocalBroadcastHelper.registerReceiverForActions(localBroadcastManager, broadcastReceiver, new String[]{LocalBroadcastConstants.INTENT_WXPAY_RESULT});


        mProgressDialogHelper = ProgressDialogHelper.getInstance();
        mProgressDialogHelper.initProgressDialog(this);

        headerTitle.setText("提问");
        headerMore.setVisibility(View.INVISIBLE);


        tourPicBlock = new TourPicBlock(this, addImgView, 3, 3);
        tourPicBlock.setEnableTakePhoto(true);
        tourPicBlock.startLoadData();
        if (imagePathList != null) {
            for (PhotoUri photoUri : imagePathList) {
                tourPicBlock.addPic(photoUri);
            }
        }

        edDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lengthLimit.setText(s.length() + "/280");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        TextView rewardText = (TextView) rewardView.findViewById(R.id.text);
        rewardText.setTextColor(ContextCompat.getColor(this, R.color.ging_orange));
        final ImageView rewardImg = (ImageView) rewardView.findViewById(R.id.icon_right);
        EditText rewardEdit = (EditText) rewardView.findViewById(R.id.input);
        rewardEdit.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        UIHelper.setEditTextRowData(rewardView, getResources().getString(R.string.wallet), "输入悬赏的红包", new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (s.length() != 0) {
                        rewardF = Float.parseFloat(s.toString());
                        rewardImg.setImageResource(Utils.getCostType(rewardF));
                    }
                } catch (Exception e) {
                    ToastUtil.showLong("请输入正确的钱数");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        TextView anoymousText = (TextView) anonymousView.findViewById(R.id.text);
        anoymousText.setTextColor(ContextCompat.getColor(this, R.color.ging_orange));
        UIHelper.setSwitchRowData(anonymousView, getResources().getString(R.string.anonymity_question), isAnonymous, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnonymous = !isAnonymous;
                UIHelper.setSwitchRowData(anonymousView, getResources().getString(R.string.anonymity_question), isAnonymous, this);
            }
        });
    }


    @OnClick({R.id.header_back, R.id.publish_btn})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.publish_btn:
                uploadPics();
                break;
        }
    }

    private void goToPhotoWallActivity() {
        Intent intent = new Intent(this, PhotoWallActivity.class);
        intent.putExtra(PhotoWallActivity.KEY_AMOUNT_LIMIT, 9);
        intent.putExtra(PhotoWallActivity.KEY_ENABLE_TAKE_PHOTO, true);
        startActivityForResult(intent, GO_TO_PHOTO);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PrefConstants.REQUEST_GET_ALBUM) {
            if (resultCode != RESULT_OK)
                return;
            if (data == null) {
                ToastUtil.showShort(this, R.string.no_photo_chosen);
            }
        } else if (requestCode == PrefConstants.REQUEST_TAKE_PICTURE) { //并不会执行
            String tmpUriStr = this.getIntent().getStringExtra("URI");
            updateGridView(tmpUriStr);
        } else if (requestCode == PrefConstants.REQUEST_VIEW_TOUR_PIC) {
            if (resultCode == RESULT_OK) {
                String path = data.getStringExtra(ViewTourPicActivity.KEY_PATH);
                if (path != null) {
                    PhotoUri photoUri = null;
                    for (int i = 0; i < imagePathList.size(); i++) {
                        if (path.equals(imagePathList.get(i).getUriStr())) {
                            photoUri = imagePathList.get(i);
                            break;
                        }
                    }
                    if (photoUri != null) {
                        imagePathList.remove(photoUri);
                        tourPicBlock.removePic(photoUri);
                        tourPicBlock.refresh();
                    }
                }
            }
        } else if (requestCode == GO_TO_PHOTO) {
            if (resultCode != RESULT_OK)
                return;
            String[] selectedList = data.getStringArrayExtra(PhotoWallActivity.KEY_SELECTED_LIST);
            if (selectedList == null) {
                return;
            }
            imageUriList.clear();
            imagePathList.clear();
            for (String path : selectedList) {
                //最多MAX_PHOTO_NUM张
                if (imagePathList.size() == PrefConstants.MAX_SELECT_PHOTO_NUM) {
                    ToastUtil.showShort(this, "最多可添加" + PrefConstants.MAX_SELECT_PHOTO_NUM + "张图片。");
                    break;
                }
                try {
                    if (!path.startsWith("file://")) {
                        path = Uri.fromFile(new File(path)).toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imageUriList.add(path);
                imagePathList.add(new PhotoUri(TYPE_SELECT_PIC, path));
            }
            updateGridView();
        }
    }

    private void updateGridView(String uri) {
        PhotoUri photoUri = new PhotoUri(2, uri);
        //imagePathList.removeAll(imagePathList);
        if (imagePathList.size() < 3)
            imagePathList.add(photoUri);
        updateGridView();
    }

    private void updateGridView() {
        tourPicBlock.clearPicList();
        if (imagePathList != null) {
            for (PhotoUri photoUri : imagePathList) {
                tourPicBlock.addPic(photoUri);
            }
        }
        tourPicBlock.refresh();
        uploadPics();
    }

    private void uploadPics() {

        finishUploadCount = 0;

        if (imagePathList.size() > 0) {
            for (int i = 0; i < imagePathList.size() && i < 9; i++) {
                if (imagePathList.get(i).getType() == 1 || imagePathList.get(i).getType() == 2) {
                    mProgressDialogHelper.showProgressDialog();
                    final int finalI = i;
                    Glide.with(this).load(imagePathList.get(i).getUriStr()).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Bitmap photo = resource;
                            if (photo == null) {
                                ToastUtil.showShort(PublishQuestionActivity.this, R.string.chosen_photo_invalid);
                                mProgressDialogHelper.hideProgressDialog();
                                return;
                            }
                            if (photo != null && !photo.isRecycled()) {
                                final Bitmap mPhoto = ImageUtils.compressImage(photo);
                                photo.recycle();
                                if (mPhoto != null && !mPhoto.isRecycled()) {
                                    forRecycledBitmapList.add(mPhoto);
                                    webUtils.qiniuUploadPic(mPhoto, String.valueOf(finalI), QiniuImageType.PHOTO,
                                            new QiniuComplete() {

                                                @Override
                                                public void Compplete(String key,
                                                                      ResponseInfo info,
                                                                      JSONObject response, String tag) {
                                                    if (key == null) {
                                                        mProgressDialogHelper.sayNetworkError();
                                                        return;
                                                    }

                                                    finishUploadCount++;
                                                    String uploadedUrl = PrefConstants.QINIU_PIC_PREFIX + key;
                                                    int tagIndex = Integer.valueOf(tag);
                                                    mPicArr[tagIndex] = uploadedUrl;
                                                    imagePathList.get(tagIndex).setType(2);
                                                    imagePathList.get(tagIndex).setUriStr(uploadedUrl);
                                                    if (finishUploadCount == imagePathList.size()) {
                                                        mProgressDialogHelper.hideProgressDialog();
                                                        submitTourPic();
                                                    }

                                                }
                                            });
                                }
                            }
                        }
                    });
                } else {
                    finishUploadCount++;
                    if (finishUploadCount == imagePathList.size()) {
                        mProgressDialogHelper.hideProgressDialog();
                        submitTourPic();
                    }
                }
            }
        }
    }


    private void submitTourPic() {
        mProgressDialogHelper.showSubmitProgressDialog();
        List<String> picList = new ArrayList<>();
        for (int i = 0; i < mPicArr.length; i++) {
            if (mPicArr[i] != null && !mPicArr[i].isEmpty()) {
                picList.add(mPicArr[i]);
            }
        }
        Log.printList(picList);
        JSONArray array = new JSONArray();
        for (String str : picList) {
            array.put(str);
        }
        webUtils.publishQuestion("PublishQuestion", edTitle.getText().toString(), edDescribe.getText().toString(), array.toString(), rewardF, isAnonymous ? 1 : 0, new JsonCallBack<GingResponse<QuestionWrapper>>() {
            @Override
            public void onSuccess(GingResponse<QuestionWrapper> gingResponse, Call call, Response response) {
                super.onSuccess(gingResponse, call, response);
                mProgressDialogHelper.hideProgressDialog();
                QuestionBean questionBean = gingResponse.Data.getQuestion();
                quid = questionBean.getQuid();
                webUtils.addOrder(questionBean.getQuid(), "", questionBean.getReward(), 2, 1, new JsonCallBack<GingResponse<OrderDataBean>>() {
                    @Override
                    public void onSuccess(GingResponse<OrderDataBean> gingResponse, Call call, Response response) {
                        super.onSuccess(gingResponse, call, response);
                        WxPrepayBean wxPrepayBean = gingResponse.Data.getWxPrepay();
                        if (wxPrepayBean.getResultCode().equals("SUCCESS")) {
                            WxPay wxPay = new WxPay(PublishQuestionActivity.this, wxPrepayBean.getAppid(), wxPrepayBean.getMchId(), wxPrepayBean.getPrepayId());
                            wxPay.start();
                        }
                    }
                });

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtil.showShort(PublishQuestionActivity.this, "发布失败");
            }
        });
    }

}
