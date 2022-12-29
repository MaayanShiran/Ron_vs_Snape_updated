package com.example.ron_vs_mcgonagall;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private static final String DB_FILE = "DB_FILE";
    private static MySharedPreferences mySharedPreferences = null;
    private SharedPreferences preferences;

    public static MySharedPreferences getInstance() {
        return mySharedPreferences;
    }

    public static void init(Context context) {
        if (mySharedPreferences == null) {
            mySharedPreferences = new MySharedPreferences(context);
        }
    }

    private MySharedPreferences(Context context) {
        preferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }

}
