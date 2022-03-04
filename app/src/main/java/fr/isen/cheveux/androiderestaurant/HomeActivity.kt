package fr.isen.cheveux.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import fr.isen.cheveux.androiderestaurant.databinding.ActivityHomeBinding
import org.json.JSONObject

const val EXTRA_MESSAGE = "fr.isen.cheveux.androidrestaurant.MESSAGE"

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    companion object {
        var API: JSONObject? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recover {
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
    }

    private fun onClick(text: String) {
        val intent = Intent(this, CategoryActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, text)
        }
        startActivity(intent)
    }

    private fun recover(then: Runnable) {
        val url = "http://test.api.catering.bluecodegames.com/menu"

        // Post parameters
        // Form fields and values
        val params = HashMap<String, String>()
        params["id_shop"] = "1"
        val jsonObject = JSONObject(params.toMap())

        // Volley post request with parameters
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                // Process the json
                try {
                    API = response
                    then.run()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, {
                // Error in request
                it.message?.let { it1 -> Log.e("HomeActivity", it1) }
            })


        // Volley request policy, only one time request to avoid duplicate transaction
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add the volley post request to the request queue
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    public override fun onStop() {
        super.onStop()
        println("home end")
        Toast.makeText(applicationContext, "home end", Toast.LENGTH_LONG).show()
    }
}