package com.example.estevao.whoscall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "CustomBroadcastReceiver";
    public Context context;
    public static HeadLayer mHeadLayer;
    private static CustomPhoneStateListener customPhoneListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (customPhoneListener == null)
            customPhoneListener = new CustomPhoneStateListener(context);
        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public static void initHeadLayer(Context context, String tel, String name) {
        if (mHeadLayer == null)
            mHeadLayer = new HeadLayer(context, tel, name);
    }

}