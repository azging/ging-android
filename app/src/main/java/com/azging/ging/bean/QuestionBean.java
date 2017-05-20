package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GG on 2017/5/20.
 */

public class QuestionBean extends BaseBean {
    /**
     * Quid : 085b9df654d9d2e77de290927aa70aef
     * Title : 怎么进北大
     * Description : 不知道怎么进
     * IsAnonymous : 1
     * Reward : 6.66
     * PhotoUrls : []
     * ThumbPhotoUrls : []
     * CityId : 0
     * Status : 0
     * PayStatus : 0
     * ExpireTime : 2017-05-22 03:48:00
     * CreateTime : 2017-05-20 03:48:00
     * UpdateTime : 2017-05-20 03:48:00
     */

    @SerializedName("Quid") private String Quid;
    @SerializedName("Title") private String Title;
    @SerializedName("Description") private String Description;
    @SerializedName("IsAnonymous") private int IsAnonymous;
    @SerializedName("Reward") private double Reward;
    @SerializedName("CityId") private int CityId;
    @SerializedName("Status") private int Status;
    @SerializedName("PayStatus") private int PayStatus;
    @SerializedName("ExpireTime") private String ExpireTime;
    @SerializedName("CreateTime") private String CreateTime;
    @SerializedName("UpdateTime") private String UpdateTime;
    @SerializedName("PhotoUrls") private java.util.List<String> PhotoUrls;
    @SerializedName("ThumbPhotoUrls") private java.util.List<String> ThumbPhotoUrls;

    public String getQuid() {
        return Quid;
    }

    public void setQuid(String Quid) {
        this.Quid = Quid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getIsAnonymous() {
        return IsAnonymous;
    }

    public void setIsAnonymous(int IsAnonymous) {
        this.IsAnonymous = IsAnonymous;
    }

    public double getReward() {
        return Reward;
    }

    public void setReward(double Reward) {
        this.Reward = Reward;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int CityId) {
        this.CityId = CityId;
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

    public String getExpireTime() {
        return ExpireTime;
    }

    public void setExpireTime(String ExpireTime) {
        this.ExpireTime = ExpireTime;
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

    public List<String> getPhotoUrls() {
        return PhotoUrls;
    }

    public void setPhotoUrls(List<String> PhotoUrls) {
        this.PhotoUrls = PhotoUrls;
    }

    public List<String> getThumbPhotoUrls() {
        return ThumbPhotoUrls;
    }

    public void setThumbPhotoUrls(List<String> ThumbPhotoUrls) {
        this.ThumbPhotoUrls = ThumbPhotoUrls;
    }
}
