package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 2017/5/23.
 */

public class AnswerBean extends BaseBean {
    /**
     * Auid : ac3b63ba3c17304de2f32f4728d0a3cf
     * Type : 0
     * Content : 评论评论
     * Status : 0
     * PayStatus : 0
     * CreateTime : 2017-05-22 14:15:36
     * UpdateTime : 2017-05-22 14:15:36
     */

    @SerializedName("Auid") private String Auid;
    @SerializedName("Type") private int Type;
    @SerializedName("Content") private String Content;
    @SerializedName("Status") private int Status;   //0是普通回答   1 是 被采纳  2是 被采纳 也被发红包了的
    @SerializedName("PayStatus") private int PayStatus;
    @SerializedName("CreateTime") private String CreateTime;
    @SerializedName("UpdateTime") private String UpdateTime;

    public String getAuid() {
        return Auid;
    }

    public void setAuid(String Auid) {
        this.Auid = Auid;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getPayStatus() {
        return PayStatus;
    }

    public void setPayStatus(int PayStatus) {
        this.PayStatus = PayStatus;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }
}
