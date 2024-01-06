package com.ryu.document_reader.extension;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ryu.document_reader.R;

import java.util.Arrays;

import kotlin.jvm.internal.Intrinsics;

public final class AppCompatActivityKt {
    public static final void hideBottomNavigationBar(AppCompatActivity paramAppCompatActivity) {
        Intrinsics.checkNotNullParameter(paramAppCompatActivity, "<this>");
        paramAppCompatActivity.getWindow().getDecorView().setSystemUiVisibility(5894);
        View view = paramAppCompatActivity.getWindow().getDecorView();
        Intrinsics.checkNotNullExpressionValue(view, "window.decorView");
        // TODO
        //view.setOnSystemUiVisibilityChangeListener(new AppCompatActivityKt$$ExternalSyntheticLambda0(paramAppCompatActivity));
    }

    private static final void hideBottomNavigationBar$lambda$0(AppCompatActivity paramAppCompatActivity, int paramInt) {
        Intrinsics.checkNotNullParameter(paramAppCompatActivity, "$this_hideBottomNavigationBar");
        if ((paramInt & 0x4) == 0)
            paramAppCompatActivity.getWindow().getDecorView().setSystemUiVisibility(5894);
    }

    public static final void sendFeedback(AppCompatActivity paramAppCompatActivity, String paramString) {
        Intrinsics.checkNotNullParameter(paramAppCompatActivity, "<this>");
        Intrinsics.checkNotNullParameter(paramString, "content");
        try {
            String str = "";
            Intent intent = new Intent("android.intent.action.SENDTO");
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra("android.intent.extra.EMAIL", new String[] { paramAppCompatActivity.getString(R.string.email_feedback) });
            boolean bool = Intrinsics.areEqual(paramString, "");
            if (!bool) {
                StringBuilder stringBuilder = new StringBuilder();
                String str1 = paramAppCompatActivity.getString(R.string.txt_help_to_improve_us_email_subject);
                Intrinsics.checkNotNullExpressionValue(str1, "getString(R.string.txt_h…improve_us_email_subject)");
                str1 = String.format(str1, Arrays.copyOf(new Object[] { paramAppCompatActivity.getString(R.string.app_name) }, 1));
                Intrinsics.checkNotNullExpressionValue(str1, "format(format, *args)");
                stringBuilder.append(str1);
                stringBuilder.append('\n');
                stringBuilder.append(paramString);
                paramString = stringBuilder.toString();
            } else {
                str = paramAppCompatActivity.getString(R.string.txt_help_to_improve_us_email_subject);
                Intrinsics.checkNotNullExpressionValue(str, "getString(R.string.txt_h…improve_us_email_subject)");
                str = String.format(str, Arrays.copyOf(new Object[] { paramAppCompatActivity.getString(R.string.app_name) }, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(format, *args)");
            }
            intent.putExtra("android.intent.extra.SUBJECT", str);
            paramAppCompatActivity.startActivity(Intent.createChooser(intent, "Send Feedback"));
        } catch (ActivityNotFoundException activityNotFoundException) {
            Toast.makeText((Context)paramAppCompatActivity, paramAppCompatActivity.getString(R.string.txt_no_mail_found), Toast.LENGTH_SHORT).show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
