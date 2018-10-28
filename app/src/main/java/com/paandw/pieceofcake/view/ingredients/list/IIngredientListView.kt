package com.paandw.pieceofcake.view.ingredients.list

import com.paandw.pieceofcake.data.models.Ingredient


interface IIngredientListView {
    fun bindIngredients(ingredients: MutableList<Ingredient>)
}