package fr.isen.cheveux.androiderestaurant.model

import android.util.Patterns

/**
 * @author math-cheveux
 */
data class User(
    val lastName: String,
    val firstName: String,
    val address: String,
    val email: String,
    val password: String
) {
    fun isValidForInscription(): Boolean = lastName.isNotEmpty()
            && firstName.isNotEmpty()
            && address.isNotEmpty()
            && isValidForConnection()

    fun isValidForConnection(): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotEmpty()
}
