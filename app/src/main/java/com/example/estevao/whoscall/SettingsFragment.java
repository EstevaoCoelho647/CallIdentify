package com.example.estevao.whoscall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Standard settings screen.
 * It allows to enable or disable the head service.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final static String SERVICE_ENABLED_KEY = "serviceEnabledKey";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
        enableHeadServiceCheckbox(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        enableHeadServiceCheckbox(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (SERVICE_ENABLED_KEY.equals(key)) {
            boolean enabled = sharedPreferences.getBoolean(key, false);
            if (enabled) {
                startHeadService();
            } else {
                stopHeadService();
            }
        }
    }

    private void enableHeadServiceCheckbox(boolean enabled) {
        getPreferenceScreen().findPreference(SERVICE_ENABLED_KEY).setEnabled(enabled);
    }

    private void startHeadService() {
        Context context = getActivity();
        context.startService(new Intent(context, HeadService.class));
    }

    private void stopHeadService() {
        Context context = getActivity();
        context.stopService(new Intent(context, HeadService.class));
    }
}
