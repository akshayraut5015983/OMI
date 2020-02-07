package com.omi.app;

import android.app.Application;

import com.devs.acr.AutoErrorReporter;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AutoErrorReporter.get(this)
                .setEmailAddresses("abhaykumardasondhi@gmail.com")
                .setEmailSubject("Crash Report OMI App")
                .start();
    }
}
