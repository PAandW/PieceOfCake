package com.paandw.pieceofcake.data.events;

import com.paandw.pieceofcake.data.models.Recipe;
import com.paandw.pieceofcake.data.models.RecipeListResponse;

import java.util.List;

public class GetRecipesEvent {

    public List<Recipe> recipes;
    public boolean isSuccess;

    public GetRecipesEvent(RecipeListResponse response) {
        recipes = response.matches;
        isSuccess = true;
    }

    public GetRecipesEvent(boolean isSuccess) { this.isSuccess = isSuccess; }
}
