package com.azging.ging.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.UserBean;
import com.azging.ging.net.WebUtils;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.GsonUtil;
import com.azging.ging.utils.ImageLoader;
import com.azging.ging.utils.Log;
import com.azging.ging.utils.PrefConstants;
import com.azging.ging.utils.SharedPreferencesHelper;
import com.azging.ging.utils.ToastUtil;
import com.azging.ging.utils.UIHelper;
import com.azging.ging.utils.constants.LocalBroadcastConstants;
import com.azging.ging.utils.constants.LocalBroadcastHelper;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class UserHomeActivity extends BaseMainActivity implements IActivity {
    private static String TAG = "UserHomeActivity";

    private static final String KEY_USER = "USER";

    @BindView(R.id.user_avatar) ImageView userAvatar;
    @BindView(R.id.user_nick) TextView userNick;
    @BindView(R.id.user_gender) ImageView userGender;
    @BindView(R.id.user_view) RelativeLayout userView;
    @BindView(R.id.logoff) TextView logoff;
    @BindView(R.id.my_question) View myQuestion;
    @BindView(R.id.my_answer) View myAnswer;
    @BindView(R.id.my_wallet) View myWallet;
    @BindView(R.id.feedback) View feedback;
    @BindView(R.id.about_us) View aboutUs;


    private UserBean userBean;
    private WebUtils webUtils;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver;

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

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (LocalBroadcastConstants.INTENT_USER_UPDATE.equals(action)) {
                    userBean = GsonUtil.jsonToBean(intent.getStringExtra(LocalBroadcastConstants.EXTRA_RESULT), UserBean.class);
                    wrapData();
                }
            }
        };
        LocalBroadcastHelper.registerReceiverForActions(localBroadcastManager, broadcastReceiver,
                new String[]{LocalBroadcastConstants.INTENT_USER_UPDATE});

        Log.printJSON(GsonUtil.jsonToString(userBean));

        wrapData();


        UIHelper.setIconText(myQuestion, getString(R.string.my_question), R.drawable.icon_my_question, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeListActivity.startActivity(UserHomeActivity.this, HomeListActivity.TYPE_QUESTION);
            }
        });
        UIHelper.setIconText(myAnswer, getString(R.string.my_answer), R.drawable.icon_my_answer, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeListActivity.startActivity(UserHomeActivity.this, HomeListActivity.TYPE_ANSWER);
            }
        });
        UIHelper.setIconText(myWallet, getString(R.string.my_wallet), R.drawable.icon_my_wallet, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeListActivity.startActivity(UserHomeActivity.this, HomeListActivity.TYPE_WALLET);
            }
        });
        UIHelper.setIconText(feedback, getString(R.string.feedback), R.drawable.icon_feedback, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeListActivity.startActivity(UserHomeActivity.this, HomeListActivity.TYPE_FEEDBACK);
            }
        });
        UIHelper.setIconText(aboutUs, getString(R.string.about_us), R.drawable.icon_about_us, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeListActivity.startActivity(UserHomeActivity.this, HomeListActivity.TYPE_ABOUT_US);
            }
        });
    }

    private void wrapData() {
        ImageLoader.getInstance().displayImage(getApplicationContext(), userBean.getThumbAvatarUrl(), userAvatar);
        userNick.setText(userBean.getNick());
        userGender.setImageResource(userBean.getGenderIcon());
    }


    @OnClick({R.id.logoff, R.id.user_view})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.logoff:
                webUtils.logout(TAG, new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        SharedPreferencesHelper.getInstance(UserHomeActivity.this).remove(PrefConstants.KEY_CURRENT_USER);
                        SharedPreferencesHelper.getInstance(UserHomeActivity.this).clear();
                        ToastUtil.showLong("退出成功");
                        AppManager.getAppManager().finishActivity();
                    }
                });
                break;
            case R.id.user_view:
                UserInfoActivity.startActivity(this, userBean);
                break;
        }
    }
}
