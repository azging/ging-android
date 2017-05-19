package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;

/**
 * Created by GG on 2017/5/19.
 */

public class GingResponse<T> extends BaseBean {
    public int Status;
    public String Msg;
    public T Data;

    @Override
    public String toString() {
        return "GingResponse{" +
                "status=" + Status +
                ", msg='" + Msg + '\'' +
                ", data=" + Data +
                '}';
    }
}
