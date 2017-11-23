package com.demo.aadityak.taskapp.providers;

import android.os.Bundle;

import com.demo.aadityak.taskapp.events.DataFetchedEvent;
import com.demo.aadityak.taskapp.realmStorage.APIResponseDb;
import com.demo.aadityak.taskapp.realmStorage.CategoriesTable;
import com.demo.aadityak.taskapp.realmStorage.ProductsTable;
import com.demo.aadityak.taskapp.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;

import io.realm.RealmResults;

/**
 * Created by aaditya on 20/11/17.
 */

public class OfflineDataProvider {

    APIResponseDb apiResponseDb;

    public OfflineDataProvider() {
        apiResponseDb = new APIResponseDb();
    }

    public boolean isAPIDataFetched() {
        if (apiResponseDb.getAllCategories().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void fetchParentCategories() {

        RealmResults<CategoriesTable> results = apiResponseDb.getFirstLevelCategories();
        CategoriesTable [] categoryList = new CategoriesTable[results.size()];
        results.toArray(categoryList);

        Bundle extras = new Bundle();
        extras.putParcelableArray("categories", categoryList);
        extras.putString("action", AppConstants.ActionCategoryData);
        EventBus.getDefault().post(new DataFetchedEvent(DataFetchedEvent.EventType.CategoriesDataFetched, extras));
    }

    public void fetchChildCategories(int categoryId){
        RealmResults<CategoriesTable> results = apiResponseDb.getChildCategories(categoryId);
        CategoriesTable [] categoryList = new CategoriesTable[results.size()];
        results.toArray(categoryList);

        Bundle extras = new Bundle();
        extras.putParcelableArray("categories", categoryList);
        extras.putString("action", AppConstants.ActionChildCategoryData);
        EventBus.getDefault().post(new DataFetchedEvent(DataFetchedEvent.EventType.CategoriesDataFetched, extras));
    }

    public void fetchProductList(int categoryId){
        RealmResults<ProductsTable> results = apiResponseDb.getProducts(categoryId);
        ProductsTable [] productList = new ProductsTable[results.size()];
        results.toArray(productList);

        Bundle extras = new Bundle();
        extras.putParcelableArray("products", productList);
        extras.putString("action", AppConstants.ActionProductListData);
        EventBus.getDefault().post(new DataFetchedEvent(DataFetchedEvent.EventType.ProductListDataFetched, extras));
    }

    public void fetchAllRankings() {

        Bundle extras = new Bundle();
        //extras.putParcelableArray("rankings", rankings);
        extras.putString("action", AppConstants.ActionRankingData);
        EventBus.getDefault().post(new DataFetchedEvent(DataFetchedEvent.EventType.RankingsDataFetched, extras));
    }
}
