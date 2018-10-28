package com.paandw.pieceofcake.data.models;

import java.util.List;

public class RecipeDetails {
    public String yield;
    public int prepTimeInSeconds;
    public int totalTimeInSeconds;
    public String totalTime;
    public String name;
    public String prepTime;
    public String id;
    public List<String> ingredientLines;
    public List<RecipeImages> images;
    public RecipeAttributions attribution;
}
