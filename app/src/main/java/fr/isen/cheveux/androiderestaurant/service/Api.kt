package fr.isen.cheveux.androiderestaurant.service

import android.content.Context
import com.android.volley.Cache
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author math-cheveux
 */
open class Api(protected val ctx: Context, private val baseUrl: String, private val method: Int) {
    protected fun request(
        url: String,
        params: HashMap<String, String> = HashMap(),
        requestMethod: Int = method,
        useCache: Boolean = false,
        then: RawListener
    ) {
        val requestUrl = baseUrl + url
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
            CacheRequest(requestMethod, requestUrl, jsonParams, onOK, onError)
        } else {
            JsonObjectRequest(requestMethod, requestUrl, jsonParams, onOK, onError)
        }

        request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 1f)

        VolleySingleton.getInstance(ctx).addToRequestQueue(request)
    }

    protected fun <T> convertRawResponse(
        response: JSONObject,
        classToConvert: Class<T>,
        dateFormat: String = "yyyy-MM-dd HH:mm:ss"
    ): T = GsonBuilder().registerTypeAdapter(Date::class.java, JsonDeserializer<Date> { json, _, _ ->
        return@JsonDeserializer try {
            SimpleDateFormat(dateFormat, Locale.US).parse(json.asString)
        } catch (e: ParseException) {
            null
        }
    }).create().fromJson(response.toString(), classToConvert)


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
}