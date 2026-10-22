package com.example.smartpantry.util;

import android.content.Context;
import android.content.SharedPreferences;

//Settings stored in SharedPreferences.
public final class Prefs {
    private Prefs() {}

    private static final String FILE = "smart_pantry_settings";
    private static final String KEY_ALERTS = "expiry_alerts";
    private static final String KEY_UNIT = "default_unit";

    private static SharedPreferences sp(Context c) {
        return c.getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    public static boolean alertsEnabled(Context c) { return sp(c).getBoolean(KEY_ALERTS, true); }
    public static void setAlertsEnabled(Context c, boolean on) { sp(c).edit().putBoolean(KEY_ALERTS, on).apply(); }

    public static String defaultUnit(Context c) { return sp(c).getString(KEY_UNIT, "g"); }
    public static void setDefaultUnit(Context c, String unit) { sp(c).edit().putString(KEY_UNIT, unit).apply(); }
}
