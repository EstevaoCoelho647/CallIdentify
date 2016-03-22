package com.example.estevao.whoscall;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by estevao on 22/03/16.
 */
public class CustomPhoneStateListener extends PhoneStateListener {

    private static final String LOG_TAG = "CUSTOMPHONELISTENER";
    private boolean isPhoneCalling = false;
    private Context incommingCall;

    public CustomPhoneStateListener(Context incommingCall) {
        this.incommingCall = incommingCall;
    }

    @Override
    public void onCallStateChanged(int state, final String incomingNumber) {
        if (TelephonyManager.CALL_STATE_RINGING == state) {
            // phone ringing
            Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);

            Handler handler = new Handler();

            //Put in delay because call log is not updated immediately when state changed
            // The dialler takes a little bit of time to write to it 500ms seems to be enough
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // get start of cursor
                    Log.i("CallLogDetailsActivity", "Getting patientsList");
                    ArrayList<Patient> list = new ArrayList<>();
                    Patient patient = new Patient();
                    patient.setNome("Estevão");
                    patient.setTelefone("016993514299");
                    list.add(patient);
                    Patient patient1 = new Patient();
                    patient1.setNome("Lucas");
                    patient1.setTelefone("016988306445");
                    list.add(patient1);

                    for (Patient p : list) {
                        if (p.getTelefone().equals(incomingNumber)) {
                            MyBroadcastReceiver.initHeadLayer(incommingCall, p.getTelefone(), p.getNome());
                        }
                    }
                }
            }, 500);
        }

        if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
            // active
            Log.i(LOG_TAG, "OFFHOOK");
            isPhoneCalling = true;
        }

        if (TelephonyManager.CALL_STATE_IDLE == state) {
            // run when class initial and phone call ended, need detect flag
            // from CALL_STATE_OFFHOOK
            Log.i(LOG_TAG, "IDLE number");

            destroyHeadLayer();
            isPhoneCalling = false;
        }
    }

    public static void destroyHeadLayer() {
        if (MyBroadcastReceiver.mHeadLayer != null) {
            MyBroadcastReceiver.mHeadLayer.destroy();
            MyBroadcastReceiver.mHeadLayer = null;
        }
    }
}
