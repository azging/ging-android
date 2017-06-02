package com.azging.ging.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseFragment;
import com.azging.ging.bean.BalanceBean;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.UserBean;
import com.azging.ging.net.JsonCallBack;
import com.azging.ging.net.WebUtils;
import com.azging.ging.utils.ImageLoader;
import com.azging.ging.utils.PrefConstants;
import com.azging.ging.utils.SharedPreferencesHelper;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by GG on 2017/6/2.
 */

public class WalletFragment extends BaseFragment {

    private static final String KEY_USER = "USER";

    @BindView(R.id.user_avatar) ImageView mUserAvatar;
    @BindView(R.id.user_nick) TextView mUserNick;
    @BindView(R.id.user_coins_number) TextView mUserCoinsNumber;
    @BindView(R.id.withdraws_cash) TextView mWithdrawsCash;

    private UserBean mUserBean;
    private WebUtils mWebUtils;


    public static WalletFragment startFragment(UserBean userBean) {
        WalletFragment fragment = new WalletFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_USER, userBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    public void initView() {
        mWebUtils = new WebUtils(context);
        mUserBean = (UserBean) getArguments().getSerializable(KEY_USER);

        ImageLoader.getInstance().displayImage(context, mUserBean.getThumbAvatarUrl(), mUserAvatar);
        mUserNick.setText(mUserBean.getNick());

        if (SharedPreferencesHelper.getInstance(context).getStringValue(PrefConstants.KEY_BALANCE) != null)
            mUserCoinsNumber.setText(SharedPreferencesHelper.getInstance(context).getStringValue(PrefConstants.KEY_BALANCE));

        mWebUtils.balance(new JsonCallBack<GingResponse<BalanceBean>>() {
            @Override
            public void onSuccess(GingResponse<BalanceBean> balanceBeanGingResponse, Call call, Response response) {
                super.onSuccess(balanceBeanGingResponse, call, response);
                mUserCoinsNumber.setText(String.valueOf(balanceBeanGingResponse.Data.getBalance()));
                SharedPreferencesHelper.getInstance(context).putStringValue(PrefConstants.KEY_BALANCE, String.valueOf(balanceBeanGingResponse.Data.getBalance()));
            }
        });

    }

}
