package com.ryu.document_reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ryu.document_reader.R;

import kotlin.jvm.internal.Intrinsics;

public final class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    private final Drawable mDivider;

    public SimpleDividerItemDecoration(Context paramContext) {
        this.mDivider = ContextCompat.getDrawable(paramContext, R.drawable.line_divider);
    }

    public SimpleDividerItemDecoration(Context paramContext, int paramInt) {
        this.mDivider = ContextCompat.getDrawable(paramContext, paramInt);
    }

    public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState) {
        Intrinsics.checkNotNullParameter(paramCanvas, "c");
        Intrinsics.checkNotNullParameter(paramRecyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(paramState, "state");
        int j = paramRecyclerView.getPaddingLeft();
        int i = paramRecyclerView.getWidth();
        int k = paramRecyclerView.getChildCount();
        for (byte b = 0; b < k; b++) {
            View view = paramRecyclerView.getChildAt(b);
            ViewGroup.LayoutParams layoutParams1 = view.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams1, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)layoutParams1;
            int m = view.getBottom() + layoutParams.bottomMargin;
            Drawable drawable = this.mDivider;
            Intrinsics.checkNotNull(drawable);
            int n = drawable.getIntrinsicHeight();
            drawable = this.mDivider;
            Intrinsics.checkNotNull(drawable);
            drawable.setBounds(j, m, i, n + m);
            drawable = this.mDivider;
            Intrinsics.checkNotNull(drawable);
            drawable.draw(paramCanvas);
        }
    }
}
