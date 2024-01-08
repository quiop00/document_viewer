package com.ryu.document_reader.activity.imagetopdf;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.activity.imagetopdf.convert.ConvertActivity;
import com.ryu.document_reader.adapter.ItemChoosePictureAdapter;
import com.ryu.document_reader.adapter.ItemChoosePictureVerAdapter;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.ads.TemplateView;
import com.ryu.document_reader.ads.callback.AdsListener;
import com.ryu.document_reader.callback.OnItemFileListener;
import com.ryu.document_reader.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import kotlin.text.StringsKt;

public final class ChooseImageToPdfActivity extends BaseActivity implements OnItemFileListener, AdsListener {
    public static final Companion Companion = new Companion();

    private static ArrayList<String> IMAGE_SCALE_NAME;

    private static ArrayList<String> PAGE_SIZE_NAME = new ArrayList<String>(Arrays.asList("A3 (29.7cm x 42.0cm)", "A4 (21.0cm x 29.7cm)", "A5 (14.8cm x 21.0cm)", "B4 (25cm x 35.3cm)", "B5 (17.6cm x 25cm)", "Letter (21.6cm x 27.9cm)", "Tabloid (27.9cm x 43.2cm", "Legal (21,6cm x 35.6cm)", "Executive (18.4cm x 26.7cm)", "Postcard (10.0cm x 14.7cm)",
            "American Foolscap(21.6cm x 33.0cm)", "Europe Foolscap (22.9cm x 33.0cm)"));

    private static ArrayList<Rectangle> PAGE_SIZE_VALUE = new ArrayList<Rectangle>(Arrays.asList(PageSize.A3, PageSize.A4, PageSize.A5, PageSize.B4, PageSize.B5, PageSize.LETTER, PageSize.TABLOID, PageSize.LEGAL, PageSize.EXECUTIVE, PageSize.POSTCARD,
            PageSize.FLSA, PageSize.FLSE));

    public static final int positionOfImageScale = 0;

    public static final int positionOfPageSize = 1;

    public Map<Integer, View> _$_findViewCache;

    private Dialog loadingDialog;

    private Dialog mDialogWait;

    private File mFileDir;

    private ArrayList<String> mImageSelected = new ArrayList<String>();

    private boolean mIsVertical;

    private ItemChoosePictureAdapter mItemChoosePictureAdapter;

    private ItemChoosePictureVerAdapter mItemChoosePictureVerAdapter;

    private ArrayList<String> mListImages;

    private String mNameFile = "";

    static {
        IMAGE_SCALE_NAME = new ArrayList<String>(Arrays.asList(new String[] { "Fit width or height", "Fill the entire page" }));
    }

    private final void createPDF() {
        try {
            if (this.mImageSelected.size() > 0) {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.layout_dialog_creating_pdf);
                dialog.setCancelable(false);
                Window window = dialog.getWindow();
//                if (window != null)
//                    window.setBackgroundDrawableResource(17170445);
                window = dialog.getWindow();
                if (window != null)
                    window.setLayout(-1, -2);
                dialog.show();
                Button button2 = (Button)dialog.findViewById(R.id.btnCancel);
                // TODO
//                ChooseImageToPdfActivity$$ExternalSyntheticLambda0 chooseImageToPdfActivity$$ExternalSyntheticLambda0 = new ChooseImageToPdfActivity$$ExternalSyntheticLambda0();
//                this(dialog);
//                button2.setOnClickListener(chooseImageToPdfActivity$$ExternalSyntheticLambda0);
//                Button button1 = (Button)dialog.findViewById(R.id.btnOK);
//                ChooseImageToPdfActivity$$ExternalSyntheticLambda1 chooseImageToPdfActivity$$ExternalSyntheticLambda1 = new ChooseImageToPdfActivity$$ExternalSyntheticLambda1();
//                this(dialog, this);
//                button1.setOnClickListener(chooseImageToPdfActivity$$ExternalSyntheticLambda1);
            } else {
                Toast.makeText((Context)this, getString(R.string.toast_choose_image), Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            Toast.makeText((Context)this, getString(R.string.txt_some_thing_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    private static final void createPDF$lambda$4(Dialog paramDialog, View paramView) {
        paramDialog.dismiss();
    }

    private static final void createPDF$lambda$5(Dialog paramDialog, ChooseImageToPdfActivity paramChooseImageToPdfActivity, View paramView) {
        paramDialog.dismiss();
        String str = StringsKt.trim(((EditText)paramDialog.findViewById(R.id.edtNameFile)).getText().toString()).toString();
        paramChooseImageToPdfActivity.mNameFile = str;
        if (TextUtils.isEmpty(str)) {
            Toast.makeText((Context)paramChooseImageToPdfActivity, paramChooseImageToPdfActivity.getString(R.string.txt_please_enter_file_name), Toast.LENGTH_LONG).show();
        } else {
            paramChooseImageToPdfActivity.showAds();
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
                            String str = file.getName();
                            paramFile = null;
                            if (StringsKt.endsWith$default(str, ".png", false, 2, null) || StringsKt.endsWith$default(str, ".jpg", false, 2, null)) {
                                ArrayList<String> arrayList1 = new ArrayList<>();
                                ArrayList<String> arrayList2 = this.mListImages;
                                if (arrayList2 == null) {
                                } else {
                                    arrayList1 = arrayList2;
                                }
                                arrayList1.add(file.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private final void gotoNextScreen() {
        Intent intent = new Intent((Context)this, ConvertActivity.class);
        intent.putExtra("IMAGE_LIST", this.mImageSelected);
        intent.putExtra("name", this.mNameFile);
        startActivity(intent);
    }

    private final void handleEvents() {
        // TODO
        //((ImageView)_$_findCachedViewById(R.id.btnBackSelect)).setOnClickListener(new ChooseImageToPdfActivity$$ExternalSyntheticLambda2(this));
        //((TextView)_$_findCachedViewById(R.id.btnImport)).setOnClickListener(new ChooseImageToPdfActivity$$ExternalSyntheticLambda3(this));
        //((ImageView)_$_findCachedViewById(R.id.btnStyleGrid)).setOnClickListener(new ChooseImageToPdfActivity$$ExternalSyntheticLambda4(this));
        //((ImageView)_$_findCachedViewById(R.id.btnStyleVer)).setOnClickListener(new ChooseImageToPdfActivity$$ExternalSyntheticLambda5(this));
    }

    private static final void handleEvents$lambda$0(ChooseImageToPdfActivity paramChooseImageToPdfActivity, View paramView) {
        paramChooseImageToPdfActivity.onBackPressed();
    }

    private static final void handleEvents$lambda$1(ChooseImageToPdfActivity paramChooseImageToPdfActivity, View paramView) {
        if (paramChooseImageToPdfActivity.getIntent().getBooleanExtra("isPdfEditor", false)) {
            if (paramChooseImageToPdfActivity.mImageSelected.size() > 0) {
                Intent intent = new Intent();
                ArrayList<String> arrayList = paramChooseImageToPdfActivity.mImageSelected;
                intent.putExtra("pathImage", arrayList.get(arrayList.size() - 1));
                paramChooseImageToPdfActivity.setResult(-1, intent);
                paramChooseImageToPdfActivity.finish();
            } else {
                    Toast.makeText((Context)paramChooseImageToPdfActivity, paramChooseImageToPdfActivity.getString(R.string.toast_choose_image), Toast.LENGTH_SHORT).show();
            }
        } else {
            paramChooseImageToPdfActivity.createPDF();
        }
    }

    private static final void handleEvents$lambda$2(ChooseImageToPdfActivity paramChooseImageToPdfActivity, View paramView) {
        if (paramChooseImageToPdfActivity.mIsVertical) {
            paramChooseImageToPdfActivity.mIsVertical = false;
            ((ImageView)paramChooseImageToPdfActivity._$_findCachedViewById(R.id.btnStyleGrid)).setBackgroundColor(Color.parseColor("#EAEFF1"));
            ((ImageView)paramChooseImageToPdfActivity._$_findCachedViewById(R.id.btnStyleVer)).setBackgroundColor(Color.parseColor("#ffffff"));
            ((RecyclerView)paramChooseImageToPdfActivity._$_findCachedViewById(R.id.rvChooseImageGrid)).setVisibility(View.VISIBLE);
            ((RecyclerView)paramChooseImageToPdfActivity._$_findCachedViewById(R.id.rvChooseImageVer)).setVisibility(View.GONE);
        }
    }

    private static final void handleEvents$lambda$3(ChooseImageToPdfActivity paramChooseImageToPdfActivity, View paramView) {
        if (!paramChooseImageToPdfActivity.mIsVertical) {
            paramChooseImageToPdfActivity.mIsVertical = true;
            ((ImageView)paramChooseImageToPdfActivity._$_findCachedViewById(R.id.btnStyleVer)).setBackgroundColor(Color.parseColor("#EAEFF1"));
            ((ImageView)paramChooseImageToPdfActivity._$_findCachedViewById(R.id.btnStyleGrid)).setBackgroundColor(Color.parseColor("#ffffff"));
            ((RecyclerView)paramChooseImageToPdfActivity._$_findCachedViewById(R.id.rvChooseImageGrid)).setVisibility(View.GONE);
            ((RecyclerView)paramChooseImageToPdfActivity._$_findCachedViewById(R.id.rvChooseImageVer)).setVisibility(View.VISIBLE);
        }
    }

    private final void initData() {
        this.mListImages = new ArrayList<String>();
        Context context = (Context)this;
        ArrayList<String> arrayList2 = this.mListImages;
        ItemChoosePictureVerAdapter itemChoosePictureVerAdapter2 = null;
        ArrayList<String> arrayList1 = arrayList2;
        if (arrayList2 == null) {
            arrayList1 = null;
        }
        arrayList2 = this.mImageSelected;
        ChooseImageToPdfActivity chooseImageToPdfActivity = this;
        this.mItemChoosePictureAdapter = new ItemChoosePictureAdapter(context, arrayList1, arrayList2, chooseImageToPdfActivity);
        arrayList2 = this.mListImages;
        arrayList1 = arrayList2;
        if (arrayList2 == null) {
            arrayList1 = null;
        }
        this.mItemChoosePictureVerAdapter = new ItemChoosePictureVerAdapter(context, arrayList1, this.mImageSelected, chooseImageToPdfActivity);
        ((RecyclerView)_$_findCachedViewById(R.id.rvChooseImageGrid)).setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager(context, 3));
        RecyclerView recyclerView2 = (RecyclerView)_$_findCachedViewById(R.id.rvChooseImageGrid);
        ItemChoosePictureAdapter itemChoosePictureAdapter2 = this.mItemChoosePictureAdapter;
        ItemChoosePictureAdapter itemChoosePictureAdapter1 = itemChoosePictureAdapter2;
        if (itemChoosePictureAdapter2 == null) {
            itemChoosePictureAdapter1 = null;
        }
        recyclerView2.setAdapter((RecyclerView.Adapter)itemChoosePictureAdapter1);
        RecyclerView recyclerView1 = (RecyclerView)_$_findCachedViewById(R.id.rvChooseImageVer);
        ItemChoosePictureVerAdapter itemChoosePictureVerAdapter1 = this.mItemChoosePictureVerAdapter;
        if (itemChoosePictureVerAdapter1 == null) {
            itemChoosePictureVerAdapter1 = itemChoosePictureVerAdapter2;
        }
        recyclerView1.setAdapter(itemChoosePictureVerAdapter1);
        this.mFileDir = new File(Environment.getExternalStorageDirectory().toString());
        Dialog dialog = Utils.INSTANCE.onCreateProgressDialog(context);
        this.loadingDialog = dialog;
        if (dialog != null)
            dialog.show();
        (new LoadAllFile()).execute();
        dialog = new Dialog(context);
        this.mDialogWait = dialog;
        dialog.setContentView(R.layout.layout_dialog_wait_handle);
        dialog = this.mDialogWait;
        if (dialog != null)
            dialog.setCancelable(false);
        dialog = this.mDialogWait;
        if (dialog != null) {
            Window window = dialog.getWindow();
//            if (window != null)
//                window.setBackgroundDrawableResource(17170445);
        }
        dialog = this.mDialogWait;
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null)
                window.setLayout(-1, -2);
        }
        TextView textView = (TextView)_$_findCachedViewById(R.id.btnImport);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.txt_import));
        stringBuilder.append("(0)");
        textView.setText(stringBuilder.toString());
    }

    private final void loadAds() {
        Utils utils = Utils.INSTANCE;
        Context context = (Context)this;
        TemplateView templateView = (TemplateView)_$_findCachedViewById(R.id.my_template);
        String str = getString(R.string.alldoc_native_image_list_id);
        utils.loadNativeAds(context, templateView, str, AdsManager.INSTANCE.is_show_native_ads_image_list());
    }

    private final void showAds() {
        if (BillingClientSetup.isUpgraded((Context)this) || !AdsManager.INSTANCE.is_show_inter_ads_home()) {
            gotoNextScreen();
            return;
        }
        _$_findCachedViewById(R.id.layoutAdsLoading).setVisibility(View.VISIBLE);
        AdsManager.INSTANCE.showAds((Activity)this, this);
    }

    private final void sort() {
        ArrayList<String> arrayList2 = this.mListImages;
        ArrayList<String> arrayList1 = arrayList2;
        if (arrayList2 == null) {
            arrayList1 = null;
        }
        // TODO
        //Collections.sort(arrayList1, new ChooseImageToPdfActivity$$ExternalSyntheticLambda6());
    }

    private static final int sort$lambda$6(String paramString1, String paramString2) {
        paramString1 = paramString1.toLowerCase(Locale.ROOT);
        paramString2 = paramString2.toLowerCase(Locale.ROOT);
        return paramString1.compareTo(paramString2);
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
        setContentView(R.layout.activity_choose_image_to_pdf);
        loadAds();
        initData();
        handleEvents();
    }

    public void onItemFileClick(int paramInt) {
        TextView textView = (TextView)_$_findCachedViewById(R.id.btnImport);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.txt_import));
        stringBuilder.append('(');
        stringBuilder.append(paramInt);
        stringBuilder.append(')');
        textView.setText(stringBuilder.toString());
    }

    public final void onItemFileClickMore(int paramInt) {}

    public void onShowFail() {
        gotoNextScreen();
    }

    public void onShowSuccess() {
        _$_findCachedViewById(R.id.layoutAdsLoading).setVisibility(View.GONE);
    }

    public final void queryFilesFromDevice(Uri paramUri, String[] paramArrayOfString, String paramString) {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(paramUri, paramArrayOfString, paramString, null, "date_added DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                paramString = cursor.getString(0);
                cursor.getLong(1);
                ArrayList<String> arrayList2 = this.mListImages;
                ArrayList<String> arrayList1 = arrayList2;
                if (arrayList2 == null) {
                    arrayList1 = null;
                }
                arrayList1.add(paramString);
            }
            cursor.close();
        }
    }

    public static final class Companion {
        private Companion() {}

        public final ArrayList<String> getIMAGE_SCALE_NAME() {
            return ChooseImageToPdfActivity.IMAGE_SCALE_NAME;
        }

        public final ArrayList<String> getPAGE_SIZE_NAME() {
            return ChooseImageToPdfActivity.PAGE_SIZE_NAME;
        }

        public final ArrayList<Rectangle> getPAGE_SIZE_VALUE() {
            return ChooseImageToPdfActivity.PAGE_SIZE_VALUE;
        }

        public final void setIMAGE_SCALE_NAME(ArrayList<String> param1ArrayList) {
            ChooseImageToPdfActivity.IMAGE_SCALE_NAME = param1ArrayList;
        }

        public final void setPAGE_SIZE_NAME(ArrayList<String> param1ArrayList) {
            ChooseImageToPdfActivity.PAGE_SIZE_NAME = param1ArrayList;
        }

        public final void setPAGE_SIZE_VALUE(ArrayList<Rectangle> param1ArrayList) {
            ChooseImageToPdfActivity.PAGE_SIZE_VALUE = param1ArrayList;
        }
    }

    public final class LoadAllFile extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... param1VarArgs) {
            Uri uri = MediaStore.Files.getContentUri("external");
            ChooseImageToPdfActivity.this.queryFilesFromDevice(uri, new String[] { "_data", "_size", "mime_type" }, "_data LIKE '%.png' or _data LIKE '%.jpg'");
            return null;
        }

        protected void onPostExecute(Void param1Void) {
            super.onPostExecute(param1Void);
            ItemChoosePictureAdapter itemChoosePictureAdapter2 = ChooseImageToPdfActivity.this.mItemChoosePictureAdapter;
            ArrayList arrayList2 = null;
            ItemChoosePictureAdapter itemChoosePictureAdapter1 = itemChoosePictureAdapter2;
            if (itemChoosePictureAdapter2 == null) {
                itemChoosePictureAdapter1 = null;
            }
            itemChoosePictureAdapter1.notifyDataSetChanged();
            ItemChoosePictureVerAdapter itemChoosePictureVerAdapter2 = ChooseImageToPdfActivity.this.mItemChoosePictureVerAdapter;
            ItemChoosePictureVerAdapter itemChoosePictureVerAdapter1 = itemChoosePictureVerAdapter2;
            if (itemChoosePictureVerAdapter2 == null) {
                itemChoosePictureVerAdapter1 = null;
            }
            itemChoosePictureVerAdapter1.notifyDataSetChanged();
            ArrayList arrayList1 = ChooseImageToPdfActivity.this.mListImages;
            if (arrayList1 == null) {
                arrayList1 = arrayList2;
            }
            if (arrayList1.size() == 0)
                ((RelativeLayout)ChooseImageToPdfActivity.this._$_findCachedViewById(R.id.layoutNotFile)).setVisibility(View.VISIBLE);
            if (ChooseImageToPdfActivity.this.loadingDialog != null) {
                Dialog dialog = ChooseImageToPdfActivity.this.loadingDialog;
                if (dialog.isShowing()) {
                    dialog = ChooseImageToPdfActivity.this.loadingDialog;
                    if (dialog != null)
                        dialog.dismiss();
                }
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
