package com.paandw.pieceofcake.data;

import com.paandw.pieceofcake.data.models.IngredientData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface AppService {

    @Headers({
            "X-Yummly-App-ID: 9ddc868a",
            "X-Yummly-App-Key: 8d26da538d435176bdcc0510f4bd6fd0"
    })
    @GET("metadata/ingredient")
    Call<List<IngredientData>> getIngredients();

}
