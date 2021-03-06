package fr.isen.cheveux.androiderestaurant.service

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.google.gson.Gson
import fr.isen.cheveux.androiderestaurant.R
import fr.isen.cheveux.androiderestaurant.model.*
import org.json.JSONObject

/**
 * @author math-cheveux
 */
class AppApi(ctx: Context) : Api(ctx, "http://test.api.catering.bluecodegames.com/", Request.Method.POST) {
    override fun request(
        url: String,
        params: HashMap<String, String>,
        requestMethod: Int,
        useCache: Boolean,
        then: RawListener
    ) {
        params["id_shop"] = "1"
        super.request(url, params, requestMethod, useCache, then)
    }

    fun recover(then: DataListener) {
        request("menu", useCache = true) { ack, response ->
            then.onResponse(
                ack,
                if (ack) convertRawResponse(response, ApiData::class.java)
                else ApiData(emptyList())
            )
        }
    }

    fun register(user: User, then: RegisterListener) {
        if (LoginService(ctx).isUserValidForInscription(user)) {
            val params = HashMap<String, String>()
            params["firstname"] = user.firstName
            params["lastname"] = user.lastName
            params["address"] = user.address
            params["email"] = user.email
            params["password"] = user.password
            request("user/register", params) { ack, response -> onLoginReceived(ack, response, then) }
        } else {
            Toast.makeText(ctx, ctx.resources.getString(R.string.form_error), Toast.LENGTH_SHORT).show()
        }
    }

    fun signIn(user: User, then: RegisterListener) {
        if (LoginService(ctx).isUserValidForConnection(user)) {
            val params = HashMap<String, String>()
            params["email"] = user.email
            params["password"] = user.password
            request("user/login", params) { ack, response -> onLoginReceived(ack, response, then) }
        } else {
            Toast.makeText(ctx, ctx.resources.getString(R.string.form_error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLoginReceived(ack: Boolean, response: JSONObject, then: RegisterListener) {
        var convert = RegisterData(ctx.resources.getString(R.string.api_error), code = -1)
        if (ack || response.length() > 0) {
            convert = convertRawResponse(response, RegisterData::class.java)
        }
        then.onResponse(ack && convert.code == 200, convert)
    }

    fun order(userId: Int, cart: CartData, then: OrderListener) {
        val params = HashMap<String, String>()
        params["id_user"] = userId.toString()
        params["msg"] = Gson().toJson(cart)
        request("user/order", params) { ack, response ->
            then.onResponse(ack, convertRawResponse(response, OrderData::class.java))
        }
    }

    fun invalidate() {
        VolleySingleton.getInstance(ctx).invalidate()
    }

    fun interface DataListener : Listener<ApiData>

    fun interface RegisterListener : Listener<RegisterData>

    fun interface OrderListener : Listener<OrderData>
}