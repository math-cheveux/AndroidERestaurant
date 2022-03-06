package fr.isen.cheveux.androiderestaurant.model

import com.google.gson.annotations.SerializedName

data class PlateData(
    var id: Int = 0,
    @SerializedName("name_fr")
    var frName: String = "",
    @SerializedName("name_en")
    var enName: String = "",
    @SerializedName("id_category")
    var categoryId: Int = 0,
    @SerializedName("categ_name_fr")
    var frCategoryName: String = "",
    @SerializedName("categ_name_en")
    var enCategoryName: String = "",
    var images: List<String>,
    var ingredients: List<IngredientData>,
    var prices: List<PriceData>
) : java.io.Serializable