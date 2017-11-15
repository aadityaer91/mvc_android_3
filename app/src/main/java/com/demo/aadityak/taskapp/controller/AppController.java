package com.demo.aadityak.taskapp.controller;

import android.content.Context;
import android.os.Bundle;

import com.demo.aadityak.taskapp.events.DataFetchedEvent;
import com.demo.aadityak.taskapp.events.DataRequestEvent;
import com.demo.aadityak.taskapp.providers.AppContentProvider;
import com.demo.aadityak.taskapp.services.response.HomePageResponseData;
import com.demo.aadityak.taskapp.views.fragments.BaseFragment;
import com.demo.aadityak.taskapp.views.fragments.HomeFragment;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

/**
 * Created by aaditya on 14/11/17.
 */

public class AppController {
    Context mJbllMainActivityContext;
    AppContentProvider appContentProvider;
    public BaseFragment currentFragment;

    public AppController(Context jblMainActivity) {
        this.mJbllMainActivityContext = jblMainActivity;
        this.appContentProvider = new AppContentProvider();
    }

    public void registerToListenEvent(Context context) {
        EventBus.getDefault().register(context);
    }

    public void unRegisterToListenEvent(Context context) {
        EventBus.getDefault().unregister(context);
    }

    public void dataRequestEvent(DataRequestEvent event){
        Bundle bundleArgs = event.getExtras();
        switch (event.myEventType) {
            case RequestProgListData:
                appContentProvider.requiredProgListForHomePage(bundleArgs);
                break;

            default:
                Timber.e("Don't know how to handle this status event!");
        }
    }

    public void dataFetchedEvent(DataFetchedEvent event) {
        Bundle bundleArgs = event.getExtras();
        Object object = bundleArgs.getParcelable("data");
        String action = bundleArgs.getString("action");
        switch (event.myEventType) {

            case HomePageDataFetched:
                HomePageResponseData homePageResponseData = (HomePageResponseData) object;
                if (currentFragment instanceof HomeFragment) {
                    currentFragment.updateData(homePageResponseData, action);
                }
                break;

            case StatusAPIResponseError:
                if (currentFragment != null) {
                    currentFragment.updateError(bundleArgs);
                }
                break;

            case StatusAPIResponseSuccess:
                if (currentFragment != null) {
                    currentFragment.updateSuccess(bundleArgs);
                }
                break;

            default:
                Timber.e("Don't know how to handle this status event!");
        }
    }
}
