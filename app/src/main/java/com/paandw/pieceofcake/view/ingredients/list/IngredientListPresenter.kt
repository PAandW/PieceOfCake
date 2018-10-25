package com.paandw.pieceofcake.view.ingredients.list

import com.paandw.pieceofcake.data.events.GetIngredientsEvent
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.data.room.IngredientDatabase
import com.paandw.pieceofcake.data.service.IngredientService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class IngredientListPresenter {
    private var service: IngredientService = IngredientService()
    private var ingredientDatabase : IngredientDatabase
    private var view: IIngredientListView

    constructor(view: IIngredientListView, ingredientDatabase: IngredientDatabase) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        this.view = view
        this.ingredientDatabase = ingredientDatabase


        doAsync {
            var makeCall = false
            if (ingredientDatabase.ingredientDao().count() == 0) {
                makeCall = true
            }
            uiThread {
                if (makeCall) {
                    service.getIngredients()
                }
                else {
                    searchIngredients("%pea%")
                }
            }
        }

    }

    fun onPause() {
        EventBus.getDefault().unregister(this)
    }

    fun onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    fun searchIngredients(query: String) {
        doAsync {
            var ingredients = ingredientDatabase.ingredientDao().searchIngredients(query)

            uiThread {
                if (ingredients.size > 0) {
                    view.showIngredient(ingredients[0])
                }
            }
        }
    }

    @Subscribe(sticky = true)
    fun onGetIngredientsEvent(event: GetIngredientsEvent) {
        EventBus.getDefault().removeStickyEvent(event.javaClass)
        if (event.isSuccess) {
            val ingredientList = ArrayList<Ingredient>()
            for(i in 0 until event.ingredientData.size) {
                val ingredient = Ingredient()
                ingredient.id = i + 1
                ingredient.searchValue = event.ingredientData[i].searchValue
                ingredient.term = event.ingredientData[i].term
                ingredientList.add(ingredient)
            }

            doAsync {
                var ingredient: Ingredient
                ingredientDatabase.ingredientDao().insertIngredients(ingredientList)
                uiThread {
                    view.showIngredient(ingredientList[0])
                }
            }
        }
    }


}