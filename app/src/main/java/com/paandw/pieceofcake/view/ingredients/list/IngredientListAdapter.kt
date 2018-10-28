package com.paandw.pieceofcake.view.ingredients.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.view.custom.IngredientListItem


internal class IngredientListAdapter (private val presenter: IngredientListPresenter) : RecyclerView.Adapter<IngredientListAdapter.ViewHolder>() {

    private val searchItems = ArrayList<Ingredient>()

    fun setListItems(items: MutableList<Ingredient>) {
        searchItems.clear()
        searchItems.addAll(items)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientListAdapter.ViewHolder {
        return ViewHolder(IngredientListItem(parent.context))
    }

    override fun getItemCount(): Int = searchItems.size

    override fun onBindViewHolder(holder: IngredientListAdapter.ViewHolder, position: Int) {
        holder.searchItem.setup(searchItems[position])
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var searchItem: IngredientListItem = itemView as IngredientListItem

    }

}