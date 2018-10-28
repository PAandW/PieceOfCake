package com.paandw.pieceofcake.view.custom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Recipe
import kotlinx.android.synthetic.main.view_recipe_list_item.view.*


class RecipeListItem(context: Context) : FrameLayout(context) {

    init {
        layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.view_recipe_list_item, this)
    }

    fun setup(recipe: Recipe) {
        tv_recipe_name.text = recipe.recipeName
        Glide.with(this)
                .load(recipe.smallImageUrls[0])
                .into(iv_recipe)
    }

}