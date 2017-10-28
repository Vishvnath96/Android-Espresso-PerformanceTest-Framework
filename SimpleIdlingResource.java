package com.mmt.travel.app.androidMain.utilities.idlingResourceUtils;


import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

/**
 * Created by MMT6054 on 14-Jul-17.
 */

//we ll call only one idling Resource and it will return when app is idle ,but we need return call back from visible activity code
public class SimpleIdlingResource implements IdlingResource {
    @Nullable private volatile ResourceCallback resourceCallback;
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);


    @Override
    public String getName() {
        System.out.println(this.getClass().getName());
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    public void setIdleState(boolean isIdleNow){
        mIsIdleNow.set(isIdleNow);
        if(isIdleNow && resourceCallback != null){
            resourceCallback.onTransitionToIdle();
        }

    }
}
