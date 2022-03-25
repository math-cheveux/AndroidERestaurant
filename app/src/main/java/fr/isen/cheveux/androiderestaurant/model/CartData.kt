package fr.isen.cheveux.androiderestaurant.model

import java.io.Serializable

/**
 * @author math-cheveux
 */
data class CartData(
    var items: Map<PriceData, Int>
) : Serializable
