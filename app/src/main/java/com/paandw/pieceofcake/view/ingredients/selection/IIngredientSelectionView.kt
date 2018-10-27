package com.paandw.pieceofcake.view.ingredients.selection

import com.paandw.pieceofcake.data.models.Ingredient


interface IIngredientSelectionView {

    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun bindData(ingredientList: MutableList<Ingredient>)

}