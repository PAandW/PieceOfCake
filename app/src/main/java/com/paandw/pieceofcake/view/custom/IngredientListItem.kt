package com.paandw.pieceofcake.view.custom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Ingredient
import kotlinx.android.synthetic.main.view_ingredient_search_item.view.*


class IngredientListItem(context: Context) : FrameLayout(context) {

    init {
        layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.view_ingredient_search_item, this)
    }

    fun setup(ingredient: Ingredient) {
        tv_ingredient_name.text = ingredient.term
    }

}