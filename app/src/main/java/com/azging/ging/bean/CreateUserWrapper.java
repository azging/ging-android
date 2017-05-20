package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 2017/5/20.
 */

public class CreateUserWrapper extends BaseBean {

    /**
     * User : {"Uuid":"b8c349303e94c5a7ebeac7a4ecd3c6c9","Cid":"803f47c05d04129c707e7d1cc8d4eb25","LoginType":1,"Telephone":"18519500292","WechatId":"","Nick":"185****92","AvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png","ThumbAvatarUrl":"http://download.duckr.cn/DuckrDefaultPhoto.png?imageView2/2/w/200","Gender":0,"LiveCityId":110000,"LiveCityName":"北京市","Type":1,"UpdateTime":"2017-05-20 03:28:55"}
     */

    @SerializedName("User") private UserBean User;

    public UserBean getUser() {
        return User;
    }

    public void setUser(UserBean User) {
        this.User = User;
    }

}
