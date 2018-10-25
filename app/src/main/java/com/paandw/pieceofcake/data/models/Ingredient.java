package com.paandw.pieceofcake.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Ingredient {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "search_value")
    public String searchValue;

    @ColumnInfo(name = "term")
    public String term;

}
