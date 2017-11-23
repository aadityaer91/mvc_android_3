package com.demo.aadityak.taskapp.realmStorage;

import android.content.Context;

import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by aaditya on 21/11/17.
 */

public class APIResponseDb {
    Realm realm;

    public APIResponseDb() {
        this.realm = Realm.getDefaultInstance();
    }

    public void saveAllCategories(CategoriesTable[] categoriesItems) {
        realm.beginTransaction();
        for (int i = 0; i < categoriesItems.length; i++) {
            realm.insertOrUpdate(categoriesItems[i]);
        }
        realm.commitTransaction();
    }

    public void saveAllProducts(ProductsTable[] productsItems) {
        realm.beginTransaction();
        for (int i = 0; i < productsItems.length; i++) {
            realm.insertOrUpdate(productsItems[i]);
        }
        realm.commitTransaction();
    }

    public void saveAllVariants(VariantsTable[] variantsItems) {
        realm.beginTransaction();
        for (int i = 0; i < variantsItems.length; i++) {
            realm.insertOrUpdate(variantsItems[i]);
        }
        realm.commitTransaction();
    }

    public void updateCategoriesParent(HashMap<Integer, Integer> categoryParentList) {
        realm.beginTransaction();
        Set<Integer> keys = categoryParentList.keySet();
        for (int key : keys) {
            CategoriesTable categoriesItem = realm.where(CategoriesTable.class).equalTo("id", key).findFirst();
            if (categoriesItem != null) {
                categoriesItem.setParentId(categoryParentList.get(key));
            }
        }
        realm.commitTransaction();
    }

    public void saveAllRankings(String jsonString) {
        realm.beginTransaction();
        realm.createOrUpdateAllFromJson(RankingsTable.class, jsonString);
        realm.commitTransaction();
    }

    public RealmResults<CategoriesTable> getAllCategories() {
        return realm.where(CategoriesTable.class).findAll();
    }

    public RealmResults<CategoriesTable> getFirstLevelCategories() {
        return realm.where(CategoriesTable.class).equalTo("childType", 1).findAll();
    }

    public RealmResults<CategoriesTable> getChildCategories(int categoryId) {
        return realm.where(CategoriesTable.class).equalTo("parentId", categoryId).findAll();
    }
    public RealmResults<ProductsTable> getProducts(int categoryId) {
        return realm.where(ProductsTable.class).equalTo("categoryId", categoryId).findAll();
    }

    public CategoriesTable getCategory(int categoryId) {
        return realm.where(CategoriesTable.class).equalTo("id", categoryId).findFirst();
    }

    public RealmResults<RankingsTable> getAllRankings() {
        return realm.where(RankingsTable.class).findAll();
    }

    public RankingsTable getRanking(String rankingType) {
        return realm.where(RankingsTable.class).contains("ranking", rankingType).findFirst();
    }
}
