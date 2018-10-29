package com.paandw.pieceofcake.view.recipe

import com.paandw.pieceofcake.data.events.GetRecipeDetailsEvent
import com.paandw.pieceofcake.data.models.RecipeDetails
import com.paandw.pieceofcake.data.service.RecipeService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class RecipePresenter (private var view: IRecipeView){

    private val service: RecipeService = RecipeService()
    private var recipe: RecipeDetails? = null

    fun start(recipeId: String) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        view.showLoadingDialog()
        service.getRecipeDetails(recipeId)
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

    fun readDirections() {
        if(recipe != null && recipe?.attribution != null) {
            var url = recipe!!.attribution!!.url
            url += "#directions"
            view.toReadDirections(url)
        }
    }

    @Subscribe(sticky = true)
    fun onGetRecipeEvent(event: GetRecipeDetailsEvent) {
        EventBus.getDefault().removeStickyEvent(event)
        view.hideLoadingDialog()
        if (event.isSuccess) {
            recipe = event.recipeDetails
            val imageUrl = event.recipeDetails.images[0].hostedLargeUrl
            var ingredientText = ""
            for (ingredientLine in event.recipeDetails.ingredientLines) {
                ingredientText += "- $ingredientLine\n"
            }
            val directionsUrl = event.recipeDetails.attribution.url

            view.bindData(imageUrl, ingredientText, directionsUrl)
        }
    }

}