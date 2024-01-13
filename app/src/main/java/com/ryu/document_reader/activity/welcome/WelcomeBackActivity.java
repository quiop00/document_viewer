package com.ryu.document_reader.activity.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.MyApplication;
import com.ryu.document_reader.ads.AppOpenManager;

import java.util.Map;

public final class WelcomeBackActivity extends AppCompatActivity {
    public Map<Integer, View> findViewCache;

    public void clearFindViewByIdCache() {
        this.findViewCache.clear();
    }

    public View findCachedViewById(int paramInt) {
        Map<Integer, View> map = this.findViewCache;
        View view2 = map.get(paramInt);
        View view1 = view2;
        if (view2 == null) {
            view1 = findViewById(paramInt);
            if (view1 != null) {
                map.put(paramInt, view1);
            } else {
                view1 = null;
            }
        }
        return view1;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_welcome_back);
        try {
            ((TextView)findCachedViewById(R.id.txtTitle)).setText(HtmlCompat.fromHtml(getString(R.string.app_name_splash), 0));
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
            findCachedViewById(R.id.ivIconApp).startAnimation(animation);
            Handler handler = new Handler();
            handler.postDelayed(() -> MyApplication.Companion.getAppOpenManager().showAdIfAvailable(), 1000L);
            AppOpenManager appOpenManager = MyApplication.Companion.getAppOpenManager();
            appOpenManager.setOpenAdsListener(() -> finish());
        } catch (Exception exception) {
            finish();
        }
    }
}