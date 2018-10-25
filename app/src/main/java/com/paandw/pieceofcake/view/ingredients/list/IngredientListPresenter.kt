package com.paandw.pieceofcake.view.ingredients.list

import android.arch.persistence.room.Room
import com.paandw.pieceofcake.data.events.GetIngredientsEvent
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.data.models.IngredientData
import com.paandw.pieceofcake.data.room.IngredientDao
import com.paandw.pieceofcake.data.room.IngredientDatabase
import com.paandw.pieceofcake.data.service.IngredientService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class IngredientListPresenter {
    private var service: IngredientService = IngredientService()
    private var ingredientDao : IngredientDao
    private var view: IIngredientListView

    constructor(view: IIngredientListView, ingredientDao: IngredientDao) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        this.view = view
        this.ingredientDao = ingredientDao

        if (ingredientDao.count().value == null || ingredientDao.count().value == 0) {
            service.getIngredients()
        }
    }

    fun onPause() {
        EventBus.getDefault().unregister(this)
    }

    fun onResume() {
        EventBus.getDefault().register(this)
    }

    @Subscribe(sticky = true)
    fun onGetIngredientsEvent(event: GetIngredientsEvent) {
        EventBus.getDefault().removeStickyEvent(event.javaClass)
        if (event.isSuccess) {
            val ingredientList = ArrayList<Ingredient>()
            for(i in 0..event.ingredientData.size) {
                val ingredient = Ingredient()
                ingredient.id = i + 1
                ingredient.searchValue = event.ingredientData[i].searchValue
                ingredient.term = event.ingredientData[i].term
                ingredientList.add(ingredient)
            }
            ingredientDao.insertIngredients(ingredientList)
        }

        view.showIngredient(ingredientDao.searchIngredients("salt").value!![0])
    }


}