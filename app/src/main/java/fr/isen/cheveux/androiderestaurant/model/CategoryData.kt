package fr.isen.cheveux.androiderestaurant.model

import com.google.gson.annotations.SerializedName

data class CategoryData(
    @SerializedName("name_fr")
    var frName: String = "",
    @SerializedName("name_en")
    var enName: String = "",
    var items: List<PlateData>
)