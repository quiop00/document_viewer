package com.ryu.document_reader.widget;

import androidx.recyclerview.widget.RecyclerView;

public abstract class MyRecyclerScroll extends RecyclerView.OnScrollListener {
    static final float MINIMUM = 25.0F;

    boolean isVisible = true;

    int scrollDist = 0;

    public abstract void hide();

    public void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2) {
        super.onScrolled(paramRecyclerView, paramInt1, paramInt2);
        boolean bool = this.isVisible;
        if (bool && this.scrollDist > 25.0F) {
            hide();
            this.scrollDist = 0;
            this.isVisible = false;
        } else if (!bool && this.scrollDist < -25.0F) {
            show();
            this.scrollDist = 0;
            this.isVisible = true;
        }
        bool = this.isVisible;
        if ((bool && paramInt2 > 0) || (!bool && paramInt2 < 0))
            this.scrollDist += paramInt2;
    }

    public abstract void show();
}
