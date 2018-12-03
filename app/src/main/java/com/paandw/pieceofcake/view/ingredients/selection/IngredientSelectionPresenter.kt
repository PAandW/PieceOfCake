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
        // An asynchronous (background thread) is started to access the ingredient database
        // If the count returns 0 (meaning the user hasn't downloaded the ingredient list), a network
        // call is fired off and that loading dialog we have ready is displayed
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

    // Every time the user types a letter this method is fired off which searches for names of
    // ingredients in the database that are similar to what was typed. Again, database calls are
    // made asynchronously and once they're done fire off an update to the view to display ingredients
    // that match the search
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
        view.showConfirmationDialog(ingredient)
    }

    // Adds ingredients to global static ingredient list to be used elsewhere in app
    fun addIngredientToList(ingredient: Ingredient) {
        ingredientList.remove(ingredient)
        GlobalIngredientList.add(ingredient)
        view.selectIngredient(ingredient)
        view.bindData(ingredientList)
    }

    private fun setIngredientList(ingredients: List<Ingredient>) {
        ingredientList.clear()
        ingredientList.addAll(ingredients)
    }

    // The network response. If the API call was made to get the ingredients, once it succeeds it'll
    // fire off an event that we're subscribed to here. At this point all the ingredients are inserted
    // in the database, and ideally we'll never have to make that API call again.
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