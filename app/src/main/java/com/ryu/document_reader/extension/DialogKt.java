package com.ryu.document_reader.extension;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.ryu.document_reader.R;

import java.util.Arrays;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

public final class DialogKt {

    private static final void handleRatingStar(Dialog paramDialog, int paramInt, Context paramContext) {

    }

    public static final void showRatingDialog(AppCompatActivity paramAppCompatActivity) {
        Intrinsics.checkNotNullParameter(paramAppCompatActivity, "<this>");
        Dialog dialog = ContextKt.onCreateDialog((Context)paramAppCompatActivity, R.layout.layout_dialog_rating_app, false);
        Ref.IntRef intRef = new Ref.IntRef();
        dialog.show();
//        ((ImageView)dialog.findViewById(R.id.ivStar1)).setOnClickListener(new DialogKt$$ExternalSyntheticLambda10(intRef, dialog, paramAppCompatActivity, dialog));
//        ((ImageView)dialog.findViewById(R.id.ivStar2)).setOnClickListener(new DialogKt$$ExternalSyntheticLambda11(intRef, dialog, paramAppCompatActivity, dialog));
//        ((ImageView)dialog.findViewById(R.id.ivStar3)).setOnClickListener(new DialogKt$$ExternalSyntheticLambda12(intRef, dialog, paramAppCompatActivity, dialog));
//        ((ImageView)dialog.findViewById(R.id.ivStar4)).setOnClickListener(new DialogKt$$ExternalSyntheticLambda13(intRef, dialog, paramAppCompatActivity, dialog));
//        ((ImageView)dialog.findViewById(R.id.ivStar5)).setOnClickListener(new DialogKt$$ExternalSyntheticLambda1(intRef, dialog, paramAppCompatActivity, dialog));
//        ((AppCompatRatingBar)dialog.findViewById(R.id.ratingbar)).setOnRatingBarChangeListener(new DialogKt$$ExternalSyntheticLambda2(paramAppCompatActivity, dialog));
//        ((Button)dialog.findViewById(R.id.btnRatingApp)).setOnClickListener(new DialogKt$$ExternalSyntheticLambda3(dialog, intRef, paramAppCompatActivity));
//        ((Button)dialog.findViewById(R.id.btnMaybeLater)).setOnClickListener(new DialogKt$$ExternalSyntheticLambda4(dialog));
    }

    public static final void showSatisfiedDialog(AppCompatActivity paramAppCompatActivity) {
        Intrinsics.checkNotNullParameter(paramAppCompatActivity, "<this>");
        Dialog dialog = ContextKt.onCreateBottomSheetDialog((Context)paramAppCompatActivity, R.layout.layout_dialog_satisfied, false);
        TextView textView = (TextView)dialog.findViewById(R.id.tvTitle);
        String str = paramAppCompatActivity.getString(R.string.txt_are_you_satisfied);
        str = String.format(str, Arrays.copyOf(new Object[] { paramAppCompatActivity.getString(R.string.app_name) }, 1));
        textView.setText(str);
        // TODO
//        ((ImageView)dialog.findViewById(R.id.btnClose)).setOnClickListener(new DialogKt$$ExternalSyntheticLambda0(dialog));
//        ((TextView)dialog.findViewById(R.id.btnGood)).setOnClickListener(new DialogKt$$ExternalSyntheticLambda5(dialog, paramAppCompatActivity));
//        ((TextView)dialog.findViewById(R.id.btnNotReally)).setOnClickListener(new DialogKt$$ExternalSyntheticLambda6(dialog));
        dialog.show();
    }

    private static final void showRatingDialog$lambda$14(Dialog paramDialog, View paramView) {
        Intrinsics.checkNotNullParameter(paramDialog, "$mDialogRating");
        paramDialog.dismiss();
    }

    private static final void showSatisfiedDialog$lambda$3(Dialog paramDialog, View paramView) {
        Intrinsics.checkNotNullParameter(paramDialog, "$dialog");
        paramDialog.cancel();
    }


    private static final void showSatisfiedDialog$lambda$5(Dialog paramDialog, View paramView) {
        Intrinsics.checkNotNullParameter(paramDialog, "$dialog");
        paramDialog.cancel();
    }

}
