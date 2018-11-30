package com.paandw.pieceofcake.view.recipe.list

import com.paandw.pieceofcake.data.events.GetRecipesEvent
import com.paandw.pieceofcake.data.models.Recipe
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

    fun onRecipeClick(recipe: Recipe) {
        view.toRecipeDetails(recipe)
    }

    @Subscribe(sticky = true)
    fun onGetRecipesEvent(event: GetRecipesEvent) {
        EventBus.getDefault().removeStickyEvent(event)
        view.hideLoadingDialog()
        if (event.isSuccess) {
            val recipes = event.recipes
            recipes.removeAll { it.smallImageUrls != null && it.smallImageUrls.isEmpty() }
            view.bindData(recipes)
        }
    }

}