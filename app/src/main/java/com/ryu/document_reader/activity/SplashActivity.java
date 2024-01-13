package com.ryu.document_reader.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
//import com.ironsource.mediationsdk.IronSource;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.activity.viewer.ExcelViewerActivity;
import com.ryu.document_reader.activity.viewer.ImageViewerActivity;
import com.ryu.document_reader.activity.viewer.PdfViewerActivity;
import com.ryu.document_reader.activity.viewer.PowerPointViewerActivity;
import com.ryu.document_reader.activity.viewer.TextViewerActivity;
import com.ryu.document_reader.activity.viewer.WordViewerActivity;
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

    private void handleEvent() {
        Dialog dialog = this.mWarningDialog;
        TextView txtGotoStore = dialog.findViewById(R.id.btnGotoStore);
        if (txtGotoStore != null)
            txtGotoStore.setOnClickListener(view -> onClickBtnGotoStore());
        ImageView imgBack = dialog.findViewById(R.id.btnBack);
        if (imgBack != null)
            imgBack.setOnClickListener(view -> onClickBack());
        TextView txtDoLater = dialog.findViewById(R.id.btnDoLater);
        if (txtDoLater != null)
            txtDoLater.setOnClickListener(view -> doLater(dialog));
    }

    private void onClickBtnGotoStore() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://play.google.com/store/apps/details?id=");
            stringBuilder.append(getPackageName());
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
            startActivity(intent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void onClickBack() {
        if (isCloseApp) {
            finish();
        } else {
            showAds();
        }
    }

    private void doLater(Dialog dialog) {
        dialog.dismiss();
        showAds();
    }

    private final void initData() {
        openFileFromAnotherApp();

        // Initialize Firebase Remote Config
        initFirebaseRemoteConfig();

        // Initialize Mobile Ads
        initMobileAds();
    }

    private void initFirebaseRemoteConfig() {
        this.mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        this.mFirebaseRemoteConfig.setConfigSettingsAsync(
                new FirebaseRemoteConfigSettings.Builder()
                        .setMinimumFetchIntervalInSeconds(10L)
                        .build()
        );
        this.mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        this.mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, task -> loadDataRemoteConfig());
    }

    private void initMobileAds() {
        Context context = this;

        if (!BillingClientSetup.isUpgraded(context)) {
            MobileAds.initialize(context, this::addListenerMobile);
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(this::gotoNextScreen, 2000L);
        }
    }

    private void addListenerMobile(InitializationStatus initializationStatus) {
        Intrinsics.checkNotNullParameter(initializationStatus, "it");
        AppOpenManager appOpenManager = MyApplication.Companion.getAppOpenManager();
        if (appOpenManager != null)
            appOpenManager.fetchAd();
        AdsManager adsManager = AdsManager.INSTANCE;
        Context context = getApplicationContext();
        adsManager.loadAds(context);
    }

    private void initView() {
        Context context = this;
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

    private void loadDataRemoteConfig() {
        Constants constants = Constants.INSTANCE;

        constants.setCURERENT_VERSION_CODE(getRemoteConfigLong("current_version"));
        constants.setMINIMUM_VERSION_CODE(getRemoteConfigLong("minimum_version"));
        constants.setDISTANCE_TIME_ADS_OTHER(getRemoteConfigLong("distance_time_show_other_ads"));
        constants.setDISTANCE_TIME_ADS_SAME(getRemoteConfigLong("distance_time_show_same_ads"));
        constants.setDISTANCE_TIME_WAIT_MAX(getRemoteConfigLong("distance_time_wait_max_ads"));

        AdsManager adsManager = AdsManager.INSTANCE;
        adsManager.set_show_native_ads_home(getRemoteConfigBoolean("is_show_native_ads_home"));
        adsManager.set_show_native_ads_list_file(getRemoteConfigBoolean("is_show_native_ads_list_file"));
        adsManager.set_show_native_ads_search_file(getRemoteConfigBoolean("is_show_native_ads_search_file"));
        adsManager.set_show_native_ads_language(getRemoteConfigBoolean("is_show_native_ads_language"));
        adsManager.set_show_native_save_file(getRemoteConfigBoolean("is_show_native_save_file"));
        adsManager.set_show_native_ads_image_convert(getRemoteConfigBoolean("is_show_native_ads_image_convert"));
        adsManager.set_show_native_ads_image_list(getRemoteConfigBoolean("is_show_native_ads_image_list"));
        adsManager.set_show_banner_ads_list_file(getRemoteConfigBoolean("is_show_banner_ads_list_file"));
        adsManager.set_show_banner_ads_pdf_viewer(getRemoteConfigBoolean("is_show_banner_ads_pdf_viewer"));
        adsManager.set_show_banner_ads_office_viewer(getRemoteConfigBoolean("is_show_banner_ads_office_viewer"));
        adsManager.set_show_banner_ads_image_viewer(getRemoteConfigBoolean("is_show_banner_ads_image_viewer"));
        adsManager.set_show_inter_ads_splash(getRemoteConfigBoolean("is_show_inter_ads_splash"));
        adsManager.set_show_inter_ads_home(getRemoteConfigBoolean("is_show_inter_ads_home"));
        adsManager.set_show_inter_ads_list_file(getRemoteConfigBoolean("is_show_inter_ads_list_file"));
        adsManager.set_show_inter_ads_search_file(getRemoteConfigBoolean("is_show_inter_ads_search_file"));
        adsManager.set_show_open_ads_resume_home(getRemoteConfigBoolean("is_show_open_ads_resume_home"));
        adsManager.set_show_open_ads_resume_list_file(getRemoteConfigBoolean("is_show_open_ads_resume_list_file"));
        adsManager.set_show_open_ads_resume_search_file(getRemoteConfigBoolean("is_show_open_ads_resume_search_file"));
        adsManager.set_show_open_ads_resume_pdf_viewer(getRemoteConfigBoolean("is_show_open_ads_resume_pdf_viewer"));
        adsManager.set_show_open_ads_resume_office_viewer(getRemoteConfigBoolean("is_show_open_ads_resume_office_viewer"));
        adsManager.set_show_open_ads_resume_image_viewer(getRemoteConfigBoolean("is_show_open_ads_resume_image_viewer"));
        adsManager.set_show_open_ads_resume_language_viewer(getRemoteConfigBoolean("is_show_open_ads_resume_language_viewer"));
        adsManager.set_show_open_ads_resume_screenshot_editor(getRemoteConfigBoolean("is_show_open_ads_resume_screenshot_editor"));
        adsManager.set_show_open_ads_any_position(getRemoteConfigBoolean("is_show_open_ads_any_position"));
        adsManager.set_show_button_convert(getRemoteConfigBoolean("is_show_button_convert"));
        adsManager.setEmail_feedback(getRemoteConfigString("email_feedback"));

        constants.setMAX_TIME_AT_SPLASH(getRemoteConfigLong("max_time_at_splash"));

        handleVersionCheck();
    }

    private long getRemoteConfigLong(String key) {
        return this.mFirebaseRemoteConfig.getLong(key);
    }

    private boolean getRemoteConfigBoolean(String key) {
        return this.mFirebaseRemoteConfig.getBoolean(key);
    }

    private String getRemoteConfigString(String key) {
        return this.mFirebaseRemoteConfig.getString(key);
    }

    private void handleVersionCheck() {
        long currentVersionCode = Constants.INSTANCE.getCURERENT_VERSION_CODE();
        long minimumVersionCode = Constants.INSTANCE.getMINIMUM_VERSION_CODE();

        if (currentVersionCode < minimumVersionCode) {
            showVersionWarningDialog();
        } else {
            showAds();
        }
    }

    private void showVersionWarningDialog() {
        Dialog warningDialog = this.mWarningDialog;
        if (warningDialog != null) {
            TextView doLaterButton = warningDialog.findViewById(R.id.btnDoLater);
            if (doLaterButton != null) {
                doLaterButton.setVisibility(View.VISIBLE);
            }
            warningDialog.show();
            this.isCloseApp = false;
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
                            intent = new Intent(context, PdfViewerActivity.class);
                            this.mIntent = intent;
                            intent.putExtra("url", str);
                        } else {
                            if (StringsKt.endsWith(str, ".doc", false, 2, null) || StringsKt.endsWith(str, ".docx", false, 2, null)) {
                                Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_doc_viewer", "event_click_goto_page_doc_viewer");
                                intent = new Intent(context, WordViewerActivity.class);
                                this.mIntent = intent;
                                intent.putExtra("url", str);
                                return;
                            }
                            if (StringsKt.endsWith(str, ".xlsx", false, 2, null) || StringsKt.endsWith(str, ".xls", false, 2, null)) {
                                Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_xlsx_viewer", "event_click_goto_page_xlsx_viewer");
                                intent = new Intent(context, ExcelViewerActivity.class);
                                this.mIntent = intent;
                                intent.putExtra("url", str);
                                return;
                            }
                            if (StringsKt.endsWith(str, ".pptx", false, 2, null) || StringsKt.endsWith(str, ".ppt", false, 2, null)) {
                                Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_pptx_viewer", "event_click_goto_page_pptx_viewer");
                                intent = new Intent(context, PowerPointViewerActivity.class);
                                this.mIntent = intent;
                                intent.putExtra("url", str);
                                return;
                            }
                            if (StringsKt.endsWith(str, ".png", false, 2, null) || StringsKt.endsWith(str, ".jpg", false, 2, null)) {
                                Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_png_viewer", "event_click_goto_page_png_viewer");
                                intent = new Intent(context, ImageViewerActivity.class);
                                this.mIntent = intent;
                                intent.putExtra("url", str);
                                return;
                            }
                            if (StringsKt.endsWith(str, ".txt", false, 2, null)) {
                                Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_txt_viewer", "event_click_goto_page_txt_viewer");
                                intent = new Intent(context, TextViewerActivity.class);
                                this.mIntent = intent;
                                intent.putExtra("url", str);
                            }
                        }
                    }
                }
            }
        }
    }

    private void showAds() {
        if (!BillingClientSetup.isUpgraded(this) && AdsManager.INSTANCE.is_show_inter_ads_splash()) {
            AdsManager.INSTANCE.showAds(this, this);
        } else {
            gotoNextScreen();
        }
    }

    public void clearFindViewByIdCache() {
        this.findViewCache.clear();
    }

    public View findCachedViewById(int viewId) {
        View cachedView = findViewCache.get(viewId);

        if (cachedView == null) {
            cachedView = findViewById(viewId);

            if (cachedView != null) {
                findViewCache.put(viewId, cachedView);
            }
        }

        return cachedView;
    }

    public void onAdDismissed() {
        gotoNextScreen();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Utils utils = Utils.INSTANCE;
        Context context = this;
        utils.setLanguageForApp(context);
        setContentView(R.layout.activity_splash);
        utils.setNumberBackApp(context, utils.getNumberBackApp(context) + 1);
        int i = utils.getTheme(context);
        Theme = i;
        if (i == 1) {
            findCachedViewById(R.id.layoutMain).setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlackAltLight));
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