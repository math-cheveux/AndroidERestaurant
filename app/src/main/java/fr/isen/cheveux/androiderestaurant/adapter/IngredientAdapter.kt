package fr.isen.cheveux.androiderestaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.cheveux.androiderestaurant.databinding.IngredientRowItemBinding
import fr.isen.cheveux.androiderestaurant.model.IngredientData

/**
 * @author math-cheveux
 */
class IngredientAdapter(private val dataSet: List<IngredientData>) :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IngredientRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    inner class ViewHolder(private val binding: IngredientRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: IngredientData) {
            binding.itemIngredientName.text = data.frName
        }
    }
}