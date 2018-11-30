package com.paandw.pieceofcake.view.ingredients.list

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

    fun refreshIngredientList() {
        view.bindIngredients(GlobalIngredientList.get())
    }

    fun deleteAllIngredients() {
        GlobalIngredientList.clear()
        refreshIngredientList()
    }

}