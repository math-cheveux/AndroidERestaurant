package fr.isen.cheveux.androiderestaurant

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.cheveux.androiderestaurant.databinding.ActivityLoginBinding
import fr.isen.cheveux.androiderestaurant.model.User

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var mode: Mode = Mode.SIGN_IN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        refresh()

        binding.loginButton.setOnClickListener {
            if (binding.editPassword.text.toString() != binding.editConfirm.text.toString()) {
                Toast.makeText(this, "Passwords are not identical.", Toast.LENGTH_SHORT).show()
            }
            val user = User(
                binding.editLastName.text.toString().trim(),
                binding.editFirstName.text.toString().trim(),
                binding.editAddress.text.toString().trim(),
                binding.editEmail.text.toString().trim(),
                binding.editPassword.text.toString().trim()
            )
        }
        binding.textSwitchLoginMode.setOnClickListener {
            mode = mode.reverse()
            refresh()
        }
    }

    private fun refresh() {
        val display = if (mode == Mode.REGISTER) View.VISIBLE else View.GONE
        binding.lastNameLayout.visibility = display
        binding.firstNameLayout.visibility = display
        binding.addressLayout.visibility = display
        binding.confirmLayout.visibility = display
        binding.loginButton.text = mode.getString(this)
        binding.textSwitchLoginMode.text = mode.getSwitchString(this)
    }

    private enum class Mode(private val stringId: Int, private val switchStringId: Int) {
        SIGN_IN(R.string.sign_in, R.string.no_account), REGISTER(R.string.register, R.string.have_account);

        fun reverse(): Mode {
            return when (this) {
                SIGN_IN -> REGISTER
                REGISTER -> SIGN_IN
            }
        }

        fun getString(ctx: Context): String = ctx.resources.getString(stringId)

        fun getSwitchString(ctx: Context): String = ctx.resources.getString(switchStringId)
    }
}