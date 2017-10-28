package com.mmt.travel.app.androidMain.utilities.idlingResourceUtils;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingResource;

import com.mmt.travel.app.mobile.MMTApplication;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;

/**
 * Created by MMT6054 on 17-Jul-17.
 */
//visible activity instance will be send and Espresso will wait until activity components are not loaded
public class BaseIdlingResource implements IdlingResource {
    Activity visibleActivity;
    @Nullable
    private volatile ResourceCallback resourceCallback;
    //private AtomicBoolean mIsIdleNow = new AtomicBoolean(false);
    private boolean isIdle = false;

    public BaseIdlingResource(Activity bindActivity) {
        visibleActivity = (bindActivity);
    }

    @Override
    public String getName() {
        return visibleActivity.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle()) {
            return true;
        }
        visibleActivity = getCurrentActivity();
        if (isIdle()) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public boolean isIdle() {
        return visibleActivity != null && resourceCallback != null;
    }

    public Activity getCurrentActivity() {
        return ((MMTApplication) InstrumentationRegistry.getTargetContext().getApplicationContext()).getCurrentActivity();
    }
}
