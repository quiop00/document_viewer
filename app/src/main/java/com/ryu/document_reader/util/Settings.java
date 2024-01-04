package com.ryu.document_reader.util;
import com.ryu.document_reader.activity.MyApplication;
import android.content.SharedPreferences;

import kotlin.jvm.internal.Intrinsics;

public final class Settings {
    public static final Settings INSTANCE = new Settings();

    private static final SharedPreferences sharedPreferences = MyApplication.Companion.getContext().getSharedPreferences("AllDocumentReader2022", 0);

    public final boolean isSetDefaultApp() {
        return sharedPreferences.getBoolean("SET_DEFAULT_APP", false);
    }

    public final void setSetDefaultApp(boolean paramBoolean) {
        SharedPreferences sharedPreferences = Settings.sharedPreferences;
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "sharedPreferences");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intrinsics.checkNotNullExpressionValue(editor, "editor");
        editor.putBoolean("SET_DEFAULT_APP", paramBoolean);
        editor.apply();
    }
}