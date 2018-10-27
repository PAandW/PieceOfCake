package com.paandw.pieceofcake.view.ingredients.list

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.data.room.IngredientDatabase
import com.paandw.pieceofcake.view.ingredients.selection.IngredientSelectionActivity
import com.paandw.pieceofcake.view.recipe.list.RecipeListActivity
import kotlinx.android.synthetic.main.activity_ingredient_list.*
import kotlinx.android.synthetic.main.toolbar.*

class IngredientListActivity : AppCompatActivity(), IIngredientListView {

    private var presenter: IngredientListPresenter? = null
    private val INGREDIENT_SELECTION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_list)
        toolbar.title = "Ingredient List"
        presenter = IngredientListPresenter(this, Room.databaseBuilder(this, IngredientDatabase::class.java, "ingredient_database").build())

        btn_add_ingredients.setOnClickListener {
            startActivityForResult(Intent(this, IngredientSelectionActivity::class.java), INGREDIENT_SELECTION_REQUEST_CODE)
        }

        btn_find_recipes.setOnClickListener {
            startActivity(Intent(this, RecipeListActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPause() {
        super.onPause()
        presenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun bindIngredients(ingredients: List<Ingredient>) {
        if (ingredients.isEmpty()) {
            iv_cake.visibility = View.VISIBLE
            tv_empty_state.visibility = View.VISIBLE
        } else {
            iv_cake.visibility = View.INVISIBLE
            tv_empty_state.visibility = View.INVISIBLE
        }
    }
}
