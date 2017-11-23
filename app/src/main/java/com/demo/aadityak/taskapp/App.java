package com.demo.aadityak.taskapp;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import io.realm.Realm;

/**
 * Created by aadityak on 15/11/2017.
 */

public class App extends Application {

    private static App mContext;
    private static DisplayImageOptions imageDisplayOptions;
    private static DisplayImageOptions nonFadeDisplayImageOptions;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Realm.init(this);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        imageDisplayOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(350, true, true, false))
                .build();
        nonFadeDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(false)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static DisplayImageOptions defaultDisplayImageOptions() {
        return imageDisplayOptions;
    }

    public static DisplayImageOptions nonFadingDisplayImageOptions() {
        return nonFadeDisplayImageOptions;
    }
}
