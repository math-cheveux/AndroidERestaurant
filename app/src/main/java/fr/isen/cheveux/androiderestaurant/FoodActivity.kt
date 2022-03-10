package fr.isen.cheveux.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import fr.isen.cheveux.androiderestaurant.adapter.ImageFragmentStateAdapter
import fr.isen.cheveux.androiderestaurant.adapter.IngredientAdapter
import fr.isen.cheveux.androiderestaurant.adapter.PriceAdapter
import fr.isen.cheveux.androiderestaurant.databinding.ActivityFoodBinding
import fr.isen.cheveux.androiderestaurant.model.CartData
import fr.isen.cheveux.androiderestaurant.model.PlateData
import fr.isen.cheveux.androiderestaurant.model.PriceData

class FoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodBinding
    private var command: Map<PriceData, Int> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.foodToolbar)

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
            if (command.isNotEmpty() && command.map { it.value }.reduce { acc, itemCount -> acc + itemCount } > 0) {
                binding.buttonCommand.visibility = View.VISIBLE
                binding.buttonCommand.text =
                    ("Total "
                            + command.entries.map { it.key.price * it.value }
                        .reduce { acc, entry -> acc + entry }
                        .toString()
                            + getString(R.string.currency_euro))
            } else {
                binding.buttonCommand.visibility = View.GONE
            }
        }

        binding.buttonCommand.setOnClickListener {
            val cart = CartData.getInstance(this)
            cart.addItems(command)
            cart.save(this)
            (binding.foodPrices.adapter as PriceAdapter).resetCounts()
            command = emptyMap()
            binding.buttonCommand.visibility = View.GONE
            Snackbar.make(binding.root, "Votre panier a de nouveaux éléments.", Snackbar.LENGTH_LONG)
                .setAction("Voir le panier") {
                    startActivity(Intent(this, CartActivity::class.java))
                }
                .show()
        }
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
}