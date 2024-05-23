package com.example.vip_project_1

import android.animation.Animator
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.animation.ObjectAnimator

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment


import com.google.android.material.bottomnavigation.BottomNavigationView


class home_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        replaceFragment(HomeFragment())
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_bar -> replaceFragment(HomeFragment())
                R.id.games_bar -> replaceFragment(GamesFragment())
                R.id.profile_bar -> replaceFragment(ProfileFragment())

            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()

    }
}

