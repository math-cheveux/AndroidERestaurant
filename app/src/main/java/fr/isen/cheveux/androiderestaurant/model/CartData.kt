package fr.isen.cheveux.androiderestaurant.model

import android.content.Context
import java.io.*

/**
 * @author math-cheveux
 */
data class CartData(
    var items: Map<PriceData, Int>
) : Serializable {
    companion object {
        private const val FILENAME = "cart.save"

        fun getInstance(context: Context): CartData {
            return try {
                val fis: FileInputStream = context.openFileInput(FILENAME)
                val ins = ObjectInputStream(fis)
                val obj: CartData = ins.readObject() as CartData
                ins.close()
                fis.close()
                obj
            } catch (e: FileNotFoundException) {
                CartData(HashMap())
            }
        }
    }

    fun addItems(items: Map<PriceData, Int>) {
        for (item in items) {
            this.items = this.items.plus(Pair(item.key, this.items[item.key]?.plus(item.value) ?: item.value))
        }
    }

    fun save(context: Context) {
        val fos: FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        val outs = ObjectOutputStream(fos)
        outs.writeObject(this)
        outs.close()
        fos.close()
    }
}
