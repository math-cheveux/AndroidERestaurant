package fr.isen.cheveux.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_MESSAGE = "fr.isen.cheveux.androidrestaurant.MESSAGE"

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, button1.text.toString())
            }
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, button2.text.toString())
            }
            startActivity(intent)
        }

        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, button3.text.toString())
            }
            startActivity(intent)
        }
    }
}