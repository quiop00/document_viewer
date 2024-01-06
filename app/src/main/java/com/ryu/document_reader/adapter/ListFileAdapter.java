package com.ryu.document_reader.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.SplashActivity;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.activity.home.HomeActivity2;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.ads.NativeTemplateStyle;
import com.ryu.document_reader.ads.TemplateView;
import com.ryu.document_reader.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;

public final class ListFileAdapter extends RecyclerView.Adapter<ListFileAdapter.MyViewHolder> {
    private int color;

    private boolean isListFile;

    private boolean isSelectedMode;

    private ArrayList<File> mFiles;

    private NativeAd mNativeAd;

    private final ArrayList<File> mSelectedFile;

    private OnItemListener onItemListener;

    private final OnItemSelectListener onItemSelectListener;

    public ListFileAdapter(ArrayList<File> paramArrayList, OnItemListener paramOnItemListener, int paramInt, boolean paramBoolean, OnItemSelectListener paramOnItemSelectListener) {
        this.mFiles = paramArrayList;
        this.onItemListener = paramOnItemListener;
        this.color = paramInt;
        this.isListFile = paramBoolean;
        this.onItemSelectListener = paramOnItemSelectListener;
        this.mSelectedFile = new ArrayList<File>();
    }

    private static final void onBindViewHolder$lambda$0(ListFileAdapter paramListFileAdapter, View paramView, NativeAd paramNativeAd) {
        Intrinsics.checkNotNullParameter(paramListFileAdapter, "this$0");
        Intrinsics.checkNotNullParameter(paramView, "$itemView");
        Intrinsics.checkNotNullParameter(paramNativeAd, "nativeAd");
        paramListFileAdapter.mNativeAd = paramNativeAd;
        NativeTemplateStyle nativeTemplateStyle = (new NativeTemplateStyle.Builder()).build();
        ((TemplateView) paramView.findViewById(R.id.my_template)).setStyles(nativeTemplateStyle);
        ((TemplateView) paramView.findViewById(R.id.my_template)).setNativeAd(paramNativeAd);
    }

    private static final void onBindViewHolder$lambda$1(Ref.BooleanRef paramBooleanRef, View paramView1, File paramFile, View paramView2) {
        Intrinsics.checkNotNullParameter(paramBooleanRef, "$isFavorite");
        Intrinsics.checkNotNullParameter(paramView1, "$itemView");
        Intrinsics.checkNotNullParameter(paramFile, "$file");
        if (paramBooleanRef.element) {
            paramBooleanRef.element = false;
            ((ImageView) paramView1.findViewById(R.id.imgFavorite)).setImageResource(R.drawable.ic_favourite_inactive);
            HomeActivity2.Companion.getFileFavorites().remove(paramFile);
        } else {
            paramBooleanRef.element = true;
            ((ImageView) paramView1.findViewById(R.id.imgFavorite)).setImageResource(R.drawable.ic_favourite_active);
            HomeActivity2.Companion.getFileFavorites().add(paramFile);
        }
        Utils utils = Utils.INSTANCE;
        String str = paramFile.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(str, "file.absolutePath");
        Context context = paramView1.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "itemView.context");
        utils.setFileFavorite(str, context, paramBooleanRef.element);
    }

    private static final void onBindViewHolder$lambda$2(ListFileAdapter paramListFileAdapter, String paramString, File paramFile, View paramView) {
        Intrinsics.checkNotNullParameter(paramListFileAdapter, "this$0");
        Intrinsics.checkNotNullParameter(paramFile, "$file");
        OnItemListener onItemListener = paramListFileAdapter.onItemListener;
        Intrinsics.checkNotNullExpressionValue(paramString, "name");
        String str = paramFile.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(str, "file.absolutePath");
        onItemListener.onItemClick(paramString, str);
    }

    private static final void onBindViewHolder$lambda$3(ListFileAdapter paramListFileAdapter, File paramFile, int paramInt, View paramView) {
        Intrinsics.checkNotNullParameter(paramListFileAdapter, "this$0");
        Intrinsics.checkNotNullParameter(paramFile, "$file");
        if (!paramListFileAdapter.mSelectedFile.contains(paramFile)) {
            paramListFileAdapter.mSelectedFile.add(paramFile);
        } else {
            paramListFileAdapter.mSelectedFile.remove(paramFile);
        }
        paramListFileAdapter.notifyItemChanged(paramInt);
        OnItemSelectListener onItemSelectListener = paramListFileAdapter.onItemSelectListener;
        if (onItemSelectListener != null)
            onItemSelectListener.onItemSelected();
    }

    public final int getColor() {
        return this.color;
    }

    public int getItemCount() {
        return this.mFiles.size() + 1;
    }

    public final ArrayList<File> getMFiles() {
        return this.mFiles;
    }

    public final ArrayList<File> getMSelectedFile() {
        return this.mSelectedFile;
    }

    public final OnItemListener getOnItemListener() {
        return this.onItemListener;
    }

    public final OnItemSelectListener getOnItemSelectListener() {
        return this.onItemSelectListener;
    }

    public final boolean isListFile() {
        return this.isListFile;
    }

    public final boolean isSelectedMode() {
        return this.isSelectedMode;
    }

    public void onBindViewHolder(MyViewHolder paramMyViewHolder, int paramInt) {
        NativeTemplateStyle nativeTemplateStyle = null;
        Intrinsics.checkNotNullParameter(paramMyViewHolder, "holder");
        View view = paramMyViewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(view, "holder.itemView");
        int j = SplashActivity.Companion.getTheme();
        int i = View.INVISIBLE;
        if (j == 1) {
            ((CardView) view.findViewById(R.id.layoutItem)).setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorBlackAltLight));
            ((TextView) view.findViewById(R.id.tvName)).setTextColor(-1);
            ((TextView) view.findViewById(R.id.tvTime)).setTextColor(-1);
            ((ImageView) view.findViewById(R.id.imgFavorite)).setColorFilter(-1);
        }
        int k = this.mFiles.size();
        j = 8;
        if (k > 0 && paramInt == AdsManager.INSTANCE.getAdsPositionInListFile()) {
            ((ConstraintLayout) view.findViewById(R.id.my_main)).setVisibility(View.GONE);
            if (!BillingClientSetup.isUpgraded(view.getContext()) && ((AdsManager.INSTANCE.is_show_native_ads_list_file() && this.isListFile) || (AdsManager.INSTANCE.is_show_native_ads_search_file() && !this.isListFile))) {
                ((TemplateView) view.findViewById(R.id.my_template)).setColor(this.color);
                ((TemplateView) view.findViewById(R.id.my_template)).setVisibility(View.VISIBLE);
                if (this.mNativeAd == null) {
                    // TODO
                    AdLoader adLoader = (new AdLoader.Builder(view.getContext(), view.getContext().getString(R.string.alldoc_native_list_file_id))).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {

                        }
                    }).withAdListener(new ListFileAdapterBindViewHolderAdLoader(view)).withNativeAdOptions((new NativeAdOptions.Builder()).build()).build();
                    Intrinsics.checkNotNullExpressionValue(adLoader, "itemView = holder.itemVi…                 .build()");
                    adLoader.loadAd((new AdRequest.Builder()).build());
                } else {
                    nativeTemplateStyle = (new NativeTemplateStyle.Builder()).build();
                    ((TemplateView) view.findViewById(R.id.my_template)).setStyles(nativeTemplateStyle);
                    ((TemplateView) view.findViewById(R.id.my_template)).setNativeAd(this.mNativeAd);
                }
            } else {
                ((TemplateView) view.findViewById(R.id.my_template)).setVisibility(View.GONE);
            }
        } else {
            ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.my_main);
            Intrinsics.checkNotNullExpressionValue(constraintLayout, "itemView.my_main");
            View view1 = (View) constraintLayout;
            if (this.mFiles.size() <= 0)
                i = View.VISIBLE;
            else {
                i = View.GONE;
            }
            view1.setVisibility(i);
            if (this.mFiles.size() > 0) {
                View view2 = ((RecyclerView.ViewHolder) paramMyViewHolder).itemView;
                Intrinsics.checkNotNullExpressionValue(view2, "holder.itemView");
                ((TemplateView) view2.findViewById(R.id.my_template)).setVisibility(View.GONE);
                CheckBox checkBox = (CheckBox) view2.findViewById(R.id.cbSelected);
                Intrinsics.checkNotNullExpressionValue(checkBox, "itemView.cbSelected");
                View view4 = (View) checkBox;
                i = j;
                if (this.isSelectedMode)
                    i = View.VISIBLE;
                view4.setVisibility(i);
                ImageView imageView = (ImageView) view2.findViewById(R.id.imgFavorite);
                Intrinsics.checkNotNullExpressionValue(imageView, "itemView.imgFavorite");
                View view3 = (View) imageView;
                if (this.isSelectedMode) {
                    i = View.INVISIBLE;
                } else {
                    i = View.GONE;
                }
                view3.setVisibility(i);
                if (paramInt > 0) {
                    i = paramInt - 1;
                } else {
                    i = paramInt;
                }
                File file = (File) this.mFiles.get(i);
                Intrinsics.checkNotNullExpressionValue(file, "mFiles[pos]");
                String str1 = file.getName();
                ((CheckBox) view2.findViewById(R.id.cbSelected)).setChecked(this.mSelectedFile.contains(file));
                Intrinsics.checkNotNullExpressionValue(str1, "name");
                if (StringsKt.endsWith(str1, ".pdf", false, 2, null)) {
                    ((CardView) view2.findViewById(R.id.cardType)).setCardBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.color_bg_menu_pdf));
                    ((ImageView) view2.findViewById(R.id.imgType)).setImageResource(R.drawable.ic_icon_menu_pdf);
                } else if (StringsKt.endsWith(str1, ".doc", false, 2, null) || StringsKt.endsWith$default(str1, ".docx", false, 2, null)) {
                    ((CardView) view2.findViewById(R.id.cardType)).setCardBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.color_bg_menu_word));
                    ((ImageView) view2.findViewById(R.id.imgType)).setImageResource(R.drawable.ic_icon_menu_word);
                } else if (StringsKt.endsWith(str1, ".xlsx", false, 2, null) || StringsKt.endsWith$default(str1, ".xls", false, 2, null)) {
                    ((CardView) view2.findViewById(R.id.cardType)).setCardBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.color_bg_menu_excel));
                    ((ImageView) view2.findViewById(R.id.imgType)).setImageResource(R.drawable.ic_icon_menu_excel);
                } else if (StringsKt.endsWith(str1, ".pptx", false, 2, null) || StringsKt.endsWith$default(str1, ".ppt", false, 2, null)) {
                    ((CardView) view2.findViewById(R.id.cardType)).setCardBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.color_bg_menu_ppt));
                    ((ImageView) view2.findViewById(R.id.imgType)).setImageResource(R.drawable.ic_icon_menu_ppt);
                } else if (StringsKt.endsWith(str1, ".png", false, 2, null) || StringsKt.endsWith$default(str1, ".jpg", false, 2, null)) {
                    ((CardView) view2.findViewById(R.id.cardType)).setCardBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.color_bg_menu_screenshot));
                    ((ImageView) view2.findViewById(R.id.imgType)).setImageResource(R.drawable.ic_icon_menu_screenshot);
                } else if (StringsKt.endsWith(str1, ".txt", false, 2, null)) {
                    ((CardView) view2.findViewById(R.id.cardType)).setCardBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.color_bg_menu_text));
                    ((ImageView) view2.findViewById(R.id.imgType)).setImageResource(R.drawable.ic_icon_menu_txt);
                }
                ((TextView) view2.findViewById(R.id.tvName)).setText(str1);
                long l = file.lastModified();
                ((TextView) view2.findViewById(R.id.tvTime)).setText(DateFormat.format("yyyy-MM-dd hh:mm:ss a", new Date(l)));
                Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                Utils utils = Utils.INSTANCE;
                String str2 = file.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(str2, "file.absolutePath");
                Context context = view2.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "itemView.context");
                booleanRef.element = utils.isFileFavorite(str2, context);
                if (booleanRef.element) {
                    ((ImageView) view2.findViewById(R.id.imgFavorite)).setImageResource(R.drawable.ic_favourite_active);
                } else {
                    ((ImageView) view2.findViewById(R.id.imgFavorite)).setImageResource(R.drawable.ic_favourite_inactive);
                }
                // TODO
//                ((ImageView)view2.findViewById(R.id.imgFavorite)).setOnClickListener(new ListFileAdapter$$ExternalSyntheticLambda1(booleanRef, view2, file));
//                view2.setOnClickListener(new ListFileAdapter$$ExternalSyntheticLambda2(this, str1, file));
//                ((CheckBox)view2.findViewById(R.id.cbSelected)).setOnClickListener(new ListFileAdapter$$ExternalSyntheticLambda3(this, file, paramInt));
            }
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        Intrinsics.checkNotNullParameter(paramViewGroup, "parent");
        View view = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.layout_item_list_file, paramViewGroup, false);
        Intrinsics.checkNotNullExpressionValue(view, "itemView");
        return new MyViewHolder(view);
    }

    public final void setColor(int paramInt) {
        this.color = paramInt;
    }

    public final void setListFile(boolean paramBoolean) {
        this.isListFile = paramBoolean;
    }

    public final void setMFiles(ArrayList<File> paramArrayList) {
        Intrinsics.checkNotNullParameter(paramArrayList, "<set-?>");
        this.mFiles = paramArrayList;
    }

    public final void setOnItemListener(OnItemListener paramOnItemListener) {
        Intrinsics.checkNotNullParameter(paramOnItemListener, "<set-?>");
        this.onItemListener = paramOnItemListener;
    }

    public final void setSelectedMode(boolean paramBoolean) {
        this.isSelectedMode = paramBoolean;
        notifyDataSetChanged();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View param1View) {
            super(param1View);
        }
    }

    public static interface OnItemListener {
        void onItemClick(String param1String1, String param1String2);
    }

    public static interface OnItemSelectListener {
        void onItemSelected();
    }

    public static final class ListFileAdapterBindViewHolderAdLoader extends AdListener {
        final View itemView;

        ListFileAdapterBindViewHolderAdLoader(View param1View) {
            this.itemView = param1View;
        }

        public void onAdFailedToLoad(LoadAdError param1LoadAdError) {
            Intrinsics.checkNotNullParameter(param1LoadAdError, "adError");
            ((TemplateView) this.itemView.findViewById(R.id.my_template)).setVisibility(View.GONE);
        }
    }
}