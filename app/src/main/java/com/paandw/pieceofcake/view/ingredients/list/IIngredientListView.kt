package com.paandw.pieceofcake.view.ingredients.list

import com.paandw.pieceofcake.data.models.Ingredient


interface IIngredientListView {
    fun showProgress()
    fun hideProgress()
    fun showIngredient(ingredient: Ingredient)
}