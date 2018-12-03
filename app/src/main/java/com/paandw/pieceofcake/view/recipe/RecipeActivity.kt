package com.paandw.pieceofcake.view.recipe

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_recipe)

        // The recipe ID and recipe name are taken from the intent that was passed to it
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

        btn_read_directions.setOnClickListener { presenter.readDirections() }
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
        loadingDialog.dismiss()
    }

    override fun bindData(imageUrl: String, ingredientText: String, directionsUrl: String, rating: String, prepTime: String) {
        // Glide is a library used to load images in a background thread and display them when they're ready
        Glide.with(this)
                .load(imageUrl)
                .into(iv_recipe)

        tv_ingredient_list.text = ingredientText
        tv_rating.text = rating
        tv_prep_time.text = prepTime
    }

    // Since the directions aren't included in the API, we have to use a thing called "CustomTabs".
    // This will open a webpage with the URL provided and allow the user to navigate it while
    // remaining within the context of the app.
    override fun toReadDirections(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder()
                .setToolbarColor(ContextCompat.getColor(this, R.color.primaryDarkColor))
                .build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}
