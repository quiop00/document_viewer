package com.ryu.document_reader.model;

import kotlin.jvm.internal.Intrinsics;

public final class FileApp {
    private boolean isLove;

    private String name;

    private String path;

    private long time;

    private String type;

    public FileApp(String paramString1, String paramString2, String paramString3, long paramLong, boolean paramBoolean) {
        this.type = paramString1;
        this.path = paramString2;
        this.name = paramString3;
        this.time = paramLong;
        this.isLove = paramBoolean;
    }

    public final String getName() {
        return this.name;
    }

    public final String getPath() {
        return this.path;
    }

    public final long getTime() {
        return this.time;
    }

    public final String getType() {
        return this.type;
    }

    public final boolean isLove() {
        return this.isLove;
    }

    public final void setLove(boolean paramBoolean) {
        this.isLove = paramBoolean;
    }

    public final void setName(String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "<set-?>");
        this.name = paramString;
    }

    public final void setPath(String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "<set-?>");
        this.path = paramString;
    }

    public final void setTime(long paramLong) {
        this.time = paramLong;
    }

    public final void setType(String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "<set-?>");
        this.type = paramString;
    }
}

