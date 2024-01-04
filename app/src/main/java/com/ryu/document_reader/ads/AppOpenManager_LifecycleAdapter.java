package com.ryu.document_reader.ads;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;

public class AppOpenManager_LifecycleAdapter implements GeneratedAdapter {
    final AppOpenManager mReceiver;

    AppOpenManager_LifecycleAdapter(AppOpenManager paramAppOpenManager) {
        this.mReceiver = paramAppOpenManager;
    }

    public void callMethods(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent, boolean paramBoolean, MethodCallsLogger paramMethodCallsLogger) {
        boolean bool;
        if (paramMethodCallsLogger != null) {
            bool = true;
        } else {
            bool = false;
        }
        if (paramBoolean)
            return;
        if (paramEvent == Lifecycle.Event.ON_START && (!bool || paramMethodCallsLogger.approveCall("onStart", 1)))
            this.mReceiver.onStart();
    }
}
