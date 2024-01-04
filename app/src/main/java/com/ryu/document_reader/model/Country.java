package com.ryu.document_reader.model;

import kotlin.jvm.internal.Intrinsics;

public final class Country {
    private int icon;

    private String locale;

    private String name;

    public Country(int paramInt, String paramString1, String paramString2) {
        this.icon = paramInt;
        this.name = paramString1;
        this.locale = paramString2;
    }

    public final int component1() {
        return this.icon;
    }

    public final String component2() {
        return this.name;
    }

    public final String component3() {
        return this.locale;
    }

    public final Country copy(int paramInt, String paramString1, String paramString2) {
        Intrinsics.checkNotNullParameter(paramString1, "name");
        Intrinsics.checkNotNullParameter(paramString2, "locale");
        return new Country(paramInt, paramString1, paramString2);
    }

    public boolean equals(Object paramObject) {
        if (this == paramObject)
            return true;
        if (!(paramObject instanceof Country))
            return false;
        paramObject = paramObject;
        return (this.icon != ((Country)paramObject).icon) ? false : (!Intrinsics.areEqual(this.name, ((Country)paramObject).name) ? false : (!!Intrinsics.areEqual(this.locale, ((Country)paramObject).locale)));
    }

    public final int getIcon() {
        return this.icon;
    }

    public final String getLocale() {
        return this.locale;
    }

    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        return (this.icon * 31 + this.name.hashCode()) * 31 + this.locale.hashCode();
    }

    public final void setIcon(int paramInt) {
        this.icon = paramInt;
    }

    public final void setLocale(String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "<set-?>");
        this.locale = paramString;
    }

    public final void setName(String paramString) {
        Intrinsics.checkNotNullParameter(paramString, "<set-?>");
        this.name = paramString;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Country(icon=");
        stringBuilder.append(this.icon);
        stringBuilder.append(", name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", locale=");
        stringBuilder.append(this.locale);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}
