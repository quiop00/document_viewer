package com.ryu.document_reader.activity.permission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.text.HtmlCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.home.HomeActivity2;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.ads.callback.AdsListener;
import com.ryu.document_reader.extension.ContextKt;
import com.ryu.document_reader.util.Utils;

import java.util.Arrays;
import java.util.Map;

public final class PermissionActivity extends AppCompatActivity implements AdsListener {
    private final int CODE_MANAGE_FILE = 100;

    private final int CODE_READ_STORAGE = 101;

    public Map<Integer, View> findViewCache;

    private final Runnable checkOverlaySetting = new PermissionActivity$checkOverlaySetting$1();

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private final void gotoNextScreen() {
        startActivity(new Intent((Context)this, HomeActivity2.class));
        finish();
    }

    private final void handleEvents() {
//        TODO
        findCachedViewById(R.id.ivClose).setOnClickListener(view -> findCachedViewById(R.id.rlBottom).setVisibility(View.GONE));
        findCachedViewById(R.id.btnGo).setOnClickListener(view -> requestPermission());
        findCachedViewById(R.id.btnGotoSet).setOnClickListener(view -> requestPermission());
    }

    private final void initView() {
        ((TextView)findCachedViewById(R.id.tvFile)).setSelected(true);
        if (!ContextKt.isPermissionGranted((Context)this))
            showPermissionDialog();
    }

    private final void requestPermission() {
        if (Build.VERSION.SDK_INT >= 30) {
            this.mHandler.postDelayed(this.checkOverlaySetting, 100L);
            try {
                Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                intent.addCategory("android.intent.category.DEFAULT");
                String str = String.format("package:%s", Arrays.copyOf(new Object[] { getApplicationContext().getPackageName() }, 1));
                intent.setData(Uri.parse(str));
                startActivityForResult(intent, this.CODE_MANAGE_FILE);
            } catch (Exception exception) {
                Intent intent = new Intent();
                intent.setAction("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
                startActivityForResult(intent, this.CODE_MANAGE_FILE);
            }
        } else {
            int i = this.CODE_READ_STORAGE;
            ActivityCompat.requestPermissions(this, new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }, i);
        }
    }

    private final void showAds() {
        if (AdsManager.INSTANCE.is_show_inter_ads_home()) {
            findViewById(R.id.layoutAdsLoading).setVisibility(View.VISIBLE);
            Log.d("6666666666", "showAds");
            AdsManager.INSTANCE.showAds((Activity)this, this);
        } else {
            gotoNextScreen();
        }
    }

    private final void showPermissionDialog() {
        this.mHandler.removeCallbacks(this.checkOverlaySetting);
        Dialog dialog = ContextKt.onCreateBottomSheetDialog((Context)this, R.layout.layout_dialog_permission, false);
        String str = getString(R.string.txt_content_permission);
        str = String.format(str, Arrays.copyOf(new Object[] { getString(R.string.app_name) }, 1));
        ((TextView)dialog.findViewById(R.id.tvContentPermission)).setText((CharSequence) HtmlCompat.fromHtml(str, 0));
        dialog.findViewById(R.id.btnAllow).setOnClickListener(view -> {
            dialog.dismiss();
            requestPermission();
        });
        dialog.findViewById(R.id.btnLater).setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public void clearFindViewByIdCache() {
        this.findViewCache.clear();
    }

    public View findCachedViewById(int paramInt) {
        Map<Integer, View> map = this.findViewCache;
        View view2 = map.get(Integer.valueOf(paramInt));
        View view1 = view2;
        if (view2 == null) {
            view1 = findViewById(paramInt);
            if (view1 != null) {
                map.put(Integer.valueOf(paramInt), view1);
            } else {
                view1 = null;
            }
        }
        return view1;
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if (paramInt1 == this.CODE_MANAGE_FILE && Build.VERSION.SDK_INT >= 30)
            if (Environment.isExternalStorageManager()) {
                showAds();
            } else {
                showPermissionDialog();
            }
    }

    public void onAdDismissed() {
        findViewById(R.id.layoutAdsLoading).setVisibility(View.GONE);
        gotoNextScreen();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Utils utils = Utils.INSTANCE;
        Context context = (Context)this;
        utils.setLanguageForApp(context);
        setContentView(R.layout.activity_permission);
        utils = Utils.INSTANCE;
        AdView adView = (AdView)findCachedViewById(R.id.adView);
        utils.loadBannerAds(context, adView, false);
        initView();
        handleEvents();
    }

    public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
        super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
        if (paramInt == this.CODE_READ_STORAGE) {
            if (paramArrayOfint.length == 0) {
                paramInt = 1;
            } else {
                paramInt = 0;
            }
            if ((paramInt ^ 0x1) != 0 && paramArrayOfint[0] == 0) {
                showAds();
            } else {
                showPermissionDialog();
            }
        }
    }

    public void onShowFail() {
        findViewById(R.id.layoutAdsLoading).setVisibility(View.GONE);
        gotoNextScreen();
    }

    public void onShowSuccess() {
        findViewById(R.id.layoutAdsLoading).setVisibility(View.GONE);
    }

    public final class PermissionActivity$checkOverlaySetting$1 implements Runnable {
        public void run() {
            if (Build.VERSION.SDK_INT < 23)
                return;
            if (ContextKt.isPermissionGranted((Context)PermissionActivity.this)) {
                PermissionActivity.this.showAds();
                return;
            }
            PermissionActivity.this.mHandler.postDelayed(this, 100L);
        }
    }
}