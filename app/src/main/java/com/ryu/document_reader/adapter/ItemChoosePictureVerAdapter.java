package com.ryu.document_reader.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ryu.document_reader.R;
import com.ryu.document_reader.callback.OnItemFileListener;
import com.ryu.document_reader.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

public final class ItemChoosePictureVerAdapter extends RecyclerView.Adapter<ItemChoosePictureVerAdapter.MyViewHolder> {
    private Context context;

    private ArrayList<String> imageSelected;

    private ArrayList<String> itemPictures;

    private OnItemFileListener onItemFileListener;

    public ItemChoosePictureVerAdapter(Context paramContext, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2, OnItemFileListener paramOnItemFileListener) {
        this.context = paramContext;
        this.itemPictures = paramArrayList1;
        this.imageSelected = paramArrayList2;
        this.onItemFileListener = paramOnItemFileListener;
    }

    private static final void onBindViewHolder$lambda$0(int paramInt, ItemChoosePictureVerAdapter paramItemChoosePictureVerAdapter, String paramString, View paramView) {
        Intrinsics.checkNotNullParameter(paramItemChoosePictureVerAdapter, "this$0");
        Intrinsics.checkNotNullParameter(paramString, "$itemPicture");
        if (paramInt == -1) {
            paramItemChoosePictureVerAdapter.imageSelected.add(paramString);
        } else {
            paramItemChoosePictureVerAdapter.imageSelected.remove(paramString);
        }
        paramItemChoosePictureVerAdapter.notifyDataSetChanged();
        paramItemChoosePictureVerAdapter.onItemFileListener.onItemFileClick(paramItemChoosePictureVerAdapter.imageSelected.size());
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

    public final OnItemFileListener getOnItemFileListener() {
        return this.onItemFileListener;
    }

    public void onBindViewHolder(MyViewHolder paramMyViewHolder, int paramInt) {
        Intrinsics.checkNotNullParameter(paramMyViewHolder, "holder");
        View view = paramMyViewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(view, "holder.itemView");
        String str1 = (String)this.itemPictures.get(paramInt);
        Intrinsics.checkNotNullExpressionValue(str1, "itemPictures[position]");
        String str2 = str1;
        File file = new File(str2);
        Glide.with(this.context).load(str2).into((ImageView)view.findViewById(R.id.imgPicture));
        paramInt = this.imageSelected.indexOf(str2);
        ((TextView)view.findViewById(R.id.txtNameImage)).setText(file.getName());
        CharSequence charSequence = DateFormat.format("dd-MM-yyyy", new Date(file.lastModified()));
        long l = file.length();
        TextView textView = (TextView)view.findViewById(R.id.txtTimeSize);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(charSequence);
        stringBuilder.append(" • ");
        stringBuilder.append(Utils.INSTANCE.getSizeFile(l));
        textView.setText(stringBuilder.toString());
        if (paramInt == -1) {
            ((ImageView)view.findViewById(R.id.imgTick)).setBackgroundResource(R.drawable.ic_untick_nn);
        } else {
            ((ImageView)view.findViewById(R.id.imgTick)).setBackgroundResource(R.drawable.ic_tick_nn);
        }
        // TODO
        //view.setOnClickListener(new ItemChoosePictureVerAdapter$$ExternalSyntheticLambda0(paramInt, this, str2));
    }

    public MyViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        Intrinsics.checkNotNullParameter(paramViewGroup, "parent");
        View view = LayoutInflater.from(this.context).inflate(R.layout.layout_item_choose_image_ver, paramViewGroup, false);
        Intrinsics.checkNotNullExpressionValue(view, "itemView");
        return new MyViewHolder(view);
    }

    public final void setContext(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "<set-?>");
        this.context = paramContext;
    }

    public final void setImageSelected(ArrayList<String> paramArrayList) {
        Intrinsics.checkNotNullParameter(paramArrayList, "<set-?>");
        this.imageSelected = paramArrayList;
    }

    public final void setItemPictures(ArrayList<String> paramArrayList) {
        Intrinsics.checkNotNullParameter(paramArrayList, "<set-?>");
        this.itemPictures = paramArrayList;
    }

    public final void setOnItemFileListener(OnItemFileListener paramOnItemFileListener) {
        Intrinsics.checkNotNullParameter(paramOnItemFileListener, "<set-?>");
        this.onItemFileListener = paramOnItemFileListener;
    }

    public final class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View param1View) {
            super(param1View);
        }
    }
}