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
import android.widget.Button
import android.widget.TextView


import com.google.android.material.bottomnavigation.BottomNavigationView


class home_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val Welcome : TextView= findViewById(R.id.WelcomeText)

        val bottom_Nav : BottomNavigationView=findViewById(R.id.bottom_nav_bar)
        Handler(Looper.getMainLooper()).postDelayed({
            // Fade out the TextView
            fadeOutTextView(Welcome,bottom_Nav)
        }, 5000) // 5000 milliseconds = 5 seconds
    }
}

private fun fadeOutTextView(textView: TextView , bottomNavigationView: BottomNavigationView ){
    // Create an ObjectAnimator to fade out the TextView
    val fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f)
    fadeOut.duration = 1000 // Duration of the fade out animation in milliseconds

    // Add a listener to change visibility after the animation ends
    fadeOut.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            textView.visibility = View.GONE
            bottomNavigationView.visibility=View.VISIBLE

        }
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })

    // Start the fade out animation
    fadeOut.start()
}
