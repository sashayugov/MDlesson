package com.example.mdlesson.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mdlesson.ui.earth_image.EarthFragment
import com.example.mdlesson.ui.mars_image.MarsFragment
import com.example.mdlesson.ui.moon_image.MoonFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), MoonFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH_FRAGMENT]
            1 -> fragments[MARS_FRAGMENT]
            2 -> fragments[MOON_FRAGMENT]
            else -> fragments[EARTH_FRAGMENT]
        }
    }

    companion object {
        private const val EARTH_FRAGMENT = 0
        private const val MARS_FRAGMENT = 1
        private const val MOON_FRAGMENT = 2
    }
}