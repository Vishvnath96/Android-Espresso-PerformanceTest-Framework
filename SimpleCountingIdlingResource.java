package com.mmt.travel.app.androidMain.utilities.idlingResourceUtils;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;

/**
 * Created by MMT6054 on 17-Jul-17.
 */
//increment the value when resource is busy and decrement its value when resource is idle ,and compare if its value is 0 then return callback
public class SimpleCountingIdlingResource implements IdlingResource {

    private final String mResourceName;
    private final AtomicInteger counter = new AtomicInteger(0);

    //written from main thread,read from any thread
    private volatile ResourceCallback resourceCallback;

    public SimpleCountingIdlingResource(String resourceName) {
        mResourceName = checkNotNull(resourceName);
    }


    @Override
    public String getName() {
        return mResourceName;
    }

    @Override
    public boolean isIdleNow() {
        return counter.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    //increment the counter
    public void increment() {
        counter.getAndIncrement();
    }

    //decrement the count
    public void decrement() {
        int countVal = counter.decrementAndGet();
        if (countVal == 0) {
            //if value gone from non-zeo to zero.That means resource is idle now,tell Espresso
            if (null != resourceCallback) {
                resourceCallback.onTransitionToIdle();
            }
        }
        if (countVal < 0) {
            throw new IllegalArgumentException();
        }
    }


}
