package com.paandw.pieceofcake.view.ingredients.list

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.view.ingredients.selection.IngredientSelectionActivity
import com.paandw.pieceofcake.view.recipe.list.RecipeListActivity
import kotlinx.android.synthetic.main.activity_ingredient_list.*
import kotlinx.android.synthetic.main.toolbar.*

class IngredientListActivity : AppCompatActivity(), IIngredientListView {

    private var presenter: IngredientListPresenter? = null // Presenters are used to handle all non-view related logic
    private var adapter: IngredientListAdapter? = null
    private val INGREDIENT_SELECTION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        // This is the main screen of the app. It opens directly to this
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_ingredient_list)
        toolbar.title = "Ingredient List"
        // The toolbar "menu" is just a single trash can icon
        toolbar.inflateMenu(R.menu.menu_ingredient_list)
        // Sets a listener on the trash can icon to display an Android material popup confirming
        // they want to delete all the ingredients in the list
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

        // RecyclerView used to display ingredients requires a layout manager and recyclerview adapter to be created
        rv_ingredients.layoutManager = LinearLayoutManager(this)
        rv_ingredients.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_ingredients.adapter = adapter

        // Listeners for both buttons at the bottom of the screen. First one takes you to ingredient
        // search/selection screen, second takes you to recipe list screen
        btn_find_ingredients.setOnClickListener {
            startActivityForResult(Intent(this, IngredientSelectionActivity::class.java), INGREDIENT_SELECTION_REQUEST_CODE)
        }

        btn_find_recipes.setOnClickListener {
            startActivity(Intent(this, RecipeListActivity::class.java))
        }

        presenter?.refreshIngredientList()
    }

    // This will be fired off automatically by the OS when returning from the ingredient selection
    // screen. It detects that changes were made to the ingredient list and then refreshes it
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

    // Straightforward - shows a confirmation dialog to make sure the user wants to delete an item
    override fun showIngredientDeletionConfirmation(ingredient: Ingredient) {
        MaterialDialog.Builder(this)
                .title("Remove Ingredient")
                .content("Remove ${ingredient.term} from the ingredient list?")
                .positiveText("Yes")
                .negativeText("No")
                .positiveColor(ContextCompat.getColor(this, R.color.primaryTextColor))
                .negativeColor(ContextCompat.getColor(this, R.color.primaryTextColor))
                .onPositive { _, _ -> presenter?.deleteIngredient(ingredient) }
                .show()
    }

    // Binds the ingredients to the recyclerview to be displayed. If the list of ingredients is
    // empty then the empty state graphic and text are shown instead and the delete all icon in
    // the toolbar is hidden
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
