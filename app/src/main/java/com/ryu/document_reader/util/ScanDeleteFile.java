package com.ryu.document_reader.util;

import android.content.ContentResolver;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;

import java.io.File;

public class ScanDeleteFile implements MediaScannerConnection.OnScanCompletedListener {
    final Context context;

    public ScanDeleteFile(Context paramContext) {
        this.context = paramContext;
    }

    public void onScanCompleted(String paramString, Uri paramUri) {
        try {
            File file = new File(paramString);
            file.delete();
            boolean bool = file.exists();
            if (bool) {
                file.getCanonicalFile().delete();
                if (file.exists())
                    this.context.deleteFile(file.getName());
            } else {
                Log.d("delete success222!!!", "abc");
            }
            ContentResolver contentResolver = this.context.getContentResolver();
            String str = (String)null;
            String[] arrayOfString = (String[])null;
            contentResolver.delete(paramUri, null, null);
            MediaScannerConnection.scanFile(this.context, new String[] { file.getParent() }, null, null);
//            if (file.exists()) {
//                StringBuilder stringBuilder = new StringBuilder();
//                Log.d("delete success3333!!!", stringBuilder.toString());
//            } else {
//                Log.d("delete fial222!!!", "abc");
//            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}