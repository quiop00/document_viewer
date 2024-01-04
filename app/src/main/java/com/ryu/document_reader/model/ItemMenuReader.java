package com.ryu.document_reader.model;

import kotlin.jvm.internal.Intrinsics;

public final class ItemMenuReader {
    private int idIconMenu;

    private String titleMenu;

    public ItemMenuReader(int paramInt, String paramString) {
        this.idIconMenu = paramInt;
        this.titleMenu = paramString;
    }

    public final int component1() {
        return this.idIconMenu;
    }

    public final String component2() {
        return this.titleMenu;
    }

    public final ItemMenuReader copy(int paramInt, String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "titleMenu");
        return new ItemMenuReader(paramInt, paramString);
    }

    public boolean equals(Object paramObject) {
        if (this == paramObject)
            return true;
        if (!(paramObject instanceof ItemMenuReader))
            return false;
        paramObject = paramObject;
        return (this.idIconMenu != ((ItemMenuReader)paramObject).idIconMenu) ? false : (!!Intrinsics.areEqual(this.titleMenu, ((ItemMenuReader)paramObject).titleMenu));
    }

    public final int getIdIconMenu() {
        return this.idIconMenu;
    }

    public final String getTitleMenu() {
        return this.titleMenu;
    }

    public int hashCode() {
        return this.idIconMenu * 31 + this.titleMenu.hashCode();
    }

    public final void setIdIconMenu(int paramInt) {
        this.idIconMenu = paramInt;
    }

    public final void setTitleMenu(String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "<set-?>");
        this.titleMenu = paramString;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ItemMenuReader(idIconMenu=");
        stringBuilder.append(this.idIconMenu);
        stringBuilder.append(", titleMenu=");
        stringBuilder.append(this.titleMenu);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}
