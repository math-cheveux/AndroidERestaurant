package fr.isen.cheveux.androiderestaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.cheveux.androiderestaurant.databinding.ImgRowItemBinding

/**
 * @author math-cheveux
 */
class ImageAdapter(private val urls: List<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImgRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(urls[position])
    }

    override fun getItemCount(): Int = urls.size

    inner class ViewHolder(private val binding: ImgRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            Picasso.get().load(url).into(binding.itemImg)
        }
    }
}