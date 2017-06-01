package com.azging.ging.utils.constants;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.azging.ging.bean.UserBean;
import com.azging.ging.utils.GsonUtil;

/**
 * Created by GG on 2017/6/1.
 */

public class LocalBroadcastHelper {

    public static void registerReceiverForActions(LocalBroadcastManager manager, BroadcastReceiver receiver, String[] actions) {
        IntentFilter intentFilter = new IntentFilter();
        for (String action : actions) {
            intentFilter.addAction(action);
        }
        manager.registerReceiver(receiver, intentFilter);
    }


    public static void notifyWXPayResult(LocalBroadcastManager manager, boolean result) {
        Intent intent = new Intent(LocalBroadcastConstants.INTENT_WXPAY_RESULT);
        intent.putExtra(LocalBroadcastConstants.EXTRA_RESULT, result);
        manager.sendBroadcast(intent);
    }

    public static void notifyUserInfoUpdate(LocalBroadcastManager manager, UserBean userBean) {
        Intent intent= new Intent(LocalBroadcastConstants.INTENT_USER_UPDATE);
        intent.putExtra(LocalBroadcastConstants.EXTRA_RESULT, GsonUtil.jsonToString(userBean));
        manager.sendBroadcast(intent);
    }
}
