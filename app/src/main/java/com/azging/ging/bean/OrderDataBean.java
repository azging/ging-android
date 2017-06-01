package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GG on 2017/6/1.
 */

public class OrderDataBean extends BaseBean {

    /**
     * Order : {"Ouid":"3931c77539c2b29dda66c92fa0f9c77a","Amount":0.01,"TradeType":1,"PaymentType":0,"CreateTime":"2017-06-01 10:48:56","UpdateTime":"2017-06-01 10:48:56"}
     * WxPrepay : {"appid":"wx18a5f802e5e464e0","mch_id":"1252578101","nonce_str":"hHt6Iqk8rq0akmMp","prepay_id":"wx201706011048577f7c1043030876224635","result_code":"SUCCESS","return_code":"SUCCESS","return_msg":"OK","sign":"A100670BB10E27A78F50FB14CAFF547C","trade_type":"APP"}
     * BalancePrepay : []
     * CreateUser : []
     */

    @SerializedName("Order") private OrderBean Order;
    @SerializedName("WxPrepay") private WxPrepayBean WxPrepay;
    @SerializedName("BalancePrepay") private List<?> BalancePrepay;
    @SerializedName("CreateUser") private List<UserBean> CreateUser;

    public OrderBean getOrder() {
        return Order;
    }

    public void setOrder(OrderBean Order) {
        this.Order = Order;
    }

    public WxPrepayBean getWxPrepay() {
        return WxPrepay;
    }

    public void setWxPrepay(WxPrepayBean WxPrepay) {
        this.WxPrepay = WxPrepay;
    }

    public List<?> getBalancePrepay() {
        return BalancePrepay;
    }

    public void setBalancePrepay(List<?> BalancePrepay) {
        this.BalancePrepay = BalancePrepay;
    }

    public List<UserBean> getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(List<UserBean> CreateUser) {
        this.CreateUser = CreateUser;
    }
}
