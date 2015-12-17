package com.llchyan.retrofitdemo.base;

import android.app.Application;

/**
 * Created by LinLin on 2015/12/5.
 */
public class BaseApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
//        Fresco.initialize(this);
//        startService(new Intent(this, ConnectionService.class));
    }
}
