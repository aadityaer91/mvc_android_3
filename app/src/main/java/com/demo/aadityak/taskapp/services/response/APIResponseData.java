package com.demo.aadityak.taskapp.services.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aadityak on 15/11/2017.
 */
public class APIResponseData {

    @SerializedName("categories")
    private Category []categories;

    @SerializedName("rankings")
    private Ranking []rankings;

    public Category[] getCategories() {
        return categories;
    }

    public Ranking[] getRankings() {
        return rankings;
    }
}
