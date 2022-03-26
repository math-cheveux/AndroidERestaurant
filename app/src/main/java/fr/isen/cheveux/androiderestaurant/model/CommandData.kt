package fr.isen.cheveux.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * @author math-cheveux
 */
data class CommandData(
    @SerializedName("id_sender")
    val senderId: Int,
    @SerializedName("id_receiver")
    val receiverId: Int,
    val sender: String,
    val receiver: String,
    val code: String,
    @SerializedName("type_msg")
    val messageType: Int,
    val message: String,
    @SerializedName("create_date")
    val creationDate: Date?
)
