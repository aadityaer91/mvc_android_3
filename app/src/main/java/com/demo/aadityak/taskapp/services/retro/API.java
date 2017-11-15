package com.demo.aadityak.taskapp.services.retro;

import com.demo.aadityak.taskapp.BuildConfig;
import com.demo.aadityak.taskapp.services.request.APIRequestData;
import com.demo.aadityak.taskapp.services.response.APIResponseData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Created by aadityak on 15/11/2017.
 */
public interface API {

    @POST(BuildConfig.APIFolderPath)
    Call<APIResponseData> fetchResponseUsingPost(@Body APIRequestData data);

    @POST(BuildConfig.APIFolderPath)
    Call<APIResponseData> fetchResponseUsingPost(@Body HashMap<String, String> arguments);

    @GET(BuildConfig.APIFolderPath)
    Call<APIResponseData> fetchResponseUsingGet(@QueryMap HashMap<String, String> arguments);

    @GET(BuildConfig.APIFolderPath)
    Call<APIResponseData> fetchResponseUsingGet(@Body APIRequestData data);

}
