package com.paandw.pieceofcake.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.paandw.pieceofcake.data.models.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredients(List<Ingredient> ingredients);

    @Query("SELECT COUNT() FROM ingredient")
    int count();

    @Query("SELECT * FROM ingredient WHERE search_value LIKE :value")
    List<Ingredient> searchIngredients(String value);

}
