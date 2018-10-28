package com.paandw.pieceofcake.view.ingredients.selection

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.view.custom.IngredientSearchItem


internal class IngredientSelectionAdapter (private val presenter: IngredientSelectionPresenter) : RecyclerView.Adapter<IngredientSelectionAdapter.ViewHolder>() {

    private val searchItems = ArrayList<Ingredient>()

    fun setSearchItems(items: MutableList<Ingredient>) {
        searchItems.clear()
        searchItems.addAll(items)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientSelectionAdapter.ViewHolder {
        return ViewHolder(IngredientSearchItem(parent.context))
    }

    override fun getItemCount(): Int = searchItems.size

    override fun onBindViewHolder(holder: IngredientSelectionAdapter.ViewHolder, position: Int) {
        holder.searchItem.setup(searchItems[position], presenter)
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var searchItem: IngredientSearchItem = itemView as IngredientSearchItem

    }

}