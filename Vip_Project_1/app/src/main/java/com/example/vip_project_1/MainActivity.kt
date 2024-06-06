/**
 * @author Carlo Barnardo
 * @editor Sebastian Klopper
 */
package com.example.vip_project_1

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ServiceCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    /**
     * Queries the usage stats of the user and displays them in a RecyclerView.
     */
    private fun startService() {
        val context = applicationContext
        context.startService(Intent(context, BlockApp::class.java))
    }


    /**
     * Called when the activity is created.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        startService()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        usernameEditText = findViewById(R.id.username_input)
        passwordEditText = findViewById(R.id.password_input)
        val registerButton = findViewById<Button>(R.id.signup_button)
        registerButton.setOnClickListener{
        val intent = Intent(this , MainActivity2::class.java)
        startActivity(intent)
        }
        val loginBtn = findViewById<Button>(R.id.login_button)
        loginBtn.setOnClickListener {
            val authentication = Authentication()
            val isValid = authentication.validateCredentials(usernameEditText.text.toString(), passwordEditText.text.toString())
            if (isValid) {
                val intent = Intent(this , HomeActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
            }
        }
    }
