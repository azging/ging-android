package com.azging.ging.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.adapter.QuestionAdapter;
import com.azging.ging.base.BaseFragment;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.bean.QuestionWrapperListBean;
import com.azging.ging.customui.DividerDecoration;
import com.azging.ging.net.JsonCallBack;
import com.azging.ging.net.WebUtils;
import com.azging.ging.utils.Log;
import com.azging.ging.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by GG on 2017/5/22.
 */

public class QuestionListFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String KEY_TYPE = "TYPE";

    public static final int TYPE_NEW_QUESTION_LIST = 1;
    public static final int TYPE_HOT_QUESTION_LIST = 2;
    public static final int TYPE_NEARBY_QUESTION_LIST = 3;
    public static final int TYPE_MY_QUESTION_ALL = 4;
    public static final int TYPE_MY_QUESTION_NO_RESULT = 5;
    public static final int TYPE_MY_ANSWER_ALL = 6;
    public static final int TYPE_MY_ANSWER_GET_MONEY = 7;
    @BindView(R.id.empty_text) TextView mEmptyText;
    @BindView(R.id.empty_view) LinearLayout mEmptyView;
    Unbinder unbinder;

    private String TAG = "QuestionListFragment";


    public static QuestionListFragment startFragment(int type) {
        QuestionListFragment fragment = new QuestionListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @BindView(R.id.rv_list) RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout) SwipeRefreshLayout mSwipeRefreshLayout;

    private WebUtils webUtils;
    private QuestionAdapter questionAdapter;
    private List<QuestionWrapper> mData = new ArrayList<>();
    private String orderStr;
    private int mType;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_question_list;
    }

    @Override
    public void initView() {
        mType = getArguments().getInt(KEY_TYPE);

        webUtils = new WebUtils(context);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        initAdapter();
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }


    private void initAdapter() {

        DividerDecoration dividerDecoration = new DividerDecoration(getActivity());
        dividerDecoration.setDividerHeightDip(5);
        dividerDecoration.setDividerColor(ContextCompat.getColor(context, R.color.ging_gray));
        mRecyclerView.addItemDecoration(dividerDecoration);


        questionAdapter = new QuestionAdapter(mData);
        questionAdapter.setOnLoadMoreListener(this, mRecyclerView);
//        questionAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(questionAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                QuestionDetailActivity.startActivity(context, (QuestionWrapper) adapter.getData().get(position));
            }
        });
    }


    @Override
    public void onRefresh() {
        questionAdapter.setEnableLoadMore(false);
        orderStr = "";
        mData.clear();
        getWebResult(new JsonCallBack<GingResponse<QuestionWrapperListBean>>() {
            @Override
            public void onSuccess(GingResponse<QuestionWrapperListBean> gingResponse, Call call, Response response) {
                super.onSuccess(gingResponse, call, response);

//                Log.printJSON("question", GsonUtil.jsonToString(gingResponse.Data));
                if (gingResponse.Data.getQuestionWrapperList() != null) {
                    questionAdapter.setNewData(gingResponse.Data.getQuestionWrapperList());
                    mData.addAll(gingResponse.Data.getQuestionWrapperList());
                }
                orderStr = gingResponse.Data.getOrderStr();
//                mCurrentCounter = PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                questionAdapter.setEnableLoadMore(true);
                if (mData.size() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                }

            }
        });
    }

    private void getWebResult(JsonCallBack<GingResponse<QuestionWrapperListBean>> callBack) {
        switch (mType) {
            case TYPE_NEW_QUESTION_LIST:
                Log.e("new");
                webUtils.newQuestionList(TAG + "NEW", orderStr, callBack);
                break;
            case TYPE_HOT_QUESTION_LIST:
                Log.e("hot");
                webUtils.hotQuestionList(TAG + "HOT", orderStr, callBack);
                break;
            case TYPE_NEARBY_QUESTION_LIST:
                Log.e("nearby");
                webUtils.nearbyQuestionList(TAG + "NEARBY", orderStr, callBack);
                break;
            case TYPE_MY_QUESTION_ALL:
                webUtils.userQuestionList(TAG + "USER_QUESTION_ALL", 0, orderStr, callBack);
                break;
            case TYPE_MY_QUESTION_NO_RESULT:
                webUtils.userQuestionList(TAG + "USER_QUESTION_UN_RESULT", 1, orderStr, callBack);
                break;
            case TYPE_MY_ANSWER_ALL:
                webUtils.userAnswerList(TAG + "USER_ANSWER_ALL", 0, orderStr, callBack);
                break;
            case TYPE_MY_ANSWER_GET_MONEY:
                webUtils.userAnswerList(TAG + "USER_ANSWER_GET_MONEY", 1, orderStr, callBack);
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        getWebResult(new JsonCallBack<GingResponse<QuestionWrapperListBean>>() {
            @Override
            public void onSuccess(GingResponse<QuestionWrapperListBean> gingResponse, Call call, Response response) {
                super.onSuccess(gingResponse, call, response);
                int oldSize = mData.size();
                if (gingResponse.Data.getQuestionWrapperList() != null) {
                    questionAdapter.addData(gingResponse.Data.getQuestionWrapperList());
                    mData.addAll(gingResponse.Data.getQuestionWrapperList());
                }
                questionAdapter.loadMoreComplete();


                if (mData.size() == oldSize || orderStr.equals(gingResponse.Data.getOrderStr())) {
                    ToastUtil.showShort("没有更多了");
                    questionAdapter.loadMoreEnd(true);
                }
                questionAdapter.loadMoreComplete();

                orderStr = gingResponse.Data.getOrderStr();
                mSwipeRefreshLayout.setEnabled(true);

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                questionAdapter.loadMoreFail();
            }
        });
    }
}
