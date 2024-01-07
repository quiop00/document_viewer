package com.ryu.document_reader.widget.drawingview.brushes;


import android.graphics.Canvas;

import com.ryu.document_reader.widget.drawingview.DrawingEvent;

public interface BrushRenderer {
    void draw(Canvas canvas);
    void onTouch(DrawingEvent drawingEvent);
    void setBrush(Brush brush);
}
