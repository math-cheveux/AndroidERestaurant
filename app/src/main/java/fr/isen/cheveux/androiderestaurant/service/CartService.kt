package fr.isen.cheveux.androiderestaurant.service

import android.content.Context
import fr.isen.cheveux.androiderestaurant.model.CartData
import fr.isen.cheveux.androiderestaurant.model.PriceData
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

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
        val fis: FileInputStream = ctx.openFileInput(FILENAME)
        val ins = ObjectInputStream(fis)
        val obj: CartData = ins.readObject() as CartData
        ins.close()
        fis.close()
        obj
    } catch (e: Exception) {
        CartData(HashMap())
    }

    fun addItems(cart: CartData, items: Map<PriceData, Int>) {
        for (item in items) {
            cart.items = cart.items.plus(Pair(item.key, cart.items[item.key]?.plus(item.value) ?: item.value))
        }
    }

    fun save(cart: CartData) {
        val fos: FileOutputStream = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        val outs = ObjectOutputStream(fos)
        outs.writeObject(cart)
        outs.close()
        fos.close()

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