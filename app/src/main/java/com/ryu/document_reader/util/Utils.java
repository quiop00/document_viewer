package com.ryu.document_reader.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAdRevenue;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.ads.TemplateView;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

public final class Utils {
    private static final String APP_DOC = "AppDoc";

    public static final Utils INSTANCE = new Utils();

    private static final String KEY_PATH = "KeyPath";

    private static final String KEY_PATH_RECENT = "KeyPathRecent";

    private static boolean onShow;

    private final void closeSafe(Closeable paramCloseable) {
        if (paramCloseable != null)
            try {
                paramCloseable.close();
            } catch (IOException iOException) {}
    }


    private final void restartTime() {
        onShow = false;
    }


    public final Intent fileShareIntent(String paramString, Uri paramUri) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", (Parcelable)paramUri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("application/pdf");
        return Intent.createChooser(intent, paramString);
    }

    public final Intent fileShareIntent(String paramString1, ArrayList<Uri> paramArrayList, String paramString2) {
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", paramArrayList);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType(paramString2);
        return Intent.createChooser(intent, paramString1);
    }

    public final long getAccessTimeFile(String paramString, Context paramContext) {
        Intrinsics.checkNotNullParameter(paramString, "path");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        return paramContext.getSharedPreferences("AppDoc", 0).getLong(paramString, 0L);
    }

    public final ArrayList<File> getAllFileFavorites(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        ArrayList<File> arrayList = new ArrayList<>();
        Set<String> set = paramContext.getSharedPreferences("AppDoc", 0).getStringSet("KeyPath", null);
        if (set != null) {
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext())
                arrayList.add(new File(iterator.next()));
        }
        return arrayList;
    }

    public final ArrayList<File> getAllFileRecents(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        ArrayList<File> arrayList = new ArrayList<>();
        Set<String> set = paramContext.getSharedPreferences("AppDoc", 0).getStringSet("KeyPathRecent", null);
        if (set != null) {
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext())
                arrayList.add(new File(iterator.next()));
        }
        return arrayList;
    }

    public final String getIOSCountryData(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        return String.valueOf(paramContext.getSharedPreferences("AppDoc", 0).getString("keyLang", "en"));
    }

    public final int getNumberBackApp(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        return paramContext.getSharedPreferences("AppDoc", 0).getInt("keyNumberReaderFile", 0);
    }

    public final boolean getOnShow() {
        return onShow;
    }

    public final String getSizeFile(double paramDouble) {
        double d2 = 100;
        double d1 = 'Ѐ';
        long l2 = Math.round(paramDouble * d2 / d1);
        long l1 = 100L;
        l2 /= l1;
        long l3 = Math.round(l2 * 100.0D / d1) / l1;
        l1 = Math.round(l3 * 100.0D / d1) / l1;
        if (l1 >= 1L) {
            return l1 +
                    " GB";
        }
        if (l3 >= 1L) {
            return l3 +
                    " MB";
        }
        return l2 +
                " KB";
    }

    public final int getTheme(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        return paramContext.getSharedPreferences("AppDoc", 0).getInt("Theme", 0);
    }

    public final boolean isCountryCheck(String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "code");
        return Intrinsics.areEqual(paramString, Locale.getDefault().getDisplayLanguage());
    }

    public final boolean isFileFavorite(String paramString, Context paramContext) {
        Intrinsics.checkNotNullParameter(paramString, "path");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        Set<String> set = paramContext.getSharedPreferences("AppDoc", 0).getStringSet("KeyPath", null);
        return set != null && set.contains(paramString);
    }

    public final boolean isGetLanguage(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        return paramContext.getSharedPreferences("AppDoc", 0).getBoolean("isSetLang", false);
    }

    public final boolean isModeNight(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        return paramContext.getSharedPreferences("AppDoc", 0).getBoolean("mode_night", false);
    }

    public final boolean isRating(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        return paramContext.getSharedPreferences("AppDoc", 0).getBoolean("isRating", false);
    }

    public final void isSetLanguage(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("AppDoc", 0).edit();
        editor.putBoolean("isSetLang", true);
        editor.apply();
    }

    public final void removeFileFavorite(String paramString, Context paramContext) {
        Intrinsics.checkNotNullParameter(paramString, "path");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        SharedPreferences sharedPreferences = paramContext.getSharedPreferences("AppDoc", 0);
        Set<String> set = sharedPreferences.getStringSet("KeyPath", null);
        if (set != null && set.contains(paramString)) {
            HashSet hashSet = new HashSet();
            hashSet.addAll(set);
            hashSet.remove(paramString);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("KeyPath", hashSet);
            editor.apply();
        }
    }

    public final void renameFileFavorite(String paramString1, String paramString2, Context paramContext) {
        Intrinsics.checkNotNullParameter(paramString1, "pathOld");
        Intrinsics.checkNotNullParameter(paramString2, "pathNew");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        SharedPreferences sharedPreferences = paramContext.getSharedPreferences("AppDoc", 0);
        Set<String> set = sharedPreferences.getStringSet("KeyPath", null);
        if (set != null && set.contains(paramString1)) {
            HashSet<String> hashSet = new HashSet<>(set);
            hashSet.remove(paramString1);
            hashSet.add(paramString2);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("KeyPath", hashSet);
            editor.apply();
        }
    }

    public final String saveBitmap(Bitmap paramBitmap, Context paramContext) {
        Intrinsics.checkNotNullParameter(paramBitmap, "bitmap");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        if (paramContext.getExternalFilesDir(null) == null)
            return "";
        File file2 = paramContext.getExternalFilesDir(null);
        Intrinsics.checkNotNull(file2);
        File file3 = file2.getAbsoluteFile();
        Intrinsics.checkNotNullExpressionValue(file3, "context.getExternalFilesDir(null)!!.absoluteFile");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file3.getAbsolutePath());
        stringBuilder.append("/AllDocument/");
        File file1 = new File(stringBuilder.toString());
        file1.mkdir();
        file1 = new File(file1, "screenshot.jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } finally {}
        String str = file1.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(str, "file.absolutePath");
        return str;
    }

    public final String saveBitmapByName(Bitmap paramBitmap, Context paramContext, String paramString) {
        Intrinsics.checkNotNullParameter(paramBitmap, "bitmap");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        Intrinsics.checkNotNullParameter(paramString, "name");
        File file2 = paramContext.getExternalFilesDir(null);
        Intrinsics.checkNotNull(file2);
        file2 = file2.getAbsoluteFile();
        Intrinsics.checkNotNullExpressionValue(file2, "context.getExternalFilesDir(null)!!.absoluteFile");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file2.getAbsolutePath());
        stringBuilder.append("/AllDocument/");
        file2 = new File(stringBuilder.toString());
        file2.mkdir();
        stringBuilder = new StringBuilder();
        stringBuilder.append(paramString);
        stringBuilder.append(".jpg");
        File file1 = new File(file2, stringBuilder.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("The screen shot stored in path that is ");
            stringBuilder1.append(file1.getAbsolutePath());
            Toast.makeText(paramContext, stringBuilder1.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            Toast.makeText(paramContext, Unit.INSTANCE.toString(), Toast.LENGTH_LONG).show();
            fileNotFoundException.printStackTrace();
        } finally {}
        String str = file1.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(str, "file.absolutePath");
        return str;
    }

    public final String saveBitmapTemp(Bitmap paramBitmap, Context paramContext) {
        Intrinsics.checkNotNullParameter(paramBitmap, "bitmap");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        File file = paramContext.getExternalFilesDir(null);
        Intrinsics.checkNotNull(file);
        file = file.getAbsoluteFile();
        Intrinsics.checkNotNullExpressionValue(file, "context.getExternalFilesDir(null)!!.absoluteFile");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getAbsolutePath());
        stringBuilder.append("/AllDocumentTemp/");
        file = new File(stringBuilder.toString());
        file.mkdir();
        file = new File(file, "screenshot.jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } finally {}
        String str = file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(str, "file.absolutePath");
        return str;
    }

    public final void setFileFavorite(String paramString, Context paramContext, boolean paramBoolean) {
        Intrinsics.checkNotNullParameter(paramString, "path");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        SharedPreferences sharedPreferences = paramContext.getSharedPreferences("AppDoc", 0);
        Set<String> set = sharedPreferences.getStringSet("KeyPath", null);
        HashSet<String> hashSet = new HashSet<>();
        if (set != null)
            hashSet.addAll(set);
        if (paramBoolean) {
            hashSet.add(paramString);
        } else {
            hashSet.remove(paramString);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("KeyPath", hashSet);
        editor.apply();
    }

    @SuppressLint("MutatingSharedPrefs")
    public final void setFileRecent(String paramString, Context paramContext) {
        Intrinsics.checkNotNullParameter(paramString, "path");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        SharedPreferences sharedPreferences = paramContext.getSharedPreferences("AppDoc", 0);
        Set<? extends String> set = sharedPreferences.getStringSet("KeyPathRecent", null);
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add(paramString);
        if (set != null) {
            set.remove(paramString);
            hashSet.addAll(set);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("KeyPathRecent", hashSet);
        editor.putLong(paramString, -System.currentTimeMillis());
        editor.apply();
    }

    public final void setIOSCountryData(String paramString, Context paramContext) {
        Intrinsics.checkNotNullParameter(paramString, "lang");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("AppDoc", 0).edit();
        editor.putString("keyLang", paramString);
        editor.apply();
    }

    public final void setIsRating(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("AppDoc", 0).edit();
        editor.putBoolean("isRating", true);
        editor.apply();
    }

    public final void setModeNight(Context paramContext, boolean paramBoolean) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("AppDoc", 0).edit();
        editor.putBoolean("mode_night", paramBoolean);
        editor.apply();
    }

    public final void setNumberBackApp(Context paramContext, int paramInt) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("AppDoc", 0).edit();
        if (paramInt == 8) {
            editor.putInt("keyNumberReaderFile", 6);
        } else {
            editor.putInt("keyNumberReaderFile", paramInt);
        }
        editor.apply();
    }

    public final void setOnShow(boolean paramBoolean) {
        onShow = paramBoolean;
    }

    public final void setStatusBarColor(Activity paramActivity, int paramInt) {
        Intrinsics.checkNotNullParameter(paramActivity, "activity");
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = paramActivity.getWindow();
            window.addFlags(-2147483648);
            window.clearFlags(67108864);
            window.setStatusBarColor(paramInt);
        }
    }

    public final void setTextViewDrawableColor(TextView paramTextView, int paramInt) {
        Intrinsics.checkNotNullParameter(paramTextView, "textView");
        Drawable[] arrayOfDrawable = paramTextView.getCompoundDrawables();
        Intrinsics.checkNotNullExpressionValue(arrayOfDrawable, "textView.compoundDrawables");
        int i = arrayOfDrawable.length;
        for (byte b = 0; b < i; b++) {
            Drawable drawable = arrayOfDrawable[b];
            if (drawable != null)
                drawable.setColorFilter((ColorFilter)new PorterDuffColorFilter(paramInt, PorterDuff.Mode.SRC_IN));
        }
    }

    public final void setTheme(Context paramContext, int paramInt) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("AppDoc", 0).edit();
        editor.putInt("Theme", paramInt);
        editor.apply();
    }

    public final Bitmap takeScreenshot(View paramView) {
        Intrinsics.checkNotNullParameter(paramView, "view");
        Bitmap bitmap = Bitmap.createBitmap(paramView.getWidth(), paramView.getHeight(), Bitmap.Config.ARGB_8888);
        paramView.draw(new Canvas(bitmap));
        return bitmap;
    }

    public final void writeBitmapToUri(Context paramContext, Bitmap paramBitmap, Uri paramUri, Bitmap.CompressFormat paramCompressFormat, int paramInt) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        Intrinsics.checkNotNullParameter(paramBitmap, "bitmap");
        OutputStream outputStream2 = null;
        OutputStream outputStream1 = outputStream2;
        try {
            ContentResolver contentResolver = paramContext.getContentResolver();
            outputStream1 = outputStream2;
            Intrinsics.checkNotNull(paramUri);
            outputStream1 = outputStream2;
            OutputStream outputStream = contentResolver.openOutputStream(paramUri);
            outputStream1 = outputStream;
            paramBitmap.compress(paramCompressFormat, paramInt, outputStream);
            return;
        } finally {
            closeSafe(outputStream1);
        }
    }

    public final void setTrackEvent(Context paramContext, String paramString1, String paramString2) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        Intrinsics.checkNotNullParameter(paramString1, "key");
        Intrinsics.checkNotNullParameter(paramString2, "value");
        Bundle bundle = new Bundle();
        bundle.putString(paramString1, paramString2);
        FirebaseAnalytics.getInstance(paramContext).logEvent(String.valueOf(paramString1), bundle);
    }

    public final void setTrackRevenueByAdjust(long paramLong, String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "currency");
        AdjustAdRevenue adjustAdRevenue = new AdjustAdRevenue("admob_sdk");
        adjustAdRevenue.setRevenue(Double.valueOf(((float)paramLong / 1000000.0F)), paramString);
        Adjust.trackAdRevenue(adjustAdRevenue);
    }

    public final void setLanguageForApp(Context paramContext) {
        Locale locale = new Locale("en");
        Intrinsics.checkNotNullParameter(paramContext, "context");
        String str = getIOSCountryData(paramContext);
        if (Intrinsics.areEqual(str, "not-set")) {
            locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "{\n            Locale.getDefault()\n        }");
        } else {
            locale = new Locale(locale.getLanguage());
        }
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        paramContext.getResources().updateConfiguration(configuration, paramContext.getResources().getDisplayMetrics());
    }

    public final void loadBannerAds(Context paramContext, AdView paramAdView, boolean paramBoolean) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        Intrinsics.checkNotNullParameter(paramAdView, "mAdView");
        if (!BillingClientSetup.isUpgraded(paramContext) && paramBoolean) {
            AdRequest adRequest = (new AdRequest.Builder()).build();
            Intrinsics.checkNotNullExpressionValue(adRequest, "Builder().build()");
            paramAdView.loadAd(adRequest);
            // TODO
//            paramAdView.setOnPaidEventListener(new Utils$$ExternalSyntheticLambda2());
//            paramAdView.setAdListener(new Utils$loadBannerAds$2(paramAdView));
        } else {
            paramAdView.setVisibility(View.GONE);
        }
    }

    public final int getIdColorByType(Activity paramActivity, String paramString) {
        Intrinsics.checkNotNullParameter(paramActivity, "activity");
        Intrinsics.checkNotNullParameter(paramString, "type");
        switch (paramString) {
            default:
                return R.color.color_screenshot;
            case "favorite":
                    return R.color.color_favourite;
            case "power_point":
                    return R.color.color_powerpoint;
            case "excel":
                    return R.color.color_excel;
            case "word":
                    return R.color.color_word;
            case "text":
                    return R.color.color_text;
            case "pdf":
                    return R.color.color_pdf;
            case "all":
                    return R.color.color_all;
        }
    }

    public final void loadNativeAds(Context paramContext, TemplateView paramTemplateView, String paramString, boolean paramBoolean) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        Intrinsics.checkNotNullParameter(paramTemplateView, "template");
        Intrinsics.checkNotNullParameter(paramString, "id");
        if (!BillingClientSetup.isUpgraded(paramContext) && paramBoolean) {
            // TODO
//            AdLoader adLoader = (new AdLoader.Builder(paramContext, paramString)).forNativeAd(new Utils$$ExternalSyntheticLambda0(paramTemplateView)).withAdListener(new Utils$loadNativeAds$adLoader$2(paramTemplateView)).withNativeAdOptions((new NativeAdOptions.Builder()).build()).build();
//            adLoader.loadAd((new AdRequest.Builder()).build());
        } else {
            paramTemplateView.setVisibility(View.GONE);
        }
    }

    public final ProgressDialog onCreateProgressDialog(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "context");
        ProgressDialog progressDialog = new ProgressDialog(paramContext, R.style.AlertDialogCustom);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(paramContext.getResources().getString(R.string.txt_loading));
        return progressDialog;
    }

    public final String getEndFile(String paramString) {
        return StringsKt.endsWith(paramString, ".pdf", false, 2, null) ? ".pdf" : (StringsKt.endsWith(paramString, ".doc", false, 2, null) ? ".doc" : (StringsKt.endsWith(paramString, ".docx", false, 2, null) ? ".docx" : (StringsKt.endsWith(paramString, ".xlsx", false, 2, null) ? ".xlsx" : (StringsKt.endsWith(paramString, ".xls", false, 2, null) ? ".xls" : (StringsKt.endsWith(paramString, ".pptx", false, 2, null) ? ".pptx" : (StringsKt.endsWith(paramString, ".txt", false, 2, null) ? ".txt" : (StringsKt.endsWith(paramString, ".ppt", false, 2, null) ? ".ppt" : (StringsKt.endsWith(paramString, ".png", false, 2, null) ? ".png" : (StringsKt.endsWith(paramString, ".jpg", false, 2, null) ? ".jpg" : "")))))))));
    }
}