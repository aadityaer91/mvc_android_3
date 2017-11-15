package com.demo.aadityak.taskapp.events;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by aaditya on 14/11/17.
 */

public class DataFetchedEvent extends BaseEvent {

    public EventType myEventType;
    Bundle extras;

    public enum EventType {
        StatusAPIResponseError,
        StatusAPIResponseSuccess,
        HomePageDataFetched
    }

    public DataFetchedEvent(EventType type) {
        myEventType = type;
    }

    public DataFetchedEvent(EventType myEventType, @Nullable Bundle includeExtras) {
        this.myEventType = myEventType;
        this.extras = includeExtras;
    }

    @Nullable
    public Bundle getExtras() {
        return extras;
    }

}
