package com.paandw.pieceofcake.view.ingredients.selection

import com.paandw.pieceofcake.data.events.GetIngredientsEvent
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.data.room.IngredientDatabase
import com.paandw.pieceofcake.data.service.IngredientService
import com.paandw.pieceofcake.data.util.GlobalIngredientList
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class IngredientSelectionPresenter {

    private var view: IIngredientSelectionView
    private var database: IngredientDatabase
    private var service: IngredientService
    private val ingredientList = ArrayList<Ingredient>()

    constructor(view: IIngredientSelectionView, database: IngredientDatabase) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        this.view = view
        this.database = database
        service = IngredientService()
    }

    fun onPause() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    fun onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
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
        ingredientList.clear()
        doAsync {
            ingredientList.addAll(database.ingredientDao().searchIngredients("%$searchTerm%"))

            uiThread {
                for(existingIngredient: Ingredient in GlobalIngredientList.get()) {
                    var temp = existingIngredient
                    for (ingredient in ingredientList) {
                        if (ingredient.term.equals(existingIngredient.term, ignoreCase = true)) {
                            temp = ingredient
                        }
                    }
                    ingredientList.remove(temp)
                }
                view.bindData(ingredientList)
            }
        }
    }

    fun onIngredientSelected(ingredient: Ingredient) {
        ingredientList.remove(ingredient)
        GlobalIngredientList.add(ingredient)
        view.selectIngredient(ingredient)
        view.bindData(ingredientList)
    }

    private fun setIngredientList(ingredients: List<Ingredient>) {
        ingredientList.clear()
        ingredientList.addAll(ingredients)
    }

    @Subscribe(sticky = true)
    fun onGetIngredientsEvent(event: GetIngredientsEvent) {
        EventBus.getDefault().removeStickyEvent(event)
        if (event.isSuccess) {
            ingredientList.clear()
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
                    view.bindData(ingredientList)
                }
            }
        } else {
            view.hideLoadingDialog()
        }
    }

}