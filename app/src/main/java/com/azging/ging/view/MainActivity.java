package com.azging.ging.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseApp;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.PermissionsChecker;
import com.azging.ging.utils.PhoneUtil;
import com.azging.ging.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMainActivity implements IActivity {
    private static final int REQUEST_CODE = 0; // 请求码

    private PermissionsChecker mPermissionsChecker; // 权限检测器
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
    @BindView(R.id.main_tab) TabLayout mainTab;
    @BindView(R.id.main_viewpager) ViewPager mainViewpager;

    private String[] titles;
    private FragmentManager manager;
    private MainViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();

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
        } else {
            initCookie();
        }

        headerTitle.setText(R.string.app_name);

        fragments.add(QuestionListFragment.startFragment(QuestionListFragment.TYPE_NEW_QUESTION_LIST));
        fragments.add(QuestionListFragment.startFragment(QuestionListFragment.TYPE_HOT_QUESTION_LIST));
        fragments.add(QuestionListFragment.startFragment(QuestionListFragment.TYPE_NEARBY_QUESTION_LIST));


        titles = getResources().getStringArray(R.array.main_tilte);
        manager = getSupportFragmentManager();
        viewPagerAdapter = new MainViewPagerAdapter(manager, fragments, titles);
        mainViewpager.setOffscreenPageLimit(3);
        mainViewpager.setAdapter(viewPagerAdapter);
        mainTab.setupWithViewPager(mainViewpager);

    }

    private void initCookie() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Cookie", getCookie());
        OkGo.getInstance().addCommonHeaders(headers);
    }


    @OnClick({R.id.header_more, R.id.publish_question})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.header_more:
                if (Utils.isLoggedIn())
                    UserHomeActivity.startActivity(this, BaseApp.app.getCurrentUser());
                else
                    LoginActivity.startActivity(this);
                break;
            case R.id.publish_question:
                PublishQuestionActivity.startActivity(this);
                break;
        }
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
                AppManager.getAppManager().finishAllActivity();
            else
                initCookie();
        }
    }


    public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;
        private String[] titles;

        public MainViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = titles[position];
            return title;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
