package com.paandw.pieceofcake.view.recipe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.paandw.pieceofcake.R
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.toolbar.*

class RecipeActivity : AppCompatActivity(), IRecipeView {

    private lateinit var presenter: RecipePresenter
    private lateinit var loadingDialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val id = intent.getStringExtra("recipe_id")
        val name = intent.getStringExtra("recipe_name")

        loadingDialog = MaterialDialog.Builder(this)
                .content("Loading...")
                .progress(true, 0)
                .build()

        toolbar.title = name
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { _ -> onBackPressed() }

        presenter = RecipePresenter(this)
        presenter.start(id)
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

    override fun bindData(imageUrl: String, ingredientText: String, directionsUrl: String) {
        Glide.with(this)
                .load(imageUrl)
                .into(iv_recipe)

        tv_ingredient_list.text = ingredientText
    }
}
