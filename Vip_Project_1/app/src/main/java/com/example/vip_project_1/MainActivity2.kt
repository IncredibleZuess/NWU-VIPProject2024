package com.example.vip_project_1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val BacktoLoginbtn = findViewById<Button>(R.id.login_button)
        BacktoLoginbtn.setOnClickListener {
            val Intent = Intent(this , MainActivity::class.java)
            startActivity(Intent)
        }

        val GeneratePinbtn : Button=findViewById(R.id.pin_button)
        val Showpin: TextView=findViewById(R.id.pin1)

        GeneratePinbtn.setOnClickListener {
            Showpin.visibility = View.VISIBLE
        }


    }
}