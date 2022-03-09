package fr.isen.cheveux.androiderestaurant.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.cheveux.androiderestaurant.R
import fr.isen.cheveux.androiderestaurant.databinding.PriceRowItemBinding
import fr.isen.cheveux.androiderestaurant.model.PriceData

/**
 * @author math-cheveux
 */
class PriceAdapter(private val dataSet: List<PriceData>, private val listener: (PriceData, Int) -> Unit) :
    RecyclerView.Adapter<PriceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceAdapter.ViewHolder {
        val binding = PriceRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceAdapter.ViewHolder, position: Int) {
        holder.bind(dataSet[position], listener)
    }

    override fun getItemCount(): Int = dataSet.size

    inner class ViewHolder(private val binding: PriceRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var count: Int = 0

        fun bind(data: PriceData, listener: (PriceData, Int) -> Unit) {
            with(itemView) {
                binding.itemPriceSize.text = data.size
                binding.itemPriceValue.text = (data.price.toString() + resources.getString(R.string.currency_euro))
                binding.textCount.text = count.toString()
                binding.buttonMinus.setOnClickListener {
                    Log.d("PriceAdapter", "minus")
                    if (count > 0) {
                        count--
                        binding.textCount.text = count.toString()
                        listener(data, count)
                    }
                }
                binding.buttonPlus.setOnClickListener {
                    Log.d("PriceAdapter", "plus")
                    count++
                    binding.textCount.text = count.toString()
                    listener(data, count)
                }
            }
        }
    }
}