package fr.isen.cheveux.androiderestaurant

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.cheveux.androiderestaurant.adapter.ImageFragmentStateAdapter
import fr.isen.cheveux.androiderestaurant.adapter.IngredientAdapter
import fr.isen.cheveux.androiderestaurant.adapter.PriceAdapter
import fr.isen.cheveux.androiderestaurant.databinding.ActivityFoodBinding
import fr.isen.cheveux.androiderestaurant.model.PlateData
import fr.isen.cheveux.androiderestaurant.model.PriceData

class FoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodBinding
    private var command: Map<PriceData, Int> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plate: PlateData = intent.getSerializableExtra(EXTRA_MESSAGE) as PlateData
        binding.foodName.text = plate.frName

        if (plate.images.isNotEmpty() && plate.images[0].isNotEmpty()) {
            binding.foodPreviews.adapter = ImageFragmentStateAdapter(plate.images, this)
        } else {
            binding.foodPreviews.visibility = View.GONE
        }

        binding.foodIngredients.layoutManager = GridLayoutManager(this, 2)
        binding.foodIngredients.adapter = IngredientAdapter(plate.ingredients)

        binding.foodPrices.layoutManager = LinearLayoutManager(this)
        binding.foodPrices.adapter = PriceAdapter(plate.prices) { priceData, count ->
            command = command.plus(Pair(priceData, count))
            Log.d("FoodActivity", command.toString())
            if (command.isNotEmpty()) {
                binding.buttonCommand.visibility = View.VISIBLE
                binding.buttonCommand.text =
                    ("Total "
                            + command.entries.map { entry -> entry.key.price * entry.value }
                        .reduce { acc, entry -> acc + entry }
                        .toString()
                            + getString(R.string.currency_euro))
            } else {
                binding.buttonCommand.visibility = View.GONE
            }
        }
    }
}