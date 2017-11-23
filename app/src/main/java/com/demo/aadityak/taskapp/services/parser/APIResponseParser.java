package com.demo.aadityak.taskapp.services.parser;

import android.os.Bundle;

import com.demo.aadityak.taskapp.events.DataFetchedEvent;
import com.demo.aadityak.taskapp.realmStorage.APIResponseDb;
import com.demo.aadityak.taskapp.realmStorage.CategoriesTable;
import com.demo.aadityak.taskapp.realmStorage.ProductsTable;
import com.demo.aadityak.taskapp.realmStorage.VariantsTable;
import com.demo.aadityak.taskapp.services.response.APIResponseData;
import com.demo.aadityak.taskapp.services.response.Category;
import com.demo.aadityak.taskapp.services.response.Product;
import com.demo.aadityak.taskapp.services.response.Variant;
import com.demo.aadityak.taskapp.utils.AppConstants;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by aaditya on 14/11/17.
 */

public class APIResponseParser {

    public static APIResponseParser newInstance() {
        return new APIResponseParser();
    }

    public void parseAPIResponse(APIResponseData apiResponseData) {

        Gson gson = new Gson();
        APIResponseDb apiResponseDb = new APIResponseDb();

        saveCategories(apiResponseDb, apiResponseData);

        Bundle bundle = new Bundle();
        bundle.putString("action", AppConstants.ActionApiData);
        bundle.putString("message", "");
        DataFetchedEvent appStatusEvent = new DataFetchedEvent(DataFetchedEvent.EventType.StatusAPIResponseSuccess, bundle);
        EventBus.getDefault().post(appStatusEvent);

    }

    public void saveCategories(APIResponseDb apiResponseDb, APIResponseData apiResponseData) {
        Category[] categories = apiResponseData.getCategories();
        CategoriesTable[] categoryItems = new CategoriesTable[categories.length];
        HashMap<Integer, Integer> categoryParent = new HashMap<>();
        if (categories != null && categories.length > 0) {
            for (int i = 0; i < categories.length; i++) {
                CategoriesTable categoryItem = new CategoriesTable();
                Category categoryTupple = categories[i];
                categoryItem.setId(categoryTupple.getId());
                categoryItem.setName(categoryTupple.getName());
                if (categoryTupple.getProducts() != null && categoryTupple.getProducts().length > 0) {
                    categoryItem.setChildType(2);
                    // product management
                    saveProducts(apiResponseDb, categoryTupple);
                } else {
                    categoryItem.setChildType(1);
                    //category management
                    categoryParent.putAll(maintainCategoriesParent(categoryTupple));

                }
                categoryItems[i] = categoryItem;
            }
            apiResponseDb.saveAllCategories(categoryItems);

            apiResponseDb.updateCategoriesParent(categoryParent);
        }
    }

    public void saveProducts(APIResponseDb apiResponseDb, Category categoryTupple) {
        Product[] products = categoryTupple.getProducts();
        ProductsTable[] productsItems = new ProductsTable[products.length];
        for (int j = 0; j < products.length; j++) {
            ProductsTable productItem = new ProductsTable();
            Product productTupple = products[j];

            productItem.setId(productTupple.getId());
            productItem.setName(productTupple.getName());
            productItem.setDateAdded(productTupple.getDateAdded());
            productItem.setCategoryId(categoryTupple.getId());
            if (productTupple.getVariants() != null && productTupple.getVariants().length > 0) {
                productItem.setHasVariants(true);
                saveVariants(apiResponseDb, productTupple);
            } else {
                productItem.setHasVariants(false);
            }

            productItem.setTaxType(productTupple.getTax().getName());
            productItem.setTax(productTupple.getTax().getValue());

            productsItems[j] = productItem;
        }
        apiResponseDb.saveAllProducts(productsItems);
    }

    public void saveVariants(APIResponseDb apiResponseDb, Product productTupple) {
        Variant[] variants = productTupple.getVariants();
        VariantsTable[] variantItems = new VariantsTable[variants.length];
        for (int k = 0; k < variants.length; k++) {
            VariantsTable variantItem = new VariantsTable();
            Variant variantTupple = variants[k];

            variantItem.setId(variantTupple.getId());
            variantItem.setColor(variantTupple.getColor());
            variantItem.setSize(variantTupple.getSize());
            variantItem.setPrice(variantTupple.getPrice());
            variantItem.setParentProductId(productTupple.getId());

            variantItems[k] = variantItem;
        }
        apiResponseDb.saveAllVariants(variantItems);
    }

    public HashMap<Integer, Integer> maintainCategoriesParent(Category categoryTupple) {
        int[] childCategories = categoryTupple.getChildCategories();
        HashMap<Integer, Integer> tempMap = new HashMap<>();
        for (int i = 0; i < childCategories.length; i++) {
            tempMap.put(childCategories[i],categoryTupple.getId());
        }
        return tempMap;
//        apiResponseDb.updateCategoriesParent(childCategories, categoryTupple.getId());
    }


}
