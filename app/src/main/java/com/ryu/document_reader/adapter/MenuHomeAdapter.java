package com.ryu.document_reader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.SplashActivity;
import com.ryu.document_reader.model.ItemMenu;

import java.util.ArrayList;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

public final class MenuHomeAdapter extends RecyclerView.Adapter<MenuHomeAdapter.MyViewHolder> {
    private Context context;

    private boolean isGrid;

    private ArrayList<ItemMenu> itemMenus;

    private OnItemMenuHomeListener onItemMenuHomeListener;

    public MenuHomeAdapter(Context paramContext, ArrayList<ItemMenu> paramArrayList, OnItemMenuHomeListener paramOnItemMenuHomeListener, boolean paramBoolean) {
        this.context = paramContext;
        this.itemMenus = paramArrayList;
        this.onItemMenuHomeListener = paramOnItemMenuHomeListener;
        this.isGrid = paramBoolean;
    }

    private static final void onBindViewHolder$lambda$0(MenuHomeAdapter paramMenuHomeAdapter, int paramInt, View paramView) {
        Intrinsics.checkNotNullParameter(paramMenuHomeAdapter, "this$0");
        paramMenuHomeAdapter.onItemMenuHomeListener.onItemMenuHomeClick(paramInt);
    }

    public final Context getContext() {
        return this.context;
    }

    public int getItemCount() {
        return this.itemMenus.size();
    }

    public final ArrayList<ItemMenu> getItemMenus() {
        return this.itemMenus;
    }

    public final OnItemMenuHomeListener getOnItemMenuHomeListener() {
        return this.onItemMenuHomeListener;
    }

    public final boolean isGrid() {
        return this.isGrid;
    }

    public void onBindViewHolder(MyViewHolder paramMyViewHolder, int paramInt) {
        Intrinsics.checkNotNullParameter(paramMyViewHolder, "holder");
        View view = paramMyViewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(view, "holder.itemView");
//        paramMyViewHolder = (MyViewHolder);
        Intrinsics.checkNotNullExpressionValue(paramMyViewHolder, "itemMenus[position]");
        ItemMenu itemMenu = (ItemMenu)this.itemMenus.get(paramInt);
        int i = SplashActivity.Companion.getTheme();
        boolean bool = true;
        if (i == 1 && this.isGrid)
            ((TextView)view.findViewById(R.id.txtTitleMenuHome)).setTextColor(-1);
        ((TextView)view.findViewById(R.id.txtTitleMenuHome)).setText(itemMenu.getTitle());
        ((ImageView)view.findViewById(R.id.imgMenuHome)).setImageResource(itemMenu.getIdIcon());
        if (this.isGrid) {
            TextView textView2 = (TextView)view.findViewById(R.id.txtNumberOfFile);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(itemMenu.getTotalFile());
            stringBuilder.append(' ');
            stringBuilder.append(this.context.getString(R.string.txt_file));
            textView2.setText(stringBuilder.toString());
            TextView textView1 = (TextView)view.findViewById(R.id.txtNumberOfFile);
            Intrinsics.checkNotNullExpressionValue(textView1, "itemView.txtNumberOfFile");
            View view1 = (View)textView1;
            i = View.VISIBLE;
            if (paramInt != 8)
                bool = false;
            if (bool)
                i = View.INVISIBLE;
            view1.setVisibility(i);
        } else {
            String str;
            if (paramInt != 8) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(itemMenu.getTitle());
                stringBuilder.append(" (");
                stringBuilder.append(itemMenu.getTotalFile());
                stringBuilder.append(')');
                str = stringBuilder.toString();
            } else {
                str = itemMenu.getTitle();
            }
            ((TextView)view.findViewById(R.id.txtTitleMenuHome)).setText(str);
        }
        ((CardView)view.findViewById(R.id.cardItemMenu)).setCardBackgroundColor(itemMenu.getColor());
        // TODO
        //view.setOnClickListener(new MenuHomeAdapter$$ExternalSyntheticLambda0(this, paramInt));
    }

    public MyViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        MyViewHolder myViewHolder;
        Intrinsics.checkNotNullParameter(paramViewGroup, "parent");
        if (this.isGrid) {
            View view = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.layout_item_menu_home, paramViewGroup, false);
            Intrinsics.checkNotNullExpressionValue(view, "itemView");
            myViewHolder = new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.layout_item_menu_home_ver, paramViewGroup, false);
            Intrinsics.checkNotNullExpressionValue(view, "itemView");
            myViewHolder = new MyViewHolder(view);
        }
        return myViewHolder;
    }

    public final void setContext(Context paramContext) {
        Intrinsics.checkNotNullParameter(paramContext, "<set-?>");
        this.context = paramContext;
    }

    public final void setGrid(boolean paramBoolean) {
        this.isGrid = paramBoolean;
    }

    public final void setItemMenus(ArrayList<ItemMenu> paramArrayList) {
        Intrinsics.checkNotNullParameter(paramArrayList, "<set-?>");
        this.itemMenus = paramArrayList;
    }

    public final void setOnItemMenuHomeListener(OnItemMenuHomeListener paramOnItemMenuHomeListener) {
        Intrinsics.checkNotNullParameter(paramOnItemMenuHomeListener, "<set-?>");
        this.onItemMenuHomeListener = paramOnItemMenuHomeListener;
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View param1View) {
            super(param1View);
        }
    }


    public static interface OnItemMenuHomeListener {
        void onItemMenuHomeClick(int param1Int);
    }
}
