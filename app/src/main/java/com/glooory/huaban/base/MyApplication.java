package com.glooory.huaban.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class MyApplication extends Application {
    private RefWatcher refWatcher;
    private static MyApplication mInstance;
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        refWatcher = LeakCanary.install(this);

        Logger.init()
                .methodCount(1)
//                .logLevel(LogLevel.NONE)  //for release version
                ;

        Fresco.initialize(this);
    }

    public static RefWatcher getRefwatcher(Context context) {
        MyApplication myApplication = (MyApplication) context.getApplicationContext();
        return myApplication.refWatcher;
    }
}
