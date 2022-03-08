package fr.isen.cheveux.androiderestaurant.service

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.GsonBuilder
import fr.isen.cheveux.androiderestaurant.model.ApiData
import org.json.JSONObject

/**
 * @author math-cheveux
 */
class Api {
    companion object {
        private const val url = "http://test.api.catering.bluecodegames.com/menu"

        fun recover(ctx: Context, then: Response.Listener<ApiData>) {
            val params = HashMap<String, String>()
            params["id_shop"] = "1"
            val jsonObject = JSONObject(params.toMap())

            val request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
                { response ->
                    then.onResponse(
                        GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
                            .fromJson(response.toString(), ApiData::class.java)
                    )
                },
                { it.message?.let { it1 -> Log.e("HomeActivity", it1) } })

            request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 1f)

            VolleySingleton.getInstance(ctx).addToRequestQueue(request)
        }
    }
}