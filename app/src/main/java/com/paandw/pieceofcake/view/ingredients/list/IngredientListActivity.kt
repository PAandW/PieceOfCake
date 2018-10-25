package com.paandw.pieceofcake.view.ingredients.list

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.data.room.IngredientDatabase
import kotlinx.android.synthetic.main.activity_ingredient_list.*

class IngredientListActivity : AppCompatActivity(), IIngredientListView {

    private var presenter: IngredientListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_list)
        presenter = IngredientListPresenter(this, Room.databaseBuilder(this, IngredientDatabase::class.java, "ingredient_database").build())
    }

    override fun onPause() {
        super.onPause()
        presenter?.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showIngredient(ingredient: Ingredient) {
        tv_ingredient.text = ingredient.term
    }
}
