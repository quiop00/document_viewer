package com.ryu.document_reader.activity.imagetopdf.convert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.adapter.ItemChoosePictureAdapter;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.ads.TemplateView;
import com.ryu.document_reader.ads.callback.AdsListener;
import com.ryu.document_reader.callback.OnItemFileListener;
import com.ryu.document_reader.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;

import kotlin.text.StringsKt;

public final class ConvertActivity extends BaseActivity implements OnItemFileListener, AdsListener {
    public Map<Integer, View> _$_findViewCache;

    private Dialog mDialogWait;

    private ArrayList<String> mImageSelected = new ArrayList<String>();

    private ItemChoosePictureAdapter mItemChoosePictureAdapter;

    private String mNameFile = "";

    private String mPathSavePdf = "";

    private final void createPDF() {
        try {
            if (this.mImageSelected.size() > 0) {
                Dialog dialog = new Dialog((Context)this);
                dialog.setContentView(R.layout.layout_dialog_creating_pdf);
                dialog.setCancelable(false);
                Window window = dialog.getWindow();
//                if (window != null)
//                    window.setBackgroundDrawableResource(17170445);
                window = dialog.getWindow();
                if (window != null)
                    window.setLayout(-1, -2);
                ((EditText)dialog.findViewById(R.id.edtNameFile)).setText(this.mNameFile);
                ((EditText)dialog.findViewById(R.id.edtNameFile)).setSelection(this.mNameFile.length());
                ((EditText)dialog.findViewById(R.id.edtNameFile)).requestFocus();
                dialog.show();
                Button button2 = (Button)dialog.findViewById(R.id.btnCancel);
                // TODO
//                ConvertActivity$$ExternalSyntheticLambda0 convertActivity$$ExternalSyntheticLambda0 = new ConvertActivity$$ExternalSyntheticLambda0(dialog);
//                button2.setOnClickListener(convertActivity$$ExternalSyntheticLambda0);
                Button button1 = (Button)dialog.findViewById(R.id.btnOK);
                // TODO
//                ConvertActivity$$ExternalSyntheticLambda1 convertActivity$$ExternalSyntheticLambda1 = new ConvertActivity$$ExternalSyntheticLambda1(dialog, this);
//                button1.setOnClickListener(convertActivity$$ExternalSyntheticLambda1);
            } else {
                Toast.makeText((Context)this, getString(R.string.toast_choose_image), Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            Toast.makeText((Context)this, getString(R.string.txt_some_thing_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    private static final void createPDF$lambda$3(Dialog paramDialog, View paramView) {
        paramDialog.dismiss();
    }

    private static final void createPDF$lambda$4(Dialog paramDialog, ConvertActivity paramConvertActivity, View paramView) {
        paramDialog.dismiss();
        paramConvertActivity.mNameFile = StringsKt.trim(((EditText)paramDialog.findViewById(R.id.edtNameFile)).getText().toString()).toString();
        ((TextView)paramConvertActivity._$_findCachedViewById(R.id.txtTitle)).setText(paramConvertActivity.mNameFile);
        if (TextUtils.isEmpty(paramConvertActivity.mNameFile))
            Toast.makeText((Context)paramConvertActivity, paramConvertActivity.getString(R.string.txt_please_enter_file_name), Toast.LENGTH_LONG).show();
    }

    private final void gotoNextScreen() {
        _$_findCachedViewById(R.id.layoutAdsLoading).setVisibility(View.GONE);
        // TODO
//        Intent intent = new Intent((Context)this, SaveFileActivity.class);
//        intent.putExtra("path", this.mPathSavePdf);
//        intent.putExtra("name", this.mNameFile);
//        startActivity(intent);
        finish();
    }

    private final void handleEvent() {
        // TODO
//        ((ImageView)_$_findCachedViewById(R.id.btnBack)).setOnClickListener(new ConvertActivity$$ExternalSyntheticLambda2(this));
//        ((ImageView)_$_findCachedViewById(R.id.btnRename)).setOnClickListener(new ConvertActivity$$ExternalSyntheticLambda3(this));
//        ((TextView)_$_findCachedViewById(R.id.btnConvert)).setOnClickListener(new ConvertActivity$$ExternalSyntheticLambda4(this));
    }

    private static final void handleEvent$lambda$0(ConvertActivity paramConvertActivity, View paramView) {
        paramConvertActivity.finish();
    }

    private static final void handleEvent$lambda$1(ConvertActivity paramConvertActivity, View paramView) {
        paramConvertActivity.createPDF();
    }

    private  final void handleEvent$lambda$2(ConvertActivity paramConvertActivity, View paramView) {
        (new CreatingPDF(paramConvertActivity.mImageSelected)).execute();
    }

    private final void initData() {
        ArrayList<? extends String> arrayList = getIntent().getStringArrayListExtra("IMAGE_LIST");
        if (arrayList != null) {
            arrayList = arrayList;
            if (arrayList.isEmpty())
                this.mImageSelected = new ArrayList<String>(arrayList);
        }
        String str2 = getIntent().getStringExtra("name");
        String str1 = str2;
        if (str2 == null)
            str1 = "";
        this.mNameFile = str1;
    }

    private final void initView() {
        ((TextView)_$_findCachedViewById(R.id.txtTitle)).setText(this.mNameFile);
        Context context = (Context)this;
        ItemChoosePictureAdapter itemChoosePictureAdapter1 = new ItemChoosePictureAdapter(context, this.mImageSelected, new ArrayList(), this);
        this.mItemChoosePictureAdapter = itemChoosePictureAdapter1;
        itemChoosePictureAdapter1.setMIsConvertMode(true);
        ((RecyclerView)_$_findCachedViewById(R.id.rvChooseImageGrid)).setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager(context, 3));
        RecyclerView recyclerView = (RecyclerView)_$_findCachedViewById(R.id.rvChooseImageGrid);
        ItemChoosePictureAdapter itemChoosePictureAdapter2 = this.mItemChoosePictureAdapter;
        itemChoosePictureAdapter1 = itemChoosePictureAdapter2;
        if (itemChoosePictureAdapter2 == null) {
            itemChoosePictureAdapter1 = null;
        }
        recyclerView.setAdapter((RecyclerView.Adapter)itemChoosePictureAdapter1);
        Dialog dialog = new Dialog(context);
        this.mDialogWait = dialog;
        dialog.setContentView(R.layout.layout_dialog_wait_handle);
        dialog = this.mDialogWait;
        if (dialog != null)
            dialog.setCancelable(false);
        dialog = this.mDialogWait;
        if (dialog != null) {
            Window window = dialog.getWindow();
            // TODO
//            if (window != null)
//                window.setBackgroundDrawableResource(17170445);
        }
        dialog = this.mDialogWait;
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null)
                window.setLayout(-1, -2);
        }
    }

    private final void loadNativeAds() {
        Context context = (Context)this;
        Log.d("111111", String.valueOf(BillingClientSetup.isUpgraded(context)));
        Utils utils = Utils.INSTANCE;
        TemplateView templateView = (TemplateView)_$_findCachedViewById(R.id.my_template);
        String str = getString(R.string.alldoc_native_convert_id);
        utils.loadNativeAds(context, templateView, str, AdsManager.INSTANCE.is_show_native_ads_image_convert());
    }

    private final void showAds() {
        if (BillingClientSetup.isUpgraded((Context)this) || !AdsManager.INSTANCE.is_show_inter_ads_home()) {
            gotoNextScreen();
            return;
        }
        _$_findCachedViewById(R.id.layoutAdsLoading).setVisibility(View.VISIBLE);
        AdsManager.INSTANCE.showAds((Activity)this, this);
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
        setContentView(R.layout.activity_convert);
        loadNativeAds();
        initData();
        initView();
        handleEvent();
    }

    public void onItemFileClick(int paramInt) {}

    public void onShowFail() {
        gotoNextScreen();
    }

    public void onShowSuccess() {
        _$_findCachedViewById(R.id.layoutAdsLoading).setVisibility(View.GONE);
    }

    public final class CreatingPDF extends AsyncTask<String, String, String> {
        private Image image;

        private ArrayList<String> listImages;

        public CreatingPDF(ArrayList<String> param1ArrayList) {
            this.listImages = param1ArrayList;
        }

        protected String doInBackground(String... param1VarArgs) {
            // TODO
//            Document document = new Document(ChooseImageToPdfActivity.Companion.getPAGE_SIZE_VALUE().get(1), 38.0F, 38.0F, 50.0F, 38.0F);
//            Rectangle rectangle = document.getPageSize();
//            try {
//                StringBuilder stringBuilder = new StringBuilder();
//                File file2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
//                stringBuilder.append(file2.getAbsoluteFile().getAbsolutePath());
//                stringBuilder.append("/AllDocumentReader2022");
//                File file1 = new File(stringBuilder.toString());
//                if (!file1.exists())
//                    file1.mkdirs();
//                ConvertActivity convertActivity = ConvertActivity.this;
//                stringBuilder = new StringBuilder();
//                stringBuilder.append(file1.getAbsolutePath());
//                stringBuilder.append('/');
//                stringBuilder.append(ConvertActivity.this.mNameFile);
//                stringBuilder.append(".pdf");
//                convertActivity.mPathSavePdf = stringBuilder.toString();
//                FileOutputStream fileOutputStream = new FileOutputStream(ConvertActivity.this.mPathSavePdf);
//                PdfWriter.getInstance(document, fileOutputStream);
//                document.open();
//                byte b = 0;
//                int i = this.listImages.size();
//                while (b < i) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(this.listImages.get(b));
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
//                    this.image = Image.getInstance(this.listImages.get(b));
//                    if (bitmap.getWidth() > rectangle.getWidth() || bitmap.getHeight() > rectangle.getHeight()) {
//                        if (this.image != null)
//                            this.image.scaleToFit(rectangle.getWidth(), rectangle.getHeight());
//                    } else {
//                        Image image = this.image;
//                        if (image != null)
//                            image.scaleToFit(this.image.getWidth(), this.image.getHeight());
//                    }
//                    Image image2 = this.image;
//                    if (image2 != null) {
//                        // TODO : check
////                        float f2 = rectangle.getWidth();
////                        image1 = this.image;
////                        if (image1 != null) {
////                            Float float_ = Float.valueOf(image1.getScaledWidth());
////                        } else {
////                            image1 = null;
////                        }
////                        float f3 = image1.floatValue();
////                        float f1 = 2;
////                        f2 = (f2 - f3) / f1;
////                        f3 = rectangle.getHeight();
////                        image1 = this.image;
////                        if (image1 != null) {
////                            Float float_ = Float.valueOf(image1.getScaledHeight());
////                        } else {
////                            image1 = null;
////                        }
////                        this.image.setAbsolutePosition(f2, (f3 - image1.floatValue()) / f1);
//                    }
//                    this.image.setBorder(15);
//                    this.image.setBorderWidth(15.0F);
//                    document.add((Element)this.image);
//                    document.newPage();
//                    b++;
//                }
//                document.close();
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
            return null;
        }

        public final ArrayList<String> getListImages() {
            return this.listImages;
        }

        protected void onPostExecute(String param1String) {
            super.onPostExecute(param1String);
            if (!ConvertActivity.this.isFinishing()) {
                if (ConvertActivity.this.mDialogWait != null) {
                    Dialog dialog = ConvertActivity.this.mDialogWait;
                    if (dialog.isShowing()) {
                        dialog = ConvertActivity.this.mDialogWait;
                        if (dialog != null)
                            dialog.dismiss();
                    }
                }
                ConvertActivity.this.showAds();
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Dialog dialog = ConvertActivity.this.mDialogWait;
            if (dialog != null)
                dialog.show();
        }

        public final void setListImages(ArrayList<String> param1ArrayList) {
            this.listImages = param1ArrayList;
        }
    }
}