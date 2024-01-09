package com.ryu.document_reader.activity.permission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ryu.document_reader.R;
import com.ryu.document_reader.util.Settings;

import java.util.Arrays;
import java.util.Map;

public final class SetDefaultReaderActivity extends AppCompatActivity {
    public Map<Integer, View> findViewCache;

    private final void handleEvents() {
        findCachedViewById(R.id.btnOK).setOnClickListener(view -> {
            Settings.INSTANCE.setSetDefaultApp(true);
            SetDefaultReaderActivity.this.finish();
        });
    }

    private final void initView() {
        String str2 = getString(R.string.txt_select_app_and_click);
        str2 = String.format(str2, Arrays.copyOf(new Object[] { getString(R.string.app_name) }, 1));
        ((TextView)findCachedViewById(R.id.tvTitle)).setText((CharSequence)HtmlCompat.fromHtml(str2, 0));
        String str1 = getString(R.string.txt_select);
        str1 = String.format(str1, Arrays.copyOf(new Object[] { getString(R.string.app_name) }, 1));
        ((TextView)findCachedViewById(R.id.tvStep1)).setText((CharSequence)HtmlCompat.fromHtml(str1, 0));
        ((TextView)findCachedViewById(R.id.tvStep2)).setText((CharSequence)HtmlCompat.fromHtml(getString(R.string.txt_then_click), 0));
    }

    public void clearFindViewByIdCache() {
        this.findViewCache.clear();
    }

    public View findCachedViewById(int paramInt) {
        Map<Integer, View> map = this.findViewCache;
        View view2 = map.get(Integer.valueOf(paramInt));
        View view1 = view2;
        if (view2 == null) {
            view1 = findViewById(paramInt);
            if (view1 != null) {
                map.put(Integer.valueOf(paramInt), view1);
            } else {
                view1 = null;
            }
        }
        return view1;
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_set_default_reader);
        initView();
        handleEvents();
    }
}