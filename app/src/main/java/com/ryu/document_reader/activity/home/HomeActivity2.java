package com.ryu.document_reader.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.activity.home.fragment.HomeFragment;
import com.ryu.document_reader.activity.home.fragment.RecentFragment;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.ads.callback.AdsListener;
import com.ryu.document_reader.extension.AppCompatActivityKt;
import com.ryu.document_reader.extension.ContextKt;
import com.ryu.document_reader.extension.DialogKt;
import com.ryu.document_reader.extension.ExtensionsKt;
import com.ryu.document_reader.util.Settings;
import com.ryu.document_reader.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.StringsKt;

public final class HomeActivity2 extends BaseActivity implements AdsListener {
    public static final Companion Companion = new Companion();

    private static ArrayList<File> fileAll;

    private static ArrayList<File> fileExcels;

    private static ArrayList<File> fileFavorites;

    private static ArrayList<File> fileImages;

    private static ArrayList<File> filePDFs = new ArrayList<File>();

    private static ArrayList<File> filePowerPoints;

    private static ArrayList<File> fileRecents;

    private static ArrayList<File> fileTexts;

    private static ArrayList<File> fileWords = new ArrayList<File>();

    private final int CODE_RELOAD_FILE = 202;

    public Map<Integer, View> _$_findViewCache;

    private HomeFragment mHomeFragment;

    private Intent mIntent;

    private boolean mIsGridMenu = true;

    private Dialog mLoadingDialog;

    private RecentFragment mRecentFragment;

    static {
        filePowerPoints = new ArrayList<File>();
        fileExcels = new ArrayList<File>();
        fileTexts = new ArrayList<File>();
        fileImages = new ArrayList<File>();
        fileAll = new ArrayList<File>();
        fileFavorites = new ArrayList<File>();
        fileRecents = new ArrayList<File>();
    }

    private final void getAllFile(File paramFile) {
        try {
            File[] arrayOfFile = paramFile.listFiles();
            if (arrayOfFile != null) {
                byte b;
                if (arrayOfFile.length == 0) {
                    b = 1;
                } else {
                    b = 0;
                }
                if ((b ^ 0x1) != 0) {
                    int i = arrayOfFile.length;
                    for (b = 0; b < i; b++) {
                        File file = arrayOfFile[b];
                        if (file.isDirectory()) {
                            Intrinsics.checkNotNullExpressionValue(file, "file");
                            getAllFile(file);
                        } else {
                            String str = file.getAbsolutePath();
                            Intrinsics.checkNotNullExpressionValue(str, "name");
                            if (StringsKt.endsWith(str, ".pdf", false, 2, null)) {
                                ArrayList<File> arrayList1 = filePDFs;
                                File file2 = new File(str);
                                arrayList1.add(file2);
                                ArrayList<File> arrayList2 = fileAll;
                                File file1 = new File(str);
                                arrayList2.add(file1);
                            } else if (StringsKt.endsWith(str, ".doc", false, 2, null) || StringsKt.endsWith$default(str, ".docx", false, 2, null)) {
                                ArrayList<File> arrayList = fileWords;
                                File file1 = new File(str);
                                arrayList.add(file1);
                                arrayList = fileAll;
                                file1 = new File(str);
                                arrayList.add(file1);
                            } else if (StringsKt.endsWith(str, ".xlsx", false, 2, null) || StringsKt.endsWith$default(str, ".xls", false, 2, null)) {
                                ArrayList<File> arrayList = fileExcels;
                                File file1 = new File(str);
                                arrayList.add(file1);
                                arrayList = fileAll;
                                file1 = new File(str);
                                arrayList.add(file1);
                            } else if (StringsKt.endsWith(str, ".pptx", false, 2, null) || StringsKt.endsWith$default(str, ".ppt", false, 2, null)) {
                                ArrayList<File> arrayList = filePowerPoints;
                                File file1 = new File(str);
                                arrayList.add(file1);
                                arrayList = fileAll;
                                file1 = new File(str);
                                arrayList.add(file1);
                            } else if (StringsKt.endsWith(str, ".txt", false, 2, null)) {
                                ArrayList<File> arrayList = fileTexts;
                                File file1 = new File(str);
                                arrayList.add(file1);
                                arrayList = fileAll;
                                file1 = new File(str);
                                arrayList.add(file1);
                            }
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private final void getAllFileScreenShot() {

    }

    private final void gotoNextScreen() {
        startActivityForResult(this.mIntent, this.CODE_RELOAD_FILE);
    }

    private final void handleEvents() {
        // TODO HANDLE EVENT
//        ((BottomNavigationView)findCachedViewById(R.id.bottomNavigationView)).setOnItemSelectedListener(new HomeActivity2$$ExternalSyntheticLambda0(this));
//        ((ImageView)findCachedViewById(R.id.btnRateUs)).setOnClickListener(new HomeActivity2$$ExternalSyntheticLambda1(this));
//        ((ImageView)findCachedViewById(R.id.btnPremium)).setOnClickListener(new HomeActivity2$$ExternalSyntheticLambda2(this));
//        ((ImageView)findCachedViewById(R.id.btnGridOrVertical)).setOnClickListener(new HomeActivity2$$ExternalSyntheticLambda3(this));
//        ((ImageView)findCachedViewById(R.id.btnSearch)).setOnClickListener(new HomeActivity2$$ExternalSyntheticLambda4(this));
    }

    private static final boolean handleEvents$lambda$4(HomeActivity2 paramHomeActivity2, MenuItem paramMenuItem) {
        int b1;
        Intrinsics.checkNotNullParameter(paramHomeActivity2, "this$0");
        Intrinsics.checkNotNullParameter(paramMenuItem, "item");
        if (paramMenuItem.getItemId() == R.id.tab_setting) {
            ((ImageView)paramHomeActivity2.findCachedViewById(R.id.btnPremium)).clearAnimation();
        } else {
            paramHomeActivity2.startPremiumAnimation();
        }
        ImageView imageView4 = (ImageView)paramHomeActivity2.findCachedViewById(R.id.btnPremium);
        Intrinsics.checkNotNullExpressionValue(imageView4, "btnPremium");
        View view4 = (View)imageView4;
        if (paramMenuItem.getItemId() != R.id.tab_setting) {
            b1 = View.GONE;
        } else {
            b1 = View.VISIBLE;
        }
        // TODO check
//        int b2 = 8;
//        if (b1) {
//            b1 = 0;
//        } else {
//            b1 = 8;
//        }

        view4.setVisibility(View.VISIBLE);
        ImageView imageView3 = (ImageView)paramHomeActivity2.findCachedViewById(R.id.btnGridOrVertical);
        Intrinsics.checkNotNullExpressionValue(imageView3, "btnGridOrVertical");
        View view3 = (View)imageView3;

        // TODO Check
        if (paramMenuItem.getItemId() != R.id.tab_setting) {
            b1 = 1;
        } else {
            b1 = 0;
        }
        if (b1 != 0) {
            b1 = 0;
        } else {
            b1 = 8;
        }
        view3.setVisibility(View.VISIBLE);
        ImageView imageView2 = (ImageView)paramHomeActivity2.findCachedViewById(R.id.btnSearch);
        Intrinsics.checkNotNullExpressionValue(imageView2, "btnSearch");
        View view2 = (View)imageView2;
        if (paramMenuItem.getItemId() != R.id.tab_setting) {
            b1 = 1;
        } else {
            b1 = 0;
        }
        if (b1 != 0) {
            b1 = 0;
        } else {
            b1 = 8;
        }
        // TODO
        //view2.setVisibility(b1);
        ImageView imageView1 = (ImageView)paramHomeActivity2.findCachedViewById(R.id.btnRateUs);
        Intrinsics.checkNotNullExpressionValue(imageView1, "btnRateUs");
        View view1 = (View)imageView1;
        if (paramMenuItem.getItemId() == R.id.tab_setting) {
            b1 = 1;
        } else {
            b1 = 0;
        }
        // TODO
//        if (b1 != 0)
//            b2 = 0;
//        view1.setVisibility(b2);
        switch (paramMenuItem.getItemId()) {
                // TODO: tab_recent
            case 1000036:
                ((ViewPager2)paramHomeActivity2.findCachedViewById(R.id.viewPagerMain)).setCurrentItem(1, false);
                ((TextView)paramHomeActivity2.findCachedViewById(R.id.tvTitle)).setText(R.string.txt_recent);
                return true;
            // TODO: tab_home
            case 1000014:
                break;
            default:
                ((ViewPager2)paramHomeActivity2.findCachedViewById(R.id.viewPagerMain)).setCurrentItem(2, false);
                ((TextView)paramHomeActivity2.findCachedViewById(R.id.tvTitle)).setText(R.string.txt_setting);
                return true;
        }
        ((ViewPager2)paramHomeActivity2.findCachedViewById(R.id.viewPagerMain)).setCurrentItem(0, false);
        ((TextView)paramHomeActivity2.findCachedViewById(R.id.tvTitle)).setText((CharSequence) HtmlCompat.fromHtml(paramHomeActivity2.getString(R.string.app_name_splash), 0));
        return true;
    }

    private static final void handleEvents$lambda$5(HomeActivity2 paramHomeActivity2, View paramView) {
        Intrinsics.checkNotNullParameter(paramHomeActivity2, "this$0");
        DialogKt.showRatingDialog((AppCompatActivity)paramHomeActivity2);
    }

    private static final void handleEvents$lambda$6(HomeActivity2 paramHomeActivity2, View paramView) {
        Intrinsics.checkNotNullParameter(paramHomeActivity2, "this$0");
        // TODO
        //paramHomeActivity2.startActivity(new Intent((Context)paramHomeActivity2, ShopActivity.class));
    }

    private static final void handleEvents$lambda$7(HomeActivity2 paramHomeActivity2, View paramView) {
        Intrinsics.checkNotNullParameter(paramHomeActivity2, "this$0");
        paramHomeActivity2.mIsGridMenu = !paramHomeActivity2.mIsGridMenu;
        HomeFragment homeFragment = paramHomeActivity2.mHomeFragment;
        if (homeFragment != null)
            homeFragment.setGridOrVerticalMenu(paramHomeActivity2.mIsGridMenu);
        if (paramHomeActivity2.mIsGridMenu) {
            ((ImageView)paramHomeActivity2.findCachedViewById(R.id.btnGridOrVertical)).setImageResource(R.drawable.ic_menu_grid);
        } else {
            ((ImageView)paramHomeActivity2.findCachedViewById(R.id.btnGridOrVertical)).setImageResource(R.drawable.ic_menu_vertical);
        }
    }

    // TODO
    private static final void handleEvents$lambda$8(HomeActivity2 paramHomeActivity2, View paramView) {
//        Intrinsics.checkNotNullParameter(paramHomeActivity2, "this$0");
//        Intent intent = new Intent((Context)paramHomeActivity2, SearchActivity.class);
//        intent.putExtra("type", "all");
//        paramHomeActivity2.startActivity(intent);
    }

    private final void initData() {
        Context context = (Context)this;
        this.mLoadingDialog = ContextKt.onCreateDialog(context, R.layout.layout_dialog_wait_handle, false);
        if (ContextKt.isPermissionGranted(context))
            loadFile();
    }

    private final void initView() {
        startPremiumAnimation();
        ((TextView)findCachedViewById(R.id.tvTitle)).setSelected(true);
        ((TextView)findCachedViewById(R.id.tvTitle)).setText((CharSequence)HtmlCompat.fromHtml(getString(R.string.app_name_splash), 0));
        ((ViewPager2)findCachedViewById(R.id.viewPagerMain)).setOffscreenPageLimit(3);
        this.mHomeFragment = HomeFragment.Companion.newInstance();
        RecentFragment recentFragment = RecentFragment.Companion.newInstance();
        this.mRecentFragment = recentFragment;
        if (this.mHomeFragment != null && recentFragment != null) {
            ((ViewPager2)findCachedViewById(R.id.viewPagerMain)).setAdapter((RecyclerView.Adapter)new HomeActivity2InitView(this));
            ((ViewPager2)findCachedViewById(R.id.viewPagerMain)).setUserInputEnabled(false);
        }
    }

    private final void loadFile() {
        fileAll.clear();
        filePDFs.clear();
        fileWords.clear();
        filePowerPoints.clear();
        fileExcels.clear();
        fileTexts.clear();
        fileRecents.clear();
        fileFavorites.clear();
        fileImages.clear();
        // TODO
        //(new LoadAllFile()).execute((Void) new Void[0]);
    }

    private final void queryFilesFromDevice(Uri paramUri, String[] paramArrayOfString, String paramString) {
        Cursor cursor = getContentResolver().query(paramUri, paramArrayOfString, paramString, null, null);
        if (cursor != null) {
            boolean bool;
            for (bool = false; cursor.moveToNext(); bool = true) {
                String str = cursor.getString(0);
                cursor.getLong(1);
                Intrinsics.checkNotNullExpressionValue(str, "name");
                if (StringsKt.endsWith(str, ".pdf", false, 2, null)) {
                    filePDFs.add(new File(str));
                    fileAll.add(new File(str));
                } else if (StringsKt.endsWith$default(str, ".doc", false, 2, null) || StringsKt.endsWith$default(str, ".docx", false, 2, null)) {
                    fileWords.add(new File(str));
                    fileAll.add(new File(str));
                } else if (StringsKt.endsWith$default(str, ".xlsx", false, 2, null) || StringsKt.endsWith$default(str, ".xls", false, 2, null)) {
                    fileExcels.add(new File(str));
                    fileAll.add(new File(str));
                } else if (StringsKt.endsWith$default(str, ".pptx", false, 2, null) || StringsKt.endsWith$default(str, ".ppt", false, 2, null)) {
                    filePowerPoints.add(new File(str));
                    fileAll.add(new File(str));
                } else if (StringsKt.endsWith(str, ".txt", false, 2, null)) {
                    fileTexts.add(new File(str));
                    fileAll.add(new File(str));
                }
            }
            cursor.close();
            if (!bool)
                getAllFile(new File(Environment.getExternalStorageDirectory().toString()));
        } else {
            Log.d("999999", "cnull");
        }
    }

    private static final void showFeedbackDialog$lambda$0(Dialog paramDialog, View paramView) {
        Intrinsics.checkNotNullParameter(paramDialog, "$dialog");
        paramDialog.cancel();
    }

    private static final void showFeedbackDialog$lambda$1(Dialog paramDialog, HomeActivity2 paramHomeActivity2, View paramView) {
        boolean bool;
        Intrinsics.checkNotNullParameter(paramDialog, "$dialog");
        Intrinsics.checkNotNullParameter(paramHomeActivity2, "this$0");
        String str = StringsKt.trim(((EditText)paramDialog.findViewById(R.id.edtFeedback)).getText().toString()).toString();
        if (str.length() > 0) {
            bool = true;
        } else {
            bool = false;
        }
        if (bool) {
            if (str.length() < 15) {
                ContextKt.showNotice((Context)paramHomeActivity2, R.string.txt_the_email_contains);
            } else {
                paramDialog.cancel();
                ContextKt.showNotice((Context)paramHomeActivity2, R.string.txt_thank);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append("\nVERSION:5151");
                String str1 = stringBuilder.toString();
                AppCompatActivityKt.sendFeedback((AppCompatActivity)paramHomeActivity2, str1);
            }
        } else {
            ContextKt.showNotice((Context)paramHomeActivity2, R.string.txt_please_write);
        }
    }

    private final void showSetDefaultReaderDialog() {
        Dialog dialog = ContextKt.onCreateBottomSheetDialog((Context)this, 2131558566, false);
        // TODO
        //((TextView)dialog.findViewById(R.id.btnOK)).setOnClickListener(new HomeActivity2$$ExternalSyntheticLambda7(dialog, this));
        //((TextView)dialog.findViewById(R.id.btnLater)).setOnClickListener(new HomeActivity2$$ExternalSyntheticLambda8(dialog));
        dialog.show();
    }

    private static final void showSetDefaultReaderDialog$lambda$2(Dialog paramDialog, HomeActivity2 paramHomeActivity2, View paramView) {
        String str;
        Intrinsics.checkNotNullParameter(paramDialog, "$dialog");
        Intrinsics.checkNotNullParameter(paramHomeActivity2, "this$0");
        paramDialog.cancel();
        try {
            Uri uri;
            File file = (File) CollectionsKt.random(filePDFs, (Random)Random.Default);
            if (Build.VERSION.SDK_INT >= 24) {
                Context context = (Context)paramHomeActivity2;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(paramHomeActivity2.getPackageName());
                stringBuilder.append(".provider");
                uri = FileProvider.getUriForFile(context, stringBuilder.toString(), file);
            } else {
                uri = Uri.fromFile(file);
            }
            Log.d("555555555555", String.valueOf(ExtensionsKt.mimeType(file)));
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setDataAndType(uri, "application/pdf");
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // TODO
            //intent2.setFlags(67108865);
//            paramHomeActivity2.startActivity(intent2);
//            Intent intent1 = new Intent((Context)paramHomeActivity2, SetDefaultReaderActivity.class);
//            paramHomeActivity2.startActivity(intent1);
        } catch (ActivityNotFoundException activityNotFoundException) {
            Context context = (Context)paramHomeActivity2;
            str = paramHomeActivity2.getString(R.string.txt_no_app_found);
            Intrinsics.checkNotNullExpressionValue(str, "getString(R.string.txt_no_app_found)");
            ContextKt.showNotice(context, str);
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.d("555555555555", String.valueOf(exception.getMessage()));
            Context context = (Context)paramHomeActivity2;
            str = context.getString(R.string.txt_some_thing_wrong);
            Intrinsics.checkNotNullExpressionValue(str, "getString(R.string.txt_some_thing_wrong)");
            ContextKt.showNotice(context, str);
        }
    }

    private static final void showSetDefaultReaderDialog$lambda$3(Dialog paramDialog, View paramView) {
        Intrinsics.checkNotNullParameter(paramDialog, "$dialog");
        paramDialog.cancel();
    }

    private final void startPremiumAnimation() {
        Animation animation = AnimationUtils.loadAnimation((Context)this, R.anim.fade_out_shop);
        ((ImageView)findCachedViewById(R.id.btnPremium)).startAnimation(animation);
    }

    public void _$_clearFindViewByIdCache() {
        this._$_findViewCache.clear();
    }

    public View findCachedViewById(int paramInt) {
        Map<Integer, View> map = this._$_findViewCache;
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
        if (paramInt1 == this.CODE_RELOAD_FILE)
            loadFile();
    }

    public void onAdDismissed() {
        gotoNextScreen();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_home2);
        Utils utils = Utils.INSTANCE;
        Context context = (Context)this;
        AdView adView = (AdView)findCachedViewById(R.id.adView);
        Intrinsics.checkNotNullExpressionValue(adView, "adView");
        utils.loadBannerAds(context, adView, false);
        initData();
        initView();
        handleEvents();
    }

    protected void onDestroy() {
        Dialog dialog = this.mLoadingDialog;
        if (dialog != null) {
            Intrinsics.checkNotNull(dialog);
            if (dialog.isShowing()) {
                dialog = this.mLoadingDialog;
                if (dialog != null)
                    dialog.dismiss();
            }
        }
        this.mLoadingDialog = null;
        super.onDestroy();
    }

    public void onShowFail() {
        findCachedViewById(R.id.loadingAds).setVisibility(View.GONE);
        gotoNextScreen();
    }

    public void onShowSuccess() {
        findCachedViewById(R.id.loadingAds).setVisibility(View.GONE);
    }

    public final void showAds(Intent paramIntent) {
        Intrinsics.checkNotNullParameter(paramIntent, "intent");
        this.mIntent = paramIntent;
        if (!BillingClientSetup.isUpgraded(getApplicationContext()) && AdsManager.INSTANCE.is_show_inter_ads_home()) {
            findViewById(R.id.loadingAds).setVisibility(View.VISIBLE);
            AdsManager.INSTANCE.showAds((Activity)this, this);
        } else {
            gotoNextScreen();
        }
    }

    public final void showFeedbackDialog() {
        Dialog dialog = ContextKt.onCreateDialog((Context)this, R.layout.layout_dialog_feedback, true);
        // TODO
        //((TextView)dialog.findViewById(R.id.btnCancel)).setOnClickListener(new HomeActivity2$$ExternalSyntheticLambda5(dialog));
        //((TextView)dialog.findViewById(R.id.btnSend)).setOnClickListener(new HomeActivity2$$ExternalSyntheticLambda6(dialog, this));
        dialog.show();
    }

    public static final class Companion {
        private Companion() {}

        public final ArrayList<File> getFileAll() {
            return HomeActivity2.fileAll;
        }

        public final ArrayList<File> getFileExcels() {
            return HomeActivity2.fileExcels;
        }

        public final ArrayList<File> getFileFavorites() {
            return HomeActivity2.fileFavorites;
        }

        public final ArrayList<File> getFileImages() {
            return HomeActivity2.fileImages;
        }

        public final ArrayList<File> getFilePDFs() {
            return HomeActivity2.filePDFs;
        }

        public final ArrayList<File> getFilePowerPoints() {
            return HomeActivity2.filePowerPoints;
        }

        public final ArrayList<File> getFileRecents() {
            return HomeActivity2.fileRecents;
        }

        public final ArrayList<File> getFileTexts() {
            return HomeActivity2.fileTexts;
        }

        public final ArrayList<File> getFileWords() {
            return HomeActivity2.fileWords;
        }

        public final void setFileAll(ArrayList<File> param1ArrayList) {
            Intrinsics.checkNotNullParameter(param1ArrayList, "<set-?>");
            HomeActivity2.fileAll = param1ArrayList;
        }

        public final void setFileExcels(ArrayList<File> param1ArrayList) {
            Intrinsics.checkNotNullParameter(param1ArrayList, "<set-?>");
            HomeActivity2.fileExcels = param1ArrayList;
        }

        public final void setFileFavorites(ArrayList<File> param1ArrayList) {
            Intrinsics.checkNotNullParameter(param1ArrayList, "<set-?>");
            HomeActivity2.fileFavorites = param1ArrayList;
        }

        public final void setFileImages(ArrayList<File> param1ArrayList) {
            Intrinsics.checkNotNullParameter(param1ArrayList, "<set-?>");
            HomeActivity2.fileImages = param1ArrayList;
        }

        public final void setFilePDFs(ArrayList<File> param1ArrayList) {
            Intrinsics.checkNotNullParameter(param1ArrayList, "<set-?>");
            HomeActivity2.filePDFs = param1ArrayList;
        }

        public final void setFilePowerPoints(ArrayList<File> param1ArrayList) {
            Intrinsics.checkNotNullParameter(param1ArrayList, "<set-?>");
            HomeActivity2.filePowerPoints = param1ArrayList;
        }

        public final void setFileRecents(ArrayList<File> param1ArrayList) {
            Intrinsics.checkNotNullParameter(param1ArrayList, "<set-?>");
            HomeActivity2.fileRecents = param1ArrayList;
        }

        public final void setFileTexts(ArrayList<File> param1ArrayList) {
            Intrinsics.checkNotNullParameter(param1ArrayList, "<set-?>");
            HomeActivity2.fileTexts = param1ArrayList;
        }

        public final void setFileWords(ArrayList<File> param1ArrayList) {
            Intrinsics.checkNotNullParameter(param1ArrayList, "<set-?>");
            HomeActivity2.fileWords = param1ArrayList;
        }
    }

    public final class LoadAllFile extends AsyncTask<Void, Void, Void> {

        private final void onPostExecute(HomeActivity2 param1HomeActivity2) {
            Intrinsics.checkNotNullParameter(param1HomeActivity2, "this$0");
            if (param1HomeActivity2.mLoadingDialog != null) {
                Dialog dialog = param1HomeActivity2.mLoadingDialog;
                if (dialog != null)
                    dialog.dismiss();
            }
        }

        protected Void doInBackground(Void... param1VarArgs) {
            Intrinsics.checkNotNullParameter(param1VarArgs, "p0");
            try {
                HomeActivity2.this.getAllFileScreenShot();
                HomeActivity2.Companion.getFileFavorites().addAll(Utils.INSTANCE.getAllFileFavorites((Context)HomeActivity2.this));
                HomeActivity2.Companion.getFileRecents().addAll(Utils.INSTANCE.getAllFileRecents((Context)HomeActivity2.this));
                Uri uri = MediaStore.Files.getContentUri("external");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("version=");
                stringBuilder.append(Build.VERSION.SDK_INT);
                Log.d("999999999", stringBuilder.toString());
                if (Build.VERSION.SDK_INT >= 30) {
                    HomeActivity2 homeActivity2 = HomeActivity2.this;
                    Intrinsics.checkNotNullExpressionValue(uri, "ducumentsUri");
                    homeActivity2.queryFilesFromDevice(uri, new String[] { "_data", "_size", "mime_type" }, "_data LIKE '%.pdf' or _data LIKE '%.doc' or _data LIKE '%.docx' or _data LIKE '%.pptx' or _data LIKE '%.ppt' or _data LIKE '%.xlsx' or _data LIKE '%.xls' or _data LIKE '%.txt'");
                } else {
                    HomeActivity2 homeActivity2 = HomeActivity2.this;
                    File file = new File(Environment.getExternalStorageDirectory().toString());
                    homeActivity2.getAllFile(file);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void param1Void) {
            super.onPostExecute(param1Void);
            try {
                HomeFragment homeFragment = HomeActivity2.this.mHomeFragment;
                if (homeFragment != null)
                    homeFragment.initView();
                RecentFragment recentFragment = HomeActivity2.this.mRecentFragment;
                if (recentFragment != null)
                    recentFragment.initView();
                Handler handler = new Handler(Looper.getMainLooper());
                HomeActivity2 homeActivity2 = HomeActivity2.this;
                // TODO
//                HomeActivity2$LoadAllFile$$ExternalSyntheticLambda0 homeActivity2$LoadAllFile$$ExternalSyntheticLambda0 = new HomeActivity2$LoadAllFile$$ExternalSyntheticLambda0();
//                handler.postDelayed(homeActivity2.loadFile, 1000L);
                if (!Settings.INSTANCE.isSetDefaultApp())
                    HomeActivity2.this.showSetDefaultReaderDialog();
            } catch (Exception exception) {
                Log.d("7777777777", String.valueOf(exception.getMessage()));
                exception.printStackTrace();
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Dialog dialog = HomeActivity2.this.mLoadingDialog;
            if (dialog != null)
                dialog.show();
        }
    }

    public static final class HomeActivity2InitView extends FragmentStateAdapter {
        final HomeActivity2 activity2;

        HomeActivity2InitView(HomeActivity2 homeActivity2) {
            super((FragmentActivity)homeActivity2);
            this.activity2 = homeActivity2;
        }

        // TODO
        public Fragment createFragment(int param1Int) {
            Fragment fragment = null;
//            if (param1Int != 0) {
//                if (param1Int != 1) {
//                    fragment = (Fragment)SettingsFragment.Companion.newInstance();
//                } else {
//                    RecentFragment recentFragment = HomeActivity2.this.mRecentFragment;
//                    Intrinsics.checkNotNull(recentFragment);
//                    fragment = (Fragment)recentFragment;
//                }
//            } else {
//                HomeFragment homeFragment = HomeActivity2.this.mHomeFragment;
//                Intrinsics.checkNotNull(homeFragment);
//                fragment = (Fragment)homeFragment;
//            }
            return fragment;
        }

        public int getItemCount() {
            return 3;
        }
    }
}