package com.paandw.pieceofcake.data.domain;

import com.google.gson.Gson;
import com.paandw.pieceofcake.data.AppService;
import com.paandw.pieceofcake.data.util.IngredientConverterFactory;

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

}
