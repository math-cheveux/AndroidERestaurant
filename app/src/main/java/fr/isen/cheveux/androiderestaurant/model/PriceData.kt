package fr.isen.cheveux.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class PriceData(
    var id: Int = 0,
    var pizzaId: Int = 0,
    var sizeId: Int = 0,
    var price: Float = 0f,
    @SerializedName("create_date")
    var creationDate: Date? = null,
    @SerializedName("update_date")
    var updateDate: Date? = null,
    var size: String = ""
) : java.io.Serializable