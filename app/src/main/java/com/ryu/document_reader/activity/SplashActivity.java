package com.ryu.document_reader.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
//import com.ironsource.mediationsdk.IronSource;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.ads.AppOpenManager;
import com.ryu.document_reader.ads.callback.AdsListener;
import com.ryu.document_reader.util.Constants;
import com.ryu.document_reader.util.PathUtil;
import com.ryu.document_reader.util.Utils;

import java.util.Map;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class SplashActivity extends AppCompatActivity implements AdsListener {
    public static final Companion Companion = new Companion();

    private static int Theme;

    public Map<Integer, View> findViewCache;

    private boolean isCloseApp = true;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private Intent mIntent;

    private Dialog mWarningDialog;

    private final void gotoNextScreen() {

    }

    private final void handleEvent() {
        Dialog dialog = this.mWarningDialog;
        if (dialog != null) {
            TextView textView = (TextView)dialog.findViewById(R.id.btnGotoStore);
            if (textView != null)
                // TODO
                textView.setOnClickListener(view -> {

                });
        }
        dialog = this.mWarningDialog;
        if (dialog != null) {
            ImageView imageView = (ImageView)dialog.findViewById(R.id.btnBack);
            if (imageView != null)
                imageView.setOnClickListener(view -> {

                });
        }
        dialog = this.mWarningDialog;
        if (dialog != null) {
            TextView textView = (TextView)dialog.findViewById(R.id.btnDoLater);
            if (textView != null)
                textView.setOnClickListener(view -> {

                });
        }
    }

    private static final void handleEvent$lambda$3(SplashActivity paramSplashActivity, View paramView) {
        Intrinsics.checkNotNullParameter(paramSplashActivity, "this$0");
        try {
            Intent intent = new Intent();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://play.google.com/store/apps/details?id=");
            stringBuilder.append(paramSplashActivity.getPackageName());
            // TODO check
            //this("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
            paramSplashActivity.startActivity(intent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static final void handleEvent$lambda$4(SplashActivity paramSplashActivity, View paramView) {
        Intrinsics.checkNotNullParameter(paramSplashActivity, "this$0");
        if (paramSplashActivity.isCloseApp) {
            paramSplashActivity.finish();
        } else {
            paramSplashActivity.showAds();
        }
    }

    private static final void handleEvent$lambda$5(SplashActivity paramSplashActivity, View paramView) {
        Intrinsics.checkNotNullParameter(paramSplashActivity, "this$0");
        Dialog dialog = paramSplashActivity.mWarningDialog;
        if (dialog != null)
            dialog.dismiss();
        paramSplashActivity.showAds();
    }

    private final void initData() {
        // TODO fix
//        openFileFromAnotherApp();
//        FirebaseRemoteConfig firebaseRemoteConfig1 = FirebaseRemoteConfig.getInstance();
//        Intrinsics.checkNotNullExpressionValue(firebaseRemoteConfig1, "getInstance()");
//        this.mFirebaseRemoteConfig = firebaseRemoteConfig1;
//        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings = (new FirebaseRemoteConfigSettings.Builder()).setMinimumFetchIntervalInSeconds(10L).build();
//        Intrinsics.checkNotNullExpressionValue(firebaseRemoteConfigSettings, "Builder()\n            .s…(10)\n            .build()");
//        FirebaseRemoteConfig firebaseRemoteConfig3 = this.mFirebaseRemoteConfig;
//        FirebaseRemoteConfig firebaseRemoteConfig2 = null;
//        firebaseRemoteConfig1 = firebaseRemoteConfig3;
//        if (firebaseRemoteConfig3 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
//            firebaseRemoteConfig1 = null;
//        }
//        firebaseRemoteConfig1.setConfigSettingsAsync(firebaseRemoteConfigSettings);
//        firebaseRemoteConfig3 = this.mFirebaseRemoteConfig;
//        firebaseRemoteConfig1 = firebaseRemoteConfig3;
//        if (firebaseRemoteConfig3 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
//            firebaseRemoteConfig1 = null;
//        }
//        firebaseRemoteConfig1.setDefaultsAsync(2132082691);
//        firebaseRemoteConfig1 = this.mFirebaseRemoteConfig;
//        if (firebaseRemoteConfig1 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
//            firebaseRemoteConfig1 = firebaseRemoteConfig2;
//        }
//        firebaseRemoteConfig1.fetchAndActivate().addOnCompleteListener((Activity)this, new SplashActivity$$ExternalSyntheticLambda3(this));
//        Context context = (Context)this;
//        if (!BillingClientSetup.isUpgraded(context)) {
//            MobileAds.initialize(context, new SplashActivity$$ExternalSyntheticLambda4(this));
//        } else {
//            (new Handler(Looper.getMainLooper())).postDelayed(new SplashActivity$$ExternalSyntheticLambda5(this), 2000L);
//        }
    }

    private static final void initData$lambda$0(SplashActivity paramSplashActivity, Task paramTask) {
        Intrinsics.checkNotNullParameter(paramSplashActivity, "this$0");
        Intrinsics.checkNotNullParameter(paramTask, "it");
        paramSplashActivity.loadDataRemoteConfig();
    }

    private static final void initData$lambda$1(SplashActivity paramSplashActivity, InitializationStatus paramInitializationStatus) {
        Intrinsics.checkNotNullParameter(paramSplashActivity, "this$0");
        Intrinsics.checkNotNullParameter(paramInitializationStatus, "it");
        AppOpenManager appOpenManager = MyApplication.Companion.getAppOpenManager();
        if (appOpenManager != null)
            appOpenManager.fetchAd();
        AdsManager adsManager = AdsManager.INSTANCE;
        Context context = paramSplashActivity.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(context, "applicationContext");
        adsManager.loadAds(context);
    }

    private static final void initData$lambda$2(SplashActivity paramSplashActivity) {
        Intrinsics.checkNotNullParameter(paramSplashActivity, "this$0");
        paramSplashActivity.gotoNextScreen();
    }

    private final void initView() {
        Context context = (Context)this;
        Dialog dialog = new Dialog(context);
        this.mWarningDialog = dialog;
        dialog.setContentView(R.layout.activity_splash);
        dialog = this.mWarningDialog;
        if (dialog != null)
            dialog.setCancelable(false);
        dialog = this.mWarningDialog;
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null)
                window.setLayout(-2, -2);
        }
        dialog = this.mWarningDialog;
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null)
                window.setBackgroundDrawable((Drawable)new ColorDrawable(0));
        }
        ((TextView) findCachedViewById(R.id.txtTitle)).setText((CharSequence) HtmlCompat.fromHtml(getString(R.string.app_name_splash), 0));
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_translate);
        ((ImageView)findCachedViewById(R.id.ivIconApp)).startAnimation(animation);
    }

    private final void loadDataRemoteConfig() {
        Constants constants2 = Constants.INSTANCE;
        FirebaseRemoteConfig firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        FirebaseRemoteConfig firebaseRemoteConfig3 = null;
        FirebaseRemoteConfig firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        constants2.setCURERENT_VERSION_CODE(firebaseRemoteConfig2.getLong("current_version"));
        constants2 = Constants.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        constants2.setMINIMUM_VERSION_CODE(firebaseRemoteConfig2.getLong("minimum_version"));
        constants2 = Constants.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        constants2.setDISTANCE_TIME_ADS_OTHER(firebaseRemoteConfig2.getLong("distance_time_show_other_ads"));
        constants2 = Constants.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        constants2.setDISTANCE_TIME_ADS_SAME(firebaseRemoteConfig2.getLong("distance_time_show_same_ads"));
        constants2 = Constants.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        constants2.setDISTANCE_TIME_WAIT_MAX(firebaseRemoteConfig2.getLong("distance_time_wait_max_ads"));
        AdsManager adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_native_ads_home(firebaseRemoteConfig2.getBoolean("is_show_native_ads_home"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_native_ads_list_file(firebaseRemoteConfig2.getBoolean("is_show_native_ads_list_file"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_native_ads_search_file(firebaseRemoteConfig2.getBoolean("is_show_native_ads_search_file"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_native_ads_language(firebaseRemoteConfig2.getBoolean("is_show_native_ads_language"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_native_save_file(firebaseRemoteConfig2.getBoolean("is_show_native_save_file"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_native_ads_image_convert(firebaseRemoteConfig2.getBoolean("is_show_native_ads_image_convert"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_native_ads_image_list(firebaseRemoteConfig2.getBoolean("is_show_native_ads_image_list"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_banner_ads_list_file(firebaseRemoteConfig2.getBoolean("is_show_banner_ads_list_file"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_banner_ads_pdf_viewer(firebaseRemoteConfig2.getBoolean("is_show_banner_ads_pdf_viewer"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_banner_ads_office_viewer(firebaseRemoteConfig2.getBoolean("is_show_banner_ads_office_viewer"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_banner_ads_image_viewer(firebaseRemoteConfig2.getBoolean("is_show_banner_ads_image_viewer"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_inter_ads_splash(firebaseRemoteConfig2.getBoolean("is_show_inter_ads_splash"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_inter_ads_home(firebaseRemoteConfig2.getBoolean("is_show_inter_ads_home"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_inter_ads_list_file(firebaseRemoteConfig2.getBoolean("is_show_inter_ads_list_file"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_inter_ads_search_file(firebaseRemoteConfig2.getBoolean("is_show_inter_ads_search_file"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_open_ads_resume_home(firebaseRemoteConfig2.getBoolean("is_show_open_ads_resume_home"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_open_ads_resume_list_file(firebaseRemoteConfig2.getBoolean("is_show_open_ads_resume_list_file"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_open_ads_resume_search_file(firebaseRemoteConfig2.getBoolean("is_show_open_ads_resume_search_file"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_open_ads_resume_pdf_viewer(firebaseRemoteConfig2.getBoolean("is_show_open_ads_resume_pdf_viewer"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_open_ads_resume_office_viewer(firebaseRemoteConfig2.getBoolean("is_show_open_ads_resume_office_viewer"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_open_ads_resume_image_viewer(firebaseRemoteConfig2.getBoolean("is_show_open_ads_resume_image_viewer"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_open_ads_resume_language_viewer(firebaseRemoteConfig2.getBoolean("is_show_open_ads_resume_language_viewer"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_open_ads_resume_screenshot_editor(firebaseRemoteConfig2.getBoolean("is_show_open_ads_resume_screenshot_editor"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_open_ads_any_position(firebaseRemoteConfig2.getBoolean("is_show_open_ads_any_position"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        adsManager.set_show_button_convert(firebaseRemoteConfig2.getBoolean("is_show_button_convert"));
        adsManager = AdsManager.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        firebaseRemoteConfig2 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig2 = null;
        }
        String str = firebaseRemoteConfig2.getString("email_feedback");
        Intrinsics.checkNotNullExpressionValue(str, "mFirebaseRemoteConfig.getString(\"email_feedback\")");
        adsManager.setEmail_feedback(str);
        Constants constants1 = Constants.INSTANCE;
        firebaseRemoteConfig4 = this.mFirebaseRemoteConfig;
        FirebaseRemoteConfig firebaseRemoteConfig1 = firebaseRemoteConfig4;
        if (firebaseRemoteConfig4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirebaseRemoteConfig");
            firebaseRemoteConfig1 = null;
        }
        constants1.setMAX_TIME_AT_SPLASH(firebaseRemoteConfig1.getLong("max_time_at_splash"));
        if (5151L >= Constants.INSTANCE.getMINIMUM_VERSION_CODE()) {
            if (5151L < Constants.INSTANCE.getCURERENT_VERSION_CODE()) {
                TextView textView = null;
                Dialog dialog2 = this.mWarningDialog;
                firebaseRemoteConfig1 = firebaseRemoteConfig3;
                if (dialog2 != null)
                    textView = (TextView)dialog2.findViewById(R.id.btnDoLater);
                if (textView != null)
                    textView.setVisibility(View.VISIBLE);
                Dialog dialog1 = this.mWarningDialog;
                if (dialog1 != null)
                    dialog1.show();
                this.isCloseApp = false;
            } else {
                showAds();
            }
        } else {
            Dialog dialog = this.mWarningDialog;
            if (dialog != null)
                dialog.show();
        }
    }

    private final void openFileFromAnotherApp() {
        if (getIntent() != null) {
            String str2 = getIntent().getAction();
            String str1 = getIntent().getType();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append("  ");
            stringBuilder.append(str1);
            Log.d("5555555555", stringBuilder.toString());
            if (Intrinsics.areEqual("android.intent.action.VIEW", str2) && str1 != null) {
                Uri uri = getIntent().getData();
                if (uri != null) {
                    Context context = (Context)this;
                    String str = PathUtil.getPath(context, uri);
                    if (!Intrinsics.areEqual(str, "") && str != null) {
                        Intent intent = null;
                        Utils.INSTANCE.setFileRecent(str, context);
                        if (StringsKt.endsWith(str, ".pdf", false, 2, null)) {
                            Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_pdf_viewer", "event_click_goto_page_pdf_viewer");
                            // TODO FIX
                            //intent = new Intent(context, PdfViewerActivity.class);
                            this.mIntent = intent;
                            intent.putExtra("url", str);
                        } else {
                            if (StringsKt.endsWith(str, ".doc", false, 2, null) || StringsKt.endsWith(str, ".docx", false, 2, null)) {
                                Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_doc_viewer", "event_click_goto_page_doc_viewer");
                                // TODO FIX
                                //intent = new Intent(context, WordViewerActivity.class);
                                this.mIntent = intent;
                                intent.putExtra("url", str);
                                return;
                            }
                            if (StringsKt.endsWith(str, ".xlsx", false, 2, null) || StringsKt.endsWith(str, ".xls", false, 2, null)) {
                                Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_xlsx_viewer", "event_click_goto_page_xlsx_viewer");
                                // TODO FIX
                                //intent = new Intent(context, ExelViewerActivity.class);
                                this.mIntent = intent;
                                intent.putExtra("url", str);
                                return;
                            }
                            if (StringsKt.endsWith(str, ".pptx", false, 2, null) || StringsKt.endsWith(str, ".ppt", false, 2, null)) {
                                Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_pptx_viewer", "event_click_goto_page_pptx_viewer");
                                // TODO FIX
                                //intent = new Intent(context, PowerPointViewerActivity.class);
                                this.mIntent = intent;
                                intent.putExtra("url", str);
                                return;
                            }
                            if (StringsKt.endsWith(str, ".png", false, 2, null) || StringsKt.endsWith(str, ".jpg", false, 2, null)) {
                                Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_png_viewer", "event_click_goto_page_png_viewer");
                                // TODO FIX
                                //intent = new Intent(context, ImageViewerActivity.class);
                                this.mIntent = intent;
                                intent.putExtra("url", str);
                                return;
                            }
                            if (StringsKt.endsWith(str, ".txt", false, 2, null)) {
                                Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_txt_viewer", "event_click_goto_page_txt_viewer");
                                // TODO FIX
                                //intent = new Intent(context, TextViewerActivity.class);
                                this.mIntent = intent;
                                intent.putExtra("url", str);
                            }
                        }
                    }
                }
            }
        }
    }

    private final void showAds() {
        if (!BillingClientSetup.isUpgraded((Context)this) && AdsManager.INSTANCE.is_show_inter_ads_splash()) {
            AdsManager.INSTANCE.showAds((Activity)this, this);
        } else {
            gotoNextScreen();
        }
    }

    public void clearFindViewByIdCache() {
        this.findViewCache.clear();
    }

    public View findCachedViewById(int paramInt) {
        Map<Integer, View> map = this.findViewCache;
        View view2 = map.get(paramInt);
        View view1 = view2;
        if (view2 == null) {
            view1 = findViewById(paramInt);
            if (view1 != null) {
                map.put(paramInt, view1);
            } else {
                view1 = null;
            }
        }
        return view1;
    }

    public void onAdDismissed() {
        gotoNextScreen();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Utils utils1 = Utils.INSTANCE;
        Context context1 = (Context)this;
        utils1.setLanguageForApp(context1);
        setContentView(R.layout.activity_splash);
        Utils utils3 = Utils.INSTANCE;
        Utils utils2 = Utils.INSTANCE;
        Context context2 = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(context2, "applicationContext");
        utils3.setNumberBackApp(context1, utils2.getNumberBackApp(context2) + 1);
        utils2 = Utils.INSTANCE;
        context2 = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(context2, "applicationContext");
        int i = utils2.getTheme(context2);
        Theme = i;
        if (i == 1) {
            ((ConstraintLayout)findCachedViewById(R.id.layoutMain)).setBackgroundColor(ContextCompat.getColor(context1, R.color.colorBlackAltLight));
            ((TextView)findCachedViewById(R.id.txtTitle)).setTextColor(-1);
        }
        initData();
        initView();
        handleEvent();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mWarningDialog = null;
    }

    protected void onPause() {
        try {
            super.onPause();
            // TODO FIX
            //IronSource.onPause((Activity)this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void onResume() {
        try {
            super.onResume();
            // TODO FIX
            //IronSource.onResume((Activity)this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onShowFail() {
        gotoNextScreen();
    }

    public void onShowSuccess() {}

    public static final class Companion {
        private Companion() {}

        public final int getTheme() {
            return SplashActivity.Theme;
        }

        public final void setTheme(int param1Int) {
            SplashActivity.Theme = param1Int;
        }
    }
}