package com.paandw.pieceofcake.data.domain;

import com.google.gson.Gson;
import com.paandw.pieceofcake.data.AppService;
import com.paandw.pieceofcake.data.models.IngredientData;
import com.paandw.pieceofcake.data.util.IngredientConverterFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class IngredientDomain extends BaseDomain {

    private AppService appService;

    public IngredientDomain() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.yummly.com/v1/api/")
                .addConverterFactory(new IngredientConverterFactory(new Gson()))
                .build();

        appService = retrofit.create(AppService.class);
    }

    public void getIngredients(final IDomainCallback<List<IngredientData>> domainCallback) {
        appService.getIngredients().enqueue(new Callback<List<IngredientData>>() {
            @Override
            public void onResponse(Call<List<IngredientData>> call, Response<List<IngredientData>> response) {
                handleResponse(domainCallback, response);
            }

            @Override
            public void onFailure(Call<List<IngredientData>> call, Throwable t) {
                handleException(domainCallback, t);
            }
        });
    }

}
