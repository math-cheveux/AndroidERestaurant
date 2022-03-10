package fr.isen.cheveux.androiderestaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.cheveux.androiderestaurant.R
import fr.isen.cheveux.androiderestaurant.databinding.CartRowItemBinding
import fr.isen.cheveux.androiderestaurant.model.ApiData
import fr.isen.cheveux.androiderestaurant.model.CartData
import fr.isen.cheveux.androiderestaurant.model.PriceData

class CartAdapter(
    private val apiData: ApiData,
    private val cartData: CartData
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = cartData.items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cartData.items.toList()[position])
    }

    inner class ViewHolder(private val binding: CartRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(priceDataIntPair: Pair<PriceData, Int>) {
            binding.cartItemName.text = priceDataIntPair.first.getPlate(apiData)?.frName
                ?: itemView.resources.getString(R.string.default_unknown)
            binding.cartItemSize.text = priceDataIntPair.first.size
            binding.cartItemCount.text = priceDataIntPair.second.toString()
            binding.cartItemAmount.text =
                ((priceDataIntPair.first.price * priceDataIntPair.second).toString() + itemView.resources.getString(R.string.currency_euro))

            binding.deleteCartItemButton.setOnClickListener {
                cartData.items = cartData.items.minus(priceDataIntPair.first)
                cartData.save(itemView.context)
                notifyDataSetChanged()
            }
        }
    }
}
