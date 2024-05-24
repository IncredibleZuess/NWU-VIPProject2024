package com.example.vip_project_1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.LinearLayout
import android.widget.TextView

class AboutMeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about_me)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        setContentView(R.layout.activity_about_me)

        // Find the LinearLayout by its ID
        val lltAboutMe = findViewById<LinearLayout>(R.id.lltAboutMe)

        val AboutMe_text = TextView(this)
        AboutMe_text.text = "This is my About Me page"
        AboutMe_text.textSize = 16f
        lltAboutMe.addView(AboutMe_text)

    }
}