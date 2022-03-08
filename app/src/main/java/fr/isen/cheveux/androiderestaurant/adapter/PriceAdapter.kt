package fr.isen.cheveux.androiderestaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.cheveux.androiderestaurant.R
import fr.isen.cheveux.androiderestaurant.databinding.PriceRowItemBinding
import fr.isen.cheveux.androiderestaurant.model.PriceData

/**
 * @author math-cheveux
 */
class PriceAdapter(private val dataSet: List<PriceData>) : RecyclerView.Adapter<PriceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceAdapter.ViewHolder {
        val binding = PriceRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceAdapter.ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    inner class ViewHolder(private val binding: PriceRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PriceData) {
            with(itemView) {
                binding.itemPriceSize.text = data.size
                binding.itemPriceValue.text = (data.price.toString() + resources.getString(R.string.currency_euro))
            }
        }
    }
}