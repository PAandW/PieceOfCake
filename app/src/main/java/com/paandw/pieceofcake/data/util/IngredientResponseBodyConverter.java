package com.paandw.pieceofcake.data.util;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.Reader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class IngredientResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public IngredientResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        Reader reader = value.charStream();
        int item = reader.read();
        while (item != ' ') {
            item = reader.read();
        }

        JsonReader jsonReader = gson.newJsonReader(reader);
        jsonReader.setLenient(true);
        try {
            return adapter.read(jsonReader);
        } finally {
            reader.close();
        }
    }
}
