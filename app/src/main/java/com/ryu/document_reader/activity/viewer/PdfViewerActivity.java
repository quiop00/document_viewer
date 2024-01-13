package com.ryu.document_reader.activity.viewer;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.activity.SplashActivity;
import com.ryu.document_reader.activity.editor.EditorActivity;
import com.ryu.document_reader.activity.home.HomeActivity2;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.util.Utils;

import java.io.File;
import java.util.ArrayList;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class PdfViewerActivity extends BaseActivity {

    private boolean mIsFavorite;

    private boolean mIsVisible;

    private float mLastPositionOffset;

    private String mUrlFilePdf;

    private void handleEvents() {
        findCachedViewById(R.id.btnBack).setOnClickListener(view -> onClickBtnBack());
        findCachedViewById(R.id.btnNightMode).setOnClickListener(view -> onClickNightMode());
        findCachedViewById(R.id.btnGotoPage).setOnClickListener(view -> onClickGotoPage());
        findCachedViewById(R.id.btnShare).setOnClickListener(view -> onClickShare());
        findCachedViewById(R.id.btnSnapScreen).setOnClickListener(view -> onSnapScreen());
        findCachedViewById(R.id.btnFavourite).setOnClickListener(view -> onClickFavourite());
    }

    private void onClickBtnBack() {
        if (getIntent().getBooleanExtra("FROM_SAVE_FILE", false)) {
            Intent intent = new Intent(this, HomeActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            onBackPressed();
        }
    }

    private void onClickNightMode() {
        Utils utils = Utils.INSTANCE;
        Context context = this;
        utils.setTrackEvent(context, "event_click_night_mode_button_pdf_viewer", "event_click_night_mode_button_pdf_viewer");
        if (Utils.INSTANCE.isModeNight(context)) {
            Utils.INSTANCE.setModeNight(context, false);
            ((ImageView)findCachedViewById(R.id.btnNightMode)).setImageResource(R.drawable.ic_night_on);
        } else {
            Utils.INSTANCE.setModeNight(context, true);
            ((ImageView)findCachedViewById(R.id.btnNightMode)).setImageResource(R.drawable.ic_night_off);
        }
        ((PDFView)findCachedViewById(R.id.pdfView)).setNightMode(Utils.INSTANCE.isModeNight(context));
        ((PDFView)findCachedViewById(R.id.pdfView)).loadPages();
    }

    private void onClickGotoPage() {
        Utils utils = Utils.INSTANCE;
        Context context = this;
        utils.setTrackEvent(context, "event_click_goto_page_button_pdf_viewer", "event_click_goto_page_button_pdf_viewer");
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_gotopage);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(-1, -2);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.btnOke).setOnClickListener(view -> onOK(dialog));
        dialog.findViewById(R.id.btnCancel).setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private void onOK(Dialog paramDialog) {
        String str = StringsKt.trim(((EditText)paramDialog.findViewById(R.id.edtPageNumber)).getText().toString()).toString();
        if (Intrinsics.areEqual(str, "")) {
            Toast.makeText(this, getString(R.string.enter_page_number), Toast.LENGTH_LONG).show();
        } else {
            paramDialog.dismiss();
            try {
                ((PDFView)findCachedViewById(R.id.pdfView)).recycle();
                PDFView pDFView = (PDFView)findCachedViewById(R.id.pdfView);
                File file = new File(mUrlFilePdf);
                PDFView.Configurator configurator = pDFView.fromFile(file).enableSwipe(true).defaultPage(Integer.parseInt(str) - 1).enableAnnotationRendering(true);
                DefaultScrollHandle defaultScrollHandle = new DefaultScrollHandle(this);
                configurator.scrollHandle(defaultScrollHandle).load();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void onClickShare() {
        Context context = this;
        String str2 = mUrlFilePdf;
        String str1 = str2;
        if (str2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mUrlFilePdf");
            str1 = null;
        }
        Uri uri = FileProvider.getUriForFile(context, "com.ryu.document_reader.provider", new File(str1));
        startActivity(Intent.createChooser(Utils.INSTANCE.fileShareIntent(getString(R.string.fab_share), uri), "share.."));
    }

    private void onSnapScreen() {
        Utils utils1 = Utils.INSTANCE;
        ConstraintLayout constraintLayout = (ConstraintLayout)findCachedViewById(R.id.layoutPdf);
        Bitmap bitmap = utils1.takeScreenshot(constraintLayout);
        Utils utils2 = Utils.INSTANCE;
        Context context = this;
        String str = utils2.saveBitmap(bitmap, context);
        Intent intent = new Intent(context, EditorActivity.class);
        intent.putExtra("path", str);
        startActivity(intent);
    }

    private void onClickFavourite() {
        boolean bool = mIsFavorite;
        String str2 = null;
        if (bool) {
            mIsFavorite = false;
            ((ImageView)findCachedViewById(R.id.btnFavourite)).setImageResource(R.drawable.ic_favourite_inactive);
            ArrayList arrayList = HomeActivity2.Companion.getFileFavorites();
            String str4 = mUrlFilePdf;
            String str3 = str4;
            if (str4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mUrlFilePdf");
                str3 = null;
            }
            arrayList.remove(new File(str3));
        } else {
            mIsFavorite = true;
            ((ImageView)findCachedViewById(R.id.btnFavourite)).setImageResource(R.drawable.ic_favourite_active);
            ArrayList<File> arrayList = HomeActivity2.Companion.getFileFavorites();
            String str4 = mUrlFilePdf;
            String str3 = str4;
            if (str4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mUrlFilePdf");
                str3 = null;
            }
            arrayList.add(new File(str3));
        }
        Utils utils = Utils.INSTANCE;
        String str1 = mUrlFilePdf;
        if (str1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mUrlFilePdf");
            str1 = str2;
        }
        utils.setFileFavorite(str1, this, mIsFavorite);
    }

    private void initData() {
        String str2 = getIntent().getStringExtra("url");
        Intrinsics.checkNotNull(str2);
        this.mUrlFilePdf = str2;
        String str4 = this.mUrlFilePdf;
        String str3 = null;
        str2 = str4;
        if (str4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mUrlFilePdf");
            str2 = null;
        }
        File file = new File(str2);
        ((TextView)findCachedViewById(R.id.txtNameFile)).setText(file.getName());
        Utils utils1 = Utils.INSTANCE;
        Context context = this;
        boolean bool2 = utils1.isModeNight(context);
        PDFView pDFView = (PDFView)findCachedViewById(R.id.pdfView);
        str4 = this.mUrlFilePdf;
        String str1 = str4;
        if (str4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mUrlFilePdf");
            str1 = null;
        }
        pDFView.fromFile(new File(str1)).enableSwipe(true).defaultPage(0).enableAnnotationRendering(true).scrollHandle((ScrollHandle)new DefaultScrollHandle(context)).nightMode(bool2).load();
        Utils utils2 = Utils.INSTANCE;
        str1 = this.mUrlFilePdf;
        if (str1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mUrlFilePdf");
            str1 = str3;
        }
        boolean bool1 = utils2.isFileFavorite(str1, context);
        this.mIsFavorite = bool1;
        if (bool1) {
            ((ImageView)findCachedViewById(R.id.btnFavourite)).setImageResource(R.drawable.ic_favourite_active);
        } else {
            ((ImageView)findCachedViewById(R.id.btnFavourite)).setImageResource(R.drawable.ic_favourite_inactive);
        }
        if (bool2) {
            ((ImageView)findCachedViewById(R.id.btnNightMode)).setImageResource(R.drawable.ic_night_off);
        } else {
            ((ImageView)findCachedViewById(R.id.btnNightMode)).setImageResource(R.drawable.ic_night_on);
        }
    }

    private void initThemeView() {
        if (SplashActivity.Companion.getTheme() == 1)
            findCachedViewById(R.id.layoutPdf).setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlackAltLight));
    }

    private void loadAds() {
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = (new AdRequest.Builder()).build();
        Intrinsics.checkNotNullExpressionValue(adRequest, "Builder().build()");
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                adView.setVisibility(View.GONE);
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_pdf_viewer);
        Utils utils = Utils.INSTANCE;
        Context context = this;
        AdView adView = (AdView)findCachedViewById(R.id.adView);
        Intrinsics.checkNotNullExpressionValue(adView, "adView");
        utils.loadBannerAds(context, adView, AdsManager.INSTANCE.is_show_banner_ads_pdf_viewer());
        initData();
        initThemeView();
        handleEvents();
    }

    public void onPause() {
        super.onPause();
        //IronSource.onPause((Activity)this);
    }

    public void onResume() {
        super.onResume();
        //IronSource.onResume((Activity)this);
    }

}