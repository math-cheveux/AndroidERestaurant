package fr.isen.cheveux.androiderestaurant.service

import android.content.Context
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import fr.isen.cheveux.androiderestaurant.R
import fr.isen.cheveux.androiderestaurant.model.ApiData
import fr.isen.cheveux.androiderestaurant.model.RegisterData
import fr.isen.cheveux.androiderestaurant.model.User
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author math-cheveux
 */
class Api {
    companion object {
        private const val url = "http://test.api.catering.bluecodegames.com/"

        private fun request(
            ctx: Context,
            uri: String,
            params: HashMap<String, String> = HashMap(),
            useCache: Boolean = false,
            then: RawListener
        ) {
            params["id_shop"] = "1"

            val method = Request.Method.POST
            val requestUrl = url + uri
            val jsonParams = JSONObject(params.toMap())
            val onOK = Response.Listener<JSONObject> {
                then.onResponse(true, it)
            }
            val onError = Response.ErrorListener {
                val json = try {
                    JSONObject(String(it.networkResponse.data))
                } catch (e: JSONException) {
                    JSONObject()
                }
                then.onResponse(false, json)
            }

            val request: JsonObjectRequest = if (useCache) {
                CacheRequest(method, requestUrl, jsonParams, onOK, onError)
            } else {
                JsonObjectRequest(method, requestUrl, jsonParams, onOK, onError)
            }

            request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 1f)

            VolleySingleton.getInstance(ctx).addToRequestQueue(request)
        }

        private fun <T> convertRawResponse(
            response: JSONObject,
            classToConvert: Class<T>,
            dateFormat: String = "yyyy-MM-dd HH:mm:ss"
        ): T = GsonBuilder().registerTypeAdapter(Date::class.java, JsonDeserializer<Date> { json, _, _ ->
            return@JsonDeserializer try {
                SimpleDateFormat(dateFormat).parse(json.asString)
            } catch (e: ParseException) {
                null
            }
        }).create().fromJson(response.toString(), classToConvert)

        fun recover(ctx: Context, then: DataListener) {
            request(ctx, "menu", useCache = true) { ack, response ->
                then.onResponse(
                    ack,
                    if (ack) convertRawResponse(response, ApiData::class.java)
                    else ApiData(emptyList())
                )
            }
        }

        fun register(ctx: Context, user: User, then: RegisterListener) {
            if (user.isValidForInscription()) {
                val params = HashMap<String, String>()
                params["firstname"] = user.firstName
                params["lastname"] = user.lastName
                params["address"] = user.address
                params["email"] = user.email
                params["password"] = user.password
                request(ctx, "user/register", params) { ack, response ->
                    var convert = RegisterData(ctx.resources.getString(R.string.api_error), code = -1)
                    if (ack || response.length() > 0) {
                        convert = convertRawResponse(response, RegisterData::class.java)
                    }
                    then.onResponse(ack && convert.code == 200, convert)
                }
            } else {
                Toast.makeText(ctx, ctx.resources.getString(R.string.form_error), Toast.LENGTH_SHORT).show()
            }
        }

        fun signIn(ctx: Context, user: User, then: RegisterListener) {
            if (user.isValidForConnection()) {
                val params = HashMap<String, String>()
                params["email"] = user.email
                params["password"] = user.password
                request(ctx, "user/login", params) { ack, response ->
                    var convert = RegisterData(ctx.resources.getString(R.string.api_error), code = -1)
                    if (ack || response.length() > 0) {
                        convert = convertRawResponse(response, RegisterData::class.java)
                    }
                    then.onResponse(ack && convert.code == 200, convert)
                }
            } else {
                Toast.makeText(ctx, ctx.resources.getString(R.string.form_error), Toast.LENGTH_SHORT).show()
            }
        }

        fun invalidate(ctx: Context) {
            VolleySingleton.getInstance(ctx).invalidate()
        }
    }


    private class CacheRequest(
        method: Int,
        url: String,
        jsonObject: JSONObject,
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) : JsonObjectRequest(method, url, jsonObject, listener, errorListener) {
        override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONObject>? {
            var cacheEntry: Cache.Entry? = HttpHeaderParser.parseCacheHeaders(response)
            if (cacheEntry == null) {
                cacheEntry = Cache.Entry()
            }
            val cacheHitButRefreshed =
                (3 * 60 * 1000).toLong() // in 3 minutes cache will be hit, but also refreshed on background

            val cacheExpired = (24 * 60 * 60 * 1000).toLong() // in 24 hours this cache entry expires completely

            val now = System.currentTimeMillis()
            val softExpire = now + cacheHitButRefreshed
            val ttl = now + cacheExpired
            cacheEntry.data = response!!.data
            cacheEntry.softTtl = softExpire
            cacheEntry.ttl = ttl
            var headerValue: String? = response.headers!!["Date"]
            if (headerValue != null) {
                cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue)
            }
            headerValue = response.headers!!["Last-Modified"]
            if (headerValue != null) {
                cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue)
            }
            cacheEntry.responseHeaders = response.headers

            val jsonString = String(
                response.data,
                charset(HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET))
            )
            return Response.success(JSONObject(jsonString), cacheEntry)
        }
    }


    fun interface Listener<T> {
        fun onResponse(ack: Boolean, response: T)
    }

    fun interface RawListener : Listener<JSONObject>

    fun interface DataListener : Listener<ApiData>

    fun interface RegisterListener : Listener<RegisterData>
}