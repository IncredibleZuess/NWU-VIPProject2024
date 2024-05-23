package com.example.vip_project_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


            val Registerbtn = findViewById<Button>(R.id.signup_button)
        Registerbtn.setOnClickListener{
            val Intent = Intent(this , MainActivity2::class.java)
            startActivity(Intent)}


            val Loginbtn = findViewById<Button>(R.id.login_button)
            Loginbtn.setOnClickListener {
                val Intent = Intent(this , home_Activity::class.java)
                startActivity(Intent)
            }




        }
    }
