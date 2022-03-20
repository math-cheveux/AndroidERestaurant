package fr.isen.cheveux.androiderestaurant.service

import android.content.Context
import fr.isen.cheveux.androiderestaurant.model.LoginData
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * @author math-cheveux
 */
class LoginService(private val ctx: Context) {
    companion object {
        private const val FILENAME = "user.save"
    }

    fun getInstance(): LoginData? = try {
        val fis: FileInputStream = ctx.openFileInput(FILENAME)
        val ins = ObjectInputStream(fis)
        val obj = ins.readObject() as LoginData
        ins.close()
        fis.close()
        obj
    } catch (e: Exception) {
        null
    }

    fun isConnected(): Boolean = getInstance() != null

    fun save(data: LoginData?) {
        val fos: FileOutputStream = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        val outs = ObjectOutputStream(fos)
        outs.writeObject(data)
        outs.close()
        fos.close()
    }

    fun disconnect() = save(null)
}