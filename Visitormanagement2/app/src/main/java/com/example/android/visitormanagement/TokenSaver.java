package com.example.android.visitormanagement;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NAYA on 7/12/2017.
 */

public class TokenSaver {
    private final static String PREF = "token auth";
    private final static String TOKEN_KEY = "access_token";

    public static String getToken(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, "");
    }

    public static void setToken(Context c, String token) {
        SharedPreferences prefs = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public static void deleteToken(Context c){
        SharedPreferences prefs = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
}
}
