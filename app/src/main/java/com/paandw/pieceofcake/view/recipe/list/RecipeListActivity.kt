package com.paandw.pieceofcake.view.recipe.list

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Recipe
import com.paandw.pieceofcake.view.recipe.RecipeActivity
import kotlinx.android.synthetic.main.activity_recipe_list.*
import kotlinx.android.synthetic.main.toolbar.*

class RecipeListActivity : AppCompatActivity(), IRecipeListView {

    private lateinit var presenter: RecipeListPresenter
    private lateinit var adapter: RecipeListAdapter
    private lateinit var loadingDialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        loadingDialog = MaterialDialog.Builder(this)
                .content("Loading...")
                .progress(true, 0)
                .build()

        toolbar.title = "Recipes"
        toolbar.inflateMenu(R.menu.menu_recipe_list)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_filter) {
                presenter.initiateFilter()
            }
            true
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        presenter = RecipeListPresenter(this)
        adapter = RecipeListAdapter(presenter)
        rv_recipes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_recipes.adapter = adapter

        presenter.start()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showLoadingDialog() {
        loadingDialog.show()
    }

    override fun hideLoadingDialog() {
        loadingDialog.hide()
    }

    override fun bindData(recipes: MutableList<Recipe>) {
        adapter.setListItems(recipes)
    }

    override fun toRecipeDetails(recipe: Recipe) {
        val intent = Intent(this, RecipeActivity::class.java)
        intent.putExtra("recipe_id", recipe.id)
        intent.putExtra("recipe_name", recipe.recipeName)
        startActivity(intent)
    }

    override fun showFilterDialog(index: Int) {
        val dialog = MaterialDialog.Builder(this)
                .items(R.array.recipe_filter)
                .itemsCallbackSingleChoice(index, object: MaterialDialog.ListCallbackSingleChoice {
                    override fun onSelection(dialog: MaterialDialog?, itemView: View?, which: Int, text: CharSequence?): Boolean {
                        return true
                    }
                })
                .positiveText("Confirm")
                .negativeText("Cancel")
                .positiveColor(ContextCompat.getColor(this, R.color.primaryTextColor))
                .negativeColor(ContextCompat.getColor(this, R.color.primaryTextColor))
                .onPositive { dialog, which -> presenter.filterIndexSelected(dialog.selectedIndex) }
                .build()
        if (index >= 0) {
            dialog.selectedIndex = index
        }
        dialog.show()
    }
}
