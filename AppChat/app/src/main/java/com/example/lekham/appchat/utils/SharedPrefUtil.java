package com.example.lekham.appchat.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class SharedPrefUtil {

    private static final String APP_PREFS = "application_preferences";
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPrefUtil(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void saveString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void saveInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public void saveBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }
}
