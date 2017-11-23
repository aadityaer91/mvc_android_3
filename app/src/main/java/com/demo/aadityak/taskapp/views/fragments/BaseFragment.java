package com.demo.aadityak.taskapp.views.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.demo.aadityak.taskapp.MainActivity;
import com.demo.aadityak.taskapp.services.response.AppResponseData;

import timber.log.Timber;

/**
 * Created by aadityak on 15/11/2017.
 */

public class BaseFragment extends Fragment {

    public void updateData(Object appResponseData, String action){

    }

    public void updateError(Bundle bundle){

    }
    public void updateSuccess(Bundle bundle){

    }

    protected FragmentManager getSupportFragmentManager(){
        return getActivity().getSupportFragmentManager();
    }
    protected @Nullable
    MainActivity getMainActivity(){
        Activity currentActivity = getActivity();
        if (currentActivity instanceof MainActivity){
            return (MainActivity)currentActivity;
        }
        return null;
    }
    protected void showLoadingDialog() {
        MainActivity mainActivity = getMainActivity();
        if (mainActivity == null){
            Timber.e("Couldn't get main activity!");
            return;
        }
        mainActivity.showLoadingDialog();
    }

    protected void closeLoadingDialog() {
        MainActivity mainActivity = getMainActivity();
        if (mainActivity == null){
            Timber.e("Couldn't get main activity!");
            return;
        }
        mainActivity.closeLoadingDialog();
    }
    protected void popBackStack(){
        MainActivity mainActivity = getMainActivity();
        if (mainActivity == null){
            Timber.e("Couldn't get main activity!");
            return;
        }
        mainActivity.popBackStack();
    }

}
