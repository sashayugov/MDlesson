package com.example.mdlesson.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.mdlesson.R
import com.example.mdlesson.databinding.ActivityMainBinding
import com.example.mdlesson.ui.dailyImageUi.ImageFragment
import com.example.mdlesson.ui.planetsUi.PlanetsFragment
import com.example.mdlesson.ui.settingUi.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MDlesson)
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
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
            )
            replace(R.id.container, fragment)
        }
    }
}