package com.azging.ging.bean;

import com.azging.ging.R;
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


    private int QUESTION_STATUS_DEFAULT = 0;//不用管
    private int QUESTION_STATUS_UNPAID = 1;//未支付
    private int QUESTION_STATUS_ANSWERING = 2;//可以回答了
    private int QUESTION_STATUS_EXPIRED_NO_ANSWER = 3;//可以回答但是没有人回答的状态
    private int QUESTION_STATUS_EXPIRED_NO_ANSWER_REFUNDED = 4;//可以回答但是没有人回答 所以在退款中的状态
    private int QUESTION_STATUS_EXPIRED_UNADOPTED = 5;//没有选择 最佳答案的状态
    private int QUESTION_STATUS_EXPIRED_UNADOPTED_PAID_FIRST = 6;//没有选择最佳答案  最后到期了 把钱转给第一个人的状态
    private int QUESTION_STATUS_ADOPTED = 7;//用户正在选择的状态   这个状态 处于  存在与不存在之间
    private int QUESTION_STATUS_ADOPTED_PAID_BEST = 8;//用户选好了 最佳答案的状态

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
    @SerializedName("PhotoUrls") private List<String> PhotoUrls;
    @SerializedName("ThumbPhotoUrls") private List<String> ThumbPhotoUrls;
    @SerializedName("AnswerNum") private int AnswerNum;
    @SerializedName("ExpireTimeStr") private String ExpireTimeStr;
    @SerializedName("ShareUrl")private String ShareUrl;

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

    public String getShareUrl() {
        return ShareUrl;
    }

    public void setShareUrl(String shareUrl) {
        ShareUrl = shareUrl;
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

    public int getAnswerNum() {
        return AnswerNum;
    }

    public void setAnswerNum(int answerNum) {
        AnswerNum = answerNum;
    }

    public String getExpireTimeStr() {
        return ExpireTimeStr;
    }

    public void setExpireTimeStr(String expireTimeStr) {
        ExpireTimeStr = expireTimeStr;
    }

    public boolean isComplete() {
        if (Status == 2 || Status == 4 || Status == 6)
            return true;
        return false;
    }

    public int getCostType() {
        if (Reward <= 10)
            return R.drawable.icon_one_star;
        if (Reward <= 20)
            return R.drawable.icon_two_star;
        if (Reward <= 30)
            return R.drawable.icon_three_star;
        if (Reward <= 40)
            return R.drawable.icon_four_star;
        if (Reward <= 50)
            return R.drawable.icon_five_star;
        if (Reward <= 100)
            return R.drawable.icon_big_star;
        return R.drawable.icon_big_star;
    }

    public String getCostTypeText() {
        if (Reward <= 10)
            return "一星红包";
        if (Reward <= 20)
            return "二星红包";
        if (Reward <= 30)
            return "三星红包";
        if (Reward <= 40)
            return "四星红包";
        if (Reward <= 50)
            return "五行红包";
        return "大红包";
    }

}
