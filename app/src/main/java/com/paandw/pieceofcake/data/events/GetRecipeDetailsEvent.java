package com.paandw.pieceofcake.data.events;

import com.paandw.pieceofcake.data.models.RecipeDetails;

public class GetRecipeDetailsEvent {

    public RecipeDetails recipeDetails;
    public boolean isSuccess;

    public GetRecipeDetailsEvent(RecipeDetails response) {
        this.recipeDetails = response;
        isSuccess = true;
    }

    public GetRecipeDetailsEvent(boolean isSuccess) { this.isSuccess = isSuccess; }
}
