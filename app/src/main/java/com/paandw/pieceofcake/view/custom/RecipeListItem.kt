package com.paandw.pieceofcake.view.custom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Recipe
import com.paandw.pieceofcake.view.recipe.list.RecipeListPresenter
import kotlinx.android.synthetic.main.view_recipe_list_item.view.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


class RecipeListItem(context: Context) : FrameLayout(context) {

    init {
        layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.view_recipe_list_item, this)
    }

    fun setup(recipe: Recipe, presenter: RecipeListPresenter) {
        tv_recipe_name.text = recipe.recipeName
        tv_rating.text = "${recipe.rating.roundToInt()} / 5"
        val prepTimeInMillis = (recipe.totalTimeInSeconds * 1000).toLong()
        tv_prep_time.text = String.format("%d min",
                TimeUnit.MILLISECONDS.toMinutes(prepTimeInMillis)
        )
        Glide.with(this)
                .load(recipe.smallImageUrls[0])
                .into(iv_recipe)

        setOnClickListener { presenter.onRecipeClick(recipe) }
    }

}