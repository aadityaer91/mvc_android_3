package com.demo.aadityak.taskapp.providers;

import android.os.Bundle;

import com.demo.aadityak.taskapp.services.WebDataProvider;
import com.demo.aadityak.taskapp.services.retro.RestClient;

import java.util.HashMap;

/**
 * Created by aaditya on 14/11/17.
 */

public class AppContentProvider {

    WebDataProvider webDataProvider;

    public AppContentProvider() {
        this.webDataProvider = new WebDataProvider();
    }

    public void requiredProgListForHomePage(Bundle bundle){
        HashMap<String, String> args = RestClient.defaultAPIArguments();
        args.put("page",bundle.getString("pageNumber"));
        webDataProvider.requestForData(args);
    }
}
