package com.azging.ging.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.adapter.AnswerAdapter;
import com.azging.ging.base.BaseMainActivity;
import com.azging.ging.base.IActivity;
import com.azging.ging.bean.AnswerWrapper;
import com.azging.ging.bean.AnswerWrapperListBean;
import com.azging.ging.bean.GingResponse;
import com.azging.ging.bean.QuestionWrapper;
import com.azging.ging.controller.QuestionDetailController;
import com.azging.ging.customui.DividerDecoration;
import com.azging.ging.net.JsonCallBack;
import com.azging.ging.net.WebUtils;
import com.azging.ging.utils.AppManager;
import com.azging.ging.utils.DensityUtils;
import com.azging.ging.utils.GsonUtil;
import com.azging.ging.utils.ToastUtil;
import com.azging.ging.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by GG on 2017/5/23.
 */

public class QuestionDetailActivity extends BaseMainActivity implements IActivity, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    public static String TAG = "QuestionDetailActivity";

    private static final String KEY_QUESTION_DETAIL = "QUESTION_DETAIL";
    @BindView(R.id.header_left) RelativeLayout mHeaderLeft;
    @BindView(R.id.header_right) RelativeLayout mHeaderRight;
    @BindView(R.id.header_back) ImageView headerBack;
    @BindView(R.id.header_title) TextView headerTitle;
    @BindView(R.id.header_more) ImageView headerMore;
    @BindView(R.id.header_view) RelativeLayout headerView;
    @BindView(R.id.rv_list) RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.answer_question_btn) TextView answerQuestionBtn;
    @BindView(R.id.plan_free_add_comment_view) View addCommentView;
    @BindView(R.id.add_comment_text) EditText addCommentEdit;
    @BindView(R.id.add_comment_button) View sendCommentImage;


    InputMethodManager imm;

    private WebUtils webUtils;
    private QuestionWrapper questionWrapper;
    private String orderStr;
    private List<AnswerWrapper> mData = new ArrayList<>();
    private AnswerAdapter adapter;
    private QuestionDetailController controller;

    public static void startActivity(Context context, QuestionWrapper questionWrapper) {
        Intent intent = new Intent(context, QuestionDetailActivity.class);
        intent.putExtra(KEY_QUESTION_DETAIL, GsonUtil.jsonToString(questionWrapper));
        context.startActivity(intent);
    }

    @Override
    public int initView() {
        return R.layout.activity_question_detail;
    }

    @Override
    public void initData() {

        webUtils = new WebUtils(this);
        questionWrapper = GsonUtil.jsonToBean(getIntent().getStringExtra(KEY_QUESTION_DETAIL), QuestionWrapper.class);

        headerTitle.setText("求助详情");
        headerMore.setVisibility(View.INVISIBLE);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();

        initHeader();

        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();

        addCommentEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            sendComment();
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });

    }

    private void sendComment() {
        if (!sendCommentImage.isEnabled()) return;
        sendCommentImage.setEnabled(false);
        if (!Utils.isLoggedIn()) {
            LoginActivity.startActivity(this);
            sendCommentImage.setEnabled(true);
            return;
        }
        String comment = addCommentEdit.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            ToastUtil.showShort(this, R.string.content_not_null);
            sendCommentImage.setEnabled(true);
            return;
        }

        webUtils.addAnswer(TAG, questionWrapper.getQuestion().getQuid(), comment, 1, new JsonCallBack<GingResponse<AnswerWrapper>>() {
            @Override
            public void onSuccess(GingResponse<AnswerWrapper> gingResponse, Call call, Response response) {
                super.onSuccess(gingResponse, call, response);
                hideCommentKeyboard();
                ToastUtil.showShort(QuestionDetailActivity.this, "回答成功");
                addCommentEdit.setText("");
                sendCommentImage.setEnabled(true);
                onRefresh();
            }
        });

//        webPlan.addNewPlanComment(inviteIuid, comment, replyCuid, new
//                WebResultCallback() {
//
//                    @Override
//                    public void resultGot(int rescode, JSONObject dataObj, String msg)
//                            throws JSONException {
//                        if (rescode == 0) {
//                            LogHelper.printJSON("creat comment", dataObj);
//                            CommentWrapper CommentWrapper = GsonUtil.jsonToBean(dataObj.optString("InviteCommentWrapper"), CommentWrapper
//                                    .class);
//                            addLocalComment(CommentWrapper);
//                            LocalBroadcastHelper.notifyPlanCommentChanged(localBroadcastManager, String.valueOf(inviteDetail.getInvite()
//                                    .getIuid()), inviteCommentList.size());
//                        }
//                        hideCommentKeyboard();
//                        ComFuncs.myToast(mContext, "评论成功");
//                        addCommentEdit.setText("");
//                        sendCommentImage.setEnabled(true);
//                    }
//                });
    }

    @OnClick({R.id.answer_question_btn, R.id.header_left, R.id.add_comment_button})
    void submit(View view) {
        switch (view.getId()) {
            case R.id.answer_question_btn://发布回答
                MobclickAgent.onEvent(this, "TourPicDetail_Comment");
                answerQuestionBtn.setVisibility(View.GONE);
                addCommentView.setVisibility(View.VISIBLE);
                showCommentKeyBoard();
                break;
            case R.id.header_left://返回
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.add_comment_button:
                sendComment();
                break;
        }
    }


    private void initHeader() {
        View header = LayoutInflater.from(this).inflate(R.layout.item_question_detail, null, false);
        controller = new QuestionDetailController(this, header);
        controller.wrapData(questionWrapper);
        adapter.addHeaderView(header);

        View breakLine = new View(this);
        breakLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(this, 12)));
        breakLine.setBackgroundColor(getResources().getColor(R.color.gray));
        adapter.addHeaderView(breakLine);

        TextView headerText = new TextView(this);
        headerText.setText("回答");
        headerText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(this, 30)));
        headerText.setGravity(Gravity.CENTER_VERTICAL);
        headerText.setPadding(DensityUtils.dp2px(this, 10), 0, 0, 0);
        headerText.setTextSize(13);
        adapter.addHeaderView(headerText);

        webUtils.questionDetail(TAG, questionWrapper.getQuestion().getQuid(), new JsonCallBack<GingResponse<QuestionWrapper>>() {
            @Override
            public void onSuccess(GingResponse<QuestionWrapper> gingResponse, Call call, Response response) {
                super.onSuccess(gingResponse, call, response);
                questionWrapper = gingResponse.Data;
                controller.wrapData(questionWrapper);
            }
        });
    }

    private void initAdapter() {
        DividerDecoration dividerDecoration = new DividerDecoration(this);
        dividerDecoration.setDividerHeightDip(1);
        dividerDecoration.setPaddingLeft(10);
        dividerDecoration.setPaddingRight(10);
        dividerDecoration.setDividerColor(getResources().getColor(R.color.gray));
        mRecyclerView.addItemDecoration(dividerDecoration);


        adapter = new AnswerAdapter(mData);
        adapter.setOnLoadMoreListener(this, mRecyclerView);
//        questionAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
            }
        });
    }

    private void getWebResult(JsonCallBack<GingResponse<AnswerWrapperListBean>> callBack) {
        webUtils.answerList(TAG, questionWrapper.getQuestion().getQuid(), orderStr, callBack);
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        orderStr = "";
        mData.clear();
        getWebResult(new JsonCallBack<GingResponse<AnswerWrapperListBean>>() {
            @Override
            public void onSuccess(GingResponse<AnswerWrapperListBean> gingResponse, Call call, Response response) {
                super.onSuccess(gingResponse, call, response);
//                mCurrentCounter = PAGE_SIZE;
                orderStr = gingResponse.Data.getOrderStr();
                if (gingResponse.Data.getAnswerWrapperList() != null) {
                    adapter.setNewData(gingResponse.Data.getAnswerWrapperList());
                    mData.addAll(gingResponse.Data.getAnswerWrapperList());
                }
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        });

    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);

        getWebResult(new JsonCallBack<GingResponse<AnswerWrapperListBean>>() {
            @Override
            public void onSuccess(GingResponse<AnswerWrapperListBean> gingResponse, Call call, Response response) {
                super.onSuccess(gingResponse, call, response);

                int oldSize = mData.size();
                if (gingResponse.Data.getAnswerWrapperList() != null) {
                    mData.addAll(gingResponse.Data.getAnswerWrapperList());
                    adapter.addData(gingResponse.Data.getAnswerWrapperList());
                }
                adapter.loadMoreComplete();


                if (mData.size() == oldSize || orderStr.equals(gingResponse.Data.getOrderStr())) {
                    ToastUtil.showShort("没有更多了");
                    adapter.loadMoreEnd(true);
                }
                adapter.loadMoreComplete();

                orderStr = gingResponse.Data.getOrderStr();
                mSwipeRefreshLayout.setEnabled(true);

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                adapter.loadMoreFail();
            }
        });
    }

    private void hideCommentKeyboard() {
        addCommentView.setVisibility(View.GONE);
        answerQuestionBtn.setVisibility(View.VISIBLE);
        addCommentEdit.setText("");
        addCommentEdit.setHint(R.string.input_hint);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(addCommentEdit.getWindowToken(), 0);
    }

    private void showCommentKeyBoard() {
        addCommentView.setVisibility(View.VISIBLE);
        answerQuestionBtn.setVisibility(View.GONE);
        addCommentEdit.requestFocus();//输入框获取焦点
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(addCommentEdit, InputMethodManager.SHOW_FORCED);//弹出软键盘时，焦点定位在软键盘中
    }

}
