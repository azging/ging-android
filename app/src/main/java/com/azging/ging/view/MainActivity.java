package com.azging.ging.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.adapter.NewQuestionAdapter;
import com.azging.ging.base.BaseApp;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.bean.QuestionWrapperListBean;
import com.azging.ging.net.JsonCallBack;
import com.azging.ging.net.WebUtils;
import com.azging.ging.utils.PermissionsChecker;
import com.azging.ging.utils.PhoneUtil;
import com.azging.ging.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseMainActivity implements IActivity, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    private String TAG = "Main";

    private static final int REQUEST_CODE = 0; // 请求码
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    protected static final int RC_PERM = 123;


    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @BindView(R.id.header_back) ImageView headerBack;
    @BindView(R.id.header_title) TextView headerTitle;
    @BindView(R.id.header_more) ImageView headerMore;
    @BindView(R.id.header_view) RelativeLayout headerView;
    @BindView(R.id.rv_list) RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    private WebUtils webUtils;
    private NewQuestionAdapter newQuestionAdapter;
    private List<QuestionWrapper> mData = new ArrayList<>();
    private String orderStr;


    @Override
    public int initView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mPermissionsChecker = new PermissionsChecker(this);
//
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }


        webUtils = new WebUtils(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();

        onRefresh();


    }

    private void initCookie() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Cookie", getCookie());
        OkGo.getInstance().addCommonHeaders(headers);
    }

    private void initAdapter() {
        newQuestionAdapter = new NewQuestionAdapter(mData);
        newQuestionAdapter.setOnLoadMoreListener(this, mRecyclerView);
        newQuestionAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(newQuestionAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                ToastUtil.showShort(String.valueOf(position));
            }
        });
    }

    @OnClick({})
    void submit(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void onRefresh() {
        newQuestionAdapter.setEnableLoadMore(false);
        orderStr = "";
        mData.clear();
        getWebResult(new JsonCallBack<GingResponse<QuestionWrapperListBean>>() {
            @Override
            public void onSuccess(GingResponse<QuestionWrapperListBean> gingResponse, Call call, Response response) {
                super.onSuccess(gingResponse, call, response);
                newQuestionAdapter.setNewData(gingResponse.Data.getQuestionWrapperList());
//                mCurrentCounter = PAGE_SIZE;
                mData.addAll(gingResponse.Data.getQuestionWrapperList());
                mSwipeRefreshLayout.setRefreshing(false);
                newQuestionAdapter.setEnableLoadMore(true);

            }
        });
    }

    private void getWebResult(JsonCallBack<GingResponse<QuestionWrapperListBean>> callBack) {
        webUtils.newQuestionList(TAG, orderStr, callBack);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        getWebResult(new JsonCallBack<GingResponse<QuestionWrapperListBean>>() {
            @Override
            public void onSuccess(GingResponse<QuestionWrapperListBean> gingResponse, Call call, Response response) {
                super.onSuccess(gingResponse, call, response);

                newQuestionAdapter.addData(gingResponse.Data.getQuestionWrapperList());
                newQuestionAdapter.loadMoreComplete();

                int oldSize = mData.size();
                mData.addAll(gingResponse.Data.getQuestionWrapperList());

                if (mData.size() == oldSize || orderStr.equals(gingResponse.Data.getOrderStr())) {
                    ToastUtil.showShort("没有更多了");
                    newQuestionAdapter.loadMoreEnd(true);
                }
                newQuestionAdapter.loadMoreComplete();

                orderStr = gingResponse.Data.getOrderStr();
                mSwipeRefreshLayout.setEnabled(true);

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                newQuestionAdapter.loadMoreFail();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    private String getCookie() {
        StringBuilder cookie = new StringBuilder();
        cookie.append("DeviceUUID=").append(PhoneUtil.getDeviceUUID(this)).append("; ").append("DeviceType=2; ");
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            cookie.append("AppVer=").append(pi.versionName).append("; ");
//            appName = pi.applicationInfo.loadLabel(getPackageManager()).toString();
//            appChannel = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA)
//                    .metaData.get("UMENG_CHANNEL").toString();
//            userAgent = "Android" + ";" + appChannel + ";" + android.os.Build.MODEL + ";" +
//                    android.os.Build.VERSION.SDK_INT
//                    + ";" + android.os.Build.VERSION.RELEASE + ";" + appVer + ";" + deviceUUID;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        cookie = cookie + "CityId=";
//        if (DuckrApp.getInstance().getCurrentCity() != null) {
//            cookie = cookie + DuckrApp.getInstance().getCurrentCity().getCityId();
//        } else {
//            cookie = cookie + "110000";
//        }

        if (BaseApp.currentLocation != null) {
            cookie.append("LocLng=").append(BaseApp.currentLocation.getLongitude()).append("; ").append("LocLat=").append(BaseApp.currentLocation.getLatitude()).append(";");
        }


        return cookie.toString();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE) {
            if (resultCode == PermissionsActivity.PERMISSIONS_DENIED)
                finish();
            else
                initCookie();
        }
    }


}
