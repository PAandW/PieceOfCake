package com.paandw.pieceofcake.view.ingredients.list

import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.data.service.IngredientService
import com.paandw.pieceofcake.data.util.GlobalIngredientList


class IngredientListPresenter {
    private var service: IngredientService = IngredientService()
    private var view: IIngredientListView

    constructor(view: IIngredientListView) {
        this.view = view
        refreshIngredientList()
    }

    fun onPause() {
        //unregister eventbus if needed
    }

    fun onResume() {
        //register eventbus if needed
    }

    fun ingredientDeleteClicked(ingredient: Ingredient) {
        view.showIngredientDeletionConfirmation(ingredient)
    }

    // The GlobalIngredientList is simply a static list of all ingredients currently stored in the
    // selected list
    fun refreshIngredientList() {
        view.bindIngredients(GlobalIngredientList.get())
    }

    fun deleteIngredient(ingredient: Ingredient) {
        GlobalIngredientList.remove(ingredient)
        refreshIngredientList()
    }

    fun deleteAllIngredients() {
        GlobalIngredientList.clear()
        refreshIngredientList()
    }

}