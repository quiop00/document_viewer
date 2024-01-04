package com.ryu.document_reader.extension;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.view.Window;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ryu.document_reader.R;

import kotlin.jvm.internal.Intrinsics;

// TODO FIX
// onCreateBottomSheetDialog
// onCreateDialog
public final class ContextKt {
    public static final boolean isPermissionGranted(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "<this>");
        int i = Build.VERSION.SDK_INT;
        boolean bool2 = true;
        boolean bool1 = bool2;
        if (i >= 23)
            if (Build.VERSION.SDK_INT >= 30) {
                bool1 = Environment.isExternalStorageManager();
            } else if (ContextCompat.checkSelfPermission(paramContext.getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                bool1 = bool2;
            } else {
                bool1 = false;
            }
        return bool1;
    }

    public static final Dialog onCreateBottomSheetDialog(Context paramContext, int paramInt, boolean paramBoolean) {
        Intrinsics.checkNotNullParameter(paramContext, "<this>");
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(paramContext, R.style.BottomSheetDialogStyle);
        bottomSheetDialog.setContentView(paramInt);
        bottomSheetDialog.setCancelable(paramBoolean);
        Window window = bottomSheetDialog.getWindow();
        if (window != null)
            //window.setBackgroundDrawableResource(17170445);
        window = bottomSheetDialog.getWindow();
        if (window != null)
            window.setLayout(-1, -2);
        return (Dialog)bottomSheetDialog;
    }

    public static final Dialog onCreateDialog(Context paramContext, int paramInt, boolean paramBoolean) {
        Intrinsics.checkNotNullParameter(paramContext, "<this>");
        Dialog dialog = new Dialog(paramContext);
        dialog.setContentView(paramInt);
        dialog.setCancelable(paramBoolean);
        Window window = dialog.getWindow();
        if (window != null)
            //window.setBackgroundDrawableResource(17170445);
        window = dialog.getWindow();
        if (window != null)
            window.setLayout(-1, -2);
        return dialog;
    }

    public static final void showNotice(Context paramContext, int paramInt) {
        Intrinsics.checkNotNullParameter(paramContext, "<this>");
        Toast.makeText(paramContext, paramInt, Toast.LENGTH_SHORT).show();
    }

    public static final void showNotice(Context paramContext, String paramString) {
        Intrinsics.checkNotNullParameter(paramContext, "<this>");
        Intrinsics.checkNotNullParameter(paramString, "msg");
        Toast.makeText(paramContext, paramString, Toast.LENGTH_SHORT).show();
    }
}
