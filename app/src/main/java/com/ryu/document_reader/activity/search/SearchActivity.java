package com.ryu.document_reader.activity.search;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.activity.SplashActivity;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.activity.home.HomeActivity2;
import com.ryu.document_reader.adapter.ListFileAdapter;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.ads.callback.AdsListener;
import com.ryu.document_reader.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import kotlin.text.StringsKt;

public final class SearchActivity extends BaseActivity implements ListFileAdapter.OnItemListener, AdsListener {
    public Map<Integer, View> _$_findViewCache;

    private int mColor;

    private ArrayList<File> mFileTemps = new ArrayList<File>();

    private ArrayList<File> mFiles = new ArrayList<File>();

    private Intent mIntent;

    private ListFileAdapter mListFileAdapter;

    private String mType = "all";

    private final ArrayList<File> getListFileByType() {
        String str = this.mType;
        switch (str) {
            case "favorite":
                ((TextView)_$_findCachedViewById(R.id.txtTitle)).setText(getString(R.string.fab_favourite));
                return HomeActivity2.Companion.getFileFavorites();
            case "power_point":
                ((TextView)_$_findCachedViewById(R.id.txtTitle)).setText("POWER POINT");
                return HomeActivity2.Companion.getFilePowerPoints();
            case "image":
                ((TextView) _$_findCachedViewById(R.id.txtTitle)).setText(getString(R.string.screenshot));
                return HomeActivity2.Companion.getFileImages();
            case "excel":
                ((TextView) _$_findCachedViewById(R.id.txtTitle)).setText("EXCEL");
                return HomeActivity2.Companion.getFileExcels();
            case "word":
                ((TextView) _$_findCachedViewById(R.id.txtTitle)).setText("WORD");
                return HomeActivity2.Companion.getFileWords();
            case "text":
                ((TextView) _$_findCachedViewById(R.id.txtTitle)).setText("TEXT");
                return HomeActivity2.Companion.getFileTexts();
            case "pdf":
                ((TextView) _$_findCachedViewById(R.id.txtTitle)).setText("PDF");
                return HomeActivity2.Companion.getFilePDFs();
            case "recent":
                ((TextView)_$_findCachedViewById(R.id.txtTitle)).setText(getString(R.string.recent_files));
                return HomeActivity2.Companion.getFileRecents();
            default:
                ((TextView)_$_findCachedViewById(R.id.txtTitle)).setText(getString(R.string.all));
                return HomeActivity2.Companion.getFileAll();
        }

    }

    private final void gotoNextScreen() {
        Intent intent2 = this.mIntent;
        Intent intent1 = intent2;
        if (intent2 == null) {
            intent1 = null;
        }
        startActivity(intent1);
    }

    private final void handleEvents() {
        ((ImageView)_$_findCachedViewById(R.id.btnBack)).setOnClickListener(view -> onBackPress());
        ((ImageView)_$_findCachedViewById(R.id.imgClear)).setOnClickListener(view -> clearText());
        ((EditText)_$_findCachedViewById(R.id.edtSearch)).addTextChangedListener(new SearchActivity$handleEvents$3());
    }

    private final void clearText() {
        ((EditText)_$_findCachedViewById(R.id.edtSearch)).setText("");
    }

    private final void initData() {
        String str = getIntent().getStringExtra("type");
        if (!TextUtils.isEmpty(str)) {
            this.mType = str;
        }
        this.mFiles.addAll(getListFileByType());
        this.mFileTemps.addAll(this.mFiles);
        this.mListFileAdapter = new ListFileAdapter(this.mFiles, this, this.mColor, false, null);
        RecyclerView recyclerView = (RecyclerView)_$_findCachedViewById(R.id.rvSearch);
        ListFileAdapter listFileAdapter2 = this.mListFileAdapter;
        ListFileAdapter listFileAdapter1 = listFileAdapter2;
        if (listFileAdapter2 == null) {
            listFileAdapter1 = null;
        }
        recyclerView.setAdapter((RecyclerView.Adapter)listFileAdapter1);
        EditText editText = (EditText)_$_findCachedViewById(R.id.edtSearch);
        showKeyboard(editText);
    }

    private final void initThemView() {
        if (SplashActivity.Companion.getTheme() == 1)
            ((FrameLayout)_$_findCachedViewById(R.id.layoutMain)).setBackgroundColor(ContextCompat.getColor((Context)this, R.color.colorBlackAltLight));
    }

    private final void initThemeColor() {
        try {
            String str = getIntent().getStringExtra("type");
            Context context = (Context)this;
            Utils utils = Utils.INSTANCE;
            Activity activity = (Activity)this;
            this.mColor = ContextCompat.getColor(context, utils.getIdColorByType(activity, str));
            Utils.INSTANCE.setStatusBarColor((Activity)this, this.mColor);
            ((RelativeLayout)_$_findCachedViewById(R.id.toolbar)).setBackgroundColor(this.mColor);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private final void showAds() {
        if (!BillingClientSetup.isUpgraded((Context)this) && AdsManager.INSTANCE.is_show_inter_ads_search_file()) {
            _$_findCachedViewById(R.id.loadingAds).setVisibility(View.VISIBLE);
            AdsManager.INSTANCE.showAds((Activity)this, this);
        } else {
            gotoNextScreen();
        }
    }

    private static final void showKeyboard$lambda$0(EditText paramEditText) {
        paramEditText.requestFocus();
        Object object = paramEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        ((InputMethodManager)object).showSoftInput((View)paramEditText, 1);
    }

    public void _$_clearFindViewByIdCache() {
        this._$_findViewCache.clear();
    }

    public View _$_findCachedViewById(int paramInt) {
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

    public void onAdDismissed() {
        gotoNextScreen();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_search);
        initThemeColor();
        initData();
        initThemView();
        handleEvents();
    }

    public void onItemClick(String paramString1, String paramString2) {
        Intent intent;

        Utils utils = Utils.INSTANCE;
        Context context = (Context)this;
        utils.setFileRecent(paramString2, context);
        // TODO
//        if (StringsKt.endsWith(paramString1, ".pdf", false, 2, null)) {
//            intent = new Intent(context, PdfViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
//        } else if (StringsKt.endsWith(paramString1, ".doc", false, 2, null) || StringsKt.endsWith(paramString1, ".docx", false, 2, null)) {
//            intent = new Intent(context, WordViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
//        } else if (StringsKt.endsWith(paramString1, ".xlsx", false, 2, null) || StringsKt.endsWith(paramString1, ".xls", false, 2, null)) {
//            intent = new Intent(context, ExelViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
//        } else if (StringsKt.endsWith(paramString1, ".pptx", false, 2, null) || StringsKt.endsWith(paramString1, ".ppt", false, 2, null)) {
//            intent = new Intent(context, PowerPointViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
//        } else if (StringsKt.endsWith(paramString1, ".png", false, 2, null) || StringsKt.endsWith(paramString1, ".jpg", false, 2, null)) {
//            intent = new Intent(context, ImageViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
//        } else if (StringsKt.endsWith(paramString1, ".txt", false, 2, null)) {
//            intent = new Intent(context, TextViewerActivity.class);
//            this.mIntent = intent;
//            intent.putExtra("url", paramString2);
//        }
        showAds();
    }

    public void onPause() {
        super.onPause();
//        IronSource.onPause((Activity)this);
    }

    public void onResume() {
        super.onResume();
//        IronSource.onResume((Activity)this);
    }

    public void onShowFail() {
        _$_findCachedViewById(R.id.loadingAds).setVisibility(View.GONE);
        gotoNextScreen();
    }

    public void onShowSuccess() {
        _$_findCachedViewById(R.id.loadingAds).setVisibility(View.GONE);
    }

    public final void showKeyboard(EditText paramEditText) {
        // TODO
        //paramEditText.post(new SearchActivity$$ExternalSyntheticLambda2(paramEditText));
    }

    public final class SearchActivity$handleEvents$3 implements TextWatcher {

        public void afterTextChanged(Editable param1Editable) {}

        public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}

        public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            SearchActivity.this.mFiles.clear();
            if (TextUtils.isEmpty(param1CharSequence)) {
                ((ImageView)SearchActivity.this._$_findCachedViewById(R.id.imgClear)).setVisibility(View.GONE);
                SearchActivity.this.mFiles.addAll(SearchActivity.this.mFileTemps);
            } else {
                ((ImageView)SearchActivity.this._$_findCachedViewById(R.id.imgClear)).setVisibility(View.VISIBLE);
                ArrayList<File> arrayList2 = SearchActivity.this.mFiles;
                ArrayList arrayList = SearchActivity.this.mFileTemps;
                ArrayList<File> arrayList1 = new ArrayList();
                for (Object file : arrayList) {
                    String str1 = ((File)file).getName();
                    str1 = str1.toLowerCase();
                    String str2 = str1;
                    str1 = String.valueOf(param1CharSequence).toLowerCase();
                    if (StringsKt.indexOf$default(str2, str1, 0, false, 6, null) != -1) {
                        param1Int1 = 1;
                    } else {
                        param1Int1 = 0;
                    }
                    if (param1Int1 != 0)
                        arrayList1.add((File) file);
                }
                arrayList2.addAll(arrayList1);
            }
            ListFileAdapter listFileAdapter2 = SearchActivity.this.mListFileAdapter;
            ListFileAdapter listFileAdapter1 = listFileAdapter2;
            if (listFileAdapter2 == null) {
                listFileAdapter1 = null;
            }
            listFileAdapter1.notifyDataSetChanged();
        }
    }
}