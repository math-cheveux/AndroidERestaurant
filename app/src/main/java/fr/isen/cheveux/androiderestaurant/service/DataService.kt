package fr.isen.cheveux.androiderestaurant.service

import fr.isen.cheveux.androiderestaurant.model.ApiData
import fr.isen.cheveux.androiderestaurant.model.PlateData
import fr.isen.cheveux.androiderestaurant.model.PriceData

/**
 * @author math-cheveux
 */
class DataService(private val apiData: ApiData) {
    fun getPlate(priceData: PriceData): PlateData? {
        for (categoryData in apiData.data) {
            for (plateData in categoryData.items) {
                if (plateData.id == priceData.pizzaId) {
                    return plateData
                }
            }
        }
        return null
    }

    fun getPriceById(id: Int): PriceData? {
        for (categoryData in apiData.data) {
            for (plateData in categoryData.items) {
                for (priceData in plateData.prices) {
                    if (priceData.id == id) {
                        return priceData
                    }
                }
            }
        }
        return null
    }
}