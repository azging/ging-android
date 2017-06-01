package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 2017/6/1.
 */

public class OrderBean extends BaseBean {
    /**
     * Ouid : 3931c77539c2b29dda66c92fa0f9c77a
     * Amount : 0.01
     * TradeType : 1
     * PaymentType : 0
     * CreateTime : 2017-06-01 10:48:56
     * UpdateTime : 2017-06-01 10:48:56
     */

    @SerializedName("Ouid") private String Ouid;
    @SerializedName("Amount") private double Amount;
    @SerializedName("TradeType") private int TradeType;
    @SerializedName("PaymentType") private int PaymentType;
    @SerializedName("CreateTime") private String CreateTime;
    @SerializedName("UpdateTime") private String UpdateTime;

    public String getOuid() {
        return Ouid;
    }

    public void setOuid(String Ouid) {
        this.Ouid = Ouid;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public int getTradeType() {
        return TradeType;
    }

    public void setTradeType(int TradeType) {
        this.TradeType = TradeType;
    }

    public int getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(int PaymentType) {
        this.PaymentType = PaymentType;
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
