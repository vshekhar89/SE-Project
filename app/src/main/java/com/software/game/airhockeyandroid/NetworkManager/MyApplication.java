package com.software.game.airhockeyandroid.NetworkManager;

import android.app.Application;
import android.content.Context;

/**
 * Created by Abhishek on 10/13/2015.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
