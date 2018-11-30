package com.paandw.pieceofcake.view.custom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.view.ingredients.list.IngredientListPresenter
import kotlinx.android.synthetic.main.view_ingredient_list_item.view.*


class IngredientListItem(context: Context) : FrameLayout(context) {

    private var presenter: IngredientListPresenter? = null
    private lateinit var ingredient: Ingredient

    init {
        layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.view_ingredient_list_item, this)
        btn_delete.setOnClickListener { presenter?.ingredientDeleteClicked(ingredient) }
    }

    fun setup(ingredient: Ingredient, presenter: IngredientListPresenter) {
        this.ingredient = ingredient
        this.presenter = presenter
        tv_ingredient_name.text = ingredient.term
    }

}