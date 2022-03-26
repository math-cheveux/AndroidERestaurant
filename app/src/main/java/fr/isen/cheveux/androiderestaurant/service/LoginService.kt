package fr.isen.cheveux.androiderestaurant.service

import android.content.Context
import android.util.Patterns
import fr.isen.cheveux.androiderestaurant.model.LoginData
import fr.isen.cheveux.androiderestaurant.model.User

/**
 * @author math-cheveux
 */
class LoginService(private val ctx: Context) {
    companion object {
        private const val LOGIN_PREFERENCE_FILENAME = "fr.isen.cheveux.androiderestaurant.LOGIN_PREFERENCE_FILE_KEY"
        private const val USER_ID_KEY = "user_id"
    }

    fun isConnected(): Boolean = ctx.getSharedPreferences(
        LOGIN_PREFERENCE_FILENAME,
        Context.MODE_PRIVATE
    ).contains(USER_ID_KEY)

    fun save(data: LoginData?) {
        with(
            ctx.getSharedPreferences(
                LOGIN_PREFERENCE_FILENAME,
                Context.MODE_PRIVATE
            ).edit()
        ) {
            if (data != null) {
                putInt(USER_ID_KEY, data.id)
            } else {
                remove(USER_ID_KEY)
            }
            apply()
        }
    }

    fun disconnect() = save(null)

    fun isUserValidForInscription(user: User): Boolean = user.lastName.isNotEmpty()
            && user.firstName.isNotEmpty()
            && user.address.isNotEmpty()
            && isUserValidForConnection(user)

    fun isUserValidForConnection(user: User): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(user.email).matches() && user.password.isNotEmpty()
}