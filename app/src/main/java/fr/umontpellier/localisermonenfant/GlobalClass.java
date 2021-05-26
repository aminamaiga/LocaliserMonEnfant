package fr.umontpellier.localisermonenfant;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

public class GlobalClass extends MultiDexApplication {
    public static final String PREFS_LOGIN = "LoginPreference";
    public static final String TOKEN = "TOKEN";
}
