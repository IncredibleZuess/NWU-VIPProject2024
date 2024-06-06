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
        AboutMe_text.text = "# Sign Up Procedure\n" +
                "-> Open app\n" +
                "-> Go to the sign up page\n" +
                "-> Enter you Name (This will the name displayed on the app)\n" +
                "-> Generate your \"Parent\" Pin (This is 000000 fo now, future versions will contain proper pin generation and storage)\n" +
                "-> Proceed to complete Sign-Up\n" +
                "\n" +
                "# Gameplay to gain more time\n" +
                "-> Start at the home page\n" +
                "-> Navigate to the \"Games\" page\n" +
                "-> Select the game category of choice\n" +
                "-> Select the Difficulty of choice\n" +
                "-> You will be awarded time if you successfully complete the game\n" +
                "\n" +
                "# How the app works\n" +
                "-> The \"Parent\" will have the ability to set up to 5 apps they wish to track and manage\n" +
                "-> They can then set the \"Time Limit\" for each app\n" +
                "-> They will receive a notification 5 Min before their time is up so they can save any work they need to\n" +
                "-> After their time is up they will be locked out of the app until they \"Earn\" more time or at Midnight (00:00 local time)\n" +
                "\n" +
                "# Settings\n" +
                "-> The settings menu is divided into 2 main categories: \"Default\" and \"Parent\"\n" +
                "-> Default:\n" +
                "    -> App cosmetics, such as background, font, .... (Subject to implementation)\n" +
                "    -> About\n" +
                "    -> Notification\n" +
                "-> Parent: \n" +
                "    -> show stats to child\n" +
                "    -> Button to clear all saved data\n" +
                "\n" +
                "# Home Page\n" +
                "-> Welcome message for the user\n" +
                "-> Contains of a bar graph showing app usage for the day\n" +
                "\n" +
                "# Navigation \n" +
                "-> This is a anchored bar to switch between the Home Page, Settings Page and Game Page\n"
        AboutMe_text.textSize = 16f
        lltAboutMe.addView(AboutMe_text)

    }
}