package fr.isen.cheveux.androiderestaurant.service

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import fr.isen.cheveux.androiderestaurant.TAG
import fr.isen.cheveux.androiderestaurant.model.ApiData
import fr.isen.cheveux.androiderestaurant.model.CartData
import fr.isen.cheveux.androiderestaurant.model.PriceData
import java.io.InputStreamReader
import java.io.OutputStreamWriter

/**
 * @author math-cheveux
 */
class CartService(private val ctx: Context) {
    companion object {
        private const val FILENAME = "cart.save"
        private const val CART_PREFERENCE_FILENAME = "fr.isen.cheveux.androiderestaurant.CART_PREFERENCE_FILE_KEY"
        private const val CART_COUNT_KEY = "cart_count"
    }

    fun getInstance(): CartData = try {
        ctx.openFileInput(FILENAME).use { fis ->
            InputStreamReader(fis).use { ins ->
                Gson().fromJson(ins, CartData::class.java)
            }
        }
    } catch (e: Exception) {
        Log.d(TAG, "getInstance: $e")
        CartData(emptyMap())
    }

    fun getPrices(cart: CartData, apiData: ApiData): Map<PriceData, Int> {
        val dataService = DataService(apiData)
        val default = PriceData()
        return cart.items
            .mapKeys { entry -> dataService.getPriceById(entry.key) ?: default }
            .filterKeys { priceData -> priceData != default }
    }

    fun addItems(cart: CartData, items: Map<PriceData, Int>) {
        for (item in items) {
            cart.items = cart.items.plus(Pair(item.key.id, cart.items[item.key.id]?.plus(item.value) ?: item.value))
        }
    }

    fun removeItem(cart: CartData, item: PriceData) {
        cart.items = cart.items.minus(item.id)
    }

    fun save(cart: CartData) {
        ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE).use { fos ->
            OutputStreamWriter(fos).use { outs ->
                Gson().toJson(cart, outs)
            }
        }

        with(
            ctx.getSharedPreferences(
                CART_PREFERENCE_FILENAME,
                Context.MODE_PRIVATE
            ).edit()
        ) {
            putInt(CART_COUNT_KEY, getNbItems(cart))
            apply()
        }
    }

    private fun getNbItems(cart: CartData): Int =
        if (cart.items.isNotEmpty()) cart.items.map { it.value }.reduce { acc, amount -> acc + amount } else 0

    fun getNbItems(): Int = ctx.getSharedPreferences(
        CART_PREFERENCE_FILENAME,
        Context.MODE_PRIVATE
    ).getInt(CART_COUNT_KEY, 0)
}