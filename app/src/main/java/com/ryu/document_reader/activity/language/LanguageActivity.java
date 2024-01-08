package com.ryu.document_reader.activity.language;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.activity.home.HomeActivity2;
import com.ryu.document_reader.adapter.LanguageAdapter;
import com.ryu.document_reader.ads.AdsManager;
import com.ryu.document_reader.ads.TemplateView;
import com.ryu.document_reader.model.Country;
import com.ryu.document_reader.util.Utils;

import java.util.ArrayList;
import java.util.Map;

public final class LanguageActivity extends BaseActivity implements LanguageAdapter.OnItemLanguageListener {
    public Map<Integer, View> _$_findViewCache;

    private LanguageAdapter mItemLanguageAdapter;

    private ArrayList<Country> mLanguages;

    private int mPosCheck;

    private final void handleEvents() {
        // TODO
        //((ImageView)_$_findCachedViewById(R.id.btnTickLanguage)).setOnClickListener(new LanguageActivity$$ExternalSyntheticLambda0(this));
    }

    private  final void handleEvents$lambda$2(LanguageActivity paramLanguageActivity, View paramView) {
        // TODO
//        Intent intent;
//        Utils utils1 = Utils.INSTANCE;
//        Context context = paramView.getContext();
//        utils1.isSetLanguage(context);
//        Utils utils2 = Utils.INSTANCE;
//        ArrayList<Country> arrayList2 = paramLanguageActivity.mLanguages;
//        ArrayList<Country> arrayList1 = arrayList2;
//        if (arrayList2 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("mLanguages");
//            arrayList1 = null;
//        }
//        utils2.setIOSCountryData(((Country)arrayList1.get(paramLanguageActivity.mPosCheck)).getLocale(), context);
//        if (paramLanguageActivity.getIntent().getBooleanExtra("FROM_HOME", false)) {
//            intent = new Intent(context, HomeActivity2.class);
//        } else {
//            intent = new Intent(context, PermissionActivity.class);
//        }
//        paramLanguageActivity.startActivity(intent);
//        paramLanguageActivity.finish();
    }

    private final void initData() {
        // Byte code:
        //   0: new java/util/ArrayList
        //   3: dup
        //   4: invokespecial <init> : ()V
        //   7: astore #4
        //   9: aload_0
        //   10: aload #4
        //   12: putfield mLanguages : Ljava/util/ArrayList;
        //   15: aload_0
        //   16: ldc 2131886333
        //   18: invokevirtual getString : (I)Ljava/lang/String;
        //   21: astore_3
        //   22: aload_3
        //   23: ldc 'getString(R.string.english)'
        //   25: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   28: aload #4
        //   30: new com/alldoucment/reader/viewer/model/Country
        //   33: dup
        //   34: ldc 2131231118
        //   36: aload_3
        //   37: ldc 'en'
        //   39: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   42: invokevirtual add : (Ljava/lang/Object;)Z
        //   45: pop
        //   46: aload_0
        //   47: getfield mLanguages : Ljava/util/ArrayList;
        //   50: astore #5
        //   52: aconst_null
        //   53: astore #4
        //   55: aload #5
        //   57: astore_3
        //   58: aload #5
        //   60: ifnonnull -> 70
        //   63: ldc 'mLanguages'
        //   65: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   68: aconst_null
        //   69: astore_3
        //   70: aload_0
        //   71: ldc 2131886788
        //   73: invokevirtual getString : (I)Ljava/lang/String;
        //   76: astore #5
        //   78: aload #5
        //   80: ldc 'getString(R.string.spain)'
        //   82: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   85: aload_3
        //   86: new com/alldoucment/reader/viewer/model/Country
        //   89: dup
        //   90: ldc 2131231130
        //   92: aload #5
        //   94: ldc 'es'
        //   96: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   99: invokevirtual add : (Ljava/lang/Object;)Z
        //   102: pop
        //   103: aload_0
        //   104: getfield mLanguages : Ljava/util/ArrayList;
        //   107: astore #5
        //   109: aload #5
        //   111: astore_3
        //   112: aload #5
        //   114: ifnonnull -> 124
        //   117: ldc 'mLanguages'
        //   119: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   122: aconst_null
        //   123: astore_3
        //   124: aload_0
        //   125: ldc 2131886448
        //   127: invokevirtual getString : (I)Ljava/lang/String;
        //   130: astore #5
        //   132: aload #5
        //   134: ldc 'getString(R.string.french)'
        //   136: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   139: aload_3
        //   140: new com/alldoucment/reader/viewer/model/Country
        //   143: dup
        //   144: ldc 2131231119
        //   146: aload #5
        //   148: ldc 'fr'
        //   150: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   153: invokevirtual add : (Ljava/lang/Object;)Z
        //   156: pop
        //   157: aload_0
        //   158: getfield mLanguages : Ljava/util/ArrayList;
        //   161: astore #5
        //   163: aload #5
        //   165: astore_3
        //   166: aload #5
        //   168: ifnonnull -> 178
        //   171: ldc 'mLanguages'
        //   173: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   176: aconst_null
        //   177: astore_3
        //   178: aload_0
        //   179: ldc 2131886472
        //   181: invokevirtual getString : (I)Ljava/lang/String;
        //   184: astore #5
        //   186: aload #5
        //   188: ldc 'getString(R.string.indonesia)'
        //   190: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   193: aload_3
        //   194: new com/alldoucment/reader/viewer/model/Country
        //   197: dup
        //   198: ldc 2131231122
        //   200: aload #5
        //   202: ldc 'in'
        //   204: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   207: invokevirtual add : (Ljava/lang/Object;)Z
        //   210: pop
        //   211: aload_0
        //   212: getfield mLanguages : Ljava/util/ArrayList;
        //   215: astore #5
        //   217: aload #5
        //   219: astore_3
        //   220: aload #5
        //   222: ifnonnull -> 232
        //   225: ldc 'mLanguages'
        //   227: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   230: aconst_null
        //   231: astore_3
        //   232: aload_0
        //   233: ldc 2131886927
        //   235: invokevirtual getString : (I)Ljava/lang/String;
        //   238: astore #5
        //   240: aload #5
        //   242: ldc 'getString(R.string.vietnamese)'
        //   244: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   247: aload_3
        //   248: new com/alldoucment/reader/viewer/model/Country
        //   251: dup
        //   252: ldc 2131231132
        //   254: aload #5
        //   256: ldc 'vi'
        //   258: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   261: invokevirtual add : (Ljava/lang/Object;)Z
        //   264: pop
        //   265: aload_0
        //   266: getfield mLanguages : Ljava/util/ArrayList;
        //   269: astore #5
        //   271: aload #5
        //   273: astore_3
        //   274: aload #5
        //   276: ifnonnull -> 286
        //   279: ldc 'mLanguages'
        //   281: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   284: aconst_null
        //   285: astore_3
        //   286: aload_0
        //   287: ldc 2131886666
        //   289: invokevirtual getString : (I)Ljava/lang/String;
        //   292: astore #5
        //   294: aload #5
        //   296: ldc 'getString(R.string.portugal)'
        //   298: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   301: aload_3
        //   302: new com/alldoucment/reader/viewer/model/Country
        //   305: dup
        //   306: ldc 2131231128
        //   308: aload #5
        //   310: ldc 'pt'
        //   312: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   315: invokevirtual add : (Ljava/lang/Object;)Z
        //   318: pop
        //   319: aload_0
        //   320: getfield mLanguages : Ljava/util/ArrayList;
        //   323: astore #5
        //   325: aload #5
        //   327: astore_3
        //   328: aload #5
        //   330: ifnonnull -> 340
        //   333: ldc 'mLanguages'
        //   335: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   338: aconst_null
        //   339: astore_3
        //   340: aload_0
        //   341: ldc 2131886918
        //   343: invokevirtual getString : (I)Ljava/lang/String;
        //   346: astore #5
        //   348: aload #5
        //   350: ldc 'getString(R.string.uae)'
        //   352: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   355: aload_3
        //   356: new com/alldoucment/reader/viewer/model/Country
        //   359: dup
        //   360: ldc 2131231131
        //   362: aload #5
        //   364: ldc 'ar'
        //   366: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   369: invokevirtual add : (Ljava/lang/Object;)Z
        //   372: pop
        //   373: aload_0
        //   374: getfield mLanguages : Ljava/util/ArrayList;
        //   377: astore #5
        //   379: aload #5
        //   381: astore_3
        //   382: aload #5
        //   384: ifnonnull -> 394
        //   387: ldc 'mLanguages'
        //   389: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   392: aconst_null
        //   393: astore_3
        //   394: aload_0
        //   395: ldc 2131886717
        //   397: invokevirtual getString : (I)Ljava/lang/String;
        //   400: astore #5
        //   402: aload #5
        //   404: ldc 'getString(R.string.rusia)'
        //   406: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   409: aload_3
        //   410: new com/alldoucment/reader/viewer/model/Country
        //   413: dup
        //   414: ldc 2131231129
        //   416: aload #5
        //   418: ldc 'ru'
        //   420: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   423: invokevirtual add : (Ljava/lang/Object;)Z
        //   426: pop
        //   427: aload_0
        //   428: getfield mLanguages : Ljava/util/ArrayList;
        //   431: astore #5
        //   433: aload #5
        //   435: astore_3
        //   436: aload #5
        //   438: ifnonnull -> 448
        //   441: ldc 'mLanguages'
        //   443: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   446: aconst_null
        //   447: astore_3
        //   448: aload_0
        //   449: ldc 2131886471
        //   451: invokevirtual getString : (I)Ljava/lang/String;
        //   454: astore #5
        //   456: aload #5
        //   458: ldc 'getString(R.string.india)'
        //   460: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   463: aload_3
        //   464: new com/alldoucment/reader/viewer/model/Country
        //   467: dup
        //   468: ldc 2131231121
        //   470: aload #5
        //   472: ldc 'hi'
        //   474: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   477: invokevirtual add : (Ljava/lang/Object;)Z
        //   480: pop
        //   481: aload_0
        //   482: getfield mLanguages : Ljava/util/ArrayList;
        //   485: astore #5
        //   487: aload #5
        //   489: astore_3
        //   490: aload #5
        //   492: ifnonnull -> 502
        //   495: ldc 'mLanguages'
        //   497: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   500: aconst_null
        //   501: astore_3
        //   502: aload_0
        //   503: ldc 2131886450
        //   505: invokevirtual getString : (I)Ljava/lang/String;
        //   508: astore #5
        //   510: aload #5
        //   512: ldc 'getString(R.string.germany)'
        //   514: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   517: aload_3
        //   518: new com/alldoucment/reader/viewer/model/Country
        //   521: dup
        //   522: ldc 2131231120
        //   524: aload #5
        //   526: ldc 'hsb'
        //   528: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   531: invokevirtual add : (Ljava/lang/Object;)Z
        //   534: pop
        //   535: aload_0
        //   536: getfield mLanguages : Ljava/util/ArrayList;
        //   539: astore #5
        //   541: aload #5
        //   543: astore_3
        //   544: aload #5
        //   546: ifnonnull -> 556
        //   549: ldc 'mLanguages'
        //   551: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   554: aconst_null
        //   555: astore_3
        //   556: aload_0
        //   557: ldc 2131886482
        //   559: invokevirtual getString : (I)Ljava/lang/String;
        //   562: astore #5
        //   564: aload #5
        //   566: ldc 'getString(R.string.italy)'
        //   568: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   571: aload_3
        //   572: new com/alldoucment/reader/viewer/model/Country
        //   575: dup
        //   576: ldc 2131231124
        //   578: aload #5
        //   580: ldc 'it'
        //   582: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   585: invokevirtual add : (Ljava/lang/Object;)Z
        //   588: pop
        //   589: aload_0
        //   590: getfield mLanguages : Ljava/util/ArrayList;
        //   593: astore #5
        //   595: aload #5
        //   597: astore_3
        //   598: aload #5
        //   600: ifnonnull -> 610
        //   603: ldc 'mLanguages'
        //   605: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   608: aconst_null
        //   609: astore_3
        //   610: aload_0
        //   611: ldc 2131886484
        //   613: invokevirtual getString : (I)Ljava/lang/String;
        //   616: astore #5
        //   618: aload #5
        //   620: ldc 'getString(R.string.japan)'
        //   622: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   625: aload_3
        //   626: new com/alldoucment/reader/viewer/model/Country
        //   629: dup
        //   630: ldc 2131231125
        //   632: aload #5
        //   634: ldc 'ja'
        //   636: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   639: invokevirtual add : (Ljava/lang/Object;)Z
        //   642: pop
        //   643: aload_0
        //   644: getfield mLanguages : Ljava/util/ArrayList;
        //   647: astore #5
        //   649: aload #5
        //   651: astore_3
        //   652: aload #5
        //   654: ifnonnull -> 664
        //   657: ldc 'mLanguages'
        //   659: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   662: aconst_null
        //   663: astore_3
        //   664: aload_0
        //   665: ldc 2131886481
        //   667: invokevirtual getString : (I)Ljava/lang/String;
        //   670: astore #5
        //   672: aload #5
        //   674: ldc 'getString(R.string.israel)'
        //   676: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   679: aload_3
        //   680: new com/alldoucment/reader/viewer/model/Country
        //   683: dup
        //   684: ldc 2131231123
        //   686: aload #5
        //   688: ldc 'iw'
        //   690: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   693: invokevirtual add : (Ljava/lang/Object;)Z
        //   696: pop
        //   697: aload_0
        //   698: getfield mLanguages : Ljava/util/ArrayList;
        //   701: astore #5
        //   703: aload #5
        //   705: astore_3
        //   706: aload #5
        //   708: ifnonnull -> 718
        //   711: ldc 'mLanguages'
        //   713: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   716: aconst_null
        //   717: astore_3
        //   718: aload_0
        //   719: ldc 2131886486
        //   721: invokevirtual getString : (I)Ljava/lang/String;
        //   724: astore #5
        //   726: aload #5
        //   728: ldc 'getString(R.string.korea)'
        //   730: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   733: aload_3
        //   734: new com/alldoucment/reader/viewer/model/Country
        //   737: dup
        //   738: ldc 2131231126
        //   740: aload #5
        //   742: ldc 'ko'
        //   744: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   747: invokevirtual add : (Ljava/lang/Object;)Z
        //   750: pop
        //   751: aload_0
        //   752: getfield mLanguages : Ljava/util/ArrayList;
        //   755: astore #5
        //   757: aload #5
        //   759: astore_3
        //   760: aload #5
        //   762: ifnonnull -> 772
        //   765: ldc 'mLanguages'
        //   767: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   770: aconst_null
        //   771: astore_3
        //   772: aload_0
        //   773: ldc 2131886601
        //   775: invokevirtual getString : (I)Ljava/lang/String;
        //   778: astore #5
        //   780: aload #5
        //   782: ldc 'getString(R.string.nederland)'
        //   784: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   787: aload_3
        //   788: new com/alldoucment/reader/viewer/model/Country
        //   791: dup
        //   792: ldc 2131231127
        //   794: aload #5
        //   796: ldc_w 'nl'
        //   799: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
        //   802: invokevirtual add : (Ljava/lang/Object;)Z
        //   805: pop
        //   806: aload_0
        //   807: getfield mLanguages : Ljava/util/ArrayList;
        //   810: astore #5
        //   812: aload #5
        //   814: astore_3
        //   815: aload #5
        //   817: ifnonnull -> 827
        //   820: ldc 'mLanguages'
        //   822: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   825: aconst_null
        //   826: astore_3
        //   827: aload_3
        //   828: checkcast java/lang/Iterable
        //   831: invokeinterface iterator : ()Ljava/util/Iterator;
        //   836: astore #7
        //   838: aload #7
        //   840: invokeinterface hasNext : ()Z
        //   845: istore_2
        //   846: iconst_0
        //   847: istore_1
        //   848: iload_2
        //   849: ifeq -> 913
        //   852: aload #7
        //   854: invokeinterface next : ()Ljava/lang/Object;
        //   859: astore_3
        //   860: aload_3
        //   861: checkcast com/alldoucment/reader/viewer/model/Country
        //   864: astore #5
        //   866: invokestatic getSystem : ()Landroid/content/res/Resources;
        //   869: invokevirtual getConfiguration : ()Landroid/content/res/Configuration;
        //   872: getfield locale : Ljava/util/Locale;
        //   875: invokevirtual toString : ()Ljava/lang/String;
        //   878: astore #6
        //   880: aload #6
        //   882: ldc_w 'getSystem().configuration.locale.toString()'
        //   885: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   888: aload #6
        //   890: checkcast java/lang/CharSequence
        //   893: aload #5
        //   895: invokevirtual getLocale : ()Ljava/lang/String;
        //   898: checkcast java/lang/CharSequence
        //   901: iconst_0
        //   902: iconst_2
        //   903: aconst_null
        //   904: invokestatic contains$default : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
        //   907: ifeq -> 838
        //   910: goto -> 915
        //   913: aconst_null
        //   914: astore_3
        //   915: aload_3
        //   916: checkcast com/alldoucment/reader/viewer/model/Country
        //   919: astore #6
        //   921: aload #6
        //   923: ifnull -> 1007
        //   926: ldc_w '113333'
        //   929: aload #6
        //   931: invokevirtual getLocale : ()Ljava/lang/String;
        //   934: invokestatic d : (Ljava/lang/String;Ljava/lang/String;)I
        //   937: pop
        //   938: aload #6
        //   940: invokevirtual getLocale : ()Ljava/lang/String;
        //   943: ldc 'en'
        //   945: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
        //   948: ifne -> 1007
        //   951: aload_0
        //   952: getfield mLanguages : Ljava/util/ArrayList;
        //   955: astore #5
        //   957: aload #5
        //   959: astore_3
        //   960: aload #5
        //   962: ifnonnull -> 972
        //   965: ldc 'mLanguages'
        //   967: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   970: aconst_null
        //   971: astore_3
        //   972: aload_3
        //   973: aload #6
        //   975: invokevirtual remove : (Ljava/lang/Object;)Z
        //   978: pop
        //   979: aload_0
        //   980: getfield mLanguages : Ljava/util/ArrayList;
        //   983: astore #5
        //   985: aload #5
        //   987: astore_3
        //   988: aload #5
        //   990: ifnonnull -> 1000
        //   993: ldc 'mLanguages'
        //   995: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   998: aconst_null
        //   999: astore_3
        //   1000: aload_3
        //   1001: iconst_2
        //   1002: aload #6
        //   1004: invokevirtual add : (ILjava/lang/Object;)V
        //   1007: aload_0
        //   1008: getfield mLanguages : Ljava/util/ArrayList;
        //   1011: astore #5
        //   1013: aload #5
        //   1015: astore_3
        //   1016: aload #5
        //   1018: ifnonnull -> 1028
        //   1021: ldc 'mLanguages'
        //   1023: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   1026: aconst_null
        //   1027: astore_3
        //   1028: aload_3
        //   1029: checkcast java/util/List
        //   1032: invokeinterface iterator : ()Ljava/util/Iterator;
        //   1037: astore #7
        //   1039: aload #7
        //   1041: invokeinterface hasNext : ()Z
        //   1046: ifeq -> 1105
        //   1049: aload #7
        //   1051: invokeinterface next : ()Ljava/lang/Object;
        //   1056: checkcast com/alldoucment/reader/viewer/model/Country
        //   1059: invokevirtual getLocale : ()Ljava/lang/String;
        //   1062: astore_3
        //   1063: getstatic com/alldoucment/reader/viewer/util/Utils.INSTANCE : Lcom/alldoucment/reader/viewer/util/Utils;
        //   1066: astore #5
        //   1068: aload_0
        //   1069: invokevirtual getApplicationContext : ()Landroid/content/Context;
        //   1072: astore #6
        //   1074: aload #6
        //   1076: ldc_w 'applicationContext'
        //   1079: invokestatic checkNotNullExpressionValue : (Ljava/lang/Object;Ljava/lang/String;)V
        //   1082: aload_3
        //   1083: aload #5
        //   1085: aload #6
        //   1087: invokevirtual getIOSCountryData : (Landroid/content/Context;)Ljava/lang/String;
        //   1090: invokestatic areEqual : (Ljava/lang/Object;Ljava/lang/Object;)Z
        //   1093: ifeq -> 1099
        //   1096: goto -> 1107
        //   1099: iinc #1, 1
        //   1102: goto -> 1039
        //   1105: iconst_m1
        //   1106: istore_1
        //   1107: aload_0
        //   1108: iload_1
        //   1109: putfield mPosCheck : I
        //   1112: aload_0
        //   1113: checkcast android/content/Context
        //   1116: astore #6
        //   1118: aload_0
        //   1119: getfield mLanguages : Ljava/util/ArrayList;
        //   1122: astore #5
        //   1124: aload #5
        //   1126: astore_3
        //   1127: aload #5
        //   1129: ifnonnull -> 1139
        //   1132: ldc 'mLanguages'
        //   1134: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   1137: aconst_null
        //   1138: astore_3
        //   1139: aload_0
        //   1140: new com/alldoucment/reader/viewer/adapter/LanguageAdapter
        //   1143: dup
        //   1144: aload #6
        //   1146: aload_3
        //   1147: aload_0
        //   1148: checkcast com/alldoucment/reader/viewer/adapter/LanguageAdapter$OnItemLanguageListener
        //   1151: invokespecial <init> : (Landroid/content/Context;Ljava/util/ArrayList;Lcom/alldoucment/reader/viewer/adapter/LanguageAdapter$OnItemLanguageListener;)V
        //   1154: putfield mItemLanguageAdapter : Lcom/alldoucment/reader/viewer/adapter/LanguageAdapter;
        //   1157: aload_0
        //   1158: getstatic com/alldoucment/reader/viewer/R$id.rvLanguage : I
        //   1161: invokevirtual _$_findCachedViewById : (I)Landroid/view/View;
        //   1164: checkcast androidx/recyclerview/widget/RecyclerView
        //   1167: astore #5
        //   1169: aload_0
        //   1170: getfield mItemLanguageAdapter : Lcom/alldoucment/reader/viewer/adapter/LanguageAdapter;
        //   1173: astore_3
        //   1174: aload_3
        //   1175: ifnonnull -> 1190
        //   1178: ldc_w 'mItemLanguageAdapter'
        //   1181: invokestatic throwUninitializedPropertyAccessException : (Ljava/lang/String;)V
        //   1184: aload #4
        //   1186: astore_3
        //   1187: goto -> 1190
        //   1190: aload #5
        //   1192: aload_3
        //   1193: checkcast androidx/recyclerview/widget/RecyclerView$Adapter
        //   1196: invokevirtual setAdapter : (Landroidx/recyclerview/widget/RecyclerView$Adapter;)V
        //   1199: return
    }

    private final void loadAds() {
        TemplateView templateView = findViewById(R.id.my_template);
        Utils utils = Utils.INSTANCE;
        String str = getString(R.string.alldoc_native_language_id);
        utils.loadNativeAds(this, templateView, str, AdsManager.INSTANCE.is_show_native_ads_language());
    }

    public void _$_clearFindViewByIdCache() {
        this._$_findViewCache.clear();
    }

    public View _$_findCachedViewById(int paramInt) {
        Map<Integer, View> map = this._$_findViewCache;
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

    public final int getMPosCheck() {
        return this.mPosCheck;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_language);
        initData();
        loadAds();
        handleEvents();
    }

    public void onItemLanguageClick(int paramInt) {
        this.mPosCheck = paramInt;
        LanguageAdapter languageAdapter2 = this.mItemLanguageAdapter;
        LanguageAdapter languageAdapter1 = languageAdapter2;
        if (languageAdapter2 == null) {
            languageAdapter1 = null;
        }
        languageAdapter1.notifyDataSetChanged();
    }

    public final void setMPosCheck(int paramInt) {
        this.mPosCheck = paramInt;
    }
}