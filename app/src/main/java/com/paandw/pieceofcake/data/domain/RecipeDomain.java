package com.paandw.pieceofcake.data.domain;

import com.paandw.pieceofcake.data.AppService;
import com.paandw.pieceofcake.data.models.RecipeDetails;
import com.paandw.pieceofcake.data.models.RecipeListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeDomain extends BaseDomain {

    private AppService appService;

    public RecipeDomain() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.yummly.com/v1/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        appService = retrofit.create(AppService.class);
    }

    public void getRecipes(List<String> ingredients, final IDomainCallback<RecipeListResponse> domainCallback) {
        appService.getRecipes(ingredients).enqueue(new Callback<RecipeListResponse>() {
            @Override
            public void onResponse(Call<RecipeListResponse> call, Response<RecipeListResponse> response) {
                handleResponse(domainCallback, response);
            }

            @Override
            public void onFailure(Call<RecipeListResponse> call, Throwable t) {
                handleException(domainCallback, t);
            }
        });
    }

    public void getRecipeDetails(String id, final IDomainCallback<RecipeDetails> domainCallback) {
        appService.getRecipeDetails(id).enqueue(new Callback<RecipeDetails>() {
            @Override
            public void onResponse(Call<RecipeDetails> call, Response<RecipeDetails> response) {
                handleResponse(domainCallback, response);
            }

            @Override
            public void onFailure(Call<RecipeDetails> call, Throwable t) {
                handleException(domainCallback, t);
            }
        });
    }

}
