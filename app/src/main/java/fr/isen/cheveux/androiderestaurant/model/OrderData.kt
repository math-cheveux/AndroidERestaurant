package fr.isen.cheveux.androiderestaurant.model

/**
 * @author math-cheveux
 */
data class OrderData(
    val data: List<CommandData>? = null,
    val code: String = ""
)
