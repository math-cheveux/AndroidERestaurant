package fr.isen.cheveux.androiderestaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.cheveux.androiderestaurant.adapter.ImageAdapter
import fr.isen.cheveux.androiderestaurant.databinding.ActivityFoodBinding
import fr.isen.cheveux.androiderestaurant.model.PlateData

class FoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plate: PlateData = intent.getSerializableExtra(EXTRA_MESSAGE) as PlateData
        binding.foodName.text = plate.frName

        if (plate.images.isNotEmpty() && plate.images[0].isNotEmpty()) {
            binding.foodPreviews.layoutManager = LinearLayoutManager(this)
            binding.foodPreviews.adapter = ImageAdapter(plate.images)
        }
    }
}