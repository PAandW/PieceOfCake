package com.paandw.pieceofcake.view.recipe.list

import com.paandw.pieceofcake.data.events.GetRecipesEvent
import com.paandw.pieceofcake.data.service.RecipeService
import com.paandw.pieceofcake.data.util.GlobalIngredientList
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class RecipeListPresenter(private var view: IRecipeListView) {

    private var service: RecipeService = RecipeService()

    fun start() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        val ingredientNames = ArrayList<String>()
        for (ingredient in GlobalIngredientList.get()) {
            ingredientNames.add(ingredient.term)
        }

        view.showLoadingDialog()
        service.getRecipes(ingredientNames)
    }

    fun onResume() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    fun onPause() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(sticky = true)
    fun onGetRecipesEvent(event: GetRecipesEvent) {
        EventBus.getDefault().removeStickyEvent(event)
        view.hideLoadingDialog()
        if (event.isSuccess) {
            view.bindData(event.recipes)
        }
    }

}