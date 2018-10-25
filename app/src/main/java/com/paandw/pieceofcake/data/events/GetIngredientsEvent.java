package com.paandw.pieceofcake.data.events;

import com.paandw.pieceofcake.data.models.IngredientData;

import java.util.List;

public class GetIngredientsEvent {
    public List<IngredientData> ingredientData;
    public boolean isSuccess;

    public GetIngredientsEvent(List<IngredientData> ingredientData) {
        this.ingredientData = ingredientData;
        isSuccess = true;
    }

    public GetIngredientsEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
