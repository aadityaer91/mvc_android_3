package com.demo.aadityak.taskapp.events;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by aaditya on 15/11/17.
 */

public class DataRequestEvent extends BaseEvent {
    public EventType myEventType;
    Bundle extras;
    public enum EventType{
        RequestAllData,
        RequestAllCategories,
        RequestChildCategories,
        RequestProductList,
        RequestAllRankings

    }
    public DataRequestEvent(EventType type){
        myEventType = type;
    }

    public DataRequestEvent(EventType myEventType, @Nullable Bundle includeExtras) {
        this.myEventType = myEventType;
        this.extras = includeExtras;
    }

    @Nullable
    public Bundle getExtras() {
        return extras;
    }

    public void setExtras(Bundle extras) {
        this.extras = extras;
    }
}
