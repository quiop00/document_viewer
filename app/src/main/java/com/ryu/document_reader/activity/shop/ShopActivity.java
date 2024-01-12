package com.ryu.document_reader.activity.shop;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.ryu.document_reader.R;
import com.ryu.document_reader.activity.BaseActivity;
import com.ryu.document_reader.activity.home.BillingClientSetup;
import com.ryu.document_reader.activity.home.HomeActivity2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kotlin.jvm.internal.Intrinsics;

public final class ShopActivity extends BaseActivity implements PurchasesUpdatedListener {
    private final String SUB_MONTH = "remove_ads_sub_1";

    private final String SUB_YEAR = "remove_ads_sub_3";

    public Map<Integer, View> findViewCache;

    private BillingClient mBillingClient;

    private int mIndexSelected = 1;

    private ConsumeResponseListener mListener;

    private SkuDetailsParams mSkuDetailsParamsSubs;

    private final void getPriceProduct() {
        SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder().setSkusList(Arrays.asList(this.SUB_MONTH, this.SUB_YEAR)).setType("subs").build();
        BillingClient billingClient = this.mBillingClient;
        billingClient.querySkuDetailsAsync(skuDetailsParams, (billingResult, list) -> {
            if (billingResult.getResponseCode() != 0 || list == null || list.size() <= 0) {
                return;
            }
            runOnUiThread(() -> {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    SkuDetails skuDetails = (SkuDetails) it.next();
                    Log.d("55555555555", skuDetails.getPrice());
                    String sku = skuDetails.getSku();
                    if (Intrinsics.areEqual(sku, ShopActivity.this.SUB_MONTH)) {
                        ((TextView) ShopActivity.this.findCachedViewById(R.id.txtPriceOneMonth)).setText(skuDetails.getPrice() + " / " + ShopActivity.this.getString(R.string.txt_month));
                    } else if (Intrinsics.areEqual(sku, ShopActivity.this.SUB_YEAR)) {
                        ((TextView) ShopActivity.this.findCachedViewById(R.id.txtPriceOneYear)).setText(skuDetails.getPrice() + " / " + ShopActivity.this.getString(R.string.txt_year));
                    }
                }
            });
        });
    }

    private final void handleAlreadyPurchase(List<Purchase> paramList) {
        if (paramList != null)
            for (Purchase purchase : paramList) {
                if (purchase.getSkus().indexOf(this.SUB_MONTH) > 0 || purchase.getSkus().indexOf(this.SUB_YEAR) > 0) {
                    ConsumeParams consumeParams = ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();
                    BillingClient billingClient = this.mBillingClient;
                    ConsumeResponseListener consumeResponseListener = this.mListener;
                    billingClient.consumeAsync(consumeParams, consumeResponseListener);
                }
            }
    }

    private final void handleBilling(SkuDetails paramSkuDetails) {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder().setSkuDetails(paramSkuDetails).build();
        BillingClient billingClient = this.mBillingClient;
        billingClient.launchBillingFlow((Activity)this, billingFlowParams).getResponseCode();
    }

    private final void handleEvents() {
        ((ImageView)findCachedViewById(R.id.btnClose)).setOnClickListener(view -> onBackPressed());
        ((ConstraintLayout)findCachedViewById(R.id.btnOneMonth)).setOnClickListener(view -> onClickBtnMonth());
        ((ConstraintLayout)findCachedViewById(R.id.btnOneYear)).setOnClickListener(view -> onClickBtnYear());
        ((Button)findCachedViewById(R.id.btnBuyNow)).setOnClickListener(view -> onClickBuy());
    }


    private final void onClickBtnMonth() {
        mIndexSelected = 1;
        findCachedViewById(R.id.btnOneMonth).setSelected(true);
        findCachedViewById(R.id.btnOneYear).setSelected(false);
        ((RadioButton)findCachedViewById(R.id.rbMonthly)).setChecked(true);
        ((RadioButton)findCachedViewById(R.id.rbYearly)).setChecked(false);
    }

    private final void onClickBtnYear() {
        mIndexSelected = 2;
        ((ConstraintLayout)findCachedViewById(R.id.btnOneYear)).setSelected(true);
        ((ConstraintLayout)findCachedViewById(R.id.btnOneMonth)).setSelected(false);
        ((RadioButton)findCachedViewById(R.id.rbMonthly)).setChecked(false);
        ((RadioButton)findCachedViewById(R.id.rbYearly)).setChecked(true);
    }

    private final void onClickBuy() {
        int i = mIndexSelected;
        if (i != 1) {
            if (i == 2)
                launchBillingSub(SUB_YEAR);
        } else {
            launchBillingSub(SUB_MONTH);
        }
    }

    private final void handlePurchase(List<Purchase> paramList) {
        if (paramList != null)
            for (Purchase purchase : paramList) {
                if (purchase.getPurchaseState() == 1 && !purchase.isAcknowledged()) {
                    AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();
                    BillingClient billingClient = this.mBillingClient;
                    Intrinsics.checkNotNull(billingClient);
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
                        if (billingResult.getResponseCode() == 0) {
                            startActivity(new Intent((Context)ShopActivity.this, HomeActivity2.class));
                            finish();
                        }
                    });
                }
            }
    }

    private final void initData() {
        ((ConstraintLayout)findCachedViewById(R.id.btnOneMonth)).setSelected(true);
        setupBillingClient();
        this.mSkuDetailsParamsSubs = SkuDetailsParams.newBuilder().setSkusList(Arrays.asList(this.SUB_MONTH, this.SUB_YEAR )).setType("subs").build();
        Animation animation = AnimationUtils.loadAnimation((Context)this, R.anim.translate);
        animation.setRepeatCount(-1);
        ((Button)findCachedViewById(R.id.btnBuyNow)).startAnimation(animation);
    }

    private final void launchBillingSub(String paramString) {
        BillingClient billingClient = this.mBillingClient;
        if (billingClient.isReady()) {
            BillingClient billingClient1 = this.mBillingClient;
            SkuDetailsParams skuDetailsParams = this.mSkuDetailsParamsSubs;
            billingClient1.querySkuDetailsAsync(skuDetailsParams, (billingResult, list) -> {
                if (billingResult.getResponseCode() == 0 && list != null)
                    for (SkuDetails skuDetails : list) {
                        if (Intrinsics.areEqual(skuDetails.getSku(), paramString))
                            handleBilling((SkuDetails) skuDetails);
                    }
            });
        }
    }

    private final void setupBillingClient() {
        this.mListener = (billingResult, s) -> billingResult.getResponseCode();
        BillingClient billingClient = BillingClientSetup.getInstance((Context)this, this);
        this.mBillingClient = billingClient;
        if (billingClient != null)
            billingClient.startConnection(new BillingClientStateListener() {
                @Override
                public void onBillingServiceDisconnected() {

                }

                @Override
                public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                    billingResult.getResponseCode();
                }
            });
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

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_shop);
        initData();
        handleEvents();
    }

    public void onPurchasesUpdated(BillingResult paramBillingResult, List<Purchase> paramList) {
        if (paramBillingResult.getResponseCode() == 0) {
            long l1;
            long l2 = System.currentTimeMillis() / 1000L;
            int i = this.mIndexSelected;
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        l1 = l2;
                    } else {
                        l1 = 7776000L;
                        l1 = l2 + l1;
                    }
                } else {
                    l1 = 2592000L;
                    l1 = l2 + l1;
                }
            } else {
                l1 = 604800L;
                l1 = l2 + l1;
            }
            BillingClientSetup.updateTimeUpgrade((Context)this, l1);
            handlePurchase(paramList);
        }
    }

    public final class ShopActivityBillingListener implements BillingClientStateListener {

        private final void onBillingSetupFinished$lambda$0(ShopActivity param1ShopActivity) {
            param1ShopActivity.getPriceProduct();
        }

        public void onBillingServiceDisconnected() {}

        public void onBillingSetupFinished(BillingResult param1BillingResult) {

        }
    }
}