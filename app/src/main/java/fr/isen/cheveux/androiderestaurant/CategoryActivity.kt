package fr.isen.cheveux.androiderestaurant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import fr.isen.cheveux.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.cheveux.androiderestaurant.model.ApiData
import fr.isen.cheveux.androiderestaurant.model.CategoryData

class CategoryActivity : AppCompatActivity(), Response.Listener<ApiData> {
    private lateinit var binding: ActivityCategoryBinding
    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        message = intent.getStringExtra(EXTRA_MESSAGE)

        binding.categoryTitle.apply { text = message }

        binding.categoryList.layoutManager = LinearLayoutManager(this)
        Api.recover(this, this)
    }

    override fun onResponse(response: ApiData?) {
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