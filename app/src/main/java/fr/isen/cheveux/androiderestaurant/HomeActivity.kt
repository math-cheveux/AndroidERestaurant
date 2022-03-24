package fr.isen.cheveux.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import fr.isen.cheveux.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.cheveux.androiderestaurant.service.AppApi
import fr.isen.cheveux.androiderestaurant.service.LoginService

const val TAG = "fr.isen.cheveux.androidrestaurant.debug"
const val EXTRA_MESSAGE = "fr.isen.cheveux.androidrestaurant.MESSAGE"
const val CART_PREFERENCE_FILENAME = "fr.isen.cheveux.androiderestaurant.CART_PREFERENCE_FILE_KEY"

class HomeActivity : CartAppCompatActivity(R.menu.menu_home) {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.homeToolbar)

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

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_reload -> {
            AppApi(this).invalidate()
            LoginService(this).disconnect()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    public override fun onStop() {
        super.onStop()
        println("home end")
        Toast.makeText(applicationContext, "home end", Toast.LENGTH_LONG).show()
    }
}