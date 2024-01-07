package com.ryu.document_reader.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;

import com.ryu.document_reader.R;

import java.util.Map;

import kotlin.jvm.internal.Intrinsics;

public final class ItemSetting extends CardView {
    public Map<Integer, View> findViewCache;

    public ItemSetting(Context paramContext) {
        this(paramContext, (AttributeSet)null, 6);
    }

    public ItemSetting(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 4);
    }

    public ItemSetting(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramAttributeSet);
    }

    private final void init(AttributeSet paramAttributeSet) {
        View.inflate(getContext(), R.layout.layout_item_setting, (ViewGroup)this);
        TypedArray typedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ItemSetting);
        try {
            String str = typedArray.getString(0);
            int i = typedArray.getResourceId(0, 0);
            if (i != 0) {
                Drawable drawable = AppCompatResources.getDrawable(getContext(), i);
                ((ImageView)findCachedViewById(R.id.ivIcon)).setImageDrawable(drawable);
            }
            ((TextView)findCachedViewById(R.id.tvTitle)).setText(str);
            return;
        } finally {
            typedArray.recycle();
        }
    }

    public void clearFindViewByIdCache() {
        this.findViewCache.clear();
    }

    public View findCachedViewById(int paramInt) {
        Map<Integer, View> map = this.findViewCache;
        View view2 = map.get(Integer.valueOf(paramInt));
        View view1 = view2;
        if (view2 == null) {
            view1 = findViewById(paramInt);
            if (view1 != null) {
                map.put(Integer.valueOf(paramInt), view1);
            } else {
                view1 = null;
            }
        }
        return view1;
    }
}