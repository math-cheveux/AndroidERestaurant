package fr.isen.cheveux.androiderestaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.cheveux.androiderestaurant.R
import fr.isen.cheveux.androiderestaurant.databinding.FoodRowItemBinding
import fr.isen.cheveux.androiderestaurant.model.CategoryData
import fr.isen.cheveux.androiderestaurant.model.PlateData

/**
 * @author math-cheveux
 */
class CategoryAdapter(private val dataSet: CategoryData, private val listener: (PlateData) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val binding = FoodRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet.items[position], listener)
    }

    inner class ViewHolder(private val binding: FoodRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plate: PlateData, listener: (PlateData) -> Unit) {
            with(itemView) {
                binding.itemFoodName.text = plate.frName
                if (plate.prices.isNotEmpty()) {
                    binding.itemFoodPrice.text = (plate.prices
                        .map { priceData -> priceData.price }
                        .reduce { v1, v2 -> minOf(v1, v2) }
                        .toString() + resources.getString(R.string.currency_euro))
                }
                if (plate.images.isNotEmpty() && plate.images[0].isNotEmpty()) {
                    Picasso.get().load(plate.images[0]).fit().into(binding.itemFoodPreview)
                }
                setOnClickListener { listener(plate) }
            }
        }
    }
}