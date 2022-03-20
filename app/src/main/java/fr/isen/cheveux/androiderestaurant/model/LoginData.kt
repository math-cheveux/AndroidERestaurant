package fr.isen.cheveux.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 * @author math-cheveux
 */
data class LoginData(
    val id: Int,
    val code: String,
    @SerializedName("id_shop")
    val shopId: Int,
    val email: String,
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
    val phone: String,
    val address: String,
    @SerializedName("postal_code")
    val postalCode: String,
    @SerializedName("birth_date")
    val birthDate: Date?,
    val town: String,
    val points: Int,
    val token: String,
    @SerializedName("gcmtoken")
    val gcmToken: String,
    @SerializedName("create_date")
    val creationDate: Date?,
    @SerializedName("update_date")
    val updateDate: Date?
) : Serializable
