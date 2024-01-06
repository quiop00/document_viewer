package com.ryu.document_reader.extension;

import android.webkit.MimeTypeMap;

import java.io.File;

import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

public class ExtensionsKt {
    public static final String mimeType(File paramFile) {
        Intrinsics.checkNotNullParameter(paramFile, "<this>");
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(FilesKt.getExtension(paramFile));
    }
}
