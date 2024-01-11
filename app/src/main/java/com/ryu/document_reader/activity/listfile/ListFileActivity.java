package com.ryu.document_reader.activity.listfile;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.activity.SplashActivity;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.activity.home.HomeActivity2;
import com.ryu.document_reader.adapter.ListFileAdapter;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.ads.callback.AdsListener;
import com.ryu.document_reader.extension.ContextKt;
import com.ryu.document_reader.extension.ExtensionsKt;
import com.ryu.document_reader.util.Utils;
import com.ryu.document_reader.widget.SwipeHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;

public final class ListFileActivity extends BaseActivity implements ListFileAdapter.OnItemListener, AdsListener, ListFileAdapter.OnItemSelectListener {
    public Map<Integer, View> _$_findViewCache;

    private int mColor;

    private Dialog mDialog;

    private ArrayList<File> mFiles = new ArrayList<File>();

    private Intent mIntent;

    private boolean mIsDeleTeIndex0;

    private boolean mIsSelectAll;

    private ListFileAdapter mListFileAdapter;

    private Dialog mLoadingDialog;

    private String mTitle = "";

    private String mType = "all";

    private int mTypeSort = 2;

    private final void deleteFile(File paramFile) {
        try {
            Utils utils = Utils.INSTANCE;
            String str = paramFile.getAbsolutePath();
            Context context = getApplicationContext();
            if (utils.isFileFavorite(str, context)) {
                utils.setFileFavorite(str, context, false);
            }
            getContentResolver().delete(MediaStore.Files.getContentUri("external"), "_data=?", new String[] { paramFile.getAbsolutePath() });
            if (paramFile.exists())
                paramFile.delete();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
                            getAllFile(file);
                        } else {
                            String str = file.getAbsolutePath();
                            if (StringsKt.endsWith(str, ".pdf", false, 2, null)) {
                                ArrayList<File> arrayList2 = HomeActivity2.Companion.getFilePDFs();
                                File file1 = new File(str);
                                arrayList2.add(file1);
                                ArrayList<File> arrayList1 = HomeActivity2.Companion.getFileAll();
                                File file2 = new File(str);
                                arrayList1.add(file2);
                            } else if (StringsKt.endsWith(str, ".doc", false, 2, null) || StringsKt.endsWith(str, ".docx", false, 2, null)) {
                                ArrayList<File> arrayList = HomeActivity2.Companion.getFileWords();
                                File file1 = new File(str);
                                arrayList.add(file1);
                                arrayList = HomeActivity2.Companion.getFileAll();
                                file1 = new File(str);
                                arrayList.add(file1);
                            } else if (StringsKt.endsWith(str, ".xlsx", false, 2, null) || StringsKt.endsWith(str, ".xls", false, 2, null)) {
                                ArrayList<File> arrayList = HomeActivity2.Companion.getFileExcels();
                                File file1 = new File(str);
                                arrayList.add(file1);
                                arrayList = HomeActivity2.Companion.getFileAll();
                                file1 = new File(str);
                                arrayList.add(file1);
                            } else if (StringsKt.endsWith(str, ".pptx", false, 2, null) || StringsKt.endsWith(str, ".ppt", false, 2, null)) {
                                ArrayList<File> arrayList1 = HomeActivity2.Companion.getFilePowerPoints();
                                File file2 = new File(str);
                                arrayList1.add(file2);
                                ArrayList<File> arrayList2 = HomeActivity2.Companion.getFileAll();
                                File file1 = new File(str);
                                arrayList2.add(file1);
                            } else if (StringsKt.endsWith(str, ".txt", false, 2, null)) {
                                ArrayList<File> arrayList = HomeActivity2.Companion.getFileTexts();
                                File file1 = new File(str);
                                arrayList.add(file1);
                                arrayList = HomeActivity2.Companion.getFileAll();
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
        // TODO
    }

    private final ArrayList<File> getListFileByType() {
        String str = this.mType;
        switch (str) {
            case "favorite":
                str = getString(R.string.fab_favourite);
                this.mTitle = str;
                ((TextView)findCachedViewById(R.id.txtTitle)).setText(this.mTitle);
                return HomeActivity2.Companion.getFileFavorites();
            case "power_point":
                this.mTitle = "Power Point";
                ((TextView)findCachedViewById(R.id.txtTitle)).setText(this.mTitle);
                return HomeActivity2.Companion.getFilePowerPoints();
            case "image":
                str = getString(R.string.screenshot);
                this.mTitle = str;
                ((TextView)findCachedViewById(R.id.txtTitle)).setText(this.mTitle);
                return HomeActivity2.Companion.getFileImages();
            case "excel":
                this.mTitle = "Excel";
                ((TextView)findCachedViewById(R.id.txtTitle)).setText(this.mTitle);
                return HomeActivity2.Companion.getFileExcels();
            case "word":
                this.mTitle = "Word";
                ((TextView)findCachedViewById(R.id.txtTitle)).setText(this.mTitle);
                return HomeActivity2.Companion.getFileWords();
            case "text":
                this.mTitle = "Text";
                ((TextView)findCachedViewById(R.id.txtTitle)).setText(this.mTitle);
                return HomeActivity2.Companion.getFileTexts();
            case "pdf":
                this.mTitle = "Pdf";
                ((TextView)findCachedViewById(R.id.txtTitle)).setText(this.mTitle);
                return HomeActivity2.Companion.getFilePDFs();
            case "recent":
                this.mTitle = getString(R.string.recent_files);
                ((TextView)findCachedViewById(R.id.txtTitle)).setText(this.mTitle);
                return HomeActivity2.Companion.getFileRecents();
            default:
                str = getString(R.string.all);
                this.mTitle = str;
                ((TextView)findCachedViewById(R.id.txtTitle)).setText(this.mTitle);
                return HomeActivity2.Companion.getFileAll();
        }
    }

    private final void gotoNextScreen() {
        Intent intent = this.mIntent;
        if (intent != null)
            startActivityForResult(intent, 202);
    }

    private final void handleEvents() {
        // TODO
        findCachedViewById(R.id.btnRefresh).setOnClickListener(view -> refresh());
        findCachedViewById(R.id.btnBack).setOnClickListener(view -> this.onBackPressed());
        findCachedViewById(R.id.layoutSearch).setOnClickListener(view -> onClickSearch());
//        ((ImageView)findCachedViewById(R.id.btnSelect)).setOnClickListener(new ListFileActivity$$ExternalSyntheticLambda11(this));
//        ((ImageView)findCachedViewById(R.id.btnSelectAll)).setOnClickListener(new ListFileActivity$$ExternalSyntheticLambda12(this));
//        ((LinearLayout)findCachedViewById(R.id.btnShare)).setOnClickListener(new ListFileActivity$$ExternalSyntheticLambda13(this));
//        ((LinearLayout)findCachedViewById(R.id.btnDelete)).setOnClickListener(new ListFileActivity$$ExternalSyntheticLambda1(this));
        //findCachedViewById(R.id.btnOrder).setOnClickListener(view -> {}));
        Dialog dialog = this.mDialog;
        LinearLayout layoutAccessedTime = (LinearLayout)dialog.findViewById(R.id.layout_order_by_accessed_time);
        LinearLayout layoutCreatedTime = (LinearLayout)dialog.findViewById(R.id.layout_order_by_created_time);
        LinearLayout layoutName = (LinearLayout)dialog.findViewById(R.id.layout_order_by_name);
        LinearLayout layoutNameZA = (LinearLayout)dialog.findViewById(R.id.layout_order_by_name_za);
        // TODO
        layoutAccessedTime.setOnClickListener(view -> {

        });
        layoutCreatedTime.setOnClickListener(view -> {

        });
        layoutName.setOnClickListener(view -> {

        });
        layoutNameZA.setOnClickListener(view -> {

        });
//        if (dialog != null) {
//            LinearLayout linearLayout = (LinearLayout)dialog.findViewById(R.id.layout_order_by_accessed_time);
//            if (linearLayout != null)
//                linearLayout.setOnClickListener(new ListFileActivity$$ExternalSyntheticLambda3(this));
//        }
//        dialog = this.mDialog;
//        if (dialog != null) {
//            LinearLayout linearLayout = (LinearLayout)dialog.findViewById(R.id.layout_order_by_created_time);
//            if (linearLayout != null)
//                linearLayout.setOnClickListener(new ListFileActivity$$ExternalSyntheticLambda4(this));
//        }
//        dialog = this.mDialog;
//        if (dialog != null) {
//            LinearLayout linearLayout = (LinearLayout)dialog.findViewById(R.id.layout_order_by_name);
//            if (linearLayout != null)
//                linearLayout.setOnClickListener(new ListFileActivity$$ExternalSyntheticLambda7(this));
//        }
//        dialog = this.mDialog;
//        if (dialog != null) {
//            LinearLayout linearLayout = (LinearLayout)dialog.findViewById(R.id.layout_order_by_name_za);
//            if (linearLayout != null)
//                linearLayout.setOnClickListener(new ListFileActivity$$ExternalSyntheticLambda8(this));
//        }
    }

    private final void refresh() {
        Utils.INSTANCE.setTrackEvent((Context)this, "event_click_refresh_file_at_list_file", "event_click_refresh_file_at_list_file");
        this.loadFile();
    }

    private final void handleEvents$lambda$1(ListFileActivity paramListFileActivity, View paramView) {
        paramListFileActivity.onBackPressed();
    }

    private final void handleEvents$lambda$10(ListFileActivity paramListFileActivity, View paramView) {
        Utils.INSTANCE.setTrackEvent((Context)paramListFileActivity, "event_click_order_button_at_list_file", "event_click_order_button_at_list_file");
        Dialog dialog = paramListFileActivity.mDialog;
        if (dialog != null)
            dialog.show();
    }

    private final void handleEvents$lambda$11(ListFileActivity paramListFileActivity, View paramView) {
        ImageView imageView = null;
        Utils.INSTANCE.setTrackEvent((Context)paramListFileActivity, "event_click_sort_by_time_at_list_file", "event_click_sort_by_time_at_list_file");
        Dialog dialog2 = paramListFileActivity.mDialog;
        Dialog dialog3 = null;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_accessed);
        } else {
            dialog2 = null;
        }
        // TODO
//        if (dialog2 != null)
//            dialog2.setOnShowListener(0);
        dialog2 = paramListFileActivity.mDialog;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_create);
        } else {
            dialog2 = null;
        }
//        if (dialog2 != null)
//            dialog2.setOnShowListener(4);
        dialog2 = paramListFileActivity.mDialog;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_name);
        } else {
            dialog2 = null;
        }
//        if (dialog2 != null)
//            dialog2.setOnShowListener(4);
        Dialog dialog4 = paramListFileActivity.mDialog;
        if (dialog4 != null)
            imageView = (ImageView)dialog4.findViewById(R.id.imv_tick_by_name_za);
        if (imageView != null)
            imageView.setVisibility(View.INVISIBLE);
        paramListFileActivity.mTypeSort = 3;
        (new SortFile()).execute();
        Dialog dialog1 = paramListFileActivity.mDialog;
        if (dialog1 != null)
            dialog1.dismiss();
    }

    private final void handleEvents$lambda$12(ListFileActivity paramListFileActivity, View paramView) {
        ImageView imageView = null;
        Utils.INSTANCE.setTrackEvent((Context)paramListFileActivity, "event_click_sort_by_created_time_at_list_file", "event_click_sort_by_created_time_at_list_file");
        Dialog dialog2 = paramListFileActivity.mDialog;
        Dialog dialog3 = null;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_accessed);
        } else {
            dialog2 = null;
        }
        // TODO
//        if (dialog2 != null)
//            dialog2.setVisibility(4);
        dialog2 = paramListFileActivity.mDialog;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_create);
        } else {
            dialog2 = null;
        }
//        if (dialog2 != null)
//            dialog2.setVisibility(0);
        dialog2 = paramListFileActivity.mDialog;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_name);
        } else {
            dialog2 = null;
        }
//        if (dialog2 != null)
//            dialog2.setVisibility(4);
        Dialog dialog4 = paramListFileActivity.mDialog;
        if (dialog4 != null)
            imageView = (ImageView)dialog4.findViewById(R.id.imv_tick_by_name_za);
        if (imageView != null)
            imageView.setVisibility(View.INVISIBLE);
        paramListFileActivity.mTypeSort = 2;
        (new SortFile()).execute();
        Dialog dialog1 = paramListFileActivity.mDialog;
        if (dialog1 != null)
            dialog1.dismiss();
    }

    private final void handleEvents$lambda$13(ListFileActivity paramListFileActivity, View paramView) {
        ImageView imageView = null;
        Utils.INSTANCE.setTrackEvent((Context)paramListFileActivity, "event_click_sort_by_name_at_list_file", "event_click_sort_by_name_at_list_file");
        Dialog dialog2 = paramListFileActivity.mDialog;
        Dialog dialog3 = null;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_accessed);
        } else {
            dialog2 = null;
        }
        // TODO
//        if (dialog2 != null)
//            dialog2.setVisibility(4);
        dialog2 = paramListFileActivity.mDialog;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_create);
        } else {
            dialog2 = null;
        }
        // TODO
//        if (dialog2 != null)
//            dialog2.setVisibility(4);
        dialog2 = paramListFileActivity.mDialog;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_name);
        } else {
            dialog2 = null;
        }
        // TODO
//        if (dialog2 != null)
//            dialog2.setVisibility(0);
        Dialog dialog4 = paramListFileActivity.mDialog;
        if (dialog4 != null)
            imageView = (ImageView)dialog4.findViewById(R.id.imv_tick_by_name_za);
        if (imageView != null)
            imageView.setVisibility(View.INVISIBLE);
        paramListFileActivity.mTypeSort = 0;
        (new SortFile()).execute();
        Dialog dialog1 = paramListFileActivity.mDialog;
        if (dialog1 != null)
            dialog1.dismiss();
    }

    private final void handleEvents$lambda$14(ListFileActivity paramListFileActivity, View paramView) {
        ImageView imageView = null;
        Utils.INSTANCE.setTrackEvent((Context)paramListFileActivity, "event_click_sort_by_name_za_at_list_file", "event_click_sort_by_name_za_at_list_file");
        Dialog dialog2 = paramListFileActivity.mDialog;
        Dialog dialog3 = null;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_accessed);
        } else {
            dialog2 = null;
        }
        // TODO
//        if (dialog2 != null)
//            dialog2.setVisibility(4);
        dialog2 = paramListFileActivity.mDialog;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_create);
        } else {
            dialog2 = null;
        }
//        if (dialog2 != null)
//            dialog2.setVisibility(4);
        dialog2 = paramListFileActivity.mDialog;
        if (dialog2 != null) {
            imageView = (ImageView)dialog2.findViewById(R.id.imv_tick_by_name);
        } else {
            dialog2 = null;
        }
//        if (dialog2 != null)
//            dialog2.setVisibility(4);
        Dialog dialog4 = paramListFileActivity.mDialog;
        if (dialog4 != null)
            imageView = (ImageView)dialog4.findViewById(R.id.imv_tick_by_name_za);
        if (imageView != null)
            imageView.setVisibility(View.VISIBLE);
        paramListFileActivity.mTypeSort = 1;
        (new SortFile()).execute();
        Dialog dialog1 = paramListFileActivity.mDialog;
        if (dialog1 != null)
            dialog1.dismiss();
    }

    private  final void onClickSearch() {
        Utils utils = Utils.INSTANCE;
        utils.setTrackEvent(this, "event_click_search_button_at_list_file", "event_click_search_button_at_list_file");
        // TODO
        //        Intent intent = new Intent(context, SearchActivity.class);
//        intent.putExtra("type", paramListFileActivity.mType);
//        paramListFileActivity.startActivity(intent);
    }

    private final void handleEvents$lambda$3(ListFileActivity paramListFileActivity, View paramView) {
        ((LinearLayout)paramListFileActivity.findCachedViewById(R.id.layoutOption)).setVisibility(View.VISIBLE);
        setEnableLayoutOption(false);
        TextView textView = (TextView)paramListFileActivity.findCachedViewById(R.id.txtTitle);
        String str = paramListFileActivity.getString(R.string.txt_selected);
        // TODO
       // str = String.format(str, Arrays.copyOf(new Object[] { Integer.valueOf(0) }, 1));
        textView.setText(str);
        findCachedViewById(R.id.btnSelectAll).setVisibility(View.VISIBLE);
        findCachedViewById(R.id.btnOrder).setVisibility(View.GONE);
        findCachedViewById(R.id.btnSelect).setVisibility(View.GONE);
        ListFileAdapter listFileAdapter = paramListFileActivity.mListFileAdapter;
        if (listFileAdapter != null)
            listFileAdapter.setSelectedMode(true);
    }

    private static final void handleEvents$lambda$4(ListFileActivity paramListFileActivity, View paramView) {
    }

    private static final void handleEvents$lambda$7(ListFileActivity paramListFileActivity, View paramView) {
        ListFileAdapter listFileAdapter = paramListFileActivity.mListFileAdapter;
        if (listFileAdapter != null && listFileAdapter.getMSelectedFile().isEmpty()) {
            ArrayList<Uri> arrayList = new ArrayList();
            Iterator<File> iterator = listFileAdapter.getMSelectedFile().iterator();
            String str = "*/*";
            while (iterator.hasNext()) {
                File file = iterator.next();
                if (file.exists()) {
                    arrayList.add(FileProvider.getUriForFile((Context)paramListFileActivity, "com.docreader.readerdocument.provider", file));
                    str = ExtensionsKt.mimeType(file);
                }
            }
            Log.d("7777777777777", String.valueOf(str));
            paramListFileActivity.startActivity(Intent.createChooser(Utils.INSTANCE.fileShareIntent(paramListFileActivity.getString(R.string.fab_share), arrayList, str), "share.."));
        }
    }

    private static final void handleEvents$lambda$9(ListFileActivity paramListFileActivity, View paramView) {
        ListFileAdapter listFileAdapter = paramListFileActivity.mListFileAdapter;
        if (listFileAdapter != null && listFileAdapter.getMSelectedFile().isEmpty())
            paramListFileActivity.showDeleteDialog();
    }

    private final void initData() {
        this.mFiles.clear();
        findCachedViewById(R.id.txtTitle).setSelected(true);
        String str = getIntent().getStringExtra("type");
        if (TextUtils.isEmpty(str)) {
            str = "all";
        }
        this.mType = str;
        this.mFiles.addAll(getListFileByType());
        this.mListFileAdapter = new ListFileAdapter(this.mFiles, this, this.mColor, false,  null);
        if (this.mFiles.isEmpty()) {
            findCachedViewById(R.id.layoutNofile).setVisibility(View.VISIBLE);
        } else {
            findCachedViewById(R.id.layoutNofile).setVisibility(View.GONE);
        }
        ((RecyclerView)findCachedViewById(R.id.rvListFile)).setAdapter(this.mListFileAdapter);
        (new SortFile()).execute();
        Context context = (Context)this;
        Dialog dialog2 = ContextKt.onCreateDialog(context, R.layout.layout_dialog_wait_handle, false);
        this.mLoadingDialog = dialog2;
        if (dialog2 != null) {
            // TODO
//            SpinKitView spinKitView = (SpinKitView)dialog2.findViewById(R.id.spinKitView);
//            if (spinKitView != null)
//                spinKitView.setColor(this.mColor);
        }
        new ListFileActivity$initData$1(findCachedViewById(R.id.rvListFile));
        Dialog dialog1 = new Dialog(context);
        this.mDialog = dialog1;
        dialog1.requestWindowFeature(1);
        dialog1 = this.mDialog;
        if (dialog1 != null)
            dialog1.setContentView(R.layout.layout_dialog_order);
    }

    private final void initThemeColor() {
        String str = getIntent().getStringExtra("type");
        Utils utils = Utils.INSTANCE;
        this.mColor = ContextCompat.getColor(this, utils.getIdColorByType(this, str));
        Utils.INSTANCE.setStatusBarColor(this, this.mColor);
        findCachedViewById(R.id.toolBar).setBackgroundColor(this.mColor);
    }

    private final void initThemeView() {
        if (SplashActivity.Companion.getTheme() == 1)
            findCachedViewById(R.id.layoutMain).setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlackAltLight));
    }

    private final void loadFile() {
        HomeActivity2.Companion.getFileAll().clear();
        HomeActivity2.Companion.getFilePDFs().clear();
        HomeActivity2.Companion.getFileWords().clear();
        HomeActivity2.Companion.getFilePowerPoints().clear();
        HomeActivity2.Companion.getFileExcels().clear();
        HomeActivity2.Companion.getFileTexts().clear();
        HomeActivity2.Companion.getFileRecents().clear();
        HomeActivity2.Companion.getFileFavorites().clear();
        HomeActivity2.Companion.getFileImages().clear();
        this.mFiles.clear();
        (new LoadAllFile()).execute();
    }

    private final void renameFile(File paramFile1, File paramFile2) {
        ContentValues contentValues = new ContentValues();
        try {
            Uri uri = MediaStore.Files.getContentUri("external");
            String[] arrayOfString = new String[1];
            arrayOfString[0] = "_id";
            ContentResolver contentResolver = getApplicationContext().getContentResolver();
            String str2 = paramFile2.getAbsolutePath();
            Cursor cursor = contentResolver.query(uri, arrayOfString, "_data LIKE ?", new String[] { str2 }, null);
            cursor.moveToFirst();
            long l = cursor.getLong(Math.max(cursor.getColumnIndex(arrayOfString[0]), 0));
            cursor.close();
            contentValues.put("_id", String.valueOf(l));
            contentValues.put("_display_name", paramFile1.getName());
            contentValues.put("_data", paramFile1.getAbsolutePath());
            getApplicationContext().getContentResolver().update(Uri.fromFile(paramFile2), contentValues, null, null);
        } catch (Exception exception) {
            Log.e("TAG", "renameFile: ", exception);
        }
    }

    private final void setEnableLayoutOption(boolean paramBoolean) {
        findCachedViewById(R.id.tvDelete).setEnabled(paramBoolean);
        findCachedViewById(R.id.tvShare).setEnabled(paramBoolean);
        findCachedViewById(R.id.ivDelete).setEnabled(paramBoolean);
        findCachedViewById(R.id.ivShare).setEnabled(paramBoolean);
    }

    private final void showAds() {
        if (!BillingClientSetup.isUpgraded(getApplicationContext()) && AdsManager.INSTANCE.is_show_inter_ads_list_file()) {
            findCachedViewById(R.id.loadingAds).setVisibility(View.VISIBLE);
            AdsManager.INSTANCE.showAds((Activity)this, this);
        } else {
            gotoNextScreen();
        }
    }

    private final void showDeleteDialog() {
        Dialog dialog = ContextKt.onCreateDialog((Context)this, R.layout.layout_dialog_delete, false);
        ((TextView)dialog.findViewById(R.id.tvTitleDelete)).setText(R.string.delete_alert_selected);
        dialog.show();
        // TODO
        //((Button)dialog.findViewById(R.id.btnOK)).setOnClickListener(new ListFileActivity$$ExternalSyntheticLambda0(this, dialog));
        dialog.findViewById(R.id.btnCancel).setOnClickListener(view -> dialog.dismiss());
    }

    private static final void showDeleteDialog$lambda$19$lambda$17(ListFileActivity paramListFileActivity, Dialog paramDialog, View paramView) {
        ListFileAdapter listFileAdapter = paramListFileActivity.mListFileAdapter;
        if (listFileAdapter != null) {
            paramDialog.cancel();
            for (File file : listFileAdapter.getMSelectedFile()) {
                if (file.exists())
                    file.delete();
            }
        }
        ContextKt.showNotice(paramListFileActivity, R.string.txt_delete_successfully);
        paramListFileActivity.initData();
    }

    private static final void showDeleteDialog$lambda$19$lambda$18(Dialog paramDialog, View paramView) {
        paramDialog.cancel();
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

    public void onAdDismissed() {
        gotoNextScreen();
    }

    public void onBackPressed() {
        super.onBackPressed();
        boolean bool;
        LinearLayout linearLayout = (LinearLayout)findCachedViewById(R.id.layoutOption);
        if ((linearLayout).getVisibility() == View.VISIBLE) {
            bool = true;
        } else {
            bool = false;
        }
        if (bool) {
            findCachedViewById(R.id.layoutOption).setVisibility(View.GONE);
            ((TextView)findCachedViewById(R.id.txtTitle)).setText(this.mTitle);
            findCachedViewById(R.id.btnSelectAll).setVisibility(View.GONE);
            findCachedViewById(R.id.btnOrder).setVisibility(View.VISIBLE);
            findCachedViewById(R.id.btnSelect).setVisibility(View.VISIBLE);
            ListFileAdapter listFileAdapter = this.mListFileAdapter;
            if (listFileAdapter != null)
                listFileAdapter.setSelectedMode(false);
        } else {
            finish();
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_list_file);
        initThemeColor();
        Utils utils = Utils.INSTANCE;
        AdView adView = (AdView)findCachedViewById(R.id.adView);
        utils.loadBannerAds(this, adView, AdsManager.INSTANCE.is_show_banner_ads_list_file());
        initData();
        initThemeView();
        handleEvents();
    }

    protected void onDestroy() {
        this.mDialog = null;
        this.mLoadingDialog = null;
        this.mIntent = null;
        this.mListFileAdapter = null;
        super.onDestroy();
    }

    public void onItemClick(String paramString1, String paramString2) {
        Intent intent;
        Log.d("5555555555", paramString2);
        Utils utils = Utils.INSTANCE;
        Context context = (Context)this;
        utils.setFileRecent(paramString2, context);
        if (StringsKt.endsWith(paramString1, ".pdf", false, 2, null)) {
            Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_pdf_viewer", "event_click_goto_page_pdf_viewer");
            // TODO
            //intent = new Intent(context, PdfViewerActivity.class);
            //this.mIntent = intent;
            //intent.putExtra("url", paramString2);
        } else if (StringsKt.endsWith(paramString1, ".doc", false, 2, null) || StringsKt.endsWith(paramString1, ".docx", false, 2, null)) {
//            Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_doc_viewer", "event_click_goto_page_doc_viewer");
//            intent = new Intent(context, WordViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
        } else if (StringsKt.endsWith(paramString1, ".xlsx", false, 2, null) || StringsKt.endsWith(paramString1, ".xls", false, 2, null)) {
//            Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_xlsx_viewer", "event_click_goto_page_xlsx_viewer");
//            intent = new Intent(context, ExelViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
        } else if (StringsKt.endsWith(paramString1, ".pptx", false, 2, null) || StringsKt.endsWith(paramString1, ".ppt", false, 2, null)) {
//            Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_pptx_viewer", "event_click_goto_page_pptx_viewer");
//            intent = new Intent(context, PowerPointViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
        } else if (StringsKt.endsWith(paramString1, ".png", false, 2, null) || StringsKt.endsWith(paramString1, ".jpg", false, 2, null)) {
//            Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_png_viewer", "event_click_goto_page_png_viewer");
//            intent = new Intent(context, ImageViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
        } else if (StringsKt.endsWith(paramString1, ".txt", false, 2, null)) {
//            Utils.INSTANCE.setTrackEvent(context, "event_click_goto_page_txt_viewer", "event_click_goto_page_txt_viewer");
//            intent = new Intent(context, TextViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
        }
        showAds();
    }

    public void onItemSelected() {

    }

    public void onPause() {
        super.onPause();
        // TODO
//        IronSource.onPause((Activity)this);
    }

    public void onResume() {
        super.onResume();
        // TODO
//        IronSource.onResume((Activity)this);
        this.mFiles.clear();
        this.mFiles.addAll(getListFileByType());
        ListFileAdapter listFileAdapter = this.mListFileAdapter;
        if (listFileAdapter != null)
            listFileAdapter.notifyDataSetChanged();
    }

    public void onShowFail() {
        findCachedViewById(R.id.loadingAds).setVisibility(View.GONE);
        gotoNextScreen();
    }

    public void onShowSuccess() {
        findCachedViewById(R.id.loadingAds).setVisibility(View.GONE);
    }

    public final void queryFilesFromDevice(Uri paramUri, String[] paramArrayOfString, String paramString) {
        Cursor cursor = getContentResolver().query(paramUri, paramArrayOfString, paramString, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String str = cursor.getString(0);
                cursor.getLong(1);
                if (StringsKt.endsWith(str, ".pdf", false, 2, null)) {
                    HomeActivity2.Companion.getFilePDFs().add(new File(str));
                    HomeActivity2.Companion.getFileAll().add(new File(str));
                    continue;
                }
                if (StringsKt.endsWith(str, ".doc", false, 2, null) || StringsKt.endsWith(str, ".docx", false, 2, null)) {
                    HomeActivity2.Companion.getFileWords().add(new File(str));
                    HomeActivity2.Companion.getFileAll().add(new File(str));
                    continue;
                }
                if (StringsKt.endsWith(str, ".xlsx", false, 2, null) || StringsKt.endsWith(str, ".xls", false, 2, null)) {
                    HomeActivity2.Companion.getFileExcels().add(new File(str));
                    HomeActivity2.Companion.getFileAll().add(new File(str));
                    continue;
                }
                if (StringsKt.endsWith(str, ".pptx", false, 2, null) || StringsKt.endsWith(str, ".ppt", false, 2, null)) {
                    HomeActivity2.Companion.getFilePowerPoints().add(new File(str));
                    HomeActivity2.Companion.getFileAll().add(new File(str));
                    continue;
                }
                if (StringsKt.endsWith(str, ".txt", false, 2, null)) {
                    HomeActivity2.Companion.getFileTexts().add(new File(str));
                    HomeActivity2.Companion.getFileAll().add(new File(str));
                }
            }
            cursor.close();
        }
    }

    public final class LoadAllFile extends AsyncTask<Void, Void, Void> {

        private final void onPostExecute$lambda$0(ListFileActivity param1ListFileActivity) {

        }

        protected Void doInBackground(Void... param1VarArgs) {
            try {
                ListFileActivity.this.getAllFileScreenShot();
                HomeActivity2.Companion.getFileFavorites().addAll(Utils.INSTANCE.getAllFileFavorites((Context)ListFileActivity.this));
                HomeActivity2.Companion.getFileRecents().addAll(Utils.INSTANCE.getAllFileRecents((Context)ListFileActivity.this));
                Uri uri = MediaStore.Files.getContentUri("external");
                if (Build.VERSION.SDK_INT >= 30) {
                    ListFileActivity listFileActivity = ListFileActivity.this;
                    listFileActivity.queryFilesFromDevice(uri, new String[] { "_data", "_size", "mime_type" }, "_data LIKE '%.pdf' or _data LIKE '%.doc' or _data LIKE '%.docx' or _data LIKE '%.pptx' or _data LIKE '%.ppt' or _data LIKE '%.xlsx' or _data LIKE '%.xls' or _data LIKE '%.txt'");
                } else {
                    ListFileActivity listFileActivity = ListFileActivity.this;
                    File file = new File(Environment.getExternalStorageDirectory().toString());
                    listFileActivity.getAllFile(file);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void param1Void) {
            super.onPostExecute(param1Void);
            ListFileActivity.this.mFiles.addAll(ListFileActivity.this.getListFileByType());
            ListFileAdapter listFileAdapter = ListFileActivity.this.mListFileAdapter;
            if (listFileAdapter != null)
                listFileAdapter.notifyDataSetChanged();
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Dialog dialog = ListFileActivity.this.mLoadingDialog;
                    if (dialog != null)
                        dialog.dismiss();
                }
            },500L );
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Dialog dialog = ListFileActivity.this.mLoadingDialog;
            if (dialog != null)
                dialog.show();
        }
    }


    public final class SortFile extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... param1VarArgs) {
            try {
                int i = ListFileActivity.this.mTypeSort;
                if (i != 0) {
                    if (i != 1) {
                        if (i != 2) {
                            if (i == 3) {
                                ArrayList arrayList = ListFileActivity.this.mFiles;
                                ListFileActivity listFileActivity = ListFileActivity.this;
                                if (arrayList.size() > 1) {
                                    ListFileActivity$SortFile$doInBackground$$inlined$sortBy$3 listFileActivity$SortFile$doInBackground$$inlined$sortBy$3 = new ListFileActivity$SortFile$doInBackground$$inlined$sortBy$3();
                                    CollectionsKt.sortWith(arrayList, listFileActivity$SortFile$doInBackground$$inlined$sortBy$3);
                                }
                            }
                        } else {
                            ArrayList arrayList = ListFileActivity.this.mFiles;
                            if (arrayList.size() > 1) {
                                ListFileActivity$SortFile$doInBackground$$inlined$sortBy$2 listFileActivity$SortFile$doInBackground$$inlined$sortBy$2 = new ListFileActivity$SortFile$doInBackground$$inlined$sortBy$2();
                                CollectionsKt.sortWith(arrayList, listFileActivity$SortFile$doInBackground$$inlined$sortBy$2);
                            }
                        }
                    } else {
                        ArrayList arrayList = ListFileActivity.this.mFiles;
                        if (arrayList.size() > 1) {
                            ListFileActivity$SortFile$doInBackground$$inlined$sortByDescending$1 listFileActivity$SortFile$doInBackground$$inlined$sortByDescending$1 = new ListFileActivity$SortFile$doInBackground$$inlined$sortByDescending$1();
                            CollectionsKt.sortWith(arrayList, listFileActivity$SortFile$doInBackground$$inlined$sortByDescending$1);
                        }
                    }
                } else {
                    ArrayList arrayList = ListFileActivity.this.mFiles;
                    if (arrayList.size() > 1) {
                        ListFileActivity$SortFile$doInBackground$$inlined$sortBy$1 listFileActivity$SortFile$doInBackground$$inlined$sortBy$1 = new ListFileActivity$SortFile$doInBackground$$inlined$sortBy$1();
                        CollectionsKt.sortWith(arrayList, listFileActivity$SortFile$doInBackground$$inlined$sortBy$1);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void param1Void) {
            super.onPostExecute(param1Void);
            ListFileAdapter listFileAdapter = ListFileActivity.this.mListFileAdapter;
            if (listFileAdapter != null)
                listFileAdapter.notifyDataSetChanged();
        }


        public final class ListFileActivity$SortFile$doInBackground$$inlined$sortBy$1<T> implements Comparator {
            @Override
            public int compare(Object param2T1, Object param2T2) {
                String str1 = ((File)param2T1).getName();
                str1 = str1.toLowerCase();
                String str2 = ((File)param2T2).getName();
                str2 = str2.toLowerCase();
                return ComparisonsKt.compareValues(str1, str2);
            }

        }

        public final class ListFileActivity$SortFile$doInBackground$$inlined$sortBy$2<T> implements Comparator {
            public final int compare(Object param2T1, Object param2T2) {
                long l1 = ((File)param2T1).lastModified();
                long l2 = -1L;
                return ComparisonsKt.compareValues(Long.valueOf(l1 * l2), Long.valueOf(((File)param2T2).lastModified() * l2));
            }
        }

        public final class ListFileActivity$SortFile$doInBackground$$inlined$sortBy$3<T> implements Comparator {
            public final int compare(Object param2T1, Object param2T2) {
                File file2 = (File)param2T1;
                Utils utils = Utils.INSTANCE;
                String str2 = file2.getAbsolutePath();
                Context context = ListFileActivity.this.getApplicationContext();
                Long long_ = Long.valueOf(utils.getAccessTimeFile(str2, context));
                File file1 = (File)param2T2;
                String str1 = file1.getAbsolutePath();
                context = ListFileActivity.this.getApplicationContext();
                return ComparisonsKt.compareValues(long_, Long.valueOf(utils.getAccessTimeFile(str1, context)));
            }
        }

        public final class ListFileActivity$SortFile$doInBackground$$inlined$sortByDescending$1<T> implements Comparator {
            public final int compare(Object param2T1, Object param2T2) {
                String str2 = ((File)param2T2).getName();
                str2 = str2.toLowerCase();
                String str1 = ((File)param2T1).getName();
                str1 = str1.toLowerCase();
                return ComparisonsKt.compareValues(str2, str1);
            }
        }
    }

    public final class ListFileActivity$SortFile$doInBackground$$inlined$sortBy$1<T> implements Comparator {
        public final int compare(Object param1T1, Object param1T2) {
            String str1 = ((File)param1T1).getName();
            str1 = str1.toLowerCase();
            String str2 = ((File)param1T2).getName();
            str2 = str2.toLowerCase();
            return ComparisonsKt.compareValues(str1, str2);
        }
    }

    public final class ListFileActivity$SortFile$doInBackground$$inlined$sortBy$2<T> implements Comparator {
        public final int compare(Object param1T1, Object param1T2) {
            long l1 = ((File)param1T1).lastModified();
            long l2 = -1L;
            return ComparisonsKt.compareValues(Long.valueOf(l1 * l2), Long.valueOf(((File)param1T2).lastModified() * l2));
        }
    }

    public final class ListFileActivity$SortFile$doInBackground$$inlined$sortBy$3<T> implements Comparator {
        public final int compare(Object param1T1, Object param1T2) {
            File file2 = (File)param1T1;
            Utils utils1 = Utils.INSTANCE;
            String str2 = file2.getAbsolutePath();
            Context context = ListFileActivity.this.getApplicationContext();
            Long long_ = Long.valueOf(utils1.getAccessTimeFile(str2, context));
            File file1 = (File)param1T2;
            Utils utils2 = Utils.INSTANCE;
            String str1 = file1.getAbsolutePath();
            context = ListFileActivity.this.getApplicationContext();
            return ComparisonsKt.compareValues(long_, Long.valueOf(utils2.getAccessTimeFile(str1, context)));
        }
    }

    public final class ListFileActivity$SortFile$doInBackground$$inlined$sortByDescending$1<T> implements Comparator {
        public final int compare(Object param1T1,Object param1T2) {
            String str2 = ((File)param1T2).getName();
            str2 = str2.toLowerCase();
            String str1 = ((File)param1T1).getName();
            str1 = str1.toLowerCase();
            return ComparisonsKt.compareValues(str2, str1);
        }
    }

    public final class ListFileActivity$initData$1 extends SwipeHelper {

        ListFileActivity$initData$1(View param1View) {
            super((Context)ListFileActivity.this, (RecyclerView)param1View, Boolean.valueOf(false));
        }

        private  final void instantiateUnderlayButton$lambda$4(ListFileActivity param1ListFileActivity, int param1Int) {
            try {
                if (param1ListFileActivity.mFiles.size() > 0) {
                    boolean bool = param1ListFileActivity.mIsDeleTeIndex0;
                    if (bool) {
                        if (param1Int != 0) {
                            Dialog dialog = ContextKt.onCreateDialog((Context)param1ListFileActivity, R.layout.layout_dialog_delete, true);
                            File file = (File)param1ListFileActivity.mFiles.get(param1Int - 1);
                            ((TextView)dialog.findViewById(R.id.txtFileName)).setText(file.getName());
                            dialog.show();
                            Button button2 = (Button)dialog.findViewById(R.id.btnCancel);
                            button2.setOnClickListener(view -> dialog.dismiss());
                            Button button1 = (Button)dialog.findViewById(R.id.btnOK);
                            // TODO
//                            ListFileActivity$initData$1$$ExternalSyntheticLambda3 listFileActivity$initData$1$$ExternalSyntheticLambda3 = new ListFileActivity$initData$1$$ExternalSyntheticLambda3(dialog, param1ListFileActivity, file, param1Int);
//                            button1.setOnClickListener(listFileActivity$initData$1$$ExternalSyntheticLambda3);
                        }
                    } else if (param1Int != 1) {
                        int i;
                        if (param1Int > 0) {
                            i = param1Int - 1;
                        } else {
                            i = param1Int;
                        }
                        Dialog dialog = ContextKt.onCreateDialog((Context)param1ListFileActivity, R.layout.layout_dialog_delete, true);
                        File file = (File)param1ListFileActivity.mFiles.get(i);
                        file = file;
                        ((TextView)dialog.findViewById(R.id.txtFileName)).setText(file.getName());
                        dialog.show();
                        Button button = (Button)dialog.findViewById(R.id.btnCancel);
                        button.setOnClickListener(view -> dialog.dismiss());
                        button = (Button)dialog.findViewById(R.id.btnOK);
                        // TODO
//                        ListFileActivity$initData$1$$ExternalSyntheticLambda5 listFileActivity$initData$1$$ExternalSyntheticLambda5 = new ListFileActivity$initData$1$$ExternalSyntheticLambda5(dialog, param1ListFileActivity, file, param1Int);
//                        button.setOnClickListener(listFileActivity$initData$1$$ExternalSyntheticLambda5);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        private  final void instantiateUnderlayButton$lambda$4$lambda$1(Dialog param1Dialog, ListFileActivity param1ListFileActivity, File param1File, int param1Int, View param1View) {
            param1Dialog.dismiss();
            param1ListFileActivity.deleteFile(param1File);
            param1ListFileActivity.mFiles.remove(param1Int - 1);
            ListFileAdapter listFileAdapter = param1ListFileActivity.mListFileAdapter;
            if (listFileAdapter != null)
                listFileAdapter.notifyDataSetChanged();
        }

        private final void instantiateUnderlayButton$lambda$4$lambda$2(Dialog param1Dialog, View param1View) {
            param1Dialog.dismiss();
        }

        private final void instantiateUnderlayButton$lambda$4$lambda$3(Dialog param1Dialog, ListFileActivity param1ListFileActivity, File param1File, int param1Int, View param1View) {
            param1Dialog.dismiss();
            param1ListFileActivity.deleteFile(param1File);
            if (param1Int < 1) {
                param1ListFileActivity.mFiles.remove(param1Int);
                param1ListFileActivity.mIsDeleTeIndex0 = true;
            } else {
                param1ListFileActivity.mFiles.remove(param1Int - 1);
            }
            ListFileAdapter listFileAdapter = param1ListFileActivity.mListFileAdapter;
            if (listFileAdapter != null)
                listFileAdapter.notifyDataSetChanged();
        }

        private final void instantiateUnderlayButton$lambda$7(ListFileActivity param1ListFileActivity, int param1Int) {
            try {
                if (param1ListFileActivity.mFiles.size() > 0 && param1Int != 1) {
                    Ref.IntRef intRef = new Ref.IntRef();
                    intRef.element = param1Int;
                    if (param1Int > 0)
                        intRef.element = param1Int - 1;
                    Dialog dialog = ContextKt.onCreateDialog((Context)param1ListFileActivity, R.layout.layout_dialog_rename, true);
                    ((EditText)dialog.findViewById(R.id.edtFileName)).setHint(((File)param1ListFileActivity.mFiles.get(intRef.element)).getName());
                    dialog.show();
                    Button button = (Button)dialog.findViewById(R.id.btnCancelRename);
                    button.setOnClickListener(view -> dialog.dismiss());
                    button = (Button)dialog.findViewById(R.id.btnOKRename);
                    // TODO
//                    ListFileActivity$initData$1$$ExternalSyntheticLambda1 listFileActivity$initData$1$$ExternalSyntheticLambda1 = new ListFileActivity$initData$1$$ExternalSyntheticLambda1(dialog, param1ListFileActivity, intRef);
//                    button.setOnClickListener(listFileActivity$initData$1$$ExternalSyntheticLambda1);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        private final void instantiateUnderlayButton$lambda$7$lambda$5(Dialog param1Dialog, View param1View) {
            param1Dialog.dismiss();
        }

        private final void instantiateUnderlayButton$lambda$7$lambda$6(Dialog param1Dialog, ListFileActivity param1ListFileActivity, Ref.IntRef param1IntRef, View param1View) {
            if (TextUtils.isEmpty(StringsKt.trim(((EditText)param1Dialog.findViewById(R.id.edtFileName)).getText().toString()).toString())) {
                Toast.makeText((Context)param1ListFileActivity, "Please Enter", Toast.LENGTH_LONG).show();
            } else {
                param1Dialog.dismiss();
                File file1 = (File)param1ListFileActivity.mFiles.get(param1IntRef.element);
                String str3 = ((EditText)param1Dialog.findViewById(R.id.edtFileName)).getText().toString();
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append(file1.getParent());
                stringBuilder1.append('/');
                stringBuilder1.append(str3);
                Utils utils = Utils.INSTANCE;
                String str5 = file1.getName();
                stringBuilder1.append(utils.getEndFile(str5));
                String str1 = stringBuilder1.toString();
                String str4 = file1.getParent();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str3);
                str3 = file1.getName();
                stringBuilder2.append(utils.getEndFile(str3));
                File file2 = new File(str4, stringBuilder2.toString());
                file1.renameTo(file2);
                param1ListFileActivity.mFiles.remove(param1IntRef.element);
                param1ListFileActivity.mFiles.add(param1IntRef.element, file2);
                ListFileAdapter listFileAdapter = param1ListFileActivity.mListFileAdapter;
                if (listFileAdapter != null)
                    listFileAdapter.notifyDataSetChanged();
                String str2 = file1.getAbsolutePath();
                Context context = param1ListFileActivity.getApplicationContext();
                if (utils.isFileFavorite(str2, context)) {
                    String str = file1.getAbsolutePath();
                    Context context2 = param1ListFileActivity.getApplicationContext();
                    utils.setFileFavorite(str, context2, false);
                    utils = Utils.INSTANCE;
                    Context context1 = param1ListFileActivity.getApplicationContext();
                    utils.setFileFavorite(str1, context1, true);
                }
            }
        }

        public void instantiateUnderlayButton(RecyclerView.ViewHolder param1ViewHolder, List<SwipeHelper.UnderlayButton> param1List) {
    //TODO
            //            if (param1List != null)
//                param1List.add(new SwipeHelper.UnderlayButton(ListFileActivity.this.getString(R.string.delete), AppCompatResources.getDrawable(ListFileActivity.this.getApplicationContext(), R.drawable.ic_delete_file), Color.parseColor("#E56D6D"), Color.parseColor("#ffffff"), new ListFileActivity$initData$1$$ExternalSyntheticLambda6(ListFileActivity.this)));
//            if (param1List != null)
//                param1List.add(new SwipeHelper.UnderlayButton(ListFileActivity.this.getString(R.string.rename), AppCompatResources.getDrawable(ListFileActivity.this.getApplicationContext(), R.drawable.ic_rename_file), Color.parseColor("#6DDE9C"), Color.parseColor("#ffffff"), new ListFileActivity$initData$1$$ExternalSyntheticLambda7(ListFileActivity.this)));
        }
    }
}
