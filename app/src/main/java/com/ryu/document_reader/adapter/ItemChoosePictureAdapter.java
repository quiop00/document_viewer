package com.ryu.document_reader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ryu.document_reader.R;
import com.ryu.document_reader.callback.OnItemFileListener;

import java.util.ArrayList;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

public final class ItemChoosePictureAdapter extends RecyclerView.Adapter<ItemChoosePictureAdapter.MyViewHolder> {
    private Context context;

    private ArrayList<String> imageSelected;

    private ArrayList<String> itemPictures;

    private boolean mIsConvertMode;

    private OnItemFileListener onItemFileListener;

    public ItemChoosePictureAdapter(Context paramContext, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2, OnItemFileListener paramOnItemFileListener) {
        this.context = paramContext;
        this.itemPictures = paramArrayList1;
        this.imageSelected = paramArrayList2;
        this.onItemFileListener = paramOnItemFileListener;
    }

    private static final void onBindViewHolder$lambda$0(int paramInt, ItemChoosePictureAdapter paramItemChoosePictureAdapter, String paramString, View paramView) {
        if (paramInt == -1) {
            paramItemChoosePictureAdapter.imageSelected.add(paramString);
        } else {
            paramItemChoosePictureAdapter.imageSelected.remove(paramString);
        }
        paramItemChoosePictureAdapter.notifyDataSetChanged();
        paramItemChoosePictureAdapter.onItemFileListener.onItemFileClick(paramItemChoosePictureAdapter.imageSelected.size());
    }

    public final Context getContext() {
        return this.context;
    }

    public final ArrayList<String> getImageSelected() {
        return this.imageSelected;
    }

    public int getItemCount() {
        return this.itemPictures.size();
    }

    public final ArrayList<String> getItemPictures() {
        return this.itemPictures;
    }

    public final boolean getMIsConvertMode() {
        return this.mIsConvertMode;
    }

    public final OnItemFileListener getOnItemFileListener() {
        return this.onItemFileListener;
    }

    public void onBindViewHolder(MyViewHolder paramMyViewHolder, int paramInt) {
        View view = paramMyViewHolder.itemView;
        String str = (String)this.itemPictures.get(paramInt);
        str = str;
        Glide.with(this.context).load(str).into((ImageView)view.findViewById(R.id.imgPicture));
        paramInt = this.imageSelected.indexOf(str);
        if (!this.mIsConvertMode) {
            ((TextView)view.findViewById(R.id.txtIndexImage)).setVisibility(View.VISIBLE);
        } else {
            ((TextView)view.findViewById(R.id.txtIndexImage)).setVisibility(View.GONE);
        }
        if (paramInt == -1) {
            ((TextView)view.findViewById(R.id.txtIndexImage)).setBackgroundResource(R.drawable.ic_image_uncheck);
            ((TextView)view.findViewById(R.id.txtIndexImage)).setText("");
        } else {
            ((TextView)view.findViewById(R.id.txtIndexImage)).setBackgroundResource(R.drawable.ic_image_checked);
            ((TextView)view.findViewById(R.id.txtIndexImage)).setText(String.valueOf(paramInt + 1));
        }
        // TODO
        //view.setOnClickListener(new ItemChoosePictureAdapter$$ExternalSyntheticLambda0(paramInt, this, str));
    }

    public MyViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.layout_item_choose_image, paramViewGroup, false);
        return new MyViewHolder(view);
    }

    public final void setContext(Context paramContext) {
        this.context = paramContext;
    }

    public final void setImageSelected(ArrayList<String> paramArrayList) {
        this.imageSelected = paramArrayList;
    }

    public final void setItemPictures(ArrayList<String> paramArrayList) {
        this.itemPictures = paramArrayList;
    }

    public final void setMIsConvertMode(boolean paramBoolean) {
        this.mIsConvertMode = paramBoolean;
    }

    public final void setOnItemFileListener(OnItemFileListener paramOnItemFileListener) {
        this.onItemFileListener = paramOnItemFileListener;
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View param1View) {
            super(param1View);
        }
    }
}
