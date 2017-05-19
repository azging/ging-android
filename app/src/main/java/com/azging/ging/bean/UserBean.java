package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 2017/5/19.
 */

public class UserBean extends BaseBean {


    /**
     * Uuid : 306d6b6bc2cc6102e7df0c01997ac0a6
     * Cid : 56a744eb903b0ee2fdb1a2bd54142149
     * LoginType : 1
     * Telephone : 17600209337
     * WechatId :
     * Nick : 176****37
     * AvatarUrl : http://download.duckr.cn/DuckrDefaultPhoto.png
     * ThumbAvatarUrl : http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200
     * Gender : 0
     * LiveCityId : 110000
     * LiveCityName : 北京市
     * Type : 1
     * UpdateTime : 2017-05-19 08:05:57
     */

    @SerializedName("Uuid") private String Uuid;
    @SerializedName("Cid") private String Cid;
    @SerializedName("LoginType") private int LoginType;
    @SerializedName("Telephone") private String Telephone;
    @SerializedName("WechatId") private String WechatId;
    @SerializedName("Nick") private String Nick;
    @SerializedName("AvatarUrl") private String AvatarUrl;
    @SerializedName("ThumbAvatarUrl") private String ThumbAvatarUrl;
    @SerializedName("Gender") private int Gender;
    @SerializedName("LiveCityId") private int LiveCityId;
    @SerializedName("LiveCityName") private String LiveCityName;
    @SerializedName("Type") private int Type;
    @SerializedName("UpdateTime") private String UpdateTime;

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String Uuid) {
        this.Uuid = Uuid;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String Cid) {
        this.Cid = Cid;
    }

    public int getLoginType() {
        return LoginType;
    }

    public void setLoginType(int LoginType) {
        this.LoginType = LoginType;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getWechatId() {
        return WechatId;
    }

    public void setWechatId(String WechatId) {
        this.WechatId = WechatId;
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String Nick) {
        this.Nick = Nick;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String AvatarUrl) {
        this.AvatarUrl = AvatarUrl;
    }

    public String getThumbAvatarUrl() {
        return ThumbAvatarUrl;
    }

    public void setThumbAvatarUrl(String ThumbAvatarUrl) {
        this.ThumbAvatarUrl = ThumbAvatarUrl;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int Gender) {
        this.Gender = Gender;
    }

    public int getLiveCityId() {
        return LiveCityId;
    }

    public void setLiveCityId(int LiveCityId) {
        this.LiveCityId = LiveCityId;
    }

    public String getLiveCityName() {
        return LiveCityName;
    }

    public void setLiveCityName(String LiveCityName) {
        this.LiveCityName = LiveCityName;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "Uuid='" + Uuid + '\'' +
                ", Cid='" + Cid + '\'' +
                ", LoginType=" + LoginType +
                ", Telephone='" + Telephone + '\'' +
                ", WechatId='" + WechatId + '\'' +
                ", Nick='" + Nick + '\'' +
                ", AvatarUrl='" + AvatarUrl + '\'' +
                ", ThumbAvatarUrl='" + ThumbAvatarUrl + '\'' +
                ", Gender=" + Gender +
                ", LiveCityId=" + LiveCityId +
                ", LiveCityName='" + LiveCityName + '\'' +
                ", Type=" + Type +
                ", UpdateTime='" + UpdateTime + '\'' +
                '}';
    }
}
