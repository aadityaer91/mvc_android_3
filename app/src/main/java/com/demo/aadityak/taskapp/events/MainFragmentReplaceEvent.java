package com.demo.aadityak.taskapp.events;

import com.demo.aadityak.taskapp.views.fragments.BaseFragment;

/**
 * Created by aaditya on 15/11/17.
 */

public class MainFragmentReplaceEvent {

    BaseFragment fragment;
    public MainFragmentReplaceEvent(BaseFragment replaceFragment){
        super();
        fragment = replaceFragment;
    }

    public BaseFragment getFragment() {
        return fragment;
    }
}
