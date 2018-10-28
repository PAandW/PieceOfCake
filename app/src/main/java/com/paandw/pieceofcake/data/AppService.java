package com.paandw.pieceofcake.data;

import com.paandw.pieceofcake.data.models.IngredientData;
import com.paandw.pieceofcake.data.models.RecipeListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface AppService {

    @Headers({
            "X-Yummly-App-ID: 9ddc868a",
            "X-Yummly-App-Key: 8d26da538d435176bdcc0510f4bd6fd0"
    })
    @GET("metadata/ingredient")
    Call<List<IngredientData>> getIngredients();

    @Headers({
            "X-Yummly-App-ID: 9ddc868a",
            "X-Yummly-App-Key: 8d26da538d435176bdcc0510f4bd6fd0"
    })
    @GET("recipes")
    Call<RecipeListResponse> getRecipes(@Query(value = "allowedIngredient[]", encoded = true) List<String> ingredients);
}
