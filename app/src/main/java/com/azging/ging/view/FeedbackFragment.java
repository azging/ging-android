package com.azging.ging.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.azging.ging.R;
import com.azging.ging.base.BaseFragment;
import com.azging.ging.net.WebUtils;
import com.azging.ging.utils.ToastUtil;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by GG on 2017/5/23.
 */

public class FeedbackFragment extends BaseFragment {

    @BindView(R.id.ed_feedback) EditText mEdFeedback;
    @BindView(R.id.length_limit) TextView mLengthLimit;
    private String feedbackText;
    private WebUtils mWebUtils;

    public static FeedbackFragment startFragment() {
        FeedbackFragment feedbackFragment = new FeedbackFragment();
        return feedbackFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_feedback;
    }

    @Override
    public void initView() {
        mWebUtils = new WebUtils(context);

        mEdFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                feedbackText = s.toString();
                mLengthLimit.setText(s.length() + "/500");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void submit() {
        mWebUtils.addFeedback(feedbackText, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optBoolean("IsPass"))
                        ToastUtil.showShort("提交成功");
                    else
                        ToastUtil.showLong("提交失败");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
