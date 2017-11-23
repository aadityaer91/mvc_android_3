package com.demo.aadityak.taskapp.providers;

import android.os.Bundle;

import com.demo.aadityak.taskapp.events.DataFetchedEvent;
import com.demo.aadityak.taskapp.services.retro.RestClient;
import com.demo.aadityak.taskapp.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by aaditya on 14/11/17.
 */

public class AppContentProvider {

    WebDataProvider webDataProvider;
    OfflineDataProvider offlineDataProvider;

    public AppContentProvider() {
        this.webDataProvider = new WebDataProvider();
        this.offlineDataProvider = new OfflineDataProvider();
    }

    public void requiredDataForHomePage(Bundle bundle) {
        if (shouldFetchFromOnline(AppConstants.ActionApiData)) {
            HashMap<String, String> args = RestClient.defaultAPIArguments();
            webDataProvider.requestForData(args);
        } else {
            Bundle bundleArgs = new Bundle();
            bundleArgs.putString("action", AppConstants.ActionApiData);
            bundleArgs.putString("message", "");
            DataFetchedEvent appStatusEvent = new DataFetchedEvent(DataFetchedEvent.EventType.StatusAPIResponseSuccess, bundleArgs);
            EventBus.getDefault().post(appStatusEvent);
        }
    }

    public void requiredCategoryList() {
        if (!shouldFetchFromOnline(AppConstants.ActionCategoryData)) {
            offlineDataProvider.fetchParentCategories();
        }
    }

    public void requiredChildCategoryList(Bundle bundle){
        if (!shouldFetchFromOnline(AppConstants.ActionChildCategoryData)) {
            offlineDataProvider.fetchChildCategories(bundle.getInt("parentCategoryId"));
        }
    }

    public void requiredProductList(Bundle bundle){
        offlineDataProvider.fetchProductList(bundle.getInt("categoryId"));
    }

    public void requiredRanking() {
        if (!shouldFetchFromOnline(AppConstants.ActionRankingData)) {
            offlineDataProvider.fetchAllRankings();
        }
    }

    public boolean shouldFetchFromOnline(String action) {
        boolean fetchOnline = true;

        if (action.equalsIgnoreCase(AppConstants.ActionApiData)) {
            if (offlineDataProvider.isAPIDataFetched()) {
                fetchOnline = false;
            }
        } else if (action.equalsIgnoreCase(AppConstants.ActionCategoryData)) {
            fetchOnline = false;
        } else if(action.equalsIgnoreCase(AppConstants.ActionChildCategoryData)) {
            fetchOnline = false;
        }else if (action.equalsIgnoreCase(AppConstants.ActionRankingData)) {
            fetchOnline = false;
        }

        return fetchOnline;
    }
}
