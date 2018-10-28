package com.paandw.pieceofcake.view.recipe


interface IRecipeView {

    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun bindData(imageUrl: String, ingredientText: String, directionsUrl: String)

}