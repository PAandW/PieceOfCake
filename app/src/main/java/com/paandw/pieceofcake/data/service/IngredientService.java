package com.paandw.pieceofcake.data.service;

import com.paandw.pieceofcake.data.domain.IDomainCallback;
import com.paandw.pieceofcake.data.domain.IngredientDomain;
import com.paandw.pieceofcake.data.events.GetIngredientsEvent;
import com.paandw.pieceofcake.data.models.IngredientData;

import java.util.List;

public class IngredientService extends BaseService {

    private IngredientDomain domain;

    public IngredientService() {
        domain = new IngredientDomain();
    }

    public void getIngredients() {
        domain.getIngredients(new IDomainCallback<List<IngredientData>>() {
            @Override
            public void success(List<IngredientData> ingredientData) {
                sendEvent(new GetIngredientsEvent(ingredientData));
            }

            @Override
            public void failure(String message) {
                sendEvent(new GetIngredientsEvent(false));
            }
        });
    }

}
