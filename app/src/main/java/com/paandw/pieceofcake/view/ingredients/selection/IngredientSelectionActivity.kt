package com.paandw.pieceofcake.view.ingredients.selection

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.data.room.IngredientDatabase
import kotlinx.android.synthetic.main.activity_ingredient_selection.*
import kotlinx.android.synthetic.main.toolbar.*

class IngredientSelectionActivity : AppCompatActivity(), IIngredientSelectionView {

    private lateinit var loadingDialog: MaterialDialog
    private lateinit var presenter: IngredientSelectionPresenter
    private var adapter: IngredientSelectionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_ingredient_selection)

        // The user only needs to download the list of ingredients the first time they open the app.
        // This loading dialog is instantiated here in case it needs to be shown (in the event the
        // user has not downloaded the ingredients yet
        loadingDialog = MaterialDialog.Builder(this)
                .content("Updating ingredient data")
                .progress(true, 0)
                .build()

        toolbar.title = "Find Ingredients"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        // Presenter created with a reference to the ingredient database table
        presenter = IngredientSelectionPresenter(this, Room.databaseBuilder(this, IngredientDatabase::class.java, "ingredient_database").build())
        adapter = IngredientSelectionAdapter(presenter)
        rv_ingredients.layoutManager = LinearLayoutManager(this)
        rv_ingredients.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_ingredients.adapter = adapter
        presenter.start()

        // Text listener on the search box. Whenever a letter is typed a new search to the database
        // is initiated.
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(searchValue: Editable?) {
                presenter.searchIngredients(searchValue.toString())
            }

            override fun beforeTextChanged(searchValue: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(searchValue: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        })
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

    override fun bindData(ingredientList: MutableList<Ingredient>) {
        adapter?.setSearchItems(ingredientList)
    }

    // This will display a dialog confirming the user wants to add an ingredient. This eliminates
    // the issue of users accidentally adding items to the list
    override fun showConfirmationDialog(ingredient: Ingredient) {
        MaterialDialog.Builder(this)
                .title("Add Ingredient")
                .content("Would you like to add ${ingredient.term} to your ingredient list?")
                .positiveText("Yes")
                .negativeText("No")
                .positiveColor(ContextCompat.getColor(this, R.color.primaryTextColor))
                .negativeColor(ContextCompat.getColor(this, R.color.primaryTextColor))
                .onPositive {_, _ -> presenter.addIngredientToList(ingredient)}
                .show()
    }

    override fun selectIngredient(ingredient: Ingredient) {
        et_search.text.clear()
        Toast.makeText(this, "${ingredient.term} added to list", Toast.LENGTH_SHORT).show()
    }
}
