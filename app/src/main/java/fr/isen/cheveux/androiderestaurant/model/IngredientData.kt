package fr.isen.cheveux.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class IngredientData(
    var id: Int = 0,
    @SerializedName("id_shop")
    var shopId: Int = 0,
    @SerializedName("name_fr")
    var frName: String = "",
    @SerializedName("name_en")
    var enName: String = "",
    @SerializedName("create_date")
    var creationDate: Date? = null,
    @SerializedName("update_date")
    var updateDate: Date? = null,
    @SerializedName("id_pizza")
    var pizzaId: Int = 0
) : java.io.Serializable