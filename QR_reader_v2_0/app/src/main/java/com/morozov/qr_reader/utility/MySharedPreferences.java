package com.morozov.qr_reader.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySharedPreferences {
//    public static String PREF_AGE = "pref_age";
//    public static String PREF_ID = "pref_id";
//    public static String PREF_CODE_TICKET = "pref_code_ticket";
//    public static String PREF_DATE = "pref_date";
//    public static String PREF_PROMO = "pref_promo";
//    public static String PREF_UNI = "pref_uni";
//    public static String PREF_EVENT = "pref_event";
//    public static String PREF_DATE_EVENT = "pref_date_event";
//    public static String PREF_MOB_PHONE = "pref_mob_phone";

    public static void setPreference(Context context, String pref, boolean isEnabled) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(pref, isEnabled);
        editor.apply();
    }

    public static boolean getPreference(Context context, String pref) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(pref, false);
    }
}
