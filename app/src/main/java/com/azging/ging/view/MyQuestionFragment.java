package com.azging.ging.view;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.azging.ging.R;
import com.azging.ging.base.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;

public class MyQuestionFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.rv_list) RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_question;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
