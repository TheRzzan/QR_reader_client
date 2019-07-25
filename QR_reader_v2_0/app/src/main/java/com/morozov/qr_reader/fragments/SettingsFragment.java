package com.morozov.qr_reader.fragments;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.morozov.qr_reader.R;
import com.morozov.qr_reader.utility.MySharedPreferences;

public class SettingsFragment extends PreferenceFragment {
    private static final String LOG_TAG = UserInfoFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        setListener(getResources().getString(R.string.pref_age_key));
        setListener(getResources().getString(R.string.pref_id_key));
        setListener(getResources().getString(R.string.pref_code_ticket_key));
        setListener(getResources().getString(R.string.pref_date_key));
        setListener(getResources().getString(R.string.pref_promo_key));
        setListener(getResources().getString(R.string.pref_uni_key));
        setListener(getResources().getString(R.string.pref_event_key));
        setListener(getResources().getString(R.string.pref_date_event_key));
        setListener(getResources().getString(R.string.pref_mob_phone_key));
    }

    private void setListener(String key) {
        CheckBoxPreference agePref = (CheckBoxPreference) findPreference(key);
        agePref.setChecked(MySharedPreferences.getPreference(getActivity(), key));
        agePref.setOnPreferenceChangeListener((preference, newValue) -> {
            MySharedPreferences.setPreference(getActivity(), key, (boolean) newValue);
            return true;
        });
    }
}
