package fr.isen.cheveux.androiderestaurant.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.isen.cheveux.androiderestaurant.ImageFragment

/**
 * @author math-cheveux
 */
class ImageFragmentStateAdapter(private val urls: List<String>, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return ImageFragment.newInstance(urls[position])
    }

    override fun getItemCount(): Int = urls.size
}