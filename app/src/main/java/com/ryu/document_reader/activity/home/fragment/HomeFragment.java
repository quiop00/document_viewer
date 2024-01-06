package com.ryu.document_reader.activity.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.home.HomeActivity2;
import com.ryu.document_reader.adapter.MenuHomeAdapter;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.model.ItemMenu;

import java.util.ArrayList;
import java.util.Map;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public final class HomeFragment extends Fragment implements MenuHomeAdapter.OnItemMenuHomeListener {
    public static final Companion Companion = new Companion();

    public Map<Integer, View> _$_findViewCache;

    private ArrayList<ItemMenu> mItemMenus;

    private MenuHomeAdapter mMenuHomeAdapter;

    private MenuHomeAdapter mMenuHomeVerAdapter;

    private final String[] mTypes = new String[] { "all", "pdf", "word", "excel", "power_point", "text", "image", "favorite" };

    private final void handleEvents() {
        // TODO
        //((FloatingActionButton)_$_findCachedViewById(R.id.btnConvert)).setOnClickListener(new HomeFragment$$ExternalSyntheticLambda0(this));
    }

    // TODO
    private static final void handleEvents$lambda$0(HomeFragment paramHomeFragment, View paramView) {
//        Intrinsics.checkNotNullParameter(paramHomeFragment, "this$0");
//        Intent intent = new Intent(paramHomeFragment.requireContext(), ChooseImageToPdfActivity.class);
//        if (paramHomeFragment.requireContext() instanceof HomeActivity2) {
//            Context context = paramHomeFragment.requireContext();
//            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.ryu.document_reader.activity.home.HomeActivity2");
//            ((HomeActivity2)context).showAds(intent);
//        }
    }

    @JvmStatic
    public static final HomeFragment newInstance() {
        return Companion.newInstance();
    }

    private final void setNumberFile() {
        ArrayList<ItemMenu> arrayList2 = this.mItemMenus;
        MenuHomeAdapter menuHomeAdapter2 = null;
        ArrayList<ItemMenu> arrayList1 = arrayList2;
        if (arrayList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList1 = null;
        }
        ((ItemMenu)arrayList1.get(0)).setTotalFile(HomeActivity2.Companion.getFileAll().size());
        arrayList2 = this.mItemMenus;
        arrayList1 = arrayList2;
        if (arrayList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList1 = null;
        }
        ((ItemMenu)arrayList1.get(1)).setTotalFile(HomeActivity2.Companion.getFilePDFs().size());
        arrayList2 = this.mItemMenus;
        arrayList1 = arrayList2;
        if (arrayList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList1 = null;
        }
        ((ItemMenu)arrayList1.get(2)).setTotalFile(HomeActivity2.Companion.getFileWords().size());
        arrayList2 = this.mItemMenus;
        arrayList1 = arrayList2;
        if (arrayList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList1 = null;
        }
        ((ItemMenu)arrayList1.get(3)).setTotalFile(HomeActivity2.Companion.getFileExcels().size());
        arrayList2 = this.mItemMenus;
        arrayList1 = arrayList2;
        if (arrayList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList1 = null;
        }
        ((ItemMenu)arrayList1.get(4)).setTotalFile(HomeActivity2.Companion.getFilePowerPoints().size());
        arrayList2 = this.mItemMenus;
        arrayList1 = arrayList2;
        if (arrayList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList1 = null;
        }
        ((ItemMenu)arrayList1.get(5)).setTotalFile(HomeActivity2.Companion.getFileTexts().size());
        arrayList2 = this.mItemMenus;
        arrayList1 = arrayList2;
        if (arrayList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList1 = null;
        }
        ((ItemMenu)arrayList1.get(6)).setTotalFile(HomeActivity2.Companion.getFileImages().size());
        arrayList2 = this.mItemMenus;
        arrayList1 = arrayList2;
        if (arrayList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList1 = null;
        }
        ((ItemMenu)arrayList1.get(7)).setTotalFile(HomeActivity2.Companion.getFileFavorites().size());
        MenuHomeAdapter menuHomeAdapter1 = this.mMenuHomeAdapter;
        if (menuHomeAdapter1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMenuHomeAdapter");
            menuHomeAdapter1 = menuHomeAdapter2;
        }
        menuHomeAdapter1.notifyDataSetChanged();
    }

    public void _$_clearFindViewByIdCache() {
        this._$_findViewCache.clear();
    }

    public View _$_findCachedViewById(int paramInt) {
        Map<Integer, View> map = this._$_findViewCache;
        View view2 = map.get(Integer.valueOf(paramInt));
        View view1 = view2;
        if (view2 == null) {
            view1 = getView();
            if (view1 != null) {
                view1 = view1.findViewById(paramInt);
                if (view1 != null) {
                    map.put(Integer.valueOf(paramInt), view1);
                    return view1;
                }
            }
            view1 = null;
        }
        return view1;
    }

    public final void initView() {
        ArrayList<ItemMenu> arrayList1 = new ArrayList<>();
        if (!AdsManager.INSTANCE.is_show_button_convert())
            ((FloatingActionButton)_$_findCachedViewById(R.id.btnConvert)).setVisibility(View.GONE);
        ArrayList<ItemMenu> arrayList4 = new ArrayList();
        this.mItemMenus = arrayList4;
        String str1 = getString(R.string.all);
        Intrinsics.checkNotNullExpressionValue(str1, "getString(R.string.all)");
        arrayList4.add(new ItemMenu(str1, ContextCompat.getColor(requireContext(), R.color.color_bg_menu_all), R.drawable.ic_icon_menu_all, 0));
        ArrayList<ItemMenu> arrayList13 = this.mItemMenus;
        arrayList4 = null;
        ArrayList<ItemMenu> arrayList3 = arrayList13;
        if (arrayList13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList3 = null;
        }
        String str9 = getString(R.string.pdf);
        Intrinsics.checkNotNullExpressionValue(str9, "getString(R.string.pdf)");
        arrayList3.add(new ItemMenu(str9, ContextCompat.getColor(requireContext(), R.color.color_bg_menu_pdf), R.drawable.ic_icon_menu_pdf, 0));
        ArrayList<ItemMenu> arrayList12 = this.mItemMenus;
        arrayList3 = arrayList12;
        if (arrayList12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList3 = null;
        }
        String str8 = getString(R.string.word);
        Intrinsics.checkNotNullExpressionValue(str8, "getString(R.string.word)");
        arrayList3.add(new ItemMenu(str8, ContextCompat.getColor(requireContext(), R.color.color_bg_menu_word), R.drawable.ic_icon_menu_word, 0));
        ArrayList<ItemMenu> arrayList11 = this.mItemMenus;
        arrayList3 = arrayList11;
        if (arrayList11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList3 = null;
        }
        String str7 = getString(R.string.excel);
        Intrinsics.checkNotNullExpressionValue(str7, "getString(R.string.excel)");
        arrayList3.add(new ItemMenu(str7, ContextCompat.getColor(requireContext(), R.color.color_bg_menu_excel), R.drawable.ic_icon_menu_excel, 0));
        ArrayList<ItemMenu> arrayList10 = this.mItemMenus;
        arrayList3 = arrayList10;
        if (arrayList10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList3 = null;
        }
        String str6 = getString(R.string.power_point);
        Intrinsics.checkNotNullExpressionValue(str6, "getString(R.string.power_point)");
        arrayList3.add(new ItemMenu(str6, ContextCompat.getColor(requireContext(), R.color.color_bg_menu_ppt), R.drawable.ic_icon_menu_ppt, 0));
        ArrayList<ItemMenu> arrayList9 = this.mItemMenus;
        arrayList3 = arrayList9;
        if (arrayList9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList3 = null;
        }
        String str5 = getString(R.string.text);
        Intrinsics.checkNotNullExpressionValue(str5, "getString(R.string.text)");
        arrayList3.add(new ItemMenu(str5, ContextCompat.getColor(requireContext(), R.color.color_bg_menu_text), R.drawable.ic_icon_menu_txt, 0));
        ArrayList<ItemMenu> arrayList8 = this.mItemMenus;
        arrayList3 = arrayList8;
        if (arrayList8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList3 = null;
        }
        String str4 = getString(R.string.screenshot);
        Intrinsics.checkNotNullExpressionValue(str4, "getString(R.string.screenshot)");
        arrayList3.add(new ItemMenu(str4, ContextCompat.getColor(requireContext(), R.color.color_bg_menu_screenshot), R.drawable.ic_icon_menu_screenshot, 0));
        ArrayList<ItemMenu> arrayList7 = this.mItemMenus;
        arrayList3 = arrayList7;
        if (arrayList7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList3 = null;
        }
        String str3 = getString(R.string.fab_favourite);
        Intrinsics.checkNotNullExpressionValue(str3, "getString(R.string.fab_favourite)");
        arrayList3.add(new ItemMenu(str3, ContextCompat.getColor(requireContext(), R.color.color_bg_menu_favourite), R.drawable.ic_icon_menu_favourite, 0));
        ArrayList<ItemMenu> arrayList6 = this.mItemMenus;
        arrayList3 = arrayList6;
        if (arrayList6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList3 = null;
        }
        String str2 = getString(R.string.feedback);
        Intrinsics.checkNotNullExpressionValue(str2, "getString(R.string.feedback)");
        arrayList3.add(new ItemMenu(str2, ContextCompat.getColor(requireContext(), R.color.color_bg_menu_feedback), R.drawable.ic_icon_menu_feedback, 0));
        Context context1 = requireContext();
        Intrinsics.checkNotNullExpressionValue(context1, "requireContext()");
        arrayList3 = this.mItemMenus;
        if (arrayList3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList3 = null;
        }
        HomeFragment homeFragment = this;
        this.mMenuHomeAdapter = new MenuHomeAdapter(context1, arrayList3, homeFragment, false);
        ((RecyclerView)_$_findCachedViewById(R.id.rvMenuHome)).setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager(requireContext(), 3));
        RecyclerView recyclerView2 = (RecyclerView)_$_findCachedViewById(R.id.rvMenuHome);
        MenuHomeAdapter menuHomeAdapter3 = this.mMenuHomeAdapter;
        MenuHomeAdapter menuHomeAdapter2 = menuHomeAdapter3;
        if (menuHomeAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMenuHomeAdapter");
            menuHomeAdapter2 = null;
        }
        recyclerView2.setAdapter((RecyclerView.Adapter)menuHomeAdapter2);
        Context context2 = requireContext();
        Intrinsics.checkNotNullExpressionValue(context2, "requireContext()");
        ArrayList<ItemMenu> arrayList5 = this.mItemMenus;
        ArrayList<ItemMenu> arrayList2 = arrayList5;
        if (arrayList5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mItemMenus");
            arrayList2 = null;
        }
        this.mMenuHomeVerAdapter = new MenuHomeAdapter(context2, arrayList2, homeFragment, false);
        RecyclerView recyclerView1 = (RecyclerView)_$_findCachedViewById(R.id.rvMenuHomeVer);
        MenuHomeAdapter menuHomeAdapter1 = this.mMenuHomeVerAdapter;
        if (menuHomeAdapter1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMenuHomeVerAdapter");
            arrayList1 = arrayList4;
        }
        recyclerView1.setAdapter((RecyclerView.Adapter) menuHomeAdapter1);
        setNumberFile();
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        Intrinsics.checkNotNullParameter(paramLayoutInflater, "inflater");
        return paramLayoutInflater.inflate(R.layout.fragment_home, paramViewGroup, false);
    }

    public void onItemMenuHomeClick(int paramInt) {
        if (paramInt < 8) {
            // TODO
//            Intent intent = new Intent(requireContext(), ListFileActivity.class);
//            intent.putExtra("type", this.mTypes[paramInt]);
//            if (requireContext() instanceof HomeActivity2) {
//                Context context = requireContext();
//                Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.ryu.document_reader.activity.home.HomeActivity2");
//                ((HomeActivity2)context).showAds(intent);
//            }
        } else if (requireContext() instanceof HomeActivity2) {
            Context context = requireContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.ryu.document_reader.activity.home.HomeActivity2");
            ((HomeActivity2)context).showFeedbackDialog();
        }
    }

    public void onResume() {
        super.onResume();
        setNumberFile();
    }

    public void onViewCreated(View paramView, Bundle paramBundle) {
        Intrinsics.checkNotNullParameter(paramView, "view");
        super.onViewCreated(paramView, paramBundle);
        initView();
        handleEvents();
    }

    public final void setGridOrVerticalMenu(boolean paramBoolean) {
        int b = paramBoolean ? View.VISIBLE : View.GONE;
        RecyclerView recyclerView2 = (RecyclerView)_$_findCachedViewById(R.id.rvMenuHome);
        Intrinsics.checkNotNullExpressionValue(recyclerView2, "rvMenuHome");
        ((View)recyclerView2).setVisibility(b);
        RecyclerView recyclerView1 = (RecyclerView)_$_findCachedViewById(R.id.rvMenuHomeVer);
        Intrinsics.checkNotNullExpressionValue(recyclerView1, "rvMenuHomeVer");
        View view1 = (View)recyclerView1;
        view1.setVisibility(b);
    }

    public static final class Companion {
        private Companion() {}

        @JvmStatic
        public final HomeFragment newInstance() {
            return new HomeFragment();
        }
    }
}
