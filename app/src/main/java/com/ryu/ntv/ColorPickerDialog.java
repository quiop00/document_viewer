package com.ryu.ntv;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ColorPickerDialog {
    int alpha;

    final float[] currentColorHsv;

    private Dialog dialog;

    private final boolean isAlpha;

    final OnColorPickerListener listener;

    private ImageView viewAlphaCheckered;

    private ImageView viewAlphaCursor;

    private View viewAlphaOverlay;

    private ViewGroup viewContainerAlpha;

    private ViewGroup viewContainerHue;

    private ViewGroup viewContainerSat;

    private ImageView viewCursor;

    private View viewHue;

    private View viewNewColor;

    private View viewOldColor;

    private ColorPickerSquare viewSatVal;

    private ImageView viewTarget;

    public ColorPickerDialog(Context paramContext, int paramInt, boolean paramBoolean, OnColorPickerListener paramOnColorPickerListener) {
        byte b;
        float[] arrayOfFloat = new float[3];
        this.currentColorHsv = arrayOfFloat;
        this.isAlpha = paramBoolean;
        this.listener = paramOnColorPickerListener;
        if (!paramBoolean)
            paramInt |= 0xFF000000;
        Color.colorToHSV(paramInt, arrayOfFloat);
        this.alpha = Color.alpha(paramInt);
    }

    public static interface OnColorPickerListener {
        void onCancel(ColorPickerDialog param1ColorPickerDialog);

        void onOk(ColorPickerDialog param1ColorPickerDialog, int param1Int);
    }
}
