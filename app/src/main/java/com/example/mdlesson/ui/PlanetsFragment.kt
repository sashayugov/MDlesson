package com.example.mdlesson.ui

import ZoomOutPageTransformer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.mdlesson.R
import com.example.mdlesson.databinding.FragmentPlanetsBinding
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class PlanetsFragment : Fragment() {

    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout = requireActivity().findViewById(R.id.tab_layout)
        viewPager = requireActivity().findViewById(R.id.pager)

        viewPager.apply {
            adapter = ViewPagerAdapter(requireActivity())
            setPageTransformer(ZoomOutPageTransformer())
        }
        setTabs()
    }

    private fun setTabs() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                EARTH -> {
                    getString(R.string.earth_tab_text)
                }
                MARS -> {
                    getString(R.string.mars_tab_text)
                }
                MOON -> {
                    getString(R.string.moon_tab_text)
                }
                else -> {
                    getString(R.string.earth_tab_text)
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EARTH = 0
        private const val MARS = 1
        private const val MOON = 2
    }

}