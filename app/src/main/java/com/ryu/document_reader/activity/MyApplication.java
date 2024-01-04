package com.ryu.document_reader.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.LogLevel;
import com.ryu.document_reader.ads.AppOpenManager;

import kotlin.jvm.internal.Intrinsics;

public class MyApplication extends Application {
    public static final Companion Companion = new Companion();

    public static AppOpenManager appOpenManager;

    private static MyApplication instance;

    public void onCreate() {
        super.onCreate();
        Companion companion = Companion;
        instance = this;
        try {
            AppOpenManager appOpenManager = new AppOpenManager();
            companion.setAppOpenManager(appOpenManager);
            AdjustConfig adjustConfig = new AdjustConfig((Context)this, "orfbb28riby8", "production");
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
        public void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {
            Intrinsics.checkNotNullParameter(param1Activity, "p0");
        }

        public void onActivityDestroyed(Activity param1Activity) {
            Intrinsics.checkNotNullParameter(param1Activity, "p0");
        }

        public void onActivityPaused(Activity param1Activity) {
            Intrinsics.checkNotNullParameter(param1Activity, "activity");
            Adjust.onPause();
        }

        public void onActivityResumed(Activity param1Activity) {
            Intrinsics.checkNotNullParameter(param1Activity, "activity");
            Adjust.onResume();
        }

        public void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {
            Intrinsics.checkNotNullParameter(param1Activity, "p0");
            Intrinsics.checkNotNullParameter(param1Bundle, "p1");
        }

        public void onActivityStarted(Activity param1Activity) {
            Intrinsics.checkNotNullParameter(param1Activity, "p0");
        }

        public void onActivityStopped(Activity param1Activity) {
            Intrinsics.checkNotNullParameter(param1Activity, "p0");
        }
    }

    public static final class Companion {
        private Companion() {}

        public final AppOpenManager getAppOpenManager() {
            AppOpenManager appOpenManager = MyApplication.appOpenManager;
            if (appOpenManager != null)
                return appOpenManager;
            Intrinsics.throwUninitializedPropertyAccessException("appOpenManager");
            return null;
        }

        public final MyApplication getContext() {
            if (getInstance() == null)
                return new MyApplication();
            MyApplication myApplication = getInstance();
            Intrinsics.checkNotNull(myApplication);
            return myApplication;
        }

        public final MyApplication getInstance() {
            return MyApplication.instance;
        }

        public final void setAppOpenManager(AppOpenManager param1AppOpenManager) {
            Intrinsics.checkNotNullParameter(param1AppOpenManager, "<set-?>");
            MyApplication.appOpenManager = param1AppOpenManager;
        }

        public final void setInstance(MyApplication param1MyApplication) {
            MyApplication.instance = param1MyApplication;
        }
    }
}
