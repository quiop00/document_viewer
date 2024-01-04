package com.ryu.document_reader.model;

import kotlin.jvm.internal.Intrinsics;

public final class ItemMenu {
    private int color;

    private int idIcon;

    private String title;

    private int totalFile;

    public ItemMenu(String paramString, int paramInt1, int paramInt2, int paramInt3) {
        this.title = paramString;
        this.color = paramInt1;
        this.idIcon = paramInt2;
        this.totalFile = paramInt3;
    }

    public final String component1() {
        return this.title;
    }

    public final int component2() {
        return this.color;
    }

    public final int component3() {
        return this.idIcon;
    }

    public final int component4() {
        return this.totalFile;
    }

    public final ItemMenu copy(String paramString, int paramInt1, int paramInt2, int paramInt3) {
        Intrinsics.checkNotNullParameter(paramString, "title");
        return new ItemMenu(paramString, paramInt1, paramInt2, paramInt3);
    }

    public boolean equals(Object paramObject) {
        if (this == paramObject)
            return true;
        if (!(paramObject instanceof ItemMenu))
            return false;
        paramObject = paramObject;
        return !Intrinsics.areEqual(this.title, ((ItemMenu)paramObject).title) ? false : ((this.color != ((ItemMenu)paramObject).color) ? false : ((this.idIcon != ((ItemMenu)paramObject).idIcon) ? false : (!(this.totalFile != ((ItemMenu)paramObject).totalFile))));
    }

    public final int getColor() {
        return this.color;
    }

    public final int getIdIcon() {
        return this.idIcon;
    }

    public final String getTitle() {
        return this.title;
    }

    public final int getTotalFile() {
        return this.totalFile;
    }

    public int hashCode() {
        return ((this.title.hashCode() * 31 + this.color) * 31 + this.idIcon) * 31 + this.totalFile;
    }

    public final void setColor(int paramInt) {
        this.color = paramInt;
    }

    public final void setIdIcon(int paramInt) {
        this.idIcon = paramInt;
    }

    public final void setTitle(String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "<set-?>");
        this.title = paramString;
    }

    public final void setTotalFile(int paramInt) {
        this.totalFile = paramInt;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ItemMenu(title=");
        stringBuilder.append(this.title);
        stringBuilder.append(", color=");
        stringBuilder.append(this.color);
        stringBuilder.append(", idIcon=");
        stringBuilder.append(this.idIcon);
        stringBuilder.append(", totalFile=");
        stringBuilder.append(this.totalFile);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}
