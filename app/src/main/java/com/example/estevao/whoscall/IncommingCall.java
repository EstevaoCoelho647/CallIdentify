package com.example.estevao.whoscall;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by estevao on 22/03/16.
 */
public class IncommingCall extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SettingsFragment settingsFragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, settingsFragment)
                .commit();
    }
}
