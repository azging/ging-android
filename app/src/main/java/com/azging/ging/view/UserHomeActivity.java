package com.azging.ging.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.UserBean;
import com.azging.ging.net.JsonCallBack;
import com.azging.ging.net.WebUtils;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.ImageLoader;
import com.azging.ging.utils.PrefConstants;
import com.azging.ging.utils.SharedPreferencesHelper;
import com.azging.ging.utils.ToastUtil;
import com.azging.ging.utils.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class UserHomeActivity extends BaseMainActivity implements IActivity {
    private static String TAG = "UserHomeActivity";

    private static final String KEY_USER = "USER";

    @BindView(R.id.user_avatar) ImageView userAvatar;
    @BindView(R.id.user_nick) TextView userNick;
    @BindView(R.id.user_view) LinearLayout userView;
    @BindView(R.id.logoff) TextView logoff;
    @BindView(R.id.my_question) View myQuestion;
    @BindView(R.id.my_answer) View myAnswer;
    @BindView(R.id.my_wallet) View myWallet;
    @BindView(R.id.feedback) View feedback;
    @BindView(R.id.about_us) View aboutUs;


    private UserBean userBean;
    private WebUtils webUtils;


    public static void startActivity(Context context, UserBean userBean) {
        Intent intent = new Intent(context, UserHomeActivity.class);
        intent.putExtra(KEY_USER, userBean);
        context.startActivity(intent);
    }

    @Override
    public int initView() {
        return R.layout.activity_user_home;
    }

    @Override
    public void initData() {
        webUtils = new WebUtils(this);
        userBean = (UserBean) getIntent().getSerializableExtra(KEY_USER);

        wrapData();


        UIHelper.setIconText(myQuestion, getString(R.string.my_question), R.mipmap.ic_launcher_round, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        UIHelper.setIconText(myAnswer, getString(R.string.my_answer), R.mipmap.ic_launcher_round, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        UIHelper.setIconText(myWallet, getString(R.string.my_wallet), R.mipmap.ic_launcher_round, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        UIHelper.setIconText(feedback, getString(R.string.feedback), R.mipmap.ic_launcher_round, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        UIHelper.setIconText(aboutUs, getString(R.string.about_us), R.mipmap.ic_launcher_round, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void wrapData() {
        ImageLoader.getInstance().displayImage(this, userBean.getThumbAvatarUrl(), userAvatar);
        userNick.setText(userBean.getNick());
    }


    @OnClick({R.id.logoff})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.logoff:
                webUtils.logout(TAG, new JsonCallBack() {
                    @Override
                    public void onSuccess(Object o, Call call, Response response) {
                        super.onSuccess(o, call, response);
                        SharedPreferencesHelper.getInstance(AppManager.getAppManager().currentActivity()).putStringValue(PrefConstants.KEY_CURRENT_USER, null);
                        ToastUtil.showLong("退出成功");
                        AppManager.getAppManager().finishActivity();
                    }
                });
                break;
        }
    }
}
