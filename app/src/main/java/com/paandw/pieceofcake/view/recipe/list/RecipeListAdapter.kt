package com.paandw.pieceofcake.view.recipe.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.paandw.pieceofcake.data.models.Recipe
import com.paandw.pieceofcake.view.custom.RecipeListItem


internal class RecipeListAdapter (private val presenter: RecipeListPresenter) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    private val recipeItems = ArrayList<Recipe>()

    fun setListItems(items: MutableList<Recipe>) {
        recipeItems.clear()
        recipeItems.addAll(items)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListAdapter.ViewHolder {
        return ViewHolder(RecipeListItem(parent.context))
    }

    override fun getItemCount(): Int = recipeItems.size

    override fun onBindViewHolder(holder: RecipeListAdapter.ViewHolder, position: Int) {
        holder.searchItem.setup(recipeItems[position])
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var searchItem: RecipeListItem = itemView as RecipeListItem

    }

}