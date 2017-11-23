package com.demo.aadityak.taskapp.controller;

import android.content.Context;
import android.os.Bundle;

import com.demo.aadityak.taskapp.events.DataFetchedEvent;
import com.demo.aadityak.taskapp.events.DataRequestEvent;
import com.demo.aadityak.taskapp.providers.AppContentProvider;
import com.demo.aadityak.taskapp.realmStorage.CategoriesTable;
import com.demo.aadityak.taskapp.realmStorage.ProductsTable;
import com.demo.aadityak.taskapp.services.response.Category;
import com.demo.aadityak.taskapp.services.response.HomePageResponseData;
import com.demo.aadityak.taskapp.views.fragments.BaseFragment;
import com.demo.aadityak.taskapp.views.fragments.CategoryFragment;
import com.demo.aadityak.taskapp.views.fragments.HomeFragment;
import com.demo.aadityak.taskapp.views.fragments.ProductListFragment;

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

    public void dataRequestEvent(DataRequestEvent event) {
        Bundle bundleArgs = event.getExtras();
        switch (event.myEventType) {
            case RequestAllData:
                appContentProvider.requiredDataForHomePage(bundleArgs);
                break;

            case RequestChildCategories:
                appContentProvider.requiredChildCategoryList(bundleArgs);
                break;

            case RequestAllCategories:
                appContentProvider.requiredCategoryList();
                break;
            case RequestProductList:
                appContentProvider.requiredProductList(bundleArgs);
                break;

            case RequestAllRankings:
                appContentProvider.requiredRanking();
                break;

            default:
                Timber.e("Don't know how to handle this status event!");
        }
    }

    public void dataFetchedEvent(DataFetchedEvent event) {
        Bundle bundleArgs = event.getExtras();
        String action = bundleArgs.getString("action");
        switch (event.myEventType) {

            case CategoriesDataFetched:
                CategoriesTable[] categories = (CategoriesTable[]) bundleArgs.getParcelableArray("categories");
                if (currentFragment instanceof CategoryFragment) {
                    currentFragment.updateData(categories, action);
                }
                break;

            case ChildCategoriesDataFetched:
                CategoriesTable[] childCategories = (CategoriesTable[]) bundleArgs.getParcelableArray("categories");
                if (currentFragment instanceof CategoryFragment) {
                    currentFragment.updateData(childCategories, action);
                }
                break;

            case ProductListDataFetched:
                ProductsTable[] products = (ProductsTable[]) bundleArgs.getParcelableArray("products");
                if (currentFragment instanceof ProductListFragment) {
                    currentFragment.updateData(products, action);
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
