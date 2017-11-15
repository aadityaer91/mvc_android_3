package com.demo.aadityak.taskapp.services.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aadityak on 15/11/2017.
 */
public class APIResponseData {

    @SerializedName("success")
    private boolean status;

    @SerializedName("authenticated")
    private boolean authenticated;

    @SerializedName("response")
    private Object data;

    public boolean isStatus() {
        return status;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public Object getData() {
        return data;
    }
}
