package com.ryu.document_reader.activity.home;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.PurchasesUpdatedListener;

public class BillingClientSetup {
    public static final String ITEM_SUB_1 = "remove_ads_sub_1";

    public static final String ITEM_SUB_2 = "remove_ads_sub_2";

    public static final String ITEM_SUB_3 = "remove_ads_sub_3";

    public static final String ITEM_SUB_MONTH = "remove_ads_sub_1";

    public static final String ITEM_SUB_YEAR = "remove_ads_sub_3";

    private static final String NAME_APP = "AllDocumentReader";

    private static BillingClient instance;

    private static final String key = "Upgraded";

    public static BillingClient getInstance(Context paramContext, PurchasesUpdatedListener paramPurchasesUpdatedListener) {
        BillingClient billingClient2 = instance;
        BillingClient billingClient1 = billingClient2;
        if (billingClient2 == null)
            billingClient1 = setupBillingClient(paramContext, paramPurchasesUpdatedListener);
        return billingClient1;
    }

    public static boolean isUpgraded(Context paramContext) {
        long l = paramContext.getSharedPreferences("AllDocumentReader", 0).getLong("Time", 0L);
        return (System.currentTimeMillis() / 1000L <= l);
    }

    private static BillingClient setupBillingClient(Context paramContext, PurchasesUpdatedListener paramPurchasesUpdatedListener) {
        return BillingClient.newBuilder(paramContext).enablePendingPurchases().setListener(paramPurchasesUpdatedListener).build();
    }

    public static void updateTimeUpgrade(Context paramContext, long paramLong) {
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("AllDocumentReader", 0).edit();
        editor.putLong("Time", paramLong);
        editor.apply();
    }
}
