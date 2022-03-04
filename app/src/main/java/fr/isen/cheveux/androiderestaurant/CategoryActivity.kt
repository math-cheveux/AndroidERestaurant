package fr.isen.cheveux.androiderestaurant

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.cheveux.androiderestaurant.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent.getStringExtra(EXTRA_MESSAGE)

        binding.foodTitle.apply { text = message }

        binding.foodList.layoutManager = LinearLayoutManager(this)
        val foods: Array<String> = when (message) {
            getString(R.string.home_starters) -> {
                resources.getStringArray(R.array.starters_array)
            }
            getString(R.string.home_dishes) -> {
                resources.getStringArray(R.array.dishes_array)
            }
            getString(R.string.home_desserts) -> {
                resources.getStringArray(R.array.desserts_array)
            }
            else -> {
                Array(0) { "" }
            }
        }
        binding.foodList.adapter = FoodAdapter(foods.map { Food(it) }.toTypedArray()) {
            Toast.makeText(this, "Vous avez sélectionné ${it.name}", Toast.LENGTH_SHORT).show()
        }
    }
}