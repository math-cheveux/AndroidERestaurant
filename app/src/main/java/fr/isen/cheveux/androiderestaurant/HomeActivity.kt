package fr.isen.cheveux.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.cheveux.androiderestaurant.databinding.ActivityHomeBinding

const val EXTRA_MESSAGE = "fr.isen.cheveux.androidrestaurant.MESSAGE"

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStarters.setOnClickListener {
            onClick(binding.buttonStarters.text.toString())
        }

        binding.buttonDishes.setOnClickListener {
            onClick(binding.buttonDishes.text.toString())
        }

        binding.buttonDesserts.setOnClickListener {
            onClick(binding.buttonDesserts.text.toString())
        }
    }

    private fun onClick(text: String) {
        val intent = Intent(this, CategoryActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, text)
        }
        startActivity(intent)
    }
}