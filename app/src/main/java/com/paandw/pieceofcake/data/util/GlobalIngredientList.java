package com.paandw.pieceofcake.data.util;

import com.paandw.pieceofcake.data.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class GlobalIngredientList {
    private static List<Ingredient> ingredients;

    public static void add(Ingredient ingredient) {
        createList();
        ingredients.add(ingredient);
    }

    public static List<Ingredient> get() {
        createList();
        return ingredients;
    }

    public static void clear() {
        createList();
        ingredients.clear();
    }

    private static void createList() {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
    }
}
