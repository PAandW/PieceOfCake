package com.paandw.pieceofcake.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.paandw.pieceofcake.data.models.Ingredient;

@Database(entities = {Ingredient.class}, version = 1)
public abstract class IngredientDatabase extends RoomDatabase {
    public abstract IngredientDao ingredientDao();
}
