package com.ryu.document_reader.activity.home.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.home.HomeActivity2;
import com.ryu.document_reader.adapter.ListFileAdapter;
import com.ryu.document_reader.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public final class RecentFragment extends Fragment implements ListFileAdapter.OnItemListener {
    public static final Companion Companion = new Companion();

    public Map<Integer, View> _$_findViewCache;

    private ArrayList<File> mFiles = new ArrayList<File>();

    private ListFileAdapter mListFileAdapter;

    private final void handleEvents() {}

    @JvmStatic
    public static final RecentFragment newInstance() {
        return Companion.newInstance();
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
        this.mFiles.clear();
        this.mFiles.addAll(HomeActivity2.Companion.getFileRecents());
        Context context = requireContext();
        Utils utils = Utils.INSTANCE;
        FragmentActivity fragmentActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivity, "requireActivity()");
        int i = ContextCompat.getColor(context, utils.getIdColorByType((Activity)fragmentActivity, "recent"));
        this.mListFileAdapter = new ListFileAdapter(this.mFiles, this, i, false, null);
        RecyclerView recyclerView = (RecyclerView)_$_findCachedViewById(R.id.rvRecents);
        ListFileAdapter listFileAdapter2 = this.mListFileAdapter;
        ListFileAdapter listFileAdapter1 = listFileAdapter2;
        if (listFileAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mListFileAdapter");
            listFileAdapter1 = null;
        }
        recyclerView.setAdapter((RecyclerView.Adapter)listFileAdapter1);
        LinearLayout linearLayout = (LinearLayout)_$_findCachedViewById(R.id.layoutEmpty);
        Intrinsics.checkNotNullExpressionValue(linearLayout, "layoutEmpty");
        View view = (View)linearLayout;
        if (this.mFiles.isEmpty()) {
            i = View.VISIBLE;
        } else {
            i = View.GONE;
        }
        view.setVisibility(i);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        Intrinsics.checkNotNullParameter(paramLayoutInflater, "inflater");
        return paramLayoutInflater.inflate(R.layout.fragment_recent, paramViewGroup, false);
    }

    public void onItemClick(String paramString1, String paramString2) {
        Intent intent = null;
        Intrinsics.checkNotNullParameter(paramString1, "name");
        Intrinsics.checkNotNullParameter(paramString2, "url");
        Utils utils = Utils.INSTANCE;
        Context context = requireContext();
        Intrinsics.checkNotNullExpressionValue(context, "requireContext()");
        utils.setFileRecent(paramString2, context);
        context = null;
        // TODO
//        if (StringsKt.endsWith(paramString1, ".pdf", false, 2, null)) {
//            intent = new Intent(requireContext(), PdfViewerActivity.class);
//            intent.putExtra("url", paramString2);
//        } else if (StringsKt.endsWith(paramString1, ".doc", false, 2, null) || StringsKt.endsWith$default(paramString1, ".docx", false, 2, null)) {
//            intent = new Intent(requireContext(), WordViewerActivity.class);
//            intent.putExtra("url", paramString2);
//        } else if (StringsKt.endsWith(paramString1, ".xlsx", false, 2, null) || StringsKt.endsWith$default(paramString1, ".xls", false, 2, null)) {
//            intent = new Intent(requireContext(), ExelViewerActivity.class);
//            intent.putExtra("url", paramString2);
//        } else if (StringsKt.endsWith(paramString1, ".pptx", false, 2, null) || StringsKt.endsWith$default(paramString1, ".ppt", false, 2, null)) {
//            intent = new Intent(requireContext(), PowerPointViewerActivity.class);
//            intent.putExtra("url", paramString2);
//        } else if (StringsKt.endsWith(paramString1, ".png", false, 2, null) || StringsKt.endsWith$default(paramString1, ".jpg", false, 2, null)) {
//            intent = new Intent(requireContext(), ImageViewerActivity.class);
//            intent.putExtra("url", paramString2);
//        } else if (StringsKt.endsWith(paramString1, ".txt", false, 2, null)) {
//            intent = new Intent(requireContext(), TextViewerActivity.class);
//            intent.putExtra("url", paramString2);
//        }
//        if (requireContext() instanceof HomeActivity2 && intent != null) {
//            Context context1 = requireContext();
//            Intrinsics.checkNotNull(context1, "null cannot be cast to non-null type com.ryu.document_reader.activity.home.HomeActivity2");
//            ((HomeActivity2)context1).showAds(intent);
//        }
    }

    public void onViewCreated(View paramView, Bundle paramBundle) {
        Intrinsics.checkNotNullParameter(paramView, "view");
        super.onViewCreated(paramView, paramBundle);
        initView();
        handleEvents();
    }

    public static final class Companion {
        private Companion() {}

        @JvmStatic
        public final RecentFragment newInstance() {
            return new RecentFragment();
        }
    }
}