package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 2017/6/1.
 */

public class WxPrepayBean extends BaseBean {
    /**
     * appid : wx18a5f802e5e464e0
     * mch_id : 1252578101
     * nonce_str : hHt6Iqk8rq0akmMp
     * prepay_id : wx201706011048577f7c1043030876224635
     * result_code : SUCCESS
     * return_code : SUCCESS
     * return_msg : OK
     * sign : A100670BB10E27A78F50FB14CAFF547C
     * trade_type : APP
     */

    @SerializedName("appid") private String appid;
    @SerializedName("mch_id") private String mchId;
    @SerializedName("nonce_str") private String nonceStr;
    @SerializedName("prepay_id") private String prepayId;
    @SerializedName("result_code") private String resultCode;
    @SerializedName("return_code") private String returnCode;
    @SerializedName("return_msg") private String returnMsg;
    @SerializedName("sign") private String sign;
    @SerializedName("trade_type") private String tradeType;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
