package com.paandw.pieceofcake.view.ingredients.list

import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.paandw.pieceofcake.R
import com.paandw.pieceofcake.data.models.Ingredient
import com.paandw.pieceofcake.data.room.IngredientDao
import com.paandw.pieceofcake.data.room.IngredientDatabase
import kotlinx.android.synthetic.main.activity_ingredient_list.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class IngredientListActivity : AppCompatActivity(), IIngredientListView {

    private var presenter: IngredientListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_list)
        IngredientDatabaseBuilder(this).execute(applicationContext)
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

    fun start(dao: IngredientDao) {
        presenter = IngredientListPresenter(this, dao)
    }

    internal class IngredientDatabaseBuilder : AsyncTask<Context, Void, IngredientDao> {

        private var activity: WeakReference<IngredientListActivity>

        constructor(activity: IngredientListActivity) {
            this.activity = WeakReference(activity)
        }

        override fun doInBackground(vararg context: Context): IngredientDao {
            val db = Room.databaseBuilder(context[0], IngredientDatabase::class.java, "ingredient_database").build()
            val dao = db.ingredientDao()
            return dao
        }

        override fun onPostExecute(result: IngredientDao) {
            super.onPostExecute(result)
            activity.get()?.start(result)
        }
    }
}
