package fr.isen.cheveux.androiderestaurant.service

import fr.isen.cheveux.androiderestaurant.model.ApiData
import fr.isen.cheveux.androiderestaurant.model.PlateData
import fr.isen.cheveux.androiderestaurant.model.PriceData

/**
 * @author math-cheveux
 */
class DataService {
    fun getPlate(priceData: PriceData, apiData: ApiData): PlateData? {
        for (categoryData in apiData.data) {
            for (plateData in categoryData.items) {
                if (plateData.id == priceData.pizzaId) {
                    return plateData
                }
            }
        }
        return null
    }
}