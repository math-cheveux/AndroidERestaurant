package fr.isen.cheveux.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Food(val name: String)

/**
 * @author math-cheveux
 */
class FoodAdapter(private val dataSet: Array<Food>, private val listener: (Food) -> Unit)
    : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], listener)
    }

    class ViewHolder(element: View) : RecyclerView.ViewHolder(element) {
        fun bind(food: Food, listener: (Food) -> Unit) = with(itemView) {
            itemView.findViewById<TextView>(R.id.food_name).text = food.name
            setOnClickListener { listener(food) }
        }
    }
}