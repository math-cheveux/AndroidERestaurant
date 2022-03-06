package fr.isen.cheveux.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.cheveux.androiderestaurant.databinding.TextRowItemBinding
import fr.isen.cheveux.androiderestaurant.model.CategoryData
import fr.isen.cheveux.androiderestaurant.model.PlateData

/**
 * @author math-cheveux
 */
class CategoryAdapter(private val dataSet: CategoryData, private val listener: (PlateData) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val binding = TextRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet.items[position], listener)
    }

    inner class ViewHolder(private val binding: TextRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plate: PlateData, listener: (PlateData) -> Unit) {
            with(itemView) {
                binding.itemFoodName.text = plate.frName
                setOnClickListener { listener(plate) }
            }
        }
    }
}