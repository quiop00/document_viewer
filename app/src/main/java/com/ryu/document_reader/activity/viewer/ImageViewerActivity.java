package com.ryu.document_reader.activity.viewer;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class ImageViewerActivity extends BaseActivity {
    private boolean mIsFavorite;
    private boolean mIsShowMenu;
    private String mUrlImage;
    private PdfDocument pdfDocument;

    public Map<Integer, View> findViewCache = new LinkedHashMap<>();
    private String outputPDF = "";

    public void clearFindViewByIdCache() {
        this.findViewCache.clear();
    }

    public View findCachedViewById(int i) {
        Map<Integer, View> map = this.findViewCache;
        View view = map.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            if (findViewById != null) {
                map.put(Integer.valueOf(i), findViewById);
                return findViewById;
            }
            return null;
        }
        return view;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image_viewer);
        Context context = this;
        Utils.INSTANCE.setStatusBarColor(this, ContextCompat.getColor(context, R.color.color_screenshot));
        Utils utils = Utils.INSTANCE;
        AdView adView = (AdView) findCachedViewById(R.id.adView);
        utils.loadBannerAds(context, adView, AdsManager.INSTANCE.is_show_banner_ads_image_viewer());
        initData();
        initThemeView();
        handleEvents();
    }

    private void initThemeView() {
        if (SplashActivity.Companion.getTheme() == 1) {
            findCachedViewById(R.id.layoutPdf).setBackgroundColor(ContextCompat.getColor((Context) this, R.color.colorBlackAltLight));
        }
    }

    private void initData() {
        String str = null;
        File externalFilesDir = getExternalFilesDir(null);
        if (externalFilesDir != null) {
            externalFilesDir.delete();
        }
        StringBuilder sb = new StringBuilder();
        File externalFilesDir2 = getExternalFilesDir(null);
        sb.append(externalFilesDir2 != null ? externalFilesDir2.getAbsolutePath() : null);
        sb.append("temp.pdf");
        this.outputPDF = sb.toString();
        String stringExtra = getIntent().getStringExtra("url");
        this.mUrlImage = stringExtra;
        String str2 = this.mUrlImage;
        if (str2 == null) {
            str2 = null;
        }
        ((TextView) findCachedViewById(R.id.txtTitle)).setText(new File(str2).getName());
        try {
            ContentResolver contentResolver = getContentResolver();
            String str3 = this.mUrlImage;
            if (str3 == null) {
                str3 = null;
            }
            makePDF(MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(new File(str3))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils utils = Utils.INSTANCE;
        String str4 = this.mUrlImage;
        if (str4 == null) {
        } else {
            str = str4;
        }
        boolean isFileFavorite = utils.isFileFavorite(str, this);
        this.mIsFavorite = isFileFavorite;
        if (isFileFavorite) {
            ((ImageView) findCachedViewById(R.id.btnFavourite)).setImageResource(R.drawable.ic_favourite_active);
        } else {
            ((ImageView) findCachedViewById(R.id.btnFavourite)).setImageResource(R.drawable.ic_favourite_inactive);
        }
    }

    private void loadAds() {
        AdView adView = findViewById(R.id.adView);
        AdRequest build = new AdRequest.Builder().build();
        adView.loadAd(build);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                adView.setVisibility(View.GONE);
            }
        });
    }

    public void onResume() {
        super.onResume();
//        IronSource.onResume((Activity) this);
    }

    public void onPause() {
        super.onPause();
//        IronSource.onPause((Activity) this);
    }

    private void handleEvents() {
        findCachedViewById(R.id.btnBack).setOnClickListener(view -> onBack());
        findCachedViewById(R.id.btnGotoPage).setOnClickListener(view -> onClickGotoPage());
        findCachedViewById(R.id.btnShare).setOnClickListener(view -> onClickBtnShare());
        findCachedViewById(R.id.btnSnapScreen).setOnClickListener(view -> onClickBtnSnapshot());
        findCachedViewById(R.id.btnFavourite).setOnClickListener(view -> onClickBtnFavourite());
    }

    public void onBack() {
        if (getIntent().getBooleanExtra("FROM_SAVE_FILE", false)) {
            Intent intent = new Intent((Context) this, HomeActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }
        onBackPressed();
    }

    public void onClickGotoPage() {
        Dialog dialog = new Dialog((Context) this);
        dialog.setContentView(R.layout.layout_dialog_gotopage);
        Window window = dialog.getWindow();
        window.setLayout(-1, -2);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.btnOke).setOnClickListener(view -> onClickBtnOke(dialog));
        dialog.findViewById(R.id.btnCancel).setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    public void onClickBtnOke(Dialog dialog) {
        String obj = StringsKt.trim(((EditText) dialog.findViewById(R.id.edtPageNumber)).getText().toString()).toString();
        if (Intrinsics.areEqual(obj, "")) {
            Toast.makeText(this, getString(R.string.enter_page_number), Toast.LENGTH_LONG).show();
            return;
        }
        dialog.dismiss();
        PDFView _$_findCachedViewById = (PDFView)findCachedViewById(R.id.pdfView);
        String str = mUrlImage;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mUrlImage");
            str = null;
        }
        _$_findCachedViewById.fromFile(new File(str)).enableSwipe(true).defaultPage(Integer.parseInt(obj) - 1).enableAnnotationRendering(true).scrollHandle(new DefaultScrollHandle(this)).load();
    }

    public void onClickBtnShare() {
        Context context = this;
        String str = mUrlImage;
        if (str == null) {
            str = null;
        }
        startActivity(Intent.createChooser(Utils.INSTANCE.fileShareIntent(getString(R.string.fab_share), FileProvider.getUriForFile(context, "com.ryu.document_reader.provider", new File(str))), "share.."));
    }

    public  void onClickBtnSnapshot() {
        Context context = this;
        Utils.INSTANCE.setTrackEvent(context, "event_click_snap_screen", "event_click_snap_screen");
        Utils utils = Utils.INSTANCE;
        View view2 = findCachedViewById(R.id.layoutPdf);
        Intrinsics.checkNotNullExpressionValue(view2, "layoutPdf");
        Bitmap takeScreenshot = utils.takeScreenshot(view2);
        Utils utils2 = Utils.INSTANCE;
        Intrinsics.checkNotNull(takeScreenshot);
        String saveBitmap = utils2.saveBitmap(takeScreenshot, context);
        Intent intent = new Intent(context, EditorActivity.class);
        intent.putExtra("path", saveBitmap);
        startActivity(intent);
    }

    public void onClickBtnFavourite() {
        String str = null;
        if (mIsFavorite) {
            mIsFavorite = false;
            ((ImageView) findCachedViewById(R.id.btnFavourite)).setImageResource(R.drawable.ic_favourite_inactive);
            ArrayList fileFavorites = HomeActivity2.Companion.getFileFavorites();
            String str2 = mUrlImage;
            if (str2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mUrlImage");
                str2 = null;
            }
            fileFavorites.remove(new File(str2));
        } else {
            mIsFavorite = true;
            ((ImageView) findCachedViewById(R.id.btnFavourite)).setImageResource(R.drawable.ic_favourite_active);
            ArrayList fileFavorites2 = HomeActivity2.Companion.getFileFavorites();
            String str3 = mUrlImage;
            if (str3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mUrlImage");
                str3 = null;
            }
            fileFavorites2.add(new File(str3));
        }
        Utils utils = Utils.INSTANCE;
        String str4 = mUrlImage;
        if (str4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mUrlImage");
        } else {
            str = str4;
        }
        utils.setFileFavorite(str, this, mIsFavorite);
    }

    public void makePDF(Bitmap bitmap) {
        if (bitmap != null) {
            this.pdfDocument = new PdfDocument();
            PdfDocument.PageInfo create = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
            PdfDocument pdfDocument = this.pdfDocument;
            Intrinsics.checkNotNull(pdfDocument);
            PdfDocument.Page startPage = pdfDocument.startPage(create);
            Intrinsics.checkNotNullExpressionValue(startPage, "pdfDocument!!.startPage(pageInfo)");
            Canvas canvas = startPage.getCanvas();
            new Paint().setColor(Color.parseColor("#FFFFFF"));
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            PdfDocument pdfDocument2 = this.pdfDocument;
            Intrinsics.checkNotNull(pdfDocument2);
            pdfDocument2.finishPage(startPage);
            saveFile();
            return;
        }
        Toast.makeText(getApplicationContext(), "File Load Error!", Toast.LENGTH_LONG).show();
    }

    public void saveFile() {
        if (this.pdfDocument == null) {
            Log.i("local-dev", "pdfDocument in 'saveFile' function is null");
            return;
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ImgToPDF");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.outputPDF);
            PdfDocument pdfDocument = this.pdfDocument;
            Intrinsics.checkNotNull(pdfDocument);
            pdfDocument.writeTo(fileOutputStream);
            PdfDocument pdfDocument2 = this.pdfDocument;
            Intrinsics.checkNotNull(pdfDocument2);
            pdfDocument2.close();
            viewPDFFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewPDFFile() {
        ((PDFView)findCachedViewById(R.id.pdfView)).fromFile(new File(this.outputPDF)).load();
    }
}