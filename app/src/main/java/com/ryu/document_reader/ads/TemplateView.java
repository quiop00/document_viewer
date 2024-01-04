package com.ryu.document_reader.ads;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.gms.ads.nativead.*;
import com.ryu.document_reader.R;

public class TemplateView extends FrameLayout {
    private static final String MEDIUM_TEMPLATE = "medium_template";

    private static final String SMALL_TEMPLATE = "small_template";

    private LinearLayoutCompat background;

    private TextView callToActionView;

    private ImageView iconView;

    private MediaView mediaView;

    private NativeAd nativeAd;

    private NativeAdView nativeAdView;

    private TextView primaryView;

    private RatingBar ratingBar;

    private TextView secondaryView;

    private NativeTemplateStyle styles;

    private int templateType;

    private TextView tertiaryView;

    private TextView txtAds;

    private TextView txtFull;

    public TemplateView(Context paramContext) {
        super(paramContext);
    }

    public TemplateView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initView(paramContext, paramAttributeSet);
    }

    public TemplateView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        initView(paramContext, paramAttributeSet);
    }

    public TemplateView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
        super(paramContext, paramAttributeSet, paramInt1, paramInt2);
        initView(paramContext, paramAttributeSet);
    }

    private boolean adHasOnlyStore(NativeAd paramNativeAd) {
        boolean bool;
        String str2 = paramNativeAd.getStore();
        String str1 = paramNativeAd.getAdvertiser();
        if (!TextUtils.isEmpty(str2) && TextUtils.isEmpty(str1)) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    private void applyStyles() {
        ColorDrawable colorDrawable2 = this.styles.getMainBackgroundColor();
        if (colorDrawable2 != null) {
            this.background.setBackground((Drawable) colorDrawable2);
            TextView textView = this.primaryView;
            if (textView != null)
                textView.setBackground((Drawable) colorDrawable2);
            textView = this.secondaryView;
            if (textView != null)
                textView.setBackground((Drawable) colorDrawable2);
            textView = this.tertiaryView;
            if (textView != null)
                textView.setBackground((Drawable) colorDrawable2);
        }
        Typeface typeface1 = this.styles.getPrimaryTextTypeface();
        if (typeface1 != null) {
            TextView textView = this.primaryView;
            if (textView != null)
                textView.setTypeface(typeface1);
        }
        typeface1 = this.styles.getSecondaryTextTypeface();
        if (typeface1 != null) {
            TextView textView = this.secondaryView;
            if (textView != null)
                textView.setTypeface(typeface1);
        }
        typeface1 = this.styles.getTertiaryTextTypeface();
        if (typeface1 != null) {
            TextView textView = this.tertiaryView;
            if (textView != null)
                textView.setTypeface(typeface1);
        }
        Typeface typeface2 = this.styles.getCallToActionTextTypeface();
        if (typeface2 != null) {
            TextView textView = this.callToActionView;
            if (textView != null)
                textView.setTypeface(typeface2);
        }
        int i = this.styles.getPrimaryTextTypefaceColor();
        if (i > 0) {
            TextView textView = this.primaryView;
            if (textView != null)
                textView.setTextColor(i);
        }
        i = this.styles.getSecondaryTextTypefaceColor();
        if (i > 0) {
            TextView textView = this.secondaryView;
            if (textView != null)
                textView.setTextColor(i);
        }
        i = this.styles.getTertiaryTextTypefaceColor();
        if (i > 0) {
            TextView textView = this.tertiaryView;
            if (textView != null)
                textView.setTextColor(i);
        }
        i = this.styles.getCallToActionTypefaceColor();
        if (i > 0) {
            TextView textView = this.callToActionView;
            if (textView != null)
                textView.setTextColor(i);
        }
        float f = this.styles.getCallToActionTextSize();
        if (f > 0.0F) {
            TextView textView = this.callToActionView;
            if (textView != null)
                textView.setTextSize(f);
        }
        f = this.styles.getPrimaryTextSize();
        if (f > 0.0F) {
            TextView textView = this.primaryView;
            if (textView != null)
                textView.setTextSize(f);
        }
        f = this.styles.getSecondaryTextSize();
        if (f > 0.0F) {
            TextView textView = this.secondaryView;
            if (textView != null)
                textView.setTextSize(f);
        }
        f = this.styles.getTertiaryTextSize();
        if (f > 0.0F) {
            TextView textView = this.tertiaryView;
            if (textView != null)
                textView.setTextSize(f);
        }
        ColorDrawable colorDrawable3 = this.styles.getCallToActionBackgroundColor();
        if (colorDrawable3 != null) {
            TextView textView = this.callToActionView;
            if (textView != null)
                textView.setBackground((Drawable) colorDrawable3);
        }
        ColorDrawable colorDrawable1 = this.styles.getPrimaryTextBackgroundColor();
        if (colorDrawable1 != null) {
            TextView textView = this.primaryView;
            if (textView != null)
                textView.setBackground((Drawable) colorDrawable1);
        }
        colorDrawable1 = this.styles.getSecondaryTextBackgroundColor();
        if (colorDrawable1 != null) {
            TextView textView = this.secondaryView;
            if (textView != null)
                textView.setBackground((Drawable) colorDrawable1);
        }
        colorDrawable3 = this.styles.getTertiaryTextBackgroundColor();
        if (colorDrawable3 != null) {
            TextView textView = this.tertiaryView;
            if (textView != null)
                textView.setBackground((Drawable) colorDrawable3);
        }
        invalidate();
        requestLayout();
    }

    private void initView(Context paramContext, AttributeSet paramAttributeSet) {
        TypedArray typedArray = paramContext.getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.TemplateView, 0, 0);
        try {
            this.templateType = typedArray.getResourceId(0, R.layout.gnt_medium_template_view);
            typedArray.recycle();
            return;
        } finally {
            typedArray.recycle();
        }
    }

    public void destroyNativeAd() {
        this.nativeAd.destroy();
    }

    public NativeAdView getNativeAdView() {
        return this.nativeAdView;
    }

    public String getTemplateTypeName() {
        int i = this.templateType;
        return (i == R.layout.gnt_medium_template_view) ? "medium_template" : ((i == R.layout.gnt_small_template_view) ? "small_template" : "");
    }

    // TODO
    public void onFinishInflate() {
        super.onFinishInflate();
        this.nativeAdView = (NativeAdView)findViewById(R.id.native_ad_view);
        this.primaryView = (TextView)findViewById(R.id.primary);
        this.secondaryView = (TextView)findViewById(R.id.secondary);
        this.tertiaryView = (TextView)findViewById(R.id.body);
        this.callToActionView = (TextView)findViewById(R.id.cta);
        this.iconView = (ImageView)findViewById(R.id.icon);
        this.mediaView = (MediaView)findViewById(R.id.media_view);
        this.background = (LinearLayoutCompat)findViewById(R.id.background);
        this.txtAds = (TextView)findViewById(R.id.txtAds);
        this.txtFull = (TextView)findViewById(R.id.txtFull);
    }

    public void setCallToActionViewBackground(int paramInt) {
        this.callToActionView.setBackgroundTintList(ColorStateList.valueOf(paramInt));
        this.txtAds.setBackgroundColor(paramInt);
    }

    public void setColor(int paramInt) {
        this.callToActionView.setBackgroundColor(paramInt);
        this.txtAds.setBackgroundColor(paramInt);
    }

    public void setNativeAd(NativeAd paramNativeAd) {
        this.nativeAd = paramNativeAd;
        paramNativeAd.getStore();
        String str3 = paramNativeAd.getAdvertiser();
        String str4 = paramNativeAd.getHeadline();
        String str1 = paramNativeAd.getBody();
        String str2 = paramNativeAd.getCallToAction();
        paramNativeAd.getStarRating();
        NativeAd.Image image = paramNativeAd.getIcon();
        this.nativeAdView.setCallToActionView((View) this.callToActionView);
        this.nativeAdView.setHeadlineView((View) this.primaryView);
        this.nativeAdView.setMediaView(this.mediaView);
        this.secondaryView.setVisibility(View.VISIBLE);
        if (adHasOnlyStore(paramNativeAd)) {
            this.nativeAdView.setStoreView((View) this.secondaryView);
        } else if (!TextUtils.isEmpty(str3)) {
            this.nativeAdView.setAdvertiserView((View) this.secondaryView);
        }
        this.primaryView.setText(str4);
        this.callToActionView.setText(str2);
        if (image != null) {
            this.iconView.setVisibility(View.VISIBLE);
            this.iconView.setImageDrawable(image.getDrawable());
        } else {
            this.iconView.setVisibility(View.GONE);
        }
        this.tertiaryView.setVisibility(View.GONE);
        TextView textView = this.secondaryView;
        if (textView != null) {
            textView.setText(str1);
            this.nativeAdView.setBodyView((View) this.secondaryView);
        }
        this.txtAds.setText("Ad");
        this.txtFull.setText("");
        this.txtFull.setVisibility(View.GONE);
        this.nativeAdView.setNativeAd(paramNativeAd);
    }

    public void setStyles(NativeTemplateStyle paramNativeTemplateStyle) {
        this.styles = paramNativeTemplateStyle;
        applyStyles();
    }
}
