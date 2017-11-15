package com.demo.aadityak.taskapp.services.parser;

import android.os.Bundle;

import com.demo.aadityak.taskapp.events.DataFetchedEvent;
import com.demo.aadityak.taskapp.services.response.APIResponseData;
import com.demo.aadityak.taskapp.services.response.HomePageResponseData;
import com.demo.aadityak.taskapp.utils.AppUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

/**
 * Created by aaditya on 14/11/17.
 */

public class APIResponseParser {

    public static APIResponseParser newInstance() {
        return new APIResponseParser();
    }

    public void parseAPIResponse(APIResponseData apiResponseData) {

        Gson gson = new Gson();
        String data = null;
        if (apiResponseData.getData() != null) {
            JsonObject jsonObject = gson.toJsonTree(apiResponseData.getData()).getAsJsonObject();
            Timber.v(jsonObject.toString());
            data = jsonObject.toString();

            parseHomepageResponse(data);
        }
    }

    public void parseHomepageResponse(String data) {
        HomePageResponseData homePageResponseData = (HomePageResponseData) AppUtils.convertJsonToPojo(data, HomePageResponseData.class);
        if (homePageResponseData == null) {
            return;
        }
        Bundle extras = new Bundle();
        extras.putParcelable("data", homePageResponseData);
        extras.putString("action", "homePageListData");
        EventBus.getDefault().post(new DataFetchedEvent(DataFetchedEvent.EventType.HomePageDataFetched, extras));

    }
}
