package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 2017/5/19.
 */

public class AuthCodeBean extends BaseBean {

    /**
     * AuthCode : xxxxxx
     * ExpireTime : 300
     * SMSResult : 0
     */

    @SerializedName("AuthCode") private String AuthCode;
    @SerializedName("ExpireTime") private String ExpireTime;
    @SerializedName("SMSResult") private String SMSResult;

    public String getAuthCode() {
        return AuthCode;
    }

    public void setAuthCode(String AuthCode) {
        this.AuthCode = AuthCode;
    }

    public String getExpireTime() {
        return ExpireTime;
    }

    public void setExpireTime(String ExpireTime) {
        this.ExpireTime = ExpireTime;
    }

    public String getSMSResult() {
        return SMSResult;
    }

    public void setSMSResult(String SMSResult) {
        this.SMSResult = SMSResult;
    }

    @Override
    public String toString() {
        return "AuthCodeBean{" +
                "AuthCode='" + AuthCode + '\'' +
                ", ExpireTime='" + ExpireTime + '\'' +
                ", SMSResult='" + SMSResult + '\'' +
                '}';
    }
}
