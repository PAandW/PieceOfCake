package com.paandw.pieceofcake.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.paandw.pieceofcake.data.models.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert
    void insertIngredients(List<Ingredient> ingredients);

    @Query("SELECT COUNT() FROM ingredient")
    LiveData<Integer> count();

    @Query("SELECT * FROM ingredient WHERE search_value LIKE :value")
    LiveData<List<Ingredient>> searchIngredients(String value);

}
