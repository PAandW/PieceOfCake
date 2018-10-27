package com.paandw.pieceofcake.view.ingredients.list

import com.paandw.pieceofcake.data.events.GetIngredientsEvent
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.data.room.IngredientDatabase
import com.paandw.pieceofcake.data.service.IngredientService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


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

    }

    fun onPause() {
        EventBus.getDefault().unregister(this)
    }

    fun onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
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
        }
    }


}