package fr.isen.cheveux.androiderestaurant.service

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.Response.Listener
import com.android.volley.toolbox.HttpHeaderParser
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

        fun recover(ctx: Context, then: Listener<ApiData>) {
            val params = HashMap<String, String>()
            params["id_shop"] = "1"
            val jsonObject = JSONObject(params.toMap())

            val request = CacheRequest(Request.Method.POST, url, jsonObject,
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

        fun invalidate(ctx: Context) {
            VolleySingleton.getInstance(ctx).invalidate()
        }
    }

    private class CacheRequest(
        method: Int,
        url: String,
        jsonObject: JSONObject,
        listener: Listener<JSONObject>,
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
}