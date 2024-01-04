package com.ryu.document_reader.ads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.ryu.document_reader.R;
import com.ryu.document_reader.util.Utils;

public class AppOpenManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private static final String LOG_TAG = "AppOpenManager";

    public static boolean LoadFail = false;

    public static boolean isShowingAd = false;

    private AppOpenAd appOpenAd = null;

    private Activity currentActivity;

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private OpenAdsListener mOpenAdsListener = null;

    private final Application myApplication;

    public AppOpenManager(Application paramApplication) {
        this.myApplication = paramApplication;
        paramApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    private AdRequest getAdRequest() {
        return (new AdRequest.Builder()).build();
    }

    public void showAds(Activity activity, AppOpenAd openAd) {
        this.currentActivity = activity;
        appOpenAd = openAd;
        showAdIfAvailable();
    }

    public void fetchAd() {
        try {
            if (isAdAvailable())
                return;
            AppOpenAd.AppOpenAdLoadCallback appOpenAdLoadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAdFailedToLoad(LoadAdError param1LoadAdError) {
                    super.onAdFailedToLoad(param1LoadAdError);
                    AppOpenManager.LoadFail = true;
                    Log.d("1222222", "onAdFailedToLoad");
                }

                public void onAdLoaded(AppOpenAd param1AppOpenAd) {
                    super.onAdLoaded(param1AppOpenAd);
                    Log.d("1222222", "onAdLoaded");
                    // TODO
                    AppOpenManager.this.appOpenAd = param1AppOpenAd;
                }
            };
            //super(this);
            this.loadCallback = appOpenAdLoadCallback;
            AdRequest adRequest = getAdRequest();
            Application application = this.myApplication;
            AppOpenAd.load((Context)application, application.getResources().getString(R.string.alldoc_open_id), adRequest, 1, this.loadCallback);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public boolean isAdAvailable() {
        boolean bool;
        if (this.appOpenAd != null) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onActivityCreated: ");
        stringBuilder.append(paramActivity.toString());
        Log.d("TAG", stringBuilder.toString());
    }

    public void onActivityDestroyed(Activity paramActivity) {
        this.currentActivity = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onActivityDestroyed: ");
        stringBuilder.append(paramActivity.toString());
        Log.d("TAG", stringBuilder.toString());
    }

    public void onActivityPaused(Activity paramActivity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onActivityPaused: ");
        stringBuilder.append(paramActivity.toString());
        Log.d("TAG", stringBuilder.toString());
    }

    public void onActivityPreResumed(Activity paramActivity) {
        // TODO
        //super.onActivityPreResumed(paramActivity);
    }

    public void onActivityPreStarted(Activity paramActivity) {}

    public void onActivityResumed(Activity paramActivity) {
        this.currentActivity = paramActivity;
        // TODO
//        if (!(paramActivity instanceof com.ryu.document_reader.activity.SplashActivity))
//            Utils.INSTANCE.showWelcomeBackScreen(paramActivity);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onActivityResumed: ");
        stringBuilder.append(paramActivity.toString());
        Log.d("TAG", stringBuilder.toString());
    }

    public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onActivitySaveInstanceState: ");
        stringBuilder.append(paramActivity.toString());
        Log.d("TAG", stringBuilder.toString());
    }

    public void onActivityStarted(Activity paramActivity) {
        this.currentActivity = paramActivity;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onActivityStarted: ");
        stringBuilder.append(paramActivity.toString());
        Log.d("TAG", stringBuilder.toString());
    }

    public void onActivityStopped(Activity paramActivity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onActivityStopped: ");
        stringBuilder.append(paramActivity.toString());
        Log.d("TAG", stringBuilder.toString());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {}

    public void setOpenAdsListener(OpenAdsListener paramOpenAdsListener) {
        this.mOpenAdsListener = paramOpenAdsListener;
    }

    public void showAdIfAvailable() {
        boolean bool;
        Log.d("1222222", "showAdIfAvailable");
        if (AdsManager.INSTANCE.getIsShowAds()) {
            this.mOpenAdsListener.dismissAds();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("showAdIfAvailable   ");
        stringBuilder.append(isShowingAd);
        stringBuilder.append("  ");
        stringBuilder.append(isAdAvailable());
        stringBuilder.append("  ");
        if (this.mOpenAdsListener != null) {
            bool = true;
        } else {
            bool = false;
        }
        stringBuilder.append(bool);
        Log.d("1222222", stringBuilder.toString());
        if (!isShowingAd && isAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {

                public void onAdDismissedFullScreenContent() {
                    AdsManager.INSTANCE.setIsShowAds(false);
                    // TODO
                    AppOpenManager.this.appOpenAd = null;
                    //AppOpenManager.access$002(AppOpenManager.this, null);
                    AdsManager.INSTANCE.setMTypeAds(1);
                    AdsManager.INSTANCE.setMLastTimeShowAds(System.currentTimeMillis());
                    AppOpenManager.this.fetchAd();
                    AppOpenManager.isShowingAd = false;
                    Log.d("1222222", "onAdDismissedFullScreenContent");
                    AppOpenManager.this.mOpenAdsListener.dismissAds();
                }

                public void onAdFailedToShowFullScreenContent(AdError param1AdError) {
                    AdsManager.INSTANCE.setIsShowAds(false);
                    Log.d("1222222", "onAdFailedToShowFullScreenContent");
                    AppOpenManager.this.mOpenAdsListener.dismissAds();
                }

                public void onAdShowedFullScreenContent() {
                    AdsManager.INSTANCE.setIsShowAds(true);
                    Log.d("1222222", "onAdShowedFullScreenContent");
                    AppOpenManager.LoadFail = false;
                    AppOpenManager.isShowingAd = true;
                }
            };
            this.appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            this.appOpenAd.setOnPaidEventListener(new OnPaidEventListener() {
                @Override
                public void onPaidEvent(@NonNull AdValue adValue) {
                    Utils.INSTANCE.setTrackRevenueByAdjust(adValue.getValueMicros(), adValue.getCurrencyCode());
                }

            });
            this.appOpenAd.show(this.currentActivity);
        } else {
            OpenAdsListener openAdsListener = this.mOpenAdsListener;
            if (openAdsListener != null)
                openAdsListener.dismissAds();
            if (!isShowingAd)
                fetchAd();
        }
    }

    public static interface OpenAdsListener {
        void dismissAds();
    }
}
