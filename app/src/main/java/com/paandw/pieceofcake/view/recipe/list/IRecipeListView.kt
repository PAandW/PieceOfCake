package com.paandw.pieceofcake.view.recipe.list

import com.paandw.pieceofcake.data.models.Recipe


interface IRecipeListView {
    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun bindData(recipes: MutableList<Recipe>)
    fun toRecipeDetails(recipe: Recipe)
    fun showFilterDialog(index: Int)
}