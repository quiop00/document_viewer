package com.ryu.document_reader.util;

import android.content.Context;
import android.net.Uri;

import java.net.URISyntaxException;

public class PathUtil {
    public static String getPath(Context paramContext, Uri paramUri) {
        return "";
    }

    public static boolean isDownloadsDocument(Uri paramUri) {
        return "com.android.providers.downloads.documents".equals(paramUri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri paramUri) {
        return "com.android.externalstorage.documents".equals(paramUri.getAuthority());
    }

    public static boolean isMediaDocument(Uri paramUri) {
        return "com.android.providers.media.documents".equals(paramUri.getAuthority());
    }
}
