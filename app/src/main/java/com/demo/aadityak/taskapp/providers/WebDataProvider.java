package com.demo.aadityak.taskapp.providers;

import android.os.Bundle;

import com.demo.aadityak.taskapp.App;
import com.demo.aadityak.taskapp.R;
import com.demo.aadityak.taskapp.events.DataFetchedEvent;
import com.demo.aadityak.taskapp.services.parser.APIResponseParser;
import com.demo.aadityak.taskapp.services.request.APIRequestData;
import com.demo.aadityak.taskapp.services.response.APIResponseData;
import com.demo.aadityak.taskapp.services.retro.API;
import com.demo.aadityak.taskapp.services.retro.JBLRetrofitCallback;
import com.demo.aadityak.taskapp.services.retro.RestClient;
import com.demo.aadityak.taskapp.utils.AppUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by aaditya on 15/11/2017.
 */
public class WebDataProvider {

    APIResponseParser apiResponseParser;

    public WebDataProvider() {
        apiResponseParser = APIResponseParser.newInstance();
    }

    public void requestForData(HashMap<String, String> arguments) {
            //for get call
            callWebService(arguments, true);

            //for post call
            //callWebService(arguments, false);
    }

    public void requestForData(APIRequestData apiRequestData) {

            //for get call
            callWebService(apiRequestData, true);

            //for post call
            //callWebService(apiRequestData, false);
    }

    public void callWebService(HashMap<String, String> arguments, boolean callUsingGet) {

        API restClientAPIObject = RestClient.getRetrofitBuilder();
        if (callUsingGet) {
            restClientAPIObject.fetchResponseUsingGet(arguments).enqueue(new JBLRetrofitCallback<APIResponseData>() {

                @Override
                public void onResponse(Call<APIResponseData> call, Response<APIResponseData> response) {
                    validateAPIResponse(response.body());
                }

                @Override
                public void onFailure(Call<APIResponseData> call, Throwable t) {
                    super.onFailure(call, t);
                    fireAPIResponseError("Something went wrong", "");
                }
            });
        } else {
            restClientAPIObject.fetchResponseUsingPost(arguments).enqueue(new JBLRetrofitCallback<APIResponseData>() {

                @Override
                public void onResponse(Call<APIResponseData> call, Response<APIResponseData> response) {
                    validateAPIResponse(response.body());
                }

                @Override
                public void onFailure(Call<APIResponseData> call, Throwable t) {
                    super.onFailure(call, t);
                    fireAPIResponseError("Something went wrong", "");

                }
            });
        }

    }

    public void callWebService(APIRequestData apiRequestData, boolean callUsingGet) {

        API restClientAPIObject = RestClient.getRetrofitBuilder();

        if (callUsingGet) {
            restClientAPIObject.fetchResponseUsingGet(apiRequestData).enqueue(new JBLRetrofitCallback<APIResponseData>() {

                @Override
                public void onResponse(Call<APIResponseData> call, Response<APIResponseData> response) {
                    validateAPIResponse(response.body());
                }

                @Override
                public void onFailure(Call<APIResponseData> call, Throwable t) {
                    super.onFailure(call, t);
                    Timber.e(call.toString());
                    fireAPIResponseError("Something went wrong", "");

                }
            });
        } else {
            restClientAPIObject.fetchResponseUsingPost(apiRequestData).enqueue(new JBLRetrofitCallback<APIResponseData>() {

                @Override
                public void onResponse(Call<APIResponseData> call, Response<APIResponseData> response) {

                    validateAPIResponse(response.body());
                }

                @Override
                public void onFailure(Call<APIResponseData> call, Throwable t) {
                    super.onFailure(call, t);
                    Timber.e(call.toString());
                    fireAPIResponseError("Something went wrong", "");

                }
            });
        }

    }

    public void validateAPIResponse(APIResponseData apiResponseData) {
        if (apiResponseData == null) {
            Timber.e("Api response is null, returning....");
            fireAPIResponseError(App.getAppContext().getResources().getString(R.string.internal_server_error), "");
            return;
        }

        if (apiResponseData.getCategories() == null) {
            Timber.e("Api response categories is null, returning....");
            fireAPIResponseError(App.getAppContext().getResources().getString(R.string.internal_server_error), "");
            return;
        }

        if (apiResponseData.getCategories().length == 0) {
            Timber.e("Api response categories array size is 0, returning....");
            fireAPIResponseError(App.getAppContext().getResources().getString(R.string.internal_server_error), "");
            return;
        }

        if (apiResponseData.getRankings() == null) {
            Timber.e("Api response rankings is null, returning....");
            fireAPIResponseError(App.getAppContext().getResources().getString(R.string.internal_server_error), "");
            return;
        }

        apiResponseParser.parseAPIResponse(apiResponseData);
    }

    public void fireAPIResponseError(String message, String action) {
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("action", action);
        DataFetchedEvent appStatusEvent = new DataFetchedEvent(DataFetchedEvent.EventType.StatusAPIResponseError, bundle);
        EventBus.getDefault().post(appStatusEvent);
    }

    public void loadDataFromMock(String fileName, String action) {
        String mockJson = AppUtils.readMockJson(fileName);
        APIResponseData mockResponseData = new APIResponseData();
//        mockResponseData.setAction(action);
//        mockResponseData.setData(mockJson);
//        apiResponseParser.parseAPIResponse(mockResponseData);
    }
}

