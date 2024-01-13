package com.ryu.document_reader.activity.viewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.reader.office.common.IOfficeToPicture;
import com.reader.office.constant.EventConstant;
import com.reader.office.constant.MainConstant;
import com.reader.office.fc.util.IOUtils;
import com.reader.office.macro.DialogListener;
import com.reader.office.officereader.AppFrame;
import com.reader.office.officereader.FindToolBar;
import com.reader.office.officereader.beans.AImageButton;
import com.reader.office.officereader.beans.AImageCheckButton;
import com.reader.office.officereader.beans.AToolsbar;
import com.reader.office.officereader.beans.CalloutToolsbar;
import com.reader.office.officereader.beans.PDFToolsbar;
import com.reader.office.officereader.beans.PGToolsbar;
import com.reader.office.officereader.beans.SSToolsbar;
import com.reader.office.officereader.beans.WPToolsbar;
import com.reader.office.officereader.database.DBService;
import com.reader.office.res.ResKit;
import com.reader.office.ss.sheetbar.SheetBar;
import com.reader.office.system.FileKit;
import com.reader.office.system.IControl;
import com.reader.office.system.IMainFrame;
import com.reader.office.system.MainControl;
import com.reader.office.system.dialog.ColorPickerDialog;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.activity.SplashActivity;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PowerPointViewerActivity extends BaseActivity implements IMainFrame {
    private static final String TAG = "AppActivity";

    private String action;

    private AppFrame appFrame;

    private int applicationType = -1;

    private SheetBar bottomBar;

    private CalloutToolsbar calloutBar;

    private MainControl control;

    private DBService dbService;

    private AImageCheckButton eraserButton;

    private Object f268bg = Integer.valueOf(-7829368);

    private WindowManager wm = null;

    private ImageView fabFavorite;

    private ImageView fabShare;

    private ImageView fabSnapScreen;

    private String fileName;

    private String filePath;

    private Intent filesDataRecievingIntent;

    private boolean fullscreen;

    private View gapView;

    private boolean isDispose;

    private boolean isThumbnail;

    private ConstraintLayout layoutPdf;

    private boolean mIsFavorite = false;

    private boolean mIsShowMenu = false;

    private String mUrlPowerPoint;

    private boolean marked;

    private AImageButton pageDown;

    private AImageButton pageUp;

    private AImageCheckButton penButton;

    private FindToolBar searchBar;

    private AImageButton settingsButton;

    private String tempFileExtension;

    private String tempFilePath;

    private Toast toast;

    private AToolsbar toolsbar;

    private String type;

    private WindowManager.LayoutParams wmParams = null;

    private boolean writeLog = true;

    public static void copy(Context paramContext, Uri paramUri, File paramFile) {
        try {
            InputStream inputStream = paramContext.getContentResolver().openInputStream(paramUri);
            if (inputStream != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
                IOUtils.copy(inputStream, fileOutputStream);
                inputStream.close();
                fileOutputStream.close();
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void createView() {
        String str = this.filePath.toLowerCase();
        if (str.endsWith("doc") || str.endsWith("docx") || str.endsWith("txt") || str.endsWith("dot") || str.endsWith("dotx") || str.endsWith("dotm")) {
            this.applicationType = 0;
            this.toolsbar = new WPToolsbar(getApplicationContext(), this.control);
            return;
        }
        if (str.endsWith("xls") || str.endsWith("xlsx") || str.endsWith("xlt") || str.endsWith("xltx") || str.endsWith("xltm") || str.endsWith("xlsm")) {
            this.applicationType = 1;
            this.toolsbar = new SSToolsbar(getApplicationContext(), this.control);
            return;
        }
        if (str.endsWith("ppt") || str.endsWith("pptx") || str.endsWith("pot") || str.endsWith("pptm") || str.endsWith("potx") || str.endsWith("potm")) {
            this.applicationType = 2;
            this.toolsbar = new PGToolsbar(getApplicationContext(), this.control);
            return;
        }
        if (str.endsWith("pdf")) {
            this.applicationType = 3;
            this.toolsbar = new PDFToolsbar(getApplicationContext(), this.control);
        } else {
            this.applicationType = 0;
        }
    }

    public static String getFileName(Uri paramUri) {
        if (paramUri != null) {
            String str = paramUri.getPath();
            int i = str.lastIndexOf('/');
            if (i != -1)
                return str.substring(i + 1);
        }
        return null;
    }

    private GradientDrawable getGradient(int paramInt1, int paramInt2) {
        return new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{Integer.parseInt(String.valueOf(paramInt1)), Integer.parseInt(String.valueOf(paramInt2))});
    }

    public static String getMimeType(Context paramContext, Uri paramUri) {
        return paramUri.getScheme().equals("content") ? MimeTypeMap.getSingleton().getExtensionFromMimeType(paramContext.getContentResolver().getType(paramUri)) : MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(paramUri.getPath())).toString());
    }

    private void handleEvents() {
        this.fabShare.setOnClickListener(view -> fileShare());
//        this.fabSnapScreen.setOnClickListener(new ExelViewerActivity$$ExternalSyntheticLambda1(this));
//        this.fabFavorite.setOnClickListener(new ExelViewerActivity$$ExternalSyntheticLambda2(this));
        findViewById(R.id.btnBack).setOnClickListener(view -> onBackPressed());
    }

    private void initData() {
        this.mUrlPowerPoint = getIntent().getStringExtra("url");
        this.fabSnapScreen = findViewById(R.id.btnSnapScreen);
        this.fabShare = findViewById(R.id.btnShare);
        this.fabFavorite = findViewById(R.id.btnFavourite);
        this.layoutPdf = findViewById(R.id.layoutPdf);
        RelativeLayout relativeLayout = findViewById(R.id.toolBar);
        String str = (new File(this.mUrlPowerPoint)).getName();
        ContextCompat.getColor(this, R.color.color_all);
        if (str.endsWith(".doc") || str.endsWith(".docx")) {
            ContextCompat.getColor(this, R.color.color_word);
        } else if (str.endsWith(".xlsx") || str.endsWith(".xls")) {
            ContextCompat.getColor(this, R.color.color_excel);
        } else if (str.endsWith(".pptx") || str.endsWith(".ppt")) {
            ContextCompat.getColor(this, R.color.color_powerpoint);
        } else if (str.endsWith(".txt")) {
            ContextCompat.getColor(this, R.color.color_text);
        }
        TextView textView = findViewById(R.id.txtTitle);
        textView.setText(str);
        textView.setSelected(true);
        boolean bool = Utils.INSTANCE.isFileFavorite(this.mUrlPowerPoint, this);
        this.mIsFavorite = bool;
        if (bool) {
            this.fabFavorite.setImageResource(R.drawable.ic_favourite_active);
        } else {
            this.fabFavorite.setImageResource(R.drawable.ic_favourite_inactive);
        }
    }

    private void initFloatButton() {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.file_slideshow_left, opts);

        //load page up button
        Resources res = getResources();
        pageUp = new AImageButton(this, control, res.getString(R.string.pg_slideshow_pageup), -1,
                -1, EventConstant.APP_PAGE_UP_ID);
        pageUp.setNormalBgResID(R.drawable.file_slideshow_left);
        pageUp.setPushBgResID(R.drawable.file_slideshow_left_push);
        pageUp.setLayoutParams(new LayoutParams(opts.outWidth, opts.outHeight));

        //load page down button
        pageDown = new AImageButton(this, control, res.getString(R.string.pg_slideshow_pagedown),
                -1, -1, EventConstant.APP_PAGE_DOWN_ID);
        pageDown.setNormalBgResID(R.drawable.file_slideshow_right);
        pageDown.setPushBgResID(R.drawable.file_slideshow_right_push);
        pageDown.setLayoutParams(new LayoutParams(opts.outWidth, opts.outHeight));

        BitmapFactory.decodeResource(getResources(), R.drawable.file_slideshow_pen_normal, opts);
        // load pen button
        penButton = new AImageCheckButton(this, control,
                res.getString(R.string.app_toolsbar_pen_check), res.getString(R.string.app_toolsbar_pen),
                R.drawable.file_slideshow_pen_check, R.drawable.file_slideshow_pen_normal,
                R.drawable.file_slideshow_pen_normal, EventConstant.APP_PEN_ID);
        penButton.setNormalBgResID(R.drawable.file_slideshow_pen_normal);
        penButton.setPushBgResID(R.drawable.file_slideshow_pen_push);
        penButton.setLayoutParams(new LayoutParams(opts.outWidth, opts.outHeight));

        // load eraser button
        eraserButton = new AImageCheckButton(this, control,
                res.getString(R.string.app_toolsbar_eraser_check), res.getString(R.string.app_toolsbar_eraser),
                R.drawable.file_slideshow_eraser_check, R.drawable.file_slideshow_eraser_normal,
                R.drawable.file_slideshow_eraser_normal, EventConstant.APP_ERASER_ID);
        eraserButton.setNormalBgResID(R.drawable.file_slideshow_eraser_normal);
        eraserButton.setPushBgResID(R.drawable.file_slideshow_eraser_push);
        eraserButton.setLayoutParams(new LinearLayout.LayoutParams(opts.outWidth, opts.outHeight));

        // load settings button
        settingsButton = new AImageButton(this, control, res.getString(R.string.app_toolsbar_color),
                -1, -1, EventConstant.APP_COLOR_ID);
        settingsButton.setNormalBgResID(R.drawable.file_slideshow_settings_normal);
        settingsButton.setPushBgResID(R.drawable.file_slideshow_settings_push);
        settingsButton.setLayoutParams(new LayoutParams(opts.outWidth, opts.outHeight));

        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();

        wmParams.type = android.view.WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.width = opts.outWidth;
        wmParams.height = opts.outHeight;
    }

    private void initThemeView() {
        if (SplashActivity.Companion.getTheme() == 1)
            findViewById(R.id.layoutPdf).setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlackAltLight));
    }

    private boolean isSearchbarActive() {
        AppFrame appFrame = this.appFrame;
        if (appFrame != null && !this.isDispose) {
            int i = appFrame.getChildCount();
            for (byte b = 0; b < i; b++) {
                View view = this.appFrame.getChildAt(b);
                if (view instanceof FindToolBar)
                    return (view.getVisibility() == View.VISIBLE);
            }
        }
        return false;
    }

    private void markFile() {
        this.marked = !this.marked;
    }

    private void openFile() {
        if (this.filePath == null) {
            this.filePath = this.filesDataRecievingIntent.getDataString();
            int j = getFilePath().indexOf(":");
            if (j > 0)
                this.filePath = this.filePath.substring(j + 3);
            this.filePath = Uri.decode(this.filePath);
        }
        int i = this.filePath.lastIndexOf(File.separator);
        if (i > 0) {
            setTitle(this.filePath.substring(i + 1));
        } else {
            setTitle(this.filePath);
        }
        if (FileKit.instance().isSupport(this.filePath))
            this.dbService.insertRecentFiles("openedfiles", this.filePath);
        createView();
        this.control.openFile(this.filePath);
        initMarked();
    }

    public void changePage() {
    }

    public void changeZoom() {
    }

    public void completeLayout() {
    }

    public void destroyEngine() {
        onBackPressed();
    }

    public void dispose() {
        this.isDispose = true;
        MainControl mainControl = this.control;
        if (mainControl != null) {
            mainControl.dispose();
            this.control = null;
        }
        this.toolsbar = null;
        this.searchBar = null;
        this.bottomBar = null;
        DBService dBService = this.dbService;
        if (dBService != null) {
            dBService.dispose();
            this.dbService = null;
        }
        AppFrame appFrame = this.appFrame;
        if (appFrame != null) {
            int i = appFrame.getChildCount();
            for (byte b = 0; b < i; b++) {
                View view = this.appFrame.getChildAt(b);
                if (view instanceof AToolsbar)
                    ((AToolsbar) view).dispose();
            }
            this.appFrame = null;
        }
        if (this.wm != null) {
            this.wm = null;
            this.wmParams = null;
            this.pageUp.dispose();
            this.pageDown.dispose();
            this.penButton.dispose();
            this.eraserButton.dispose();
            this.settingsButton.dispose();
            this.pageUp = null;
            this.pageDown = null;
            this.penButton = null;
            this.eraserButton = null;
            this.settingsButton = null;
        }
    }

    public void onBackPressed() {
        if (isSearchbarActive()) {
            showSearchBar(false);
            updateToolsbarStatus();
        } else {
            Object obj = control.getActionValue(EventConstant.PG_SLIDESHOW, null);
            if (obj != null && (Boolean) obj) {
                fullScreen(false);
                //
                this.control.actionEvent(EventConstant.PG_SLIDESHOW_END, null);
            } else {
                if (control.getReader() != null) {
                    control.getReader().abortReader();
                }
                if (marked != dbService.queryItem(MainConstant.TABLE_STAR, filePath)) {
                    if (!marked) {
                        dbService.deleteItem(MainConstant.TABLE_STAR, filePath);
                    } else {
                        dbService.insertStarFiles(MainConstant.TABLE_STAR, filePath);
                    }

                    Intent intent = new Intent();
                    intent.putExtra(MainConstant.INTENT_FILED_MARK_STATUS, marked);
                    setResult(RESULT_OK, intent);
                }
                if (control != null && control.isAutoTest()) {
                    System.exit(0);
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    public boolean doActionEvent(int actionID, Object obj) {
        try {
            switch (actionID) {
                case EventConstant.SYS_RESET_TITLE_ID:
                    setTitle((String) obj);
                    break;

                case EventConstant.SYS_ONBACK_ID:
                    onBackPressed();
                    break;

                case EventConstant.SYS_UPDATE_TOOLSBAR_BUTTON_STATUS: //update toolsbar state
                    updateToolsbarStatus();
                    break;

                case EventConstant.SYS_HELP_ID: //show help net
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources()
                            .getString(R.string.sys_url_wxiwei)));
                    startActivity(intent);
                    break;

                case EventConstant.APP_FIND_ID: //show search bar
                    showSearchBar(true);
                    break;

                case EventConstant.APP_SHARE_ID: //share file
                    fileShare();
                    break;

                case EventConstant.FILE_MARK_STAR_ID: //mark
                    markFile();
                    break;

                case EventConstant.APP_FINDING:
                    String content = ((String) obj).trim();
                    if (content.length() > 0 && control.getFind().find(content)) {
                        setFindBackForwardState(true);
                    } else {
                        setFindBackForwardState(false);
                        toast.setText(getLocalString("DIALOG_FIND_NOT_FOUND"));
                        toast.show();
                    }
                    break;

                case EventConstant.APP_FIND_BACKWARD:
                    if (!control.getFind().findBackward()) {
                        searchBar.setEnabled(EventConstant.APP_FIND_BACKWARD, false);
                        toast.setText(getLocalString("DIALOG_FIND_TO_BEGIN"));
                        toast.show();
                    } else {
                        searchBar.setEnabled(EventConstant.APP_FIND_FORWARD, true);
                    }
                    break;

                case EventConstant.APP_FIND_FORWARD:
                    if (!control.getFind().findForward()) {
                        searchBar.setEnabled(EventConstant.APP_FIND_FORWARD, false);
                        toast.setText(getLocalString("DIALOG_FIND_TO_END"));
                        toast.show();
                    } else {
                        searchBar.setEnabled(EventConstant.APP_FIND_BACKWARD, true);
                    }
                    break;

                case EventConstant.SS_CHANGE_SHEET:
                    bottomBar.setFocusSheetButton((Integer) obj);
                    break;

                case EventConstant.APP_DRAW_ID:
                    showCalloutToolsBar(true);
                    control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_CALLOUTDRAW);
                    appFrame.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            control.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null);

                        }
                    });

                    break;

                case EventConstant.APP_BACK_ID:
                    showCalloutToolsBar(false);
                    control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_NORMAL);
                    break;

                case EventConstant.APP_PEN_ID:
                    if ((Boolean) obj) {
                        control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_CALLOUTDRAW);
                        setEraserUnChecked();
                        appFrame.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                control.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null);

                            }
                        });
                    } else {
                        control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_NORMAL);
                    }
                    break;

                case EventConstant.APP_ERASER_ID:
                    if ((Boolean) obj) {
                        control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_CALLOUTERASE);
                        setPenUnChecked();
                    } else {
                        control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_NORMAL);
                    }
                    break;

                case EventConstant.APP_COLOR_ID:
                    ColorPickerDialog dlg = new ColorPickerDialog(this, control);
                    dlg.show();
                    dlg.setOnDismissListener(dialog -> setButtonEnabled(true));
                    setButtonEnabled(false);
                    break;

                default:
                    return false;
            }
        } catch (Exception e) {
            control.getSysKit().getErrorKit().writerLog(e);
        }
        return true;
    }

    public void error(int paramInt) {
    }

    public void fileShare() {
        ArrayList<Uri> arrayList = new ArrayList<>();
        arrayList.add(Uri.fromFile(new File(this.filePath)));
        Intent intent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_STREAM, arrayList);
        intent.setType("application/octet-stream");
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.sys_share_title)));
    }

    public void fullScreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        if (fullscreen) {
            if (wm == null || wmParams == null) {
                initFloatButton();
            }

            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wm.addView(penButton, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wmParams.y = wmParams.height;
            wm.addView(eraserButton, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wmParams.y = wmParams.height * 2;
            wm.addView(settingsButton, wmParams);

            wmParams.gravity = Gravity.LEFT | Gravity.CENTER;
            wmParams.x = MainConstant.GAP;
            wmParams.y = 0;
            wm.addView(pageUp, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.CENTER;
            wm.addView(pageDown, wmParams);

            //hide title and tool bar
            ((View) getWindow().findViewById(android.R.id.title).getParent())
                    .setVisibility(View.GONE);
            //hide status bar
            toolsbar.setVisibility(View.GONE);
            //
            gapView.setVisibility(View.GONE);

            penButton.setState(AImageCheckButton.UNCHECK);
            eraserButton.setState(AImageCheckButton.UNCHECK);

            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(params);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            //landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        } else {
            wm.removeView(pageUp);
            wm.removeView(pageDown);
            wm.removeView(penButton);
            wm.removeView(eraserButton);
            wm.removeView(settingsButton);
            //show title and tool bar
            ((View) getWindow().findViewById(android.R.id.title).getParent())
                    .setVisibility(View.VISIBLE);
            toolsbar.setVisibility(View.VISIBLE);
            gapView.setVisibility(View.VISIBLE);

            //show status bar
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(params);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }

    public AppCompatActivity getActivity() {
        return this;
    }

    public String getAppName() {
        return getString(R.string.app_name);
    }

    public int getApplicationType() {
        return this.applicationType;
    }

    public int getBottomBarHeight() {
        SheetBar sheetBar = this.bottomBar;
        return (sheetBar != null) ? sheetBar.getSheetbarHeight() : 0;
    }

    public IControl getControl() {
        return this.control;
    }

    public DialogListener getDialogListener() {
        return null;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getFilePathFromExternalAppsURI(Context paramContext, Uri paramUri, String paramString) {
        String str = getFileName(paramUri);
        this.fileName = str;
        if (TextUtils.isEmpty(str))
            return null;
        File file2 = getFilesDir();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.fileName);
        stringBuilder.append(paramString);
        File file1 = new File(file2, stringBuilder.toString());
        copy(paramContext, paramUri, file1);
        return file1.getAbsolutePath();
    }

    public String getLocalString(String paramString) {
        return ResKit.instance().getLocalString(paramString);
    }

    public byte getPageListViewMovingPosition() {
        return 0;
    }

    public FindToolBar getSearchBar() {
        return this.searchBar;
    }

    public String getTXTDefaultEncode() {
        return "GBK";
    }

    public File getTemporaryDirectory() {
        File file = getExternalFilesDir(null);
        return (file != null) ? file : getFilesDir();
    }

    public int getTopBarHeight() {
        return 0;
    }

    public Object getViewBackground() {
        return this.f268bg;
    }

    public byte getWordDefaultView() {
        return 0;
    }

    public void init() {
        this.filesDataRecievingIntent = getIntent();
        this.dbService = new DBService(getApplicationContext());
        this.action = this.filesDataRecievingIntent.getAction();
        this.type = this.filesDataRecievingIntent.getType();
        this.tempFileExtension = null;
        try {
            if (TextUtils.isEmpty(this.mUrlPowerPoint))
                this.mUrlPowerPoint = getIntent().getStringExtra("url");
            File file = new File(this.mUrlPowerPoint);
            this.tempFileExtension = getMimeType((Context) this, Uri.fromFile(file));
            this.filePath = this.mUrlPowerPoint;
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("onCreate: File Uri:");
            stringBuilder1.append(this.filePath);
            Log.d("AppActivity", stringBuilder1.toString());
            stringBuilder1 = new StringBuilder();
            stringBuilder1.append("onCreate: File Mime Type:");
            stringBuilder1.append(this.tempFileExtension);
            Log.d("AppActivity", stringBuilder1.toString());
            stringBuilder1 = new StringBuilder();
            stringBuilder1.append("onCreate: File Name");
            stringBuilder1.append(this.fileName);
            Log.d("AppActivity", stringBuilder1.toString());
        } catch (Exception exception) {
            Log.d("File Error", exception.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("init: filePath");
        stringBuilder.append(this.filePath);
        Log.d("AppActivity", stringBuilder.toString());
        try {
            openFile();
        } catch (Exception exception) {
            Log.e("AppActivity", "Exception:", exception);
        }
    }

    public void initMarked() {
        this.marked = this.dbService.queryItem(MainConstant.TABLE_STAR, this.filePath);
    }

    public boolean isChangePage() {
        return true;
    }

    public boolean isDrawPageNumber() {
        return true;
    }

    public boolean isIgnoreOriginalSize() {
        return false;
    }

    public boolean isPopUpErrorDlg() {
        return true;
    }

    public boolean isShowFindDlg() {
        return true;
    }

    public boolean isShowPasswordDlg() {
        return true;
    }

    public boolean isShowProgressBar() {
        return true;
    }

    public boolean isShowTXTEncodeDlg() {
        return true;
    }

    public boolean isShowZoomingMsg() {
        return true;
    }

    public boolean isThumbnail() {
        return this.isThumbnail;
    }

    public boolean isTouchZoom() {
        return true;
    }

    public boolean isWriteLog() {
        return this.writeLog;
    }

    public boolean isZoomAfterLayoutForWord() {
        return true;
    }

    public void onConfigurationChanged(Configuration paramConfiguration) {
        super.onConfigurationChanged(paramConfiguration);
        if (isSearchbarActive())
            this.searchBar.onConfigurationChanged(paramConfiguration);
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(5);
        this.control = new MainControl(this);
        AppFrame appFrame = new AppFrame(getApplicationContext());
        this.appFrame = appFrame;
        appFrame.post(new Runnable() {
            public void run() {
                PowerPointViewerActivity.this.init();
            }
        });
        this.control.setOffictToPicture(new IOfficeToPicture() {
            private Bitmap bitmap;

            public void callBack(Bitmap param1Bitmap) {
                saveBitmapToFile(bitmap);
            }

            public void dispose() {
            }

            public Bitmap getBitmap(int componentWidth, int componentHeight) {
                if (componentWidth == 0 || componentHeight == 0) {
                    return null;
                }
                if (bitmap == null
                        || bitmap.getWidth() != componentWidth
                        || bitmap.getHeight() != componentHeight) {
                    // custom picture size
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    //bitmap = Bitmap.createBitmap(800, 600,  Config.ARGB_8888);
                    //
                    bitmap = Bitmap.createBitmap((int) (componentWidth), (int) (componentHeight), Bitmap.Config.ARGB_8888);
                }
                return bitmap;
            }

            public byte getModeType() {
                return 1;
            }

            public boolean isZoom() {
                return false;
            }

            public void setModeType(byte param1Byte) {
            }
        });
        setContentView(R.layout.activity_power_point_viewer);
        ((FrameLayout) findViewById(R.id.appFrame)).addView(this.appFrame);
        Utils.INSTANCE.loadBannerAds(this, findViewById(R.id.adView), AdsManager.INSTANCE.is_show_banner_ads_office_viewer());
        initData();
        initThemeView();
        handleEvents();
    }

    public Dialog onCreateDialog(int paramInt) {
        return this.control.getDialog(this, paramInt);
    }

    public void onCurrentPageChange() {
    }

    protected void onDestroy() {
        dispose();
        super.onDestroy();
    }

    public boolean onEventMethod(View paramView, MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2, byte paramByte) {
        return false;
    }

    public void onPagesCountChange() {
    }

    protected void onPause() {
        super.onPause();
        Object object = this.control.getActionValue(EventConstant.PG_SLIDESHOW, null);
        if (object != null && ((Boolean) object).booleanValue()) {
            this.wm.removeView(this.pageUp);
            this.wm.removeView(this.pageDown);
            this.wm.removeView(this.penButton);
            this.wm.removeView(this.eraserButton);
            this.wm.removeView(this.settingsButton);
        }
    }

    protected void onResume() {
        super.onResume();
        Object obj = control.getActionValue(EventConstant.PG_SLIDESHOW, null);
        if (obj != null && (Boolean) obj) {
            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wm.addView(penButton, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wmParams.y = wmParams.height;
            wm.addView(eraserButton, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wmParams.y = wmParams.height * 2;
            wm.addView(settingsButton, wmParams);

            wmParams.gravity = Gravity.LEFT | Gravity.CENTER;
            wmParams.x = MainConstant.GAP;
            wmParams.y = 0;
            wm.addView(pageUp, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.CENTER;
            wm.addView(pageDown, wmParams);
        }
    }

    public void openFileFinish() {
        this.gapView = new View(getApplicationContext());
        this.control.getView().setBackgroundResource(R.drawable.bg_office);
        this.appFrame.addView(this.gapView, new LayoutParams(-1, 1));
        this.appFrame.addView(this.control.getView(), new LayoutParams(-1, -1));
    }

    public void resetTitle(String paramString) {
        if (paramString != null)
            setTitle(paramString);
    }

    public void saveBitmapToFile(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (tempFilePath == null) {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                tempFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            }
            File file = new File(tempFilePath + File.separatorChar + "tempPic");
            if (!file.exists()) {
                file.mkdir();
            }
            tempFilePath = file.getAbsolutePath();
        }

        File file = new File(tempFilePath + File.separatorChar + "export_image.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

        } catch (IOException e) {
        } finally {
            //bitmap.recycle();
        }
    }

    public void setButtonEnabled(boolean paramBoolean) {
        if (this.fullscreen) {
            this.pageUp.setEnabled(paramBoolean);
            this.pageDown.setEnabled(paramBoolean);
            this.penButton.setEnabled(paramBoolean);
            this.eraserButton.setEnabled(paramBoolean);
            this.settingsButton.setEnabled(paramBoolean);
        }
    }

    public void setEraserUnChecked() {
        if (fullscreen) {
            eraserButton.setState(AImageCheckButton.UNCHECK);
            eraserButton.postInvalidate();
        } else {
            calloutBar.setCheckState(EventConstant.APP_ERASER_ID, AImageCheckButton.UNCHECK);
            calloutBar.postInvalidate();
        }
    }

    public void setFindBackForwardState(boolean state) {
        if (isSearchbarActive()) {
            searchBar.setEnabled(EventConstant.APP_FIND_BACKWARD, state);
            searchBar.setEnabled(EventConstant.APP_FIND_FORWARD, state);
        }
    }

//    public GradientDrawable setGradientBackground(int paramInt1, int paramInt2) {
//        return new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[] { Integer.parseInt(String.valueOf(paramInt1)), Integer.parseInt(String.valueOf(paramInt2)) });
//    }
//
//    public void setGradientToToolBar() {
//        String str = this.tempFileExtension;
//        str.hashCode();
//        str.hashCode();
//        int i = str.hashCode();
//        byte b = -1;
//        switch (i) {
//            case 46164359:
//                if (!str.equals(".xlsx"))
//                    break;
//                b = 9;
//                break;
//            case 45929906:
//                if (!str.equals(".pptx"))
//                    break;
//                b = 8;
//                break;
//            case 45695193:
//                if (!str.equals(".html"))
//                    break;
//                b = 7;
//                break;
//            case 45571442:
//                if (!str.equals(".dotm"))
//                    break;
//                b = 6;
//                break;
//            case 45570926:
//                if (!str.equals(".docx"))
//                    break;
//                b = 5;
//                break;
//            case 1489193:
//                if (!str.equals(".xml"))
//                    break;
//                b = 4;
//                break;
//            case 1489169:
//                if (!str.equals(".xls"))
//                    break;
//                b = 3;
//                break;
//            case 1485698:
//                if (!str.equals(".txt"))
//                    break;
//                b = 2;
//                break;
//            case 1481606:
//                if (!str.equals(".ppt"))
//                    break;
//                b = 1;
//                break;
//            case 1470026:
//                if (!str.equals(".doc"))
//                    break;
//                b = 0;
//                break;
//        }
//        switch (b) {
//            default:
//                this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099716), getResources().getColor(2131099716)));
//                return;
//            case 9:
//                this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099739), getResources().getColor(2131099738)));
//                return;
//            case 8:
//                this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099737), getResources().getColor(2131099734)));
//                return;
//            case 7:
//                this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099733), getResources().getColor(2131099732)));
//                return;
//            case 6:
//                this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099743), getResources().getColor(2131099742)));
//                return;
//            case 5:
//                this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099743), getResources().getColor(2131099742)));
//                return;
//            case 4:
//                this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099745), getResources().getColor(2131099744)));
//                return;
//            case 3:
//                this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099739), getResources().getColor(2131099738)));
//                return;
//            case 2:
//                this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099741), getResources().getColor(2131099740)));
//                return;
//            case 1:
//                this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099737), getResources().getColor(2131099736)));
//                return;
//            case 0:
//                break;
//        }
//        this.toolsbar.setBackground((Drawable)getGradient(getResources().getColor(2131099743), getResources().getColor(2131099742)));
//    }

    public void setIgnoreOriginalSize(boolean paramBoolean) {
    }

    public void setPenUnChecked() {
        if (fullscreen) {
            penButton.setState(AImageCheckButton.UNCHECK);
            penButton.postInvalidate();
        } else {
            calloutBar.setCheckState(EventConstant.APP_PEN_ID, AImageCheckButton.UNCHECK);
            calloutBar.postInvalidate();
        }
    }

    public void setThumbnail(boolean paramBoolean) {
        this.isThumbnail = paramBoolean;
    }

    public void setWriteLog(boolean paramBoolean) {
        this.writeLog = paramBoolean;
    }

    public void showCalloutToolsBar(boolean show) {
        //show callout bar
        if (show) {
            if (calloutBar == null) {
                calloutBar = new CalloutToolsbar(getApplicationContext(), control);
                appFrame.addView(calloutBar, 0);
            }
            calloutBar.setCheckState(EventConstant.APP_PEN_ID, AImageCheckButton.CHECK);
            calloutBar.setCheckState(EventConstant.APP_ERASER_ID, AImageCheckButton.UNCHECK);
            calloutBar.setVisibility(View.VISIBLE);
            toolsbar.setVisibility(View.GONE);
        }
        // hide callout bar
        else {
            if (calloutBar != null) {
                calloutBar.setVisibility(View.GONE);
            }
            toolsbar.setVisibility(View.VISIBLE);
        }
    }

    public void showProgressBar(boolean paramBoolean) {
        setProgressBarIndeterminateVisibility(paramBoolean);
    }

    public void showSearchBar(boolean show) {
        //show search bar
        if (show) {
            if (searchBar == null) {
                searchBar = new FindToolBar(this, control);
                appFrame.addView(searchBar, 0);
            }
            searchBar.setVisibility(View.VISIBLE);
            toolsbar.setVisibility(View.GONE);
        }
        // hide search bar
        else {
            if (searchBar != null) {
                searchBar.setVisibility(View.GONE);
            }
            toolsbar.setVisibility(View.VISIBLE);
        }
    }

    public void updateToolsbarStatus() {
        if (appFrame == null || isDispose) {
            return;
        }
        int count = appFrame.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = appFrame.getChildAt(i);
            if (v instanceof AToolsbar) {
                ((AToolsbar) v).updateStatus();
            }
        }
    }

    public void updateViewImages(List<Integer> paramList) {
    }

//    public class MyAsyncTask extends AsyncTask<Bitmap, Void, Void> {
//
//        protected Void doInBackground(Bitmap... param1VarArgs) {
//            try {
//                ExelViewerActivity.this.saveBitmapToFile(param1VarArgs[0]);
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//            return null;
//        }
//
//        protected void onPostExecute(Void param1Void) {
//            super.onPostExecute(param1Void);
//        }
//    }
}