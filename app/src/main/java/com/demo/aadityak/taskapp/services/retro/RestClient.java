package com.demo.aadityak.taskapp.services.retro;

import android.os.Build;

import com.demo.aadityak.taskapp.App;
import com.demo.aadityak.taskapp.BuildConfig;
import com.demo.aadityak.taskapp.utils.AppUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aadityak on 15/11/2017.
 */
public class RestClient {
    private static API REST_CLIENT;

    private RestClient() {
    }

    public static API get() {
        return REST_CLIENT;
    }

    public static API getRetrofitBuilder() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS);
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                Request request = builder.method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        String baseURL;

        baseURL = APIBaseURL();

        if (baseURL.substring(baseURL.length() - 1).equalsIgnoreCase("/")) {
            //Do Nothing
        } else {
            baseURL = baseURL.concat("/");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(API.class);
    }

    public static final String APIBaseURL() {
        return BuildConfig.APIScheme + "://" + BuildConfig.APIHostName + "/";
    }

    public static HashMap<String, String> defaultAPIArguments() {

        HashMap<String, String> hashMap = new HashMap<>();
        /*hashMap.put("build_version", String.valueOf(BuildConfig.VERSION_CODE));
        hashMap.put("timestamp", String.valueOf(new Date().getTime()));

        hashMap.put("model", AppUtils.getmodelName());
        hashMap.put("sdk", AppUtils.getSdk());
        hashMap.put("product", AppUtils.getProduct());
        hashMap.put("ipaddress", AppUtils.getIpAddress(App.getAppContext()));
        hashMap.put("macaddress", AppUtils.getMacAddress(App.getAppContext()));
        hashMap.put("apilevel", AppUtils.getAndroidVersion(Build.VERSION.SDK_INT));
        hashMap.put("manufacturer", AppUtils.getManufacturer());
        hashMap.put("device", AppUtils.getDevice());
*/
        return hashMap;
    }

}
