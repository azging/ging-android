package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GG on 2017/5/23.
 */

public class AnswerWrapperListBean extends BaseBean {


    /**
     * AnswerWrapperList : [{"Answer":{"Auid":"39d599ddf2eaf9206711d69af37c8397","Type":0,"Content":"评论评论","Status":0,"PayStatus":0,"CreateTime":"2017-05-22 14:39:32","UpdateTime":"2017-05-22 14:39:32"},"CreateUserWrapper":{"User":{"Uuid":"b8c349303e94c5a7ebeac7a4ecd3c6c9","Cid":"803f47c05d04129c707e7d1cc8d4eb25","LoginType":1,"Telephone":"18519500292","WechatId":"","Nick":"cyy","AvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png","ThumbAvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200","Gender":1,"LiveCityId":110000,"LiveCityName":"北京市","Type":1,"UpdateTime":"2017-05-22 10:18:22"}}},{"Answer":{"Auid":"c493d16c9f3e5c8c3c2db6809f8cbfd1","Type":0,"Content":"评论评论","Status":1,"PayStatus":1,"CreateTime":"2017-05-22 14:39:28","UpdateTime":"2017-05-22 16:43:38"},"CreateUserWrapper":{"User":{"Uuid":"b8c349303e94c5a7ebeac7a4ecd3c6c9","Cid":"803f47c05d04129c707e7d1cc8d4eb25","LoginType":1,"Telephone":"18519500292","WechatId":"","Nick":"cyy","AvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png","ThumbAvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200","Gender":1,"LiveCityId":110000,"LiveCityName":"北京市","Type":1,"UpdateTime":"2017-05-22 10:18:22"}}},{"Answer":{"Auid":"f0ba5f04277a8287919bda704eb78b81","Type":0,"Content":"评论评论","Status":0,"PayStatus":0,"CreateTime":"2017-05-22 14:32:26","UpdateTime":"2017-05-22 14:32:26"},"CreateUserWrapper":{"User":{"Uuid":"b8c349303e94c5a7ebeac7a4ecd3c6c9","Cid":"803f47c05d04129c707e7d1cc8d4eb25","LoginType":1,"Telephone":"18519500292","WechatId":"","Nick":"cyy","AvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png","ThumbAvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200","Gender":1,"LiveCityId":110000,"LiveCityName":"北京市","Type":1,"UpdateTime":"2017-05-22 10:18:22"}}},{"Answer":{"Auid":"fbef84b56e6aae99f29b636b2e5c685b","Type":0,"Content":"评论评论","Status":0,"PayStatus":0,"CreateTime":"2017-05-22 14:32:17","UpdateTime":"2017-05-22 14:32:17"},"CreateUserWrapper":{"User":{"Uuid":"b8c349303e94c5a7ebeac7a4ecd3c6c9","Cid":"803f47c05d04129c707e7d1cc8d4eb25","LoginType":1,"Telephone":"18519500292","WechatId":"","Nick":"cyy","AvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png","ThumbAvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200","Gender":1,"LiveCityId":110000,"LiveCityName":"北京市","Type":1,"UpdateTime":"2017-05-22 10:18:22"}}},{"Answer":{"Auid":"e31446c220fdbbd4f5c7ba79ca865f02","Type":0,"Content":"评论评论","Status":0,"PayStatus":0,"CreateTime":"2017-05-22 14:28:45","UpdateTime":"2017-05-22 14:28:45"},"CreateUserWrapper":{"User":{"Uuid":"b8c349303e94c5a7ebeac7a4ecd3c6c9","Cid":"803f47c05d04129c707e7d1cc8d4eb25","LoginType":1,"Telephone":"18519500292","WechatId":"","Nick":"cyy","AvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png","ThumbAvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200","Gender":1,"LiveCityId":110000,"LiveCityName":"北京市","Type":1,"UpdateTime":"2017-05-22 10:18:22"}}},{"Answer":{"Auid":"25ea1c8b1203af25ba50cbdd843d1c29","Type":0,"Content":"评论评论","Status":0,"PayStatus":0,"CreateTime":"2017-05-22 14:25:46","UpdateTime":"2017-05-22 14:25:46"},"CreateUserWrapper":{"User":{"Uuid":"b8c349303e94c5a7ebeac7a4ecd3c6c9","Cid":"803f47c05d04129c707e7d1cc8d4eb25","LoginType":1,"Telephone":"18519500292","WechatId":"","Nick":"cyy","AvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png","ThumbAvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200","Gender":1,"LiveCityId":110000,"LiveCityName":"北京市","Type":1,"UpdateTime":"2017-05-22 10:18:22"}}},{"Answer":{"Auid":"ac3b63ba3c17304de2f32f4728d0a3cf","Type":0,"Content":"评论评论","Status":0,"PayStatus":0,"CreateTime":"2017-05-22 14:15:36","UpdateTime":"2017-05-22 14:15:36"},"CreateUserWrapper":{"User":{"Uuid":"b8c349303e94c5a7ebeac7a4ecd3c6c9","Cid":"803f47c05d04129c707e7d1cc8d4eb25","LoginType":1,"Telephone":"18519500292","WechatId":"","Nick":"cyy","AvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png","ThumbAvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200","Gender":1,"LiveCityId":110000,"LiveCityName":"北京市","Type":1,"UpdateTime":"2017-05-22 10:18:22"}}}]
     * OrderStr : 2017-05-22 14:15:36
     */

    @SerializedName("OrderStr") private String OrderStr;
    @SerializedName("AnswerWrapperList") private List<AnswerWrapper> AnswerWrapperList;

    public String getOrderStr() {
        return OrderStr;
    }

    public void setOrderStr(String orderStr) {
        OrderStr = orderStr;
    }

    public List<AnswerWrapper> getAnswerWrapperList() {
        return AnswerWrapperList;
    }

    public void setAnswerWrapperList(List<AnswerWrapper> answerWrapperList) {
        AnswerWrapperList = answerWrapperList;
    }
}
