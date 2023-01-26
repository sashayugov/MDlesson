package com.example.mdlesson.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mdlesson.R
import com.example.mdlesson.databinding.ActivityMainBinding
import com.example.mdlesson.ui.daily_image.ImageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNavigation
        setBottomNavigationMenu()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ImageFragment())
                .commit()
        }
    }

    private fun setBottomNavigationMenu() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    doReplaceFragment(ImageFragment())
                    true
                }
                R.id.planets -> {
                    doReplaceFragment(PlanetsFragment())
                    true
                }
                R.id.settings -> {
                    doReplaceFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.setOnItemReselectedListener {}
    }

    private fun <T : Fragment> doReplaceFragment(fragment: T) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}