package com.ryu.document_reader.ads.callback;

public interface AdsListener {
    void onAdDismissed();

    void onShowFail();

    void onShowSuccess();
}
