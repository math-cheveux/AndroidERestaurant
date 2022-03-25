package fr.isen.cheveux.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.cheveux.androiderestaurant.adapter.CartAdapter
import fr.isen.cheveux.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.cheveux.androiderestaurant.service.AppApi
import fr.isen.cheveux.androiderestaurant.service.CartService
import fr.isen.cheveux.androiderestaurant.service.LoginService

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cart = CartService(this).getInstance()

        binding.cartItems.layoutManager = LinearLayoutManager(this)
        AppApi(this).recover { ack, response ->
            if (ack) {
                binding.cartItems.adapter = CartAdapter(response, cart)
            }
        }

        if (cart.items.isNotEmpty()) {
            binding.cartButton.setOnClickListener {
                if (!LoginService(this).isConnected()) {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        } else {
            binding.cartButton.visibility = View.GONE
        }
    }
}