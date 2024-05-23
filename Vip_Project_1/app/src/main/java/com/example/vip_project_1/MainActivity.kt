/**
 * @author Carlo Barnardo
 * @edtior Sebastian Klopper
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    /**
     * Checks if the user has granted the app permission to access their usage stats.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
        val mode = appOps.unsafeCheckOpNoThrow("android:get_usage_stats", android.os.Process.myUid(),packageName)
        return mode == android.app.AppOpsManager.MODE_ALLOWED
    }

    /**
     * Queries the usage stats of the user and displays them in a RecyclerView.
     */
    private fun trackUsage() {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val currentTime = System.currentTimeMillis()
        val stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime- 1000*60*60*24, currentTime)
        if (stats != null) {
            for (usageStats in stats) {
                Log.d("UsageStats", "Package: ${usageStats.packageName}, Total Time: ${usageStats.totalTimeInForeground}")
            }
        }
    }

    /**
     * Sets up the RecyclerView with the user's usage stats.
     */
    private fun setupRecyclerView(usageStats: List<UsageStats>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    /**
     * Called when the activity is created.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.username_input)
        passwordEditText = findViewById(R.id.password_input)

        if (!hasUsageStatsPermission()) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        } else {
            trackUsage()
        }
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
                val intent = Intent(this , home_Activity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
            }
        }
    }
