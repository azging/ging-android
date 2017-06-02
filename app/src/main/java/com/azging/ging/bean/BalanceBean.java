package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 2017/6/2.
 */

public class BalanceBean extends BaseBean {

    /**
     * Balance : 0
     */

    @SerializedName("Balance") private double Balance;

    public double getBalance() {
        return Balance;
    }

    public void setBalance(int Balance) {
        this.Balance = Balance;
    }
}
