package com.paandw.pieceofcake.view.ingredients.selection

import com.paandw.pieceofcake.data.events.GetIngredientsEvent
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.data.room.IngredientDatabase
import com.paandw.pieceofcake.data.service.IngredientService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class IngredientSelectionPresenter {

    private var view: IIngredientSelectionView
    private var database: IngredientDatabase
    private var service: IngredientService

    constructor(view: IIngredientSelectionView, database: IngredientDatabase) {
        this.view = view
        this.database = database
        service = IngredientService()
    }

    fun start() {
        doAsync {
            val count = database.ingredientDao().count()

            uiThread {
                if (count == 0) {
                    view.showLoadingDialog()
                    service.getIngredients()
                }
            }
        }
    }

    fun searchIngredients(searchTerm: String) {
        doAsync {
            val ingredientList = ArrayList<Ingredient>()
            ingredientList.addAll(database.ingredientDao().searchIngredients("%$searchTerm%"))

            uiThread {
                view.bindData(ingredientList)
            }
        }
    }

    @Subscribe(sticky = true)
    fun onGetIngredientsEvent(event: GetIngredientsEvent) {
        EventBus.getDefault().removeStickyEvent(event)
        if (event.isSuccess) {
            val ingredientList = ArrayList<Ingredient>()
            doAsync {
                for (i in 0 until event.ingredientData.size) {
                    val ingredient = Ingredient()
                    ingredient.id = i
                    ingredient.searchValue = event.ingredientData[i].searchValue
                    ingredient.term = event.ingredientData[i].term
                    ingredientList.add(ingredient)
                }
                database.ingredientDao().insertIngredients(ingredientList)

                uiThread {
                    view.hideLoadingDialog()
                }
            }
        }
    }

}