package com.flickr.android.view.search;

import androidx.test.espresso.IdlingResource;


public class RecyclerViewIdlingRes implements IdlingResource, SearchItemListFragment.RecyclerViewHaveDataListener {

    private String name;
    private boolean isIdle;

    private volatile ResourceCallback resourceCallback;

    public RecyclerViewIdlingRes(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    @Override
    public void recyclerViewHaveData() {
        if (resourceCallback == null){
            return;
        }
        isIdle = true;
        resourceCallback.onTransitionToIdle();
    }
}
