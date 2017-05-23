package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 2017/5/23.
 */

public class AnswerWrapper extends BaseBean {


    /**
     * Answer : {"Auid":"ac3b63ba3c17304de2f32f4728d0a3cf","Type":0,"Content":"评论评论","Status":0,"PayStatus":0,"CreateTime":"2017-05-22 14:15:36","UpdateTime":"2017-05-22 14:15:36"}
     * CreateUserWrapper : {"User":{"Uuid":"b8c349303e94c5a7ebeac7a4ecd3c6c9","Cid":"803f47c05d04129c707e7d1cc8d4eb25","LoginType":1,"Telephone":"18519500292","WechatId":"","Nick":"cyy","AvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png","ThumbAvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200","Gender":1,"LiveCityId":110000,"LiveCityName":"北京市","Type":1,"UpdateTime":"2017-05-22 10:18:22"}}
     */

    @SerializedName("Answer") private AnswerBean Answer;
    @SerializedName("CreateUserWrapper") private CreateUserWrapper CreateUserWrapper;

    public AnswerBean getAnswer() {
        return Answer;
    }

    public void setAnswer(AnswerBean Answer) {
        this.Answer = Answer;
    }

    public CreateUserWrapper getCreateUserWrapper() {
        return CreateUserWrapper;
    }

    public void setCreateUserWrapper(CreateUserWrapper CreateUserWrapper) {
        this.CreateUserWrapper = CreateUserWrapper;
    }

}
