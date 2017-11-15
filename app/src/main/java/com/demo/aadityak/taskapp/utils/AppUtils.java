package com.demo.aadityak.taskapp.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.Log;

import com.demo.aadityak.taskapp.App;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by aaditya on 15/11/2017.
 */

public class AppUtils {

    public static final String UNAVAILABLE = "NA";

    public static String readMockJson(String fileName) {
        String jsonString = null;
        try {
            AssetManager assetManager = App.getAppContext().getAssets();
            InputStream is = assetManager.open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Nullable
    public static Object convertJsonToPojo(String jsonString, Class clz) {
        Gson gson = new Gson();
        Object pojoObject;
        try {
            pojoObject = gson.fromJson(jsonString, clz);
        } catch (Exception e) {
            Log.e("Utils","Unable to convert Json to Pojo", e);
            pojoObject = null;
        }
        return pojoObject;
    }

    public static Boolean isValidString(Object string) {
        boolean result = true;
        if (string == null) {
            result = false;
        } else if (!(string instanceof String)) {
            result = false;
        } else {
            String aString = (String) string;
            if (aString.isEmpty()) {
                result = false;
            }
        }
        return result;
    }

    public static String getmodelName() {
        String myDeviceModel = android.os.Build.MODEL;
        if (!isValidString(myDeviceModel)) {
            myDeviceModel = UNAVAILABLE;
        }
        return myDeviceModel;
    }

    public static String getDevice() {
        String device = android.os.Build.DEVICE;
        if (!isValidString(device)) {
            device = UNAVAILABLE;
        }
        return device;
    }

    public static String getSdk() {
        String sdk = android.os.Build.VERSION.SDK;
        if (!isValidString(sdk)) {
            sdk = UNAVAILABLE;
        }
        return sdk;
    }

    public static String getProduct() {
        String product = android.os.Build.PRODUCT;
        if (!isValidString(product)) {
            product = UNAVAILABLE;
        }
        return product;
    }

    public static String getIpAddress(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        if (!isValidString(ipAddress)) {
            ipAddress = UNAVAILABLE;
        }
        return ipAddress;
    }

    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        if (!isValidString(macAddress)) {
            macAddress = UNAVAILABLE;
        }
        return macAddress;
    }

    public static String getManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        if (isValidString(manufacturer)) {
            manufacturer = UNAVAILABLE;
        }
        return manufacturer;
    }

    public static String getAndroidVersion(int apilevel) {
        switch (apilevel) {
            case 15:
                return "ICE_CREAM_SANDWICH_MR1";
            case 16:
                return "JELLY_BEAN";
            case 17:
                return "JELLY_BEAN_MR1";
            case 18:
                return "JELLY_BEAN_MR2";
            case 19:
                return "KITKAT";
            case 20:
                return "KITKAT_WATCH";
            case 21:
                return "LOLLIPOP";
            case 22:
                return "LOLLIPOP_MR1";
            case 23:
                return "M";
            case 24:
                return "N";
            case 25:
                return "N_MR1";
            default:
                return UNAVAILABLE;
        }

    }
}
