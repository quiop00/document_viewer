package com.ryu.document_reader.activity.home.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.SplashActivity;
import com.ryu.document_reader.extension.AppCompatActivityKt;
import com.ryu.document_reader.extension.ContextKt;
import com.ryu.document_reader.extension.DialogKt;
import com.ryu.document_reader.util.Utils;
import com.ryu.document_reader.widget.view.ItemSetting;

import java.util.Map;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public final class SettingsFragment extends Fragment {
    public static final Companion Companion = new Companion();

    public Map<Integer, View> findViewCache;

    private final void handleEvents() {
        // TODO
//        ((ItemSetting)_$_findCachedViewById(R.id.btnShare)).setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda0(this));
//        ((ItemSetting)_$_findCachedViewById(R.id.btnRateUs)).setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda1(this));
//        ((ItemSetting)_$_findCachedViewById(R.id.btnFeedback)).setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda2(this));
//        ((ItemSetting)_$_findCachedViewById(R.id.btnTheme)).setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda3(this));
//        ((ItemSetting)_$_findCachedViewById(R.id.btnLanguage)).setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda4(this));
//        ((ItemSetting)_$_findCachedViewById(R.id.btnPremium)).setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda5(this));
    }

    private static final void handleEvents$lambda$0(SettingsFragment paramSettingsFragment, View paramView) {
        paramSettingsFragment.shareApp();
    }

    private static final void handleEvents$lambda$1(SettingsFragment paramSettingsFragment, View paramView) {
        if (paramSettingsFragment.requireContext() instanceof com.ryu.document_reader.activity.home.HomeActivity2) {
            Context context = paramSettingsFragment.requireContext();
            DialogKt.showSatisfiedDialog((AppCompatActivity)context);
        }
    }

    private static final void handleEvents$lambda$2(SettingsFragment paramSettingsFragment, View paramView) {
        if (paramSettingsFragment.requireContext() instanceof com.ryu.document_reader.activity.home.HomeActivity2) {
            Context context = paramSettingsFragment.requireContext();
            AppCompatActivityKt.sendFeedback((AppCompatActivity)context, null);
        }
    }

    private static final void handleEvents$lambda$3(SettingsFragment paramSettingsFragment, View paramView) {
        paramSettingsFragment.showThemeDialog();
    }

    // TODO
    private static final void handleEvents$lambda$4(SettingsFragment paramSettingsFragment, View paramView) {
//        Intrinsics.checkNotNullParameter(paramSettingsFragment, "this$0");
//        Intent intent = new Intent(paramSettingsFragment.requireContext(), LanguageActivity.class);
//        intent.putExtra("FROM_HOME", true);
//        paramSettingsFragment.startActivity(intent);
    }

    // TODO
    private static final void handleEvents$lambda$5(SettingsFragment paramSettingsFragment, View paramView) {
//        Intrinsics.checkNotNullParameter(paramSettingsFragment, "this$0");
//        paramSettingsFragment.startActivity(new Intent(paramSettingsFragment.requireContext(), ShopActivity.class));
    }

    private final void initView() {}

    @JvmStatic
    public static final SettingsFragment newInstance() {
        return Companion.newInstance();
    }

    private final void shareApp() {
        ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from((Activity)requireActivity()).setType("text/plain").setChooserTitle("Chooser title");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://play.google.com/store/apps/details?id=");
        stringBuilder.append(requireActivity().getPackageName());
        intentBuilder.setText(stringBuilder.toString()).startChooser();
    }

    private final void showThemeDialog() {
        FragmentActivity fragmentActivity = requireActivity();
        Dialog dialog = ContextKt.onCreateDialog((Context)fragmentActivity, R.layout.dialog_theme_default, false);
        Utils utils = Utils.INSTANCE;
        Context context = requireContext();
        if (utils.getTheme(context) == 1) {
            ((RadioButton)dialog.findViewById(R.id.theme_dark)).setChecked(true);
        } else {
            ((RadioButton)dialog.findViewById(R.id.theme_white)).setChecked(true);
        }
        dialog.findViewById(R.id.btnCancelTheme).setOnClickListener((view) -> dialog.dismiss());
        View view = dialog.findViewById(R.id.radioGroup);
        ((RadioGroup)view).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                showThemeDialog$lambda$7(dialog, radioGroup, i);
            }
        });
        dialog.show();
    }

    private final void showThemeDialog$lambda$7( Dialog paramDialog, RadioGroup paramRadioGroup, int paramInt) {
        SettingsFragment paramSettingsFragment = this;
        Utils utils = Utils.INSTANCE;
        Context context = paramSettingsFragment.requireContext();
        if (paramInt == R.id.theme_dark) {
            utils.setTheme(context, 1);
        } else {
            utils.setTheme(context, 0);
        }
        paramDialog.dismiss();
        Intent intent = new Intent(paramSettingsFragment.requireContext(), SplashActivity.class);
        // TODO CHECK
//        intent.setFlags(268468224);
        paramSettingsFragment.startActivity(intent);
    }

    public void clearFindViewByIdCache() {
        this.findViewCache.clear();
    }

    public View findCachedViewById(int paramInt) {
        Map<Integer, View> map = this.findViewCache;
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

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        return paramLayoutInflater.inflate(R.layout.fragment_settings, paramViewGroup, false);
    }

    public void onViewCreated(View paramView, Bundle paramBundle) {
        super.onViewCreated(paramView, paramBundle);
        initView();
        handleEvents();
    }

    public static final class Companion {
        private Companion() {}

        @JvmStatic
        public final SettingsFragment newInstance() {
            return new SettingsFragment();
        }
    }
}
