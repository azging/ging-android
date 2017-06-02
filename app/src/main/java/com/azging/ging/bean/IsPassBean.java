package com.azging.ging.bean;

import com.azging.ging.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 2017/6/2.
 */

public class IsPassBean extends BaseBean {

    /**
     * IsPass : true
     */

    @SerializedName("IsPass") private boolean IsPass;

    public boolean isIsPass() {
        return IsPass;
    }

    public void setIsPass(boolean IsPass) {
        this.IsPass = IsPass;
    }
}
