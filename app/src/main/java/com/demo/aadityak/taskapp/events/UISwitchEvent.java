package com.demo.aadityak.taskapp.events;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by admin on 30-06-2016.
 */
public class UISwitchEvent extends BaseEvent{

    public EventType myEventType;
    Bundle extras;
    public enum EventType{
        HomePageFragmentLoad,
        CategoryFragmentLoad,
        ProductFragmentLoad

    }
    public UISwitchEvent(EventType type){
        myEventType = type;
    }

    public UISwitchEvent(EventType myEventType, @Nullable Bundle includeExtras) {
        this.myEventType = myEventType;
        this.extras = includeExtras;
    }

    @Nullable
    public Bundle getExtras() {
        return extras;
    }
}
