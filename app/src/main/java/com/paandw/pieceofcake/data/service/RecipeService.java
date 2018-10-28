package com.paandw.pieceofcake.data.service;

import com.paandw.pieceofcake.data.domain.IDomainCallback;
import com.paandw.pieceofcake.data.domain.RecipeDomain;
import com.paandw.pieceofcake.data.events.GetRecipesEvent;
import com.paandw.pieceofcake.data.models.RecipeListResponse;

import java.util.List;

public class RecipeService extends BaseService {

    private RecipeDomain domain;

    public RecipeService() {
        domain = new RecipeDomain();
    }

    public void getRecipes(List<String> ingredients) {
        domain.getRecipes(ingredients, new IDomainCallback<RecipeListResponse>() {
            @Override
            public void success(RecipeListResponse response) {
                sendEvent(new GetRecipesEvent(response));
            }

            @Override
            public void failure(String message) {
                sendEvent(new GetRecipesEvent(false));
            }
        });
    }

}
