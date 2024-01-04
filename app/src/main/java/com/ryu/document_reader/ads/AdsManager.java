package com.ryu.document_reader.ads;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.ads.callback.AdsListener;
import com.ryu.document_reader.util.Constants;
import com.ryu.document_reader.util.Utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

public final class AdsManager {
    private static int AdsPositionInListFile;

    public static final AdsManager INSTANCE = new AdsManager();

    private static boolean IsShowAds;

    private static String email_feedback;

    private static boolean is_ready_show_open_ads;

    private static boolean is_show_banner_ads_image_viewer;

    private static boolean is_show_banner_ads_list_file;

    private static boolean is_show_banner_ads_office_viewer;

    private static boolean is_show_banner_ads_pdf_viewer;

    private static boolean is_show_button_convert;

    private static boolean is_show_inter_ads_home;

    private static boolean is_show_inter_ads_list_file;

    private static boolean is_show_inter_ads_search_file;

    private static boolean is_show_inter_ads_splash;

    private static boolean is_show_native_ads_home;

    private static boolean is_show_native_ads_image_convert;

    private static boolean is_show_native_ads_image_list;

    private static boolean is_show_native_ads_language;

    private static boolean is_show_native_ads_list_file;

    private static boolean is_show_native_ads_search_file;

    private static boolean is_show_native_save_file;

    private static boolean is_show_open_ads_any_position;

    private static boolean is_show_open_ads_resume_home;

    private static boolean is_show_open_ads_resume_image_viewer;

    private static boolean is_show_open_ads_resume_language_viewer;

    private static boolean is_show_open_ads_resume_list_file;

    private static boolean is_show_open_ads_resume_office_viewer;

    private static boolean is_show_open_ads_resume_pdf_viewer;

    private static boolean is_show_open_ads_resume_screenshot_editor;

    private static boolean is_show_open_ads_resume_search_file;

    private static Activity mActivity;

    private static AdsListener mAdsListener;

    private static Context mContext;

    private static Handler mHandler;

    private static InterstitialAd mInterstitialAd;

    private static boolean mIsNotDismissSplashAds;

    private static long mLastTimeShowAds;

    private static boolean mLoadFail;

    private static AdsManagerTimeNextScreen mTimeNextScreen;

    private static int mTypeAds = -1;

    private static boolean mWaitLoading;

    static {
        is_show_native_ads_home = true;
        is_show_native_ads_list_file = true;
        is_show_native_ads_search_file = true;
        is_show_native_ads_language = true;
        is_show_native_save_file = true;
        is_show_native_ads_image_list = true;
        is_show_native_ads_image_convert = true;
        is_show_banner_ads_list_file = true;
        is_show_banner_ads_pdf_viewer = true;
        is_show_banner_ads_office_viewer = true;
        is_show_banner_ads_image_viewer = true;
        is_show_open_ads_resume_home = true;
        is_show_open_ads_resume_list_file = true;
        is_show_open_ads_resume_search_file = true;
        is_show_open_ads_resume_pdf_viewer = true;
        is_show_open_ads_resume_office_viewer = true;
        is_show_open_ads_resume_image_viewer = true;
        is_show_open_ads_resume_language_viewer = true;
        is_show_open_ads_resume_screenshot_editor = true;
        is_show_open_ads_any_position = true;
        is_show_inter_ads_splash = true;
        is_show_inter_ads_home = true;
        is_show_inter_ads_list_file = true;
        is_show_inter_ads_search_file = true;
        AdsPositionInListFile = 1;
        is_show_button_convert = true;
        email_feedback = "teammarketing@lutech.ltd";
        mIsNotDismissSplashAds = true;
        mTimeNextScreen = new AdsManagerTimeNextScreen(Constants.INSTANCE.getMAX_TIME_AT_SPLASH());
        mHandler = new AdsManagerHandler(Looper.getMainLooper());
    }

    private final void clear() {
        mActivity = null;
    }

    private static final void showAds(Activity paramActivity) {
        Intrinsics.checkNotNullParameter(paramActivity, "$activity");
        AdsListener adsListener = mAdsListener;
        if (adsListener != null)
            adsListener.onShowSuccess();
        InterstitialAd interstitialAd2 = mInterstitialAd;
        if (interstitialAd2 != null)
            interstitialAd2.show(paramActivity);
        InterstitialAd interstitialAd1 = mInterstitialAd;
        if (interstitialAd1 != null)
            interstitialAd1.setOnPaidEventListener(new OnPaidEventListener() {
                @Override
                public void onPaidEvent(@NonNull AdValue adValue) {
                    // TODO
                }
            });
    }

    private static final void showAdsValue(AdValue paramAdValue) {
        Intrinsics.checkNotNullParameter(paramAdValue, "it");
        Utils utils = Utils.INSTANCE;
        long l = paramAdValue.getValueMicros();
        String str = paramAdValue.getCurrencyCode();
        Intrinsics.checkNotNullExpressionValue(str, "it.currencyCode");
        utils.setTrackRevenueByAdjust(l, str);
    }

    private static final void showAdsInterstitialAd(Activity paramActivity) {
        Intrinsics.checkNotNullParameter(paramActivity, "$activity");
        AdsListener adsListener = mAdsListener;
        if (adsListener != null)
            adsListener.onShowSuccess();
        InterstitialAd interstitialAd = mInterstitialAd;
        if (interstitialAd != null)
            interstitialAd.show(paramActivity);
    }

    public final void dismissTimer() {
        mTimeNextScreen.cancel();
    }

    public final int getAdsPositionInListFile() {
        return AdsPositionInListFile;
    }

    public final int getDistanceTimeShowAds(boolean paramBoolean) {
        long l;
        if ((paramBoolean && mTypeAds == 0) || (!paramBoolean && mTypeAds == 1)) {
            l = Constants.INSTANCE.getDISTANCE_TIME_ADS_SAME();
        } else {
            l = Constants.INSTANCE.getDISTANCE_TIME_ADS_OTHER();
        }
        return (int)l;
    }

    public final String getEmail_feedback() {
        return email_feedback;
    }

    public final boolean getIsShowAds() {
        return IsShowAds;
    }

    public final boolean getMIsNotDismissSplashAds() {
        return mIsNotDismissSplashAds;
    }

    public final long getMLastTimeShowAds() {
        return mLastTimeShowAds;
    }

    public final int getMTypeAds() {
        return mTypeAds;
    }

    public final boolean is_ready_show_open_ads() {
        return is_ready_show_open_ads;
    }

    public final boolean is_show_banner_ads_image_viewer() {
        return is_show_banner_ads_image_viewer;
    }

    public final boolean is_show_banner_ads_list_file() {
        return is_show_banner_ads_list_file;
    }

    public final boolean is_show_banner_ads_office_viewer() {
        return is_show_banner_ads_office_viewer;
    }

    public final boolean is_show_banner_ads_pdf_viewer() {
        return is_show_banner_ads_pdf_viewer;
    }

    public final boolean is_show_button_convert() {
        return is_show_button_convert;
    }

    public final boolean is_show_inter_ads_home() {
        return is_show_inter_ads_home;
    }

    public final boolean is_show_inter_ads_list_file() {
        return is_show_inter_ads_list_file;
    }

    public final boolean is_show_inter_ads_search_file() {
        return is_show_inter_ads_search_file;
    }

    public final boolean is_show_inter_ads_splash() {
        return is_show_inter_ads_splash;
    }

    public final boolean is_show_native_ads_home() {
        return is_show_native_ads_home;
    }

    public final boolean is_show_native_ads_image_convert() {
        return is_show_native_ads_image_convert;
    }

    public final boolean is_show_native_ads_image_list() {
        return is_show_native_ads_image_list;
    }

    public final boolean is_show_native_ads_language() {
        return is_show_native_ads_language;
    }

    public final boolean is_show_native_ads_list_file() {
        return is_show_native_ads_list_file;
    }

    public final boolean is_show_native_ads_search_file() {
        return is_show_native_ads_search_file;
    }

    public final boolean is_show_native_save_file() {
        return is_show_native_save_file;
    }

    public final boolean is_show_open_ads_any_position() {
        return is_show_open_ads_any_position;
    }

    public final boolean is_show_open_ads_resume_home() {
        return is_show_open_ads_resume_home;
    }

    public final boolean is_show_open_ads_resume_image_viewer() {
        return is_show_open_ads_resume_image_viewer;
    }

    public final boolean is_show_open_ads_resume_language_viewer() {
        return is_show_open_ads_resume_language_viewer;
    }

    public final boolean is_show_open_ads_resume_list_file() {
        return is_show_open_ads_resume_list_file;
    }

    public final boolean is_show_open_ads_resume_office_viewer() {
        return is_show_open_ads_resume_office_viewer;
    }

    public final boolean is_show_open_ads_resume_pdf_viewer() {
        return is_show_open_ads_resume_pdf_viewer;
    }

    public final boolean is_show_open_ads_resume_screenshot_editor() {
        return is_show_open_ads_resume_screenshot_editor;
    }

    public final boolean is_show_open_ads_resume_search_file() {
        return is_show_open_ads_resume_search_file;
    }

    public final void loadAds(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        mLoadFail = false;
        mWaitLoading = false;
        mContext = paramContext;
        AdRequest adRequest = (new AdRequest.Builder()).build();
        Intrinsics.checkNotNullExpressionValue(adRequest, "Builder().build()");
        InterstitialAd.load(paramContext, paramContext.getString(R.string.alldoc_inters_id), adRequest, new AdsManagerLoadAds(paramContext));
    }

    public final void setAdsPositionInListFile(int paramInt) {
        AdsPositionInListFile = paramInt;
    }

    public final void setEmail_feedback(String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "<set-?>");
        email_feedback = paramString;
    }

    public final void setIsShowAds(boolean paramBoolean) {
        IsShowAds = paramBoolean;
    }

    public final void setMIsNotDismissSplashAds(boolean paramBoolean) {
        mIsNotDismissSplashAds = paramBoolean;
    }

    public final void setMLastTimeShowAds(long paramLong) {
        mLastTimeShowAds = paramLong;
    }

    public final void setMTypeAds(int paramInt) {
        mTypeAds = paramInt;
    }

    public final void set_ready_show_open_ads(boolean paramBoolean) {
        is_ready_show_open_ads = paramBoolean;
    }

    public final void set_show_banner_ads_image_viewer(boolean paramBoolean) {
        is_show_banner_ads_image_viewer = paramBoolean;
    }

    public final void set_show_banner_ads_list_file(boolean paramBoolean) {
        is_show_banner_ads_list_file = paramBoolean;
    }

    public final void set_show_banner_ads_office_viewer(boolean paramBoolean) {
        is_show_banner_ads_office_viewer = paramBoolean;
    }

    public final void set_show_banner_ads_pdf_viewer(boolean paramBoolean) {
        is_show_banner_ads_pdf_viewer = paramBoolean;
    }

    public final void set_show_button_convert(boolean paramBoolean) {
        is_show_button_convert = paramBoolean;
    }

    public final void set_show_inter_ads_home(boolean paramBoolean) {
        is_show_inter_ads_home = paramBoolean;
    }

    public final void set_show_inter_ads_list_file(boolean paramBoolean) {
        is_show_inter_ads_list_file = paramBoolean;
    }

    public final void set_show_inter_ads_search_file(boolean paramBoolean) {
        is_show_inter_ads_search_file = paramBoolean;
    }

    public final void set_show_inter_ads_splash(boolean paramBoolean) {
        is_show_inter_ads_splash = paramBoolean;
    }

    public final void set_show_native_ads_home(boolean paramBoolean) {
        is_show_native_ads_home = paramBoolean;
    }

    public final void set_show_native_ads_image_convert(boolean paramBoolean) {
        is_show_native_ads_image_convert = paramBoolean;
    }

    public final void set_show_native_ads_image_list(boolean paramBoolean) {
        is_show_native_ads_image_list = paramBoolean;
    }

    public final void set_show_native_ads_language(boolean paramBoolean) {
        is_show_native_ads_language = paramBoolean;
    }

    public final void set_show_native_ads_list_file(boolean paramBoolean) {
        is_show_native_ads_list_file = paramBoolean;
    }

    public final void set_show_native_ads_search_file(boolean paramBoolean) {
        is_show_native_ads_search_file = paramBoolean;
    }

    public final void set_show_native_save_file(boolean paramBoolean) {
        is_show_native_save_file = paramBoolean;
    }

    public final void set_show_open_ads_any_position(boolean paramBoolean) {
        is_show_open_ads_any_position = paramBoolean;
    }

    public final void set_show_open_ads_resume_home(boolean paramBoolean) {
        is_show_open_ads_resume_home = paramBoolean;
    }

    public final void set_show_open_ads_resume_image_viewer(boolean paramBoolean) {
        is_show_open_ads_resume_image_viewer = paramBoolean;
    }

    public final void set_show_open_ads_resume_language_viewer(boolean paramBoolean) {
        is_show_open_ads_resume_language_viewer = paramBoolean;
    }

    public final void set_show_open_ads_resume_list_file(boolean paramBoolean) {
        is_show_open_ads_resume_list_file = paramBoolean;
    }

    public final void set_show_open_ads_resume_office_viewer(boolean paramBoolean) {
        is_show_open_ads_resume_office_viewer = paramBoolean;
    }

    public final void set_show_open_ads_resume_pdf_viewer(boolean paramBoolean) {
        is_show_open_ads_resume_pdf_viewer = paramBoolean;
    }

    public final void set_show_open_ads_resume_screenshot_editor(boolean paramBoolean) {
        is_show_open_ads_resume_screenshot_editor = paramBoolean;
    }

    public final void set_show_open_ads_resume_search_file(boolean paramBoolean) {
        is_show_open_ads_resume_search_file = paramBoolean;
    }

    public final void showAds(Activity paramActivity, AdsListener paramAdsListener) {
        AdsListener adsListener = paramAdsListener;
        Intrinsics.checkNotNullParameter(paramActivity, "activity");
        Intrinsics.checkNotNullParameter(paramAdsListener, "adsListener");
        mIsNotDismissSplashAds = true;
        mAdsListener = paramAdsListener;
        // TODO
//        if (paramActivity instanceof com.ryu.document_reader.activity.SplashActivity)
//            mTimeNextScreen.start();
        long l = (System.currentTimeMillis() - mLastTimeShowAds) / 1000L;
        if (IsShowAds) {
            paramAdsListener.onShowFail();
            return;
        }
        if (mLoadFail || BillingClientSetup.isUpgraded((Context)paramActivity)) {
            is_ready_show_open_ads = true;
            adsListener = mAdsListener;
            if (adsListener != null)
                adsListener.onShowFail();
            mTimeNextScreen.cancel();
            return;
        }
        if (mInterstitialAd != null) {
            if (l >= getDistanceTimeShowAds(true)) {
                (new Handler(Looper.getMainLooper())).postDelayed(new AdsManagerHandleShowAds((Activity) adsListener), 1000L);
            } else if (l < getDistanceTimeShowAds(true) - Constants.INSTANCE.getDISTANCE_TIME_WAIT_MAX()) {
                adsListener = mAdsListener;
                if (adsListener != null)
                    adsListener.onShowFail();
            } else {
                (new Handler(Looper.getMainLooper())).postDelayed(new AdsManagerShowAds2((Activity)adsListener), (getDistanceTimeShowAds(true) - l) * 1000L);
            }
        } else if (l >= getDistanceTimeShowAds(true)) {
            mActivity = (Activity)adsListener;
            mWaitLoading = true;
            Log.d("6666666666", "delTime");
        } else {
            adsListener = mAdsListener;
            if (adsListener != null)
                adsListener.onShowFail();
            Log.d("6666666666", "showAds");
        }
    }

    public static final class AdsManagerLoadAds extends InterstitialAdLoadCallback {
        final Context context;

        AdsManagerLoadAds(Context param1Context) {
            context = param1Context;
        }

        private static final void onAdLoaded$lambda$0(AdValue param1AdValue) {
            Intrinsics.checkNotNullParameter(param1AdValue, "it");
            Utils utils = Utils.INSTANCE;
            long l = param1AdValue.getValueMicros();
            String str = param1AdValue.getCurrencyCode();
            Intrinsics.checkNotNullExpressionValue(str, "it.currencyCode");
            utils.setTrackRevenueByAdjust(l, str);
        }

        public void onAdFailedToLoad(LoadAdError param1LoadAdError) {
            Intrinsics.checkNotNullParameter(param1LoadAdError, "adError");
            AdsManager adsManager = AdsManager.INSTANCE;
            AdsManager.mInterstitialAd = null;
            adsManager = AdsManager.INSTANCE;
            AdsManager.mLoadFail = true;
            AdsListener adsListener = AdsManager.mAdsListener;
            if (adsListener != null)
                adsListener.onAdDismissed();
        }

        public void onAdLoaded(InterstitialAd param1InterstitialAd) {
            Intrinsics.checkNotNullParameter(param1InterstitialAd, "interstitialAd");
            AdsManager adsManager = AdsManager.INSTANCE;
            AdsManager.mInterstitialAd = param1InterstitialAd;
            if (AdsManager.mWaitLoading && AdsManager.mActivity != null && AdsManager.INSTANCE.getMIsNotDismissSplashAds()) {
                AdsListener adsListener = AdsManager.mAdsListener;
                if (adsListener != null)
                    adsListener.onShowSuccess();
                InterstitialAd interstitialAd = AdsManager.mInterstitialAd;
                if (interstitialAd != null) {
                    Activity activity = AdsManager.mActivity;
                    Intrinsics.checkNotNull(activity);
                    interstitialAd.show(activity);
                }
            }
            param1InterstitialAd = AdsManager.mInterstitialAd;
            if (param1InterstitialAd != null)
                param1InterstitialAd.setOnPaidEventListener(new OnPaidEventListener() {
                    @Override
                    public void onPaidEvent(@NonNull AdValue adValue) {

                    }
                });
            param1InterstitialAd = AdsManager.mInterstitialAd;
            if (param1InterstitialAd != null)
                param1InterstitialAd.setFullScreenContentCallback(new AdsManagerLoadAds1(this.context));
        }

        public static final class AdsManagerLoadAds1 extends FullScreenContentCallback {
            final Context context;

            AdsManagerLoadAds1(Context param1Context) {
                context = param1Context;
            }

            public void onAdDismissedFullScreenContent() {
                AdsListener adsListener = AdsManager.mAdsListener;
                if (adsListener != null)
                    adsListener.onAdDismissed();
                AdsManager.INSTANCE.setMLastTimeShowAds(System.currentTimeMillis());
                AdsManager.INSTANCE.setIsShowAds(false);
                AdsManager.INSTANCE.set_ready_show_open_ads(true);
                AdsManager adsManager = AdsManager.INSTANCE;
                AdsManager.mInterstitialAd = null;
                AdsManager.INSTANCE.setMTypeAds(0);
                adsManager = AdsManager.INSTANCE;
                AdsManager.mAdsListener = null;
                AdsManager.INSTANCE.loadAds(this.context);
            }

            public void onAdFailedToShowFullScreenContent(AdError param1AdError) {
                Intrinsics.checkNotNullParameter(param1AdError, "p0");
                super.onAdFailedToShowFullScreenContent(param1AdError);
                AdsManager.INSTANCE.setIsShowAds(false);
                AdsListener adsListener = AdsManager.mAdsListener;
                if (adsListener != null)
                    adsListener.onShowFail();
                AdsManager adsManager = AdsManager.INSTANCE;
                AdsManager.mAdsListener = null;
                AdsManager.INSTANCE.set_ready_show_open_ads(false);
                AdsManager.INSTANCE.loadAds(this.context);
            }

            public void onAdShowedFullScreenContent() {
                AdsManager.INSTANCE.setIsShowAds(true);
                AdsManager.INSTANCE.dismissTimer();
                AdsManager.INSTANCE.clear();
                AdsManager adsManager = AdsManager.INSTANCE;
                AdsManager.mInterstitialAd = null;
                AdsManager.INSTANCE.set_ready_show_open_ads(false);
            }
        }
    }

    public static final class AdsManagerLoadAds2 extends FullScreenContentCallback {
        final Context context;

        AdsManagerLoadAds2(Context context) {
            this.context = context;
        }

        public void onAdDismissedFullScreenContent() {
            AdsListener adsListener = AdsManager.mAdsListener;
            if (adsListener != null)
                adsListener.onAdDismissed();
            AdsManager.INSTANCE.setMLastTimeShowAds(System.currentTimeMillis());
            AdsManager.INSTANCE.setIsShowAds(false);
            AdsManager.INSTANCE.set_ready_show_open_ads(true);
            AdsManager adsManager = AdsManager.INSTANCE;
            AdsManager.mInterstitialAd = null;
            AdsManager.INSTANCE.setMTypeAds(0);
            adsManager = AdsManager.INSTANCE;
            AdsManager.mAdsListener = null;
            AdsManager.INSTANCE.loadAds(this.context);
        }

        public void onAdFailedToShowFullScreenContent(AdError param1AdError) {
            Intrinsics.checkNotNullParameter(param1AdError, "p0");
            super.onAdFailedToShowFullScreenContent(param1AdError);
            AdsManager.INSTANCE.setIsShowAds(false);
            AdsListener adsListener = AdsManager.mAdsListener;
            if (adsListener != null)
                adsListener.onShowFail();
            AdsManager adsManager = AdsManager.INSTANCE;
            AdsManager.mAdsListener = null;
            AdsManager.INSTANCE.set_ready_show_open_ads(false);
            AdsManager.INSTANCE.loadAds(this.context);
        }

        public void onAdShowedFullScreenContent() {
            AdsManager.INSTANCE.setIsShowAds(true);
            AdsManager.INSTANCE.dismissTimer();
            AdsManager.INSTANCE.clear();
            AdsManager adsManager = AdsManager.INSTANCE;
            AdsManager.mInterstitialAd = null;
            AdsManager.INSTANCE.set_ready_show_open_ads(false);
        }
    }

    public static final class AdsManagerHandler extends Handler {
        AdsManagerHandler(Looper param1Looper) {
            super(param1Looper);
        }

        public void handleMessage(Message param1Message) {
            Intrinsics.checkNotNullParameter(param1Message, "msg");
            super.handleMessage(param1Message);
            if (param1Message.what == 100) {
                AdsManager.INSTANCE.setMIsNotDismissSplashAds(false);
                AdsManager.INSTANCE.set_ready_show_open_ads(true);
                AdsListener adsListener = AdsManager.mAdsListener;
                if (adsListener != null)
                    adsListener.onAdDismissed();
                Context context = AdsManager.mContext;
                if (context != null)
                    AdsManager.INSTANCE.loadAds(context);
            }
        }
    }

    public static final class AdsManagerTimeNextScreen extends CountDownTimer {
        AdsManagerTimeNextScreen(long param1Long) {
            super(param1Long, 1000L);
        }

        public void onFinish() {
            if (AdsManager.mHandler != null)
                AdsManager.mHandler.sendEmptyMessage(100);
        }

        public void onTick(long param1Long) {}
    }

    public final class AdsManagerHandleShowAds implements Runnable {
        public final Activity activity;

        public AdsManagerHandleShowAds(Activity activity) {
            this.activity = activity;
        }
        @Override // java.lang.Runnable
        public final void run() {
            AdsManager.showAds(this.activity);
        }
    }

    public final  class AdsManagerShowAds2 implements Runnable {
        public final Activity activity;

        public AdsManagerShowAds2(Activity activity) {
            this.activity = activity;
        }
        @Override // java.lang.Runnable
        public final void run() {
            AdsManager.showAdsInterstitialAd(this.activity);
        }
    }
}

