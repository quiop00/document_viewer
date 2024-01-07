package com.ryu.document_reader.activity.editor;

import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.util.Utils;
import com.ryu.document_reader.widget.cropper.CropImageView;
import com.ryu.document_reader.widget.drawingview.BrushView;
import com.ryu.document_reader.widget.drawingview.DrawingView;
import com.ryu.document_reader.widget.drawingview.brushes.BrushSettings;
import com.ryu.ntv.ColorPickerDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class EditorActivity extends BaseActivity implements CropImageView.OnSetImageUriCompleteListener, CropImageView.OnCropImageCompleteListener {
    public Map<Integer, View> findViewCache;

    private Bitmap mBitmap;

    private int mFlagAction;

    private boolean mIsDoneClick;

    private String mRootImagePath;

    private Uri mRootImageUri;

    private int mSelectedColor = Color.parseColor("#2187bb");

    private Uri mTempImageUri;

    private final void doActionCrop() {
        if (this.mFlagAction != 0) {
            saveTempImageAfterDraw();
            this.mFlagAction = 0;
            showCropMode(this.mTempImageUri);
        }
    }

    private final void doActionEraser() {
        int i = this.mFlagAction;
        if (i != 2) {
            if (i == 0)
                cropImage();
            this.mFlagAction = 2;
            ImageView imageView = (ImageView)findCachedViewById(R.id.imgFunctionEraser);
            showFunctionActive(imageView);
            ((LinearLayout)findCachedViewById(R.id.layoutBrush)).setVisibility(View.VISIBLE);
            setBrushSelected(4);
        } else if (((LinearLayout)findCachedViewById(R.id.layoutBrush)).getVisibility() == View.VISIBLE) {
            ((LinearLayout)findCachedViewById(R.id.layoutBrush)).setVisibility(View.GONE);
        } else {
            ((LinearLayout)findCachedViewById(R.id.layoutBrush)).setVisibility(View.VISIBLE);
        }
    }

    private final void doActionPen() {
        int i = this.mFlagAction;
        if (i != 1) {
            this.mFlagAction = 1;
            if (i == 0)
                cropImage();
            ImageView imageView = (ImageView)findCachedViewById(R.id.imgFunctionPen);
            showFunctionActive(imageView);
            ((ImageView)findCachedViewById(R.id.imgFunctionEraser)).setVisibility(View.VISIBLE);
            ((LinearLayout)findCachedViewById(R.id.layoutBrush)).setVisibility(View.VISIBLE);
            ((CircleImageView)findCachedViewById(R.id.imgColorPicker)).setVisibility(View.VISIBLE);
            setBrushSelected(0);
        } else if (((LinearLayout)findCachedViewById(R.id.layoutBrush)).getVisibility() == View.VISIBLE) {
            ((LinearLayout)findCachedViewById(R.id.layoutBrush)).setVisibility(View.GONE);
        } else {
            ((LinearLayout)findCachedViewById(R.id.layoutBrush)).setVisibility(View.VISIBLE);
        }
    }

    private final void getAllFileScreenShot() {
        // Byte code:
        //   0: getstatic com/alldoucment/reader/viewer/activity/home/HomeActivity2.Companion : Lcom/alldoucment/reader/viewer/activity/home/HomeActivity2$Companion;
        //   3: invokevirtual getFileImages : ()Ljava/util/ArrayList;
        //   6: invokevirtual clear : ()V
        //   9: new java/lang/StringBuilder
        //   12: astore_3
        //   13: aload_3
        //   14: invokespecial <init> : ()V
        //   17: aload_0
        //   18: invokevirtual getApplicationContext : ()Landroid/content/Context;
        //   21: aconst_null
        //   22: invokevirtual getExternalFilesDir : (Ljava/lang/String;)Ljava/io/File;
        //   25: astore #4
        //   27: aload #4
        //   29: invokestatic checkNotNull : (Ljava/lang/Object;)V
        //   32: aload_3
        //   33: aload #4
        //   35: invokevirtual getAbsolutePath : ()Ljava/lang/String;
        //   38: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   41: pop
        //   42: aload_3
        //   43: ldc_w '/AllDocument'
        //   46: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   49: pop
        //   50: aload_3
        //   51: invokevirtual toString : ()Ljava/lang/String;
        //   54: astore #4
        //   56: new java/io/File
        //   59: astore_3
        //   60: aload_3
        //   61: aload #4
        //   63: invokespecial <init> : (Ljava/lang/String;)V
        //   66: aload_3
        //   67: invokevirtual listFiles : ()[Ljava/io/File;
        //   70: astore #4
        //   72: aload #4
        //   74: ifnull -> 190
        //   77: aload #4
        //   79: arraylength
        //   80: istore_2
        //   81: iconst_0
        //   82: istore_1
        //   83: iload_1
        //   84: iload_2
        //   85: if_icmpge -> 190
        //   88: aload #4
        //   90: iload_1
        //   91: aaload
        //   92: astore_3
        //   93: aload_3
        //   94: ifnull -> 184
        //   97: aload_3
        //   98: invokevirtual isFile : ()Z
        //   101: ifeq -> 184
        //   104: aload_3
        //   105: invokevirtual getName : ()Ljava/lang/String;
        //   108: astore #5
        //   110: aload #5
        //   112: ldc_w 'fileImage.name'
        //   115: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   118: aload #5
        //   120: ldc_w '.png'
        //   123: iconst_0
        //   124: iconst_2
        //   125: aconst_null
        //   126: invokestatic endsWith$default : (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
        //   129: ifne -> 160
        //   132: aload_3
        //   133: invokevirtual getName : ()Ljava/lang/String;
        //   136: astore #5
        //   138: aload #5
        //   140: ldc_w 'fileImage.name'
        //   143: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   146: aload #5
        //   148: ldc_w '.jpg'
        //   151: iconst_0
        //   152: iconst_2
        //   153: aconst_null
        //   154: invokestatic endsWith$default : (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
        //   157: ifeq -> 184
        //   160: aload_3
        //   161: invokevirtual getName : ()Ljava/lang/String;
        //   164: ldc_w 'screenshot.jpg'
        //   167: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
        //   170: ifne -> 184
        //   173: getstatic com/alldoucment/reader/viewer/activity/home/HomeActivity2.Companion : Lcom/alldoucment/reader/viewer/activity/home/HomeActivity2$Companion;
        //   176: invokevirtual getFileImages : ()Ljava/util/ArrayList;
        //   179: aload_3
        //   180: invokevirtual add : (Ljava/lang/Object;)Z
        //   183: pop
        //   184: iinc #1, 1
        //   187: goto -> 83
        //   190: new java/lang/StringBuilder
        //   193: astore_3
        //   194: aload_3
        //   195: invokespecial <init> : ()V
        //   198: aload_3
        //   199: ldc_w 'getAllFileScreenShot: '
        //   202: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   205: pop
        //   206: aload_3
        //   207: getstatic com/alldoucment/reader/viewer/activity/home/HomeActivity2.Companion : Lcom/alldoucment/reader/viewer/activity/home/HomeActivity2$Companion;
        //   210: invokevirtual getFileImages : ()Ljava/util/ArrayList;
        //   213: invokevirtual size : ()I
        //   216: invokevirtual append : (I)Ljava/lang/StringBuilder;
        //   219: pop
        //   220: ldc_w '1313'
        //   223: aload_3
        //   224: invokevirtual toString : ()Ljava/lang/String;
        //   227: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
        //   230: pop
        //   231: goto -> 239
        //   234: astore_3
        //   235: aload_3
        //   236: invokevirtual printStackTrace : ()V
        //   239: return
        // Exception table:
        //   from	to	target	type
        //   9	72	234	java/lang/Exception
        //   77	81	234	java/lang/Exception
        //   97	160	234	java/lang/Exception
        //   160	184	234	java/lang/Exception
        //   190	231	234	java/lang/Exception
    }

    private final void handleEvents() {
        // TODO
//        ((TextView)findCachedViewById(R.id.tvRestore)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda6(this));
//        ((TextView)findCachedViewById(R.id.tvCrop)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda7(this));
//        ((ImageView)findCachedViewById(R.id.imgFunctionPen)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda8(this));
//        ((ImageView)findCachedViewById(R.id.imgFunctionEraser)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda9(this));
//        ((ImageView)findCachedViewById(R.id.imgFunctionCrop)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda10(this));
//        ((CircleImageView)findCachedViewById(R.id.imgColorPicker)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda11(this));
//        ((ImageView)findCachedViewById(R.id.imgClose)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda12(this));
//        ((ImageView)findCachedViewById(R.id.imgShare)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda13(this));
//        ((TextView)findCachedViewById(R.id.tvDone)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda14(this));
    }

    private static final void handleEvents$lambda$0(EditorActivity paramEditorActivity, View paramView) {
        paramEditorActivity.showCropMode(paramEditorActivity.mRootImageUri);
    }

    private static final void handleEvents$lambda$1(EditorActivity paramEditorActivity, View paramView) {
        paramEditorActivity.cropImage();
    }

    private static final void handleEvents$lambda$10(EditorActivity paramEditorActivity, View paramView) {
        paramEditorActivity.mIsDoneClick = true;
        if (paramEditorActivity.mFlagAction == 0)
            paramEditorActivity.cropImage();
        Dialog dialog = new Dialog((Context)paramEditorActivity);
        dialog.setContentView(R.layout.layout_dialog_savefile);
        Window window = dialog.getWindow();
        if (window != null)
            window.setLayout(-1, -2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ScreenShot_");
        stringBuilder.append(System.currentTimeMillis());
        String str = stringBuilder.toString();
        ((EditText)dialog.findViewById(R.id.edtFileName)).setText(str);
        dialog.setCancelable(false);
        // TODO
//        ((Button)dialog.findCachedViewById(R.id.btnOke)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda4(dialog, paramEditorActivity));
//        ((Button)dialog.findCachedViewById(R.id.btnCancel)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda5(dialog));
        dialog.show();
    }

    private static final void handleEvents$lambda$10$lambda$8(Dialog paramDialog, EditorActivity paramEditorActivity, View paramView) {
        String str = StringsKt.trim(((EditText)paramDialog.findViewById(R.id.edtFileName)).getText().toString()).toString();
        if (Intrinsics.areEqual(str, "")) {
            Toast.makeText((Context)paramEditorActivity, paramEditorActivity.getString(R.string.enter_page_number), Toast.LENGTH_LONG).show();
        } else {
            Utils utils = Utils.INSTANCE;
            Bitmap bitmap = ((DrawingView)paramEditorActivity.findCachedViewById(R.id.drawingView)).exportDrawing();
            utils.saveBitmapByName(bitmap, (Context)paramEditorActivity, str);
            paramEditorActivity.getAllFileScreenShot();
            paramDialog.dismiss();
            paramEditorActivity.onBackPressed();
        }
    }

    private static final void handleEvents$lambda$10$lambda$9(Dialog paramDialog, View paramView) {
        Intrinsics.checkNotNullParameter(paramDialog, "$dialog");
        paramDialog.dismiss();
    }

    private static final void handleEvents$lambda$2(EditorActivity paramEditorActivity, View paramView) {
        paramEditorActivity.doActionPen();
    }

    private static final void handleEvents$lambda$3(EditorActivity paramEditorActivity, View paramView) {
        paramEditorActivity.doActionEraser();
    }

    private static final void handleEvents$lambda$4(EditorActivity paramEditorActivity, View paramView) {
        paramEditorActivity.doActionCrop();
    }

    private static final void handleEvents$lambda$5(EditorActivity paramEditorActivity, View paramView) {
        paramEditorActivity.showColorPickerDialog();
    }

    private static final void handleEvents$lambda$6(EditorActivity paramEditorActivity, View paramView) {
        ((LinearLayout)paramEditorActivity.findCachedViewById(R.id.layoutBrush)).setVisibility(View.GONE);
    }

    private static final void handleEvents$lambda$7(EditorActivity paramEditorActivity, View paramView) {
        Utils utils = Utils.INSTANCE;
        Bitmap bitmap = ((DrawingView)paramEditorActivity.findCachedViewById(R.id.drawingView)).exportDrawing();
        Context context = (Context)paramEditorActivity;
        Uri uri = FileProvider.getUriForFile(context, "com.docreader.readerdocument.provider", new File(utils.saveBitmapTemp(bitmap, context)));
        paramEditorActivity.startActivity(Intent.createChooser(Utils.INSTANCE.fileShareIntent(paramEditorActivity.getString(R.string.fab_share), uri), "share.."));
    }

    private final void initData() throws FileNotFoundException {
        this.mRootImagePath = String.valueOf(getIntent().getStringExtra("path"));
        File file = new File(this.mRootImagePath);
        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        this.mBitmap = bitmap;
        this.mRootImageUri = Uri.fromFile(file);
        ((CropImageView)findCachedViewById(R.id.cropImageView)).setImageUriAsync(this.mRootImageUri);
        file = getExternalFilesDir(null);
        Intrinsics.checkNotNull(file);
        file = file.getAbsoluteFile();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getAbsolutePath());
        stringBuilder.append("/AllDocumentTemp/");
        file = new File(stringBuilder.toString());
        file.mkdir();
        this.mTempImageUri = Uri.fromFile(new File(file, "screenshot.jpg"));
    }

    private final void saveTempImageAfterDraw() {
        if (this.mFlagAction != 0) {
            Utils utils = Utils.INSTANCE;
            Bitmap bitmap = ((DrawingView)findCachedViewById(R.id.drawingView)).exportDrawing();
            Intrinsics.checkNotNullExpressionValue(bitmap, "drawingView.exportDrawing()");
            utils.saveBitmap(bitmap, (Context)this);
        }
    }

    private final void setBrushSelected(int paramInt) {
        BrushSettings brushSettings = ((DrawingView)findCachedViewById(R.id.drawingView)).getBrushSettings();
        Intrinsics.checkNotNullExpressionValue(brushSettings, "drawingView.brushSettings");
        brushSettings.setSelectedBrush(paramInt);
        ((SeekBar)findCachedViewById(R.id.sbSize)).setProgress((int)(brushSettings.getSelectedBrushSize() * 100.0F));
    }

    private final void setupDraw() {
        ((ImageView)findCachedViewById(R.id.imgUndo)).setEnabled(true);
        ((ImageView)findCachedViewById(R.id.imgRedo)).setEnabled(false);
        ((ImageView)findCachedViewById(R.id.imgUndo)).setImageResource(R.drawable.ic_undo_xml);
        ((ImageView)findCachedViewById(R.id.imgRedo)).setImageResource(R.drawable.ic_redo_inactive_xml);
    }

    private final void setupDrawingView() {
        ((DrawingView)findCachedViewById(R.id.drawingView)).setUndoAndRedoEnable(true);
        // TODO
        //((DrawingView)findCachedViewById(R.id.drawingView)).setOnActionDownListener(new EditorActivity$$ExternalSyntheticLambda0(this));
        ((BrushView)findCachedViewById(R.id.brushView)).setDrawingView((DrawingView)findCachedViewById(R.id.drawingView));
        ((SeekBar)findCachedViewById(R.id.sbSize)).setMax(100);
        ((SeekBar)findCachedViewById(R.id.sbSize)).setOnSeekBarChangeListener(new EditorActivity$setupDrawingView$2());
        setupUndoAndRedo();
        setBrushSelected(0);
        ((CircleImageView)findCachedViewById(R.id.imgColorPicker)).setImageDrawable((Drawable)new ColorDrawable(this.mSelectedColor));
    }

    private static final void setupDrawingView$lambda$11(EditorActivity paramEditorActivity) {
        Intrinsics.checkNotNullParameter(paramEditorActivity, "this$0");
        ((LinearLayout)paramEditorActivity.findCachedViewById(R.id.layoutBrush)).setVisibility(View.GONE);
    }

    private final void setupRedo() {
        int i;
        ((LinearLayout)findCachedViewById(R.id.layoutBrush)).setVisibility(View.GONE);
        ((DrawingView)findCachedViewById(R.id.drawingView)).redo();
        ImageView imageView = (ImageView)findCachedViewById(R.id.imgUndo);
        if (((DrawingView)findCachedViewById(R.id.drawingView)).isUndoStackEmpty()) {
            i = R.drawable.ic_undo_inactive_xml;
        } else {
            i = R.drawable.ic_undo_xml;
        }
        imageView.setImageResource(i);
        imageView = (ImageView)findCachedViewById(R.id.imgRedo);
        if (((DrawingView)findCachedViewById(R.id.drawingView)).isRedoStackEmpty()) {
            i = R.drawable.ic_redo_inactive_xml;
        } else {
            i = R.drawable.ic_redo_xml;
        }
        imageView.setImageResource(i);
        ((ImageView)findCachedViewById(R.id.imgUndo)).setEnabled(((DrawingView)findCachedViewById(R.id.drawingView)).isUndoStackEmpty() ^ true);
        ((ImageView)findCachedViewById(R.id.imgRedo)).setEnabled(((DrawingView)findCachedViewById(R.id.drawingView)).isRedoStackEmpty() ^ true);
    }

    private final void setupUndo() {
        int i;
        ((LinearLayout)findCachedViewById(R.id.layoutBrush)).setVisibility(View.GONE);
        ((DrawingView)findCachedViewById(R.id.drawingView)).undo();
        ImageView imageView = (ImageView)findCachedViewById(R.id.imgUndo);
        if (((DrawingView)findCachedViewById(R.id.drawingView)).isUndoStackEmpty()) {
            i = R.drawable.ic_undo_inactive_xml;
        } else {
            i = R.drawable.ic_undo_xml;
        }
        imageView.setImageResource(i);
        imageView = (ImageView)findCachedViewById(R.id.imgRedo);
        if (((DrawingView)findCachedViewById(R.id.drawingView)).isRedoStackEmpty()) {
            i = R.drawable.ic_redo_inactive_xml;
        } else {
            i = R.drawable.ic_redo_xml;
        }
        imageView.setImageResource(i);
        ((ImageView)findCachedViewById(R.id.imgUndo)).setEnabled(((DrawingView)findCachedViewById(R.id.drawingView)).isUndoStackEmpty() ^ true);
        ((ImageView)findCachedViewById(R.id.imgRedo)).setEnabled(((DrawingView)findCachedViewById(R.id.drawingView)).isRedoStackEmpty() ^ true);
    }

    private final void setupUndoAndRedo() {
        // TODO
//        ((ImageView)_$_findCachedViewById(R.id.imgUndo)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda1(this));
//        ((ImageView)_$_findCachedViewById(R.id.imgRedo)).setOnClickListener(new EditorActivity$$ExternalSyntheticLambda2(this));
//        ((DrawingView)_$_findCachedViewById(R.id.drawingView)).setOnDrawListener(new EditorActivity$$ExternalSyntheticLambda3(this));
    }

    private static final void setupUndoAndRedo$lambda$12(EditorActivity paramEditorActivity, View paramView) {
        Intrinsics.checkNotNullParameter(paramEditorActivity, "this$0");
        paramEditorActivity.setupUndo();
    }

    private static final void setupUndoAndRedo$lambda$13(EditorActivity paramEditorActivity, View paramView) {
        Intrinsics.checkNotNullParameter(paramEditorActivity, "this$0");
        paramEditorActivity.setupRedo();
    }

    private static final void setupUndoAndRedo$lambda$14(EditorActivity paramEditorActivity) {
        Intrinsics.checkNotNullParameter(paramEditorActivity, "this$0");
        paramEditorActivity.setupDraw();
    }

    private final void showColorPickerDialog() {
        // TODO
        //(new ColorPickerDialog((Context)this, -16777216, true, new EditorActivity$showColorPickerDialog$colorPicker$1())).show();
    }

    private final void showCropMode(Uri paramUri) {
        ((DrawingView)findCachedViewById(R.id.drawingView)).setVisibility(View.INVISIBLE);
        ((CropImageView)findCachedViewById(R.id.cropImageView)).setVisibility(View.VISIBLE);
        ((TextView)findCachedViewById(R.id.tvCrop)).setVisibility(View.VISIBLE);
        ((TextView)findCachedViewById(R.id.tvRestore)).setVisibility(View.VISIBLE);
        ((ImageView)findCachedViewById(R.id.imgRedo)).setVisibility(View.GONE);
        ((ImageView)findCachedViewById(R.id.imgUndo)).setVisibility(View.GONE);
        ((LinearLayout)findCachedViewById(R.id.layoutBrush)).setVisibility(View.GONE);
        ((ImageView)findCachedViewById(R.id.imgFunctionEraser)).setVisibility(View.INVISIBLE);
        ((CircleImageView)findCachedViewById(R.id.imgColorPicker)).setVisibility(View.INVISIBLE);
        ImageView imageView = (ImageView)findCachedViewById(R.id.imgFunctionCrop);
        Intrinsics.checkNotNullExpressionValue(imageView, "imgFunctionCrop");
        showFunctionActive(imageView);
        if (paramUri != null)
            ((CropImageView)findCachedViewById(R.id.cropImageView)).setImageUriAsync(paramUri);
    }

    private final void showFunctionActive(ImageView paramImageView) {
        ((ImageView)findCachedViewById(R.id.imgFunctionEraser)).setBackground(null);
        ((ImageView)findCachedViewById(R.id.imgFunctionPen)).setBackground(null);
        ((ImageView)findCachedViewById(R.id.imgFunctionCrop)).setBackground(null);
        paramImageView.setBackgroundResource(R.drawable.selector_border_draw_mode);
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

    public final void cropImage() {
        ((CropImageView)findCachedViewById(R.id.cropImageView)).saveCroppedImageAsync(this.mTempImageUri);
    }

    public final String getMRootImagePath() {
        return this.mRootImagePath;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_editor);
        try {
            initData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        showCropMode((Uri)null);
        setupDrawingView();
        handleEvents();
    }

    public void onCropImageComplete(CropImageView paramCropImageView, CropImageView.CropResult paramCropResult) {
        if (paramCropResult != null) {
            Exception exception = paramCropResult.getError();
        } else {
            paramCropImageView = null;
        }
        if (paramCropImageView == null && paramCropResult != null && paramCropResult.getOriginalUri() != null) {
            Bitmap bitmap = null;
            Uri uri = paramCropResult.getOriginalUri();
            Intrinsics.checkNotNullExpressionValue(uri, "cropResult!!.originalUri");
            int i = this.mFlagAction;
            this.mTempImageUri = uri;
            if (i == 1 || i == 2 || this.mIsDoneClick) {
                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(this.mRootImagePath)));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                ((DrawingView)findCachedViewById(R.id.drawingView)).setBackgroundImage(bitmap);
                ((DrawingView)findCachedViewById(R.id.drawingView)).setVisibility(View.VISIBLE);
                ((CropImageView)findCachedViewById(R.id.cropImageView)).setVisibility(View.INVISIBLE);
                ((TextView)findCachedViewById(R.id.tvCrop)).setVisibility(View.GONE);
                ((TextView)findCachedViewById(R.id.tvRestore)).setVisibility(View.GONE);
                ((ImageView)findCachedViewById(R.id.imgRedo)).setVisibility(View.VISIBLE);
                ((ImageView)findCachedViewById(R.id.imgUndo)).setVisibility(View.VISIBLE);
                return;
            }
            if (i == 0)
                showCropMode((Uri)mTempImageUri);
        }
    }

    public void onSetImageUriComplete(CropImageView paramCropImageView, Uri paramUri, Exception paramException) {}

    protected void onStart() {
        ((CropImageView)findCachedViewById(R.id.cropImageView)).setOnSetImageUriCompleteListener(this);
        ((CropImageView)findCachedViewById(R.id.cropImageView)).setOnCropImageCompleteListener(this);
        super.onStart();
    }

    public final void setMRootImagePath(String paramString) {
        this.mRootImagePath = paramString;
    }

    public static final class EditorActivity$setupDrawingView$2 implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {
            // TODO
            //((DrawingView)EditorActivity.this._$_findCachedViewById(R.id.drawingView)).getBrushSettings().setSelectedBrushSize(param1Int / 100.0F);
        }

        public void onStartTrackingTouch(SeekBar param1SeekBar) {
            Intrinsics.checkNotNullParameter(param1SeekBar, "seekBar");
        }

        public void onStopTrackingTouch(SeekBar param1SeekBar) {
            Intrinsics.checkNotNullParameter(param1SeekBar, "seekBar");
        }
    }

    public static final class EditorActivity$showColorPickerDialog$colorPicker$1 implements ColorPickerDialog.OnColorPickerListener {

        public void onCancel(ColorPickerDialog param1ColorPickerDialog) {}

        public void onOk(ColorPickerDialog param1ColorPickerDialog, int param1Int) {
            // TODO
//            EditorActivity.this.mSelectedColor = param1Int;
//            ((CircleImageView)EditorActivity.this._$_findCachedViewById(R.id.imgColorPicker)).setImageDrawable((Drawable)new ColorDrawable(EditorActivity.this.mSelectedColor));
//            ((DrawingView)EditorActivity.this._$_findCachedViewById(R.id.drawingView)).getBrushSettings().setColor(EditorActivity.this.mSelectedColor);
        }
    }
}
