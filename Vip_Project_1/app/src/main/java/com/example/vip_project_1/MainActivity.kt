package com.example.vip_project_1

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
        val mode = appOps.unsafeCheckOpNoThrow("android:get_usage_stats", android.os.Process.myUid(),packageName)
        return mode == android.app.AppOpsManager.MODE_ALLOWED
    }
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
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
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
            val intent = Intent(this , home_Activity::class.java)
            startActivity(intent)
            }
        }
    }
