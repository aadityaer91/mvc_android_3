package com.demo.aadityak.taskapp.realmStorage;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aaditya on 21/11/17.
 */

public class RankingsTable extends RealmObject {
    @PrimaryKey
    private String ranking;
    private String products;

}
