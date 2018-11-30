package com.paandw.pieceofcake.view.ingredients.selection

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
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
        setContentView(R.layout.activity_ingredient_selection)

        loadingDialog = MaterialDialog.Builder(this)
                .content("Updating ingredient data")
                .progress(true, 0)
                .build()

        toolbar.title = "Add Ingredients"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        presenter = IngredientSelectionPresenter(this, Room.databaseBuilder(this, IngredientDatabase::class.java, "ingredient_database").build())
        adapter = IngredientSelectionAdapter(presenter)
        rv_ingredients.layoutManager = LinearLayoutManager(this)
        rv_ingredients.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_ingredients.adapter = adapter
        presenter.start()

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(searchValue: Editable?) {
                presenter.searchIngredients(searchValue.toString())
            }

            override fun beforeTextChanged(searchValue: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(searchValue: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //presenter.searchIngredients(searchValue.toString())
            }
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
        Toast.makeText(this, "${ingredient.term} added to list", Toast.LENGTH_SHORT).show()
    }
}
