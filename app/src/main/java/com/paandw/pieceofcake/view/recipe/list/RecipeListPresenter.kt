package com.paandw.pieceofcake.view.recipe.list

import com.paandw.pieceofcake.data.events.GetRecipesEvent
import com.paandw.pieceofcake.data.models.Recipe
import com.paandw.pieceofcake.data.service.RecipeService
import com.paandw.pieceofcake.data.util.GlobalIngredientList
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class RecipeListPresenter(private var view: IRecipeListView) {

    private var service: RecipeService = RecipeService()
    private var filterIndex = -1
    private var recipeList = ArrayList<Recipe>()

    fun start() {
        // We subscribe to the EventBus immediately so we can detect when the getRecipes() call finishes
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        // We then get the list of ingredients needed in the recipes and initiate the API call
        // via service.getRecipes()
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

    fun initiateFilter() {
        view.showFilterDialog(filterIndex)
    }

    // Sorts the list of recipes based on the filter option selected
    fun filterIndexSelected(index: Int) {
        filterIndex = index
        if (index == 0) {
            recipeList.sortBy { it.recipeName }
        } else if (index == 1) {
            recipeList.sortByDescending { it.recipeName }
        } else if (index == 2) {
            recipeList.sortByDescending { it.rating }
        } else if (index == 3) {
            recipeList.sortBy { it.rating }
        } else if (index == 4) {
            recipeList.sortBy { it.totalTimeInSeconds }
        } else if (index == 5) {
            recipeList.sortByDescending { it.totalTimeInSeconds }
        }

        view.bindData(recipeList)
    }

    // Listener for the event fired off at the end of the getRecipes() API call. Binds the recipes
    // to the list and displays them
    @Subscribe(sticky = true)
    fun onGetRecipesEvent(event: GetRecipesEvent) {
        EventBus.getDefault().removeStickyEvent(event)
        view.hideLoadingDialog()
        if (event.isSuccess) {
            val recipes = event.recipes
            recipes.removeAll { it.smallImageUrls == null || it.smallImageUrls.isEmpty() }
            recipeList.clear()
            recipeList.addAll(recipes)
            view.bindData(recipeList)
        }
    }

}