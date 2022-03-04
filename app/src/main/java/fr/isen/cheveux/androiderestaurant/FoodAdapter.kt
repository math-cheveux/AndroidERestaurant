package fr.isen.cheveux.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.cheveux.androiderestaurant.databinding.TextRowItemBinding

data class Food(val name: String)

/**
 * @author math-cheveux
 */
class FoodAdapter(private val dataSet: Array<Food>, private val listener: (Food) -> Unit) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val binding = TextRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], listener)
    }

    inner class ViewHolder(private val binding: TextRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food, listener: (Food) -> Unit) {
            with(itemView) {
                binding.itemFoodName.text = food.name
                setOnClickListener { listener(food) }
            }
        }
    }
}