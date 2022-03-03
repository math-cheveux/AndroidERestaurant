package fr.isen.cheveux.androiderestaurant

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener { Toast.makeText(applicationContext, "Entrées", Toast.LENGTH_SHORT).show() }

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener { Toast.makeText(applicationContext, "Plats", Toast.LENGTH_SHORT).show() }

        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener { Toast.makeText(applicationContext, "Desserts", Toast.LENGTH_SHORT).show() }
    }
}