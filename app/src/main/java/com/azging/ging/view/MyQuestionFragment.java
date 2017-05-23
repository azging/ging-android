package com.azging.ging.view;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.azging.ging.R;
import com.azging.ging.adapter.MyFragmentPagerAdapter;
import com.azging.ging.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyQuestionFragment extends BaseFragment {

    private static final String KEY_TYPE = "TYPE";

    public static final int TYPE_QUESTION = 1;
    public static final int TYPE_ANSWER = 2;


    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(R.id.app_bar_layout) AppBarLayout mAppBarLayout;
    @BindView(R.id.viewpager) ViewPager mViewpager;

    private int mType;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    public static MyQuestionFragment startFragment(int type) {
        MyQuestionFragment fragment = new MyQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_question;
    }

    @Override
    public void initView() {
        mType = getArguments().getInt(KEY_TYPE);
        switch (mType) {
            case TYPE_QUESTION:
                mTitleList.add(getString(R.string.all));
                mTitleList.add(getString(R.string.no_result));
                mFragmentList.add(QuestionListFragment.startFragment(QuestionListFragment.TYPE_MY_QUESTION_ALL));
                mFragmentList.add(QuestionListFragment.startFragment(QuestionListFragment.TYPE_MY_QUESTION_NO_RESULT));
                break;
            case TYPE_ANSWER:
                mTitleList.add(getString(R.string.all));
                mTitleList.add(getString(R.string.get_money));
                mFragmentList.add(QuestionListFragment.startFragment(QuestionListFragment.TYPE_MY_ANSWER_ALL));
                mFragmentList.add(QuestionListFragment.startFragment(QuestionListFragment.TYPE_MY_ANSWER_GET_MONEY));
                break;

        }

        MyFragmentPagerAdapter mPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragmentList, mTitleList);
        mViewpager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


}
