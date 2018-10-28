package com.paandw.pieceofcake.view.recipe.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Recipe
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
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        presenter = RecipeListPresenter(this)
        adapter = RecipeListAdapter(presenter)
        rv_recipes.layoutManager = LinearLayoutManager(this)
        rv_recipes.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
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
}
