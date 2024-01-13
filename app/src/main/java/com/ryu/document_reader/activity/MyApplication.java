package com.ryu.document_reader.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.LogLevel;
import com.ryu.document_reader.ads.AppOpenManager;

public class MyApplication extends Application {
    public static final Companion Companion = new Companion();

    public static AppOpenManager appOpenManager;

    private static MyApplication instance;

    public void onCreate() {
        super.onCreate();
        Companion companion = Companion;
        instance = this;
        try {
            AppOpenManager appOpenManager = new AppOpenManager(this);
            companion.setAppOpenManager(appOpenManager);
            AdjustConfig adjustConfig = new AdjustConfig(this, "orfbb28riby8", "production");
            adjustConfig.setLogLevel(LogLevel.VERBOSE);
            adjustConfig.setPreinstallTrackingEnabled(true);
            Adjust.onCreate(adjustConfig);
            AdjustLifecycleCallbacks adjustLifecycleCallbacks = new AdjustLifecycleCallbacks();
            registerActivityLifecycleCallbacks(adjustLifecycleCallbacks);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static final class AdjustLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        public void onActivityCreated(Activity activity, Bundle param1Bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
            Adjust.onPause();
        }

        public void onActivityResumed(Activity activity) {
            Adjust.onResume();
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle param1Bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }
    }

    public static final class Companion {
        private Companion() {}

        public AppOpenManager getAppOpenManager() {
            return MyApplication.appOpenManager;
        }

        public MyApplication getContext() {
            if (getInstance() == null)
                return new MyApplication();
            MyApplication myApplication = getInstance();
            return myApplication;
        }

        public MyApplication getInstance() {
            return MyApplication.instance;
        }

        public void setAppOpenManager(AppOpenManager appOpenManager) {
            MyApplication.appOpenManager = appOpenManager;
        }

        public void setInstance(MyApplication myApplication) {
            MyApplication.instance = myApplication;
        }
    }
}
