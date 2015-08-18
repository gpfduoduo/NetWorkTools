package com.example.lenovo.networktools.ui;

import android.app.Application;

/**
 * Created by 10129302 on 15-2-10.
 */
public class NetToolsApp extends Application {


    private static NetToolsApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static NetToolsApp getAppContext() {
        return instance;
    }
}
