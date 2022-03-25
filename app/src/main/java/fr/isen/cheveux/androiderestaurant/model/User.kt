package fr.isen.cheveux.androiderestaurant.model

/**
 * @author math-cheveux
 */
data class User(
    val lastName: String,
    val firstName: String,
    val address: String,
    val email: String,
    val password: String
)
