package com.ryu.document_reader.activity.savefile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.activity.home.HomeActivity2;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.ads.TemplateView;
import com.ryu.document_reader.ads.callback.AdsListener;
import com.ryu.document_reader.util.Utils;

import java.io.File;
import com.github.barteksc.pdfviewer.PDFView;

public class SaveFileActivity extends BaseActivity implements AdsListener {
    ImageView btnGoHome;

    ImageView btnShare;

    RelativeLayout layoutAdsLoading;

    private Intent mIntent;

    String mPathFile;

    PDFView pdfView;

    RelativeLayout relativeLayouts;

    TextView tvFileName;

    private void gotoNextScreen() {
        this.layoutAdsLoading.setVisibility(View.GONE);
        startActivity(this.mIntent);
        finish();
    }

    private void handleEvent() {
        findViewById(R.id.btnShare).setOnClickListener(view -> shareFile());
        findViewById(R.id.btnBack).setOnClickListener(view -> back());
        findViewById(R.id.btnOpenFile).setOnClickListener(view -> openFile());
    }

    private void initView() {
        this.pdfView = (PDFView)findViewById(R.id.pdfView);
        this.relativeLayouts = (RelativeLayout)findViewById(R.id.relativeLayouts);
        this.layoutAdsLoading = (RelativeLayout)findViewById(R.id.layoutAdsLoading);
        this.mPathFile = getIntent().getStringExtra("path");
        HomeActivity2.Companion.getFileAll().add(0, new File(this.mPathFile));
        TextView textView = (TextView)findViewById(R.id.txtTitle);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getIntent().getStringExtra("name"));
        stringBuilder.append(".pdf");
        textView.setText(stringBuilder.toString());
        this.pdfView.fromFile(new File(this.mPathFile)).pages(new int[] { 0 }).load();
        TemplateView templateView = (TemplateView)findViewById(R.id.my_template);
        Utils.INSTANCE.loadNativeAds((Context)this, templateView, getString(R.string.alldoc_native_save_file_id), AdsManager.INSTANCE.is_show_native_save_file());
    }

    private void showAds() {
        if (BillingClientSetup.isUpgraded((Context)this) || !AdsManager.INSTANCE.is_show_native_save_file()) {
            gotoNextScreen();
            return;
        }
        this.layoutAdsLoading.setVisibility(View.VISIBLE);
        AdsManager.INSTANCE.showAds((Activity)this, this);
    }

    private void shareFile() {
        Utils.INSTANCE.shareFile(getApplicationContext(), this.mPathFile);
    }

    private void back() {
        this.mIntent = null;
        Intent intent = new Intent((Context) this, (Class<?>) HomeActivity2.class);
        this.mIntent = intent;
        //intent.setFlags(32768);
        showAds();
    }

    private void openFile() {
        Utils.INSTANCE.setFileRecent(this.mPathFile, this);
        Utils.INSTANCE.setTrackEvent(this, "event_click_open_file_from_convert_pdf_viewer", "event_click_open_file_from_convert_pdf_viewer");
        this.mIntent = null;
        // TODO
//        Intent intent = new Intent((Context) this, (Class<?>) PdfViewerActivity.class);
//        this.mIntent = intent;
//        intent.putExtra("url", this.mPathFile);
//        this.mIntent.putExtra("FROM_SAVE_FILE", true);
        showAds();
    }

    public void onAdDismissed() {
        gotoNextScreen();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_save_file);
        initView();
        handleEvent();
    }

    public void onPointerCaptureChanged(boolean paramBoolean) {
        super.onPointerCaptureChanged(paramBoolean);
    }

    public void onShowFail() {
        gotoNextScreen();
    }

    public void onShowSuccess() {
        this.layoutAdsLoading.setVisibility(View.GONE);
    }
}