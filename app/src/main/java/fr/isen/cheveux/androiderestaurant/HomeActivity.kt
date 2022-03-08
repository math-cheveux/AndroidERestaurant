package fr.isen.cheveux.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.cheveux.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.cheveux.androiderestaurant.service.Api

const val EXTRA_MESSAGE = "fr.isen.cheveux.androidrestaurant.MESSAGE"

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_reload -> {
            Api.invalidate(this)
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