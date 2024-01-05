package com.ryu.document_reader.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.ryu.document_reader.util.Utils;
import com.ryu.document_reader.extension.DialogKt;

import java.util.Map;

import kotlin.jvm.internal.Intrinsics;

public class BaseActivity extends AppCompatActivity {
    public static final Companion Companion = new Companion();

    private static boolean isFirstView = true;

    public Map<Integer, View> findViewCache;

    private boolean mIsView;

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

    public void onBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handleOnBackPress();
            }
        };

        callback.setEnabled(true); // Enable the callback
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void handleOnBackPress() {
        Utils utils = Utils.INSTANCE;
        Context context = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(context, "applicationContext");
        int i = utils.getNumberBackApp(context);
        // TODO fix
//        if (this instanceof com.ryu.document_reader.activity.listfile.ListFileActivity) {
//            setResult(-1, new Intent());
//            finish();
//        } else {
//            Utils utils1 = Utils.INSTANCE;
//            Context context1 = getApplicationContext();
//            Intrinsics.checkNotNullExpressionValue(context1, "applicationContext");
//            if (!utils1.isRating(context1)) {
//                if (this.mIsView) {
//                    if ((i == 3 || i == 5 || i == 7) && isFirstView) {
//                        isFirstView = false;
//                        DialogKt.showRatingDialog(this);
//                    } else {
//                        setResult(-1, new Intent());
//                        finish();
//                    }
//                } else if (this instanceof com.ryu.document_reader.activity.home.HomeActivity2 && i == 1) {
//                    DialogKt.showRatingDialog(this);
//                } else {
//                    setResult(-1, new Intent());
//                    finish();
//                }
//            } else {
//                setResult(-1, new Intent());
//                finish();
//            }
//        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        //if (this instanceof com.ryu.document_reader.activity.home.HomeActivity2)
            //Utils.INSTANCE.setLanguageForApp((Context)this);
    }

    public static final class Companion {
        private Companion() {}

        public final boolean isFirstView() {
            return BaseActivity.isFirstView;
        }

        public final void setFirstView(boolean param1Boolean) {
            BaseActivity.isFirstView = param1Boolean;
        }
    }
}