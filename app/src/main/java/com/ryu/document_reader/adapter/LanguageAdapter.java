package com.ryu.document_reader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.language.LanguageActivity;
import com.ryu.document_reader.model.Country;

import java.util.ArrayList;

public final class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {
    private Context context;

    private ArrayList<Country> countries;

    private OnItemLanguageListener onItemLanguageListener;

    public LanguageAdapter(Context paramContext, ArrayList<Country> paramArrayList, OnItemLanguageListener paramOnItemLanguageListener) {
        this.context = paramContext;
        this.countries = paramArrayList;
        this.onItemLanguageListener = paramOnItemLanguageListener;
    }

    private static final void onBindViewHolder$lambda$0(LanguageAdapter paramLanguageAdapter, int paramInt, View paramView) {
        paramLanguageAdapter.onItemLanguageListener.onItemLanguageClick(paramInt);
    }

    private static final void onBindViewHolder$lambda$1(LanguageAdapter paramLanguageAdapter, int paramInt, View paramView) {
        paramLanguageAdapter.onItemLanguageListener.onItemLanguageClick(paramInt);
    }

    public final Context getContext() {
        return this.context;
    }

    public final ArrayList<Country> getCountries() {
        return this.countries;
    }

    public int getItemCount() {
        return this.countries.size();
    }

    public final OnItemLanguageListener getOnItemLanguageListener() {
        return this.onItemLanguageListener;
    }

    public void onBindViewHolder(MyViewHolder paramMyViewHolder, int paramInt) {
        View view = paramMyViewHolder.itemView;
        Country country = (Country)this.countries.get(paramInt);
        country = country;
        ((ImageView)view.findViewById(R.id.imgIconFlag)).setImageResource(country.getIcon());
        ((TextView)view.findViewById(R.id.txtNameCountry)).setText(country.getName());
        if (this.context instanceof LanguageActivity) {
            boolean bool;
            RadioButton radioButton = (RadioButton)view.findViewById(R.id.rbCheck);
            Context context = this.context;
            if (((LanguageActivity)context).getMPosCheck() == paramInt) {
                bool = true;
            } else {
                bool = false;
            }
            radioButton.setChecked(bool);
        }
        // TODO
        //view.setOnClickListener(new LanguageAdapter$$ExternalSyntheticLambda0(this, paramInt));
        //((RadioButton)view.findViewById(R.id.rbCheck)).setOnClickListener(new LanguageAdapter$$ExternalSyntheticLambda1(this, paramInt));
    }

    public MyViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.layout_item_language, paramViewGroup, false);
        return new MyViewHolder(view);
    }

    public final void setContext(Context paramContext) {
        this.context = paramContext;
    }

    public final void setCountries(ArrayList<Country> paramArrayList) {
        this.countries = paramArrayList;
    }

    public final void setOnItemLanguageListener(OnItemLanguageListener paramOnItemLanguageListener) {
        this.onItemLanguageListener = paramOnItemLanguageListener;
    }


    public final class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View param1View) {
            super(param1View);
        }
    }


    public static interface OnItemLanguageListener {
        void onItemLanguageClick(int param1Int);
    }
}