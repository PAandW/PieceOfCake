package com.paandw.pieceofcake.view.ingredients.list

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import com.afollestad.materialdialogs.MaterialDialog
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.view.ingredients.selection.IngredientSelectionActivity
import com.paandw.pieceofcake.view.recipe.list.RecipeListActivity
import kotlinx.android.synthetic.main.activity_ingredient_list.*
import kotlinx.android.synthetic.main.toolbar.*

class IngredientListActivity : AppCompatActivity(), IIngredientListView {

    private var presenter: IngredientListPresenter? = null
    private var adapter: IngredientListAdapter? = null
    private val INGREDIENT_SELECTION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_list)
        toolbar.title = "Ingredient List"
        toolbar.inflateMenu(R.menu.menu_ingredient_list)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_delete_all) {
                MaterialDialog.Builder(this)
                        .title("Delete all")
                        .content("Delete all ingredients from the list?")
                        .positiveColor(ContextCompat.getColor(this, R.color.primaryTextColor))
                        .negativeColor(ContextCompat.getColor(this, R.color.primaryTextColor))
                        .negativeText("No")
                        .positiveText("Yes")
                        .onPositive { _, _ -> presenter?.deleteAllIngredients() }
                        .show()
            }
            true
        }
        presenter = IngredientListPresenter(this)

        adapter = IngredientListAdapter(presenter!!)
        rv_ingredients.layoutManager = LinearLayoutManager(this)
        rv_ingredients.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_ingredients.adapter = adapter

        btn_find_ingredients.setOnClickListener {
            startActivityForResult(Intent(this, IngredientSelectionActivity::class.java), INGREDIENT_SELECTION_REQUEST_CODE)
        }

        btn_find_recipes.setOnClickListener {
            startActivity(Intent(this, RecipeListActivity::class.java))
        }

        presenter?.refreshIngredientList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == INGREDIENT_SELECTION_REQUEST_CODE) {
            presenter?.refreshIngredientList()
        }
    }

    override fun onPause() {
        super.onPause()
        presenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun bindIngredients(ingredients: MutableList<Ingredient>) {
        if (ingredients.isEmpty()) {
            iv_cake.visibility = View.VISIBLE
            tv_empty_state.visibility = View.VISIBLE
            rv_ingredients.visibility = View.INVISIBLE
            toolbar.menu.getItem(0).isVisible = false
        } else {
            iv_cake.visibility = View.INVISIBLE
            tv_empty_state.visibility = View.INVISIBLE
            rv_ingredients.visibility = View.VISIBLE
            toolbar.menu.getItem(0).isVisible = true
        }

        adapter?.setListItems(ingredients)
    }
}
