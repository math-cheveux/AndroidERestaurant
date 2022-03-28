package fr.isen.cheveux.androiderestaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.cheveux.androiderestaurant.R
import fr.isen.cheveux.androiderestaurant.databinding.CartRowItemBinding
import fr.isen.cheveux.androiderestaurant.model.ApiData
import fr.isen.cheveux.androiderestaurant.model.CartData
import fr.isen.cheveux.androiderestaurant.model.PriceData
import fr.isen.cheveux.androiderestaurant.service.CartService
import fr.isen.cheveux.androiderestaurant.service.DataService

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
        holder.bind(CartService(holder.itemView.context).getPrices(cartData, apiData).toList()[position])
    }

    inner class ViewHolder(private val binding: CartRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(priceDataIntPair: Pair<PriceData, Int>) {
            val price = priceDataIntPair.first
            val quantity = priceDataIntPair.second
            val plate = DataService(apiData).getPlate(price)

            if (plate?.images?.isNotEmpty() == true && plate.images[0].isNotEmpty()) {
                Picasso.get().load(plate.images[0]).fit().into(binding.cartItemImage)
            }
            binding.cartItemName.text = plate?.frName ?: itemView.resources.getString(R.string.default_unknown)
            binding.cartItemSize.text = price.size
            binding.cartItemCount.text = quantity.toString()
            binding.cartItemAmount.text =
                ((price.price * quantity).toString() + itemView.resources.getString(R.string.currency_euro))

            binding.deleteCartItemButton.setOnClickListener {
                CartService(itemView.context).removeItem(cartData, price)
                CartService(itemView.context).save(cartData)
                notifyDataSetChanged()
            }
        }
    }
}
