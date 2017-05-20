package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GG on 2017/5/20.
 */

public class QuestionWrapper extends BaseBean {


    /**
     * Question : {"Quid":"085b9df654d9d2e77de290927aa70aef","Title":"怎么进北大","Description":"不知道怎么进","IsAnonymous":1,"Reward":6.66,"PhotoUrls":[],"ThumbPhotoUrls":[],"CityId":0,"Status":0,"PayStatus":0,"ExpireTime":"2017-05-22 03:48:00","CreateTime":"2017-05-20 03:48:00","UpdateTime":"2017-05-20 03:48:00"}
     * CreateUserWrapper : {"User":{"Uuid":"b8c349303e94c5a7ebeac7a4ecd3c6c9","Cid":"803f47c05d04129c707e7d1cc8d4eb25","LoginType":1,"Telephone":"18519500292","WechatId":"","Nick":"185****92","AvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png","ThumbAvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200","Gender":0,"LiveCityId":110000,"LiveCityName":"北京市","Type":1,"UpdateTime":"2017-05-20 03:28:55"}}
     */

    @SerializedName("Question") private QuestionBean Question;
    @SerializedName("CreateUserWrapper") private CreateUserWrapper CreateUserWrapper;

    public QuestionBean getQuestion() {
        return Question;
    }

    public void setQuestion(QuestionBean question) {
        Question = question;
    }

    public com.azging.ging.bean.CreateUserWrapper getCreateUserWrapper() {
        return CreateUserWrapper;
    }

    public void setCreateUserWrapper(com.azging.ging.bean.CreateUserWrapper createUserWrapper) {
        CreateUserWrapper = createUserWrapper;
    }
}

