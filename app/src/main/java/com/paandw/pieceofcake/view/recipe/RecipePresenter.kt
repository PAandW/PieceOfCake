package com.paandw.pieceofcake.view.recipe

import com.paandw.pieceofcake.data.events.GetRecipeDetailsEvent
import com.paandw.pieceofcake.data.models.RecipeDetails
import com.paandw.pieceofcake.data.service.RecipeService
import kotlinx.android.synthetic.main.view_recipe_list_item.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


class RecipePresenter (private var view: IRecipeView){

    private val service: RecipeService = RecipeService()
    private var recipe: RecipeDetails? = null

    fun start(recipeId: String) {
        // Again, registering for the EventBus so we can get the event fired off after the
        // getRecipeDetails() call responds
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        view.showLoadingDialog()
        // Call is made to get the recipe details of the recipe with the ID we passed in from the
        // previous screen
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

    // Directions aren't returned in the API response, but a link to the directions URL is. So we
    // construct that URL here and pass it to the view.
    fun readDirections() {
        if(recipe != null && recipe?.attribution != null) {
            var url = recipe!!.attribution!!.url
            url += "#directions"
            view.toReadDirections(url)
        }
    }

    // Once the recipe details are returned from API the details are passed to the view
    @Subscribe(sticky = true)
    fun onGetRecipeEvent(event: GetRecipeDetailsEvent) {
        EventBus.getDefault().removeStickyEvent(event)
        view.hideLoadingDialog()
        if (event.isSuccess) {
            recipe = event.recipeDetails
            val imageUrl = event.recipeDetails.images[0].hostedLargeUrl
            var ingredientText = ""

            // Here we construct a string containing all the ingredients in a readable form
            for (ingredientLine in event.recipeDetails.ingredientLines) {
                ingredientText += "- $ingredientLine\n"
            }
            val directionsUrl = event.recipeDetails.attribution.url
            val rating = "${event.recipeDetails.rating}/5"
            val prepTimeInMillis = (event.recipeDetails.totalTimeInSeconds * 1000).toLong()
            val prepTime = String.format("%d min",
                    TimeUnit.MILLISECONDS.toMinutes(prepTimeInMillis)
            )

            view.bindData(imageUrl, ingredientText, directionsUrl, rating, prepTime)
        }
    }

}