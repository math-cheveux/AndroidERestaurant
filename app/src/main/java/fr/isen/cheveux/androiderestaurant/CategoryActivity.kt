package fr.isen.cheveux.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import fr.isen.cheveux.androiderestaurant.adapter.CategoryAdapter
import fr.isen.cheveux.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.cheveux.androiderestaurant.model.ApiData
import fr.isen.cheveux.androiderestaurant.model.CategoryData
import fr.isen.cheveux.androiderestaurant.service.Api

class CategoryActivity : AppCompatActivity(), Response.Listener<ApiData> {
    private lateinit var binding: ActivityCategoryBinding
    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.categoryToolbar)

        message = intent.getStringExtra(EXTRA_MESSAGE)

        binding.categoryTitle.apply { text = message }

        binding.categoryList.layoutManager = LinearLayoutManager(this)
        Api.recover(this, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_common, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_cart -> {
            startActivity(Intent(this, CartActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onResponse(response: ApiData?) {
        binding.categoryLoadingBar.visibility = View.GONE
        val category: CategoryData? = response?.data?.find { categoryData -> categoryData.frName == message }
        if (category != null) {
            binding.categoryList.adapter = CategoryAdapter(category) {
                val intent = Intent(this, FoodActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, it)
                }
                startActivity(intent)
            }
        }
    }
}