/**
 * @author Carlo Barnardo
 * @return Foreground service that blocks the app from running in the background
 *
 * This helper class checks the current foreground app that the user is using and blocks it from running if they exceeded their limits.
 */

package com.example.vip_project_1

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class BlockApp : Service() {

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "block_app_channel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel() // Create the channel before starting foreground
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("BlockApp", "onStartCommand")

        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent -> // Replace MainActivity with the appropriate activity
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("BlockApp")
            .setContentText("App Blocking Service is running")
            .setContentIntent(pendingIntent)
            .setOngoing(true) // Important for foreground service notifications
            .build()

        startForeground(NOTIFICATION_ID, notification)

        // The app should start running as a frontend service by now but it does not so this will be implemented when google releases their api
        try {
            val usm = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val time = System.currentTimeMillis()
            val appList =
                usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time)
            for (usageStats in appList) {
                val packageName = usageStats.packageName
                Log.d("BlockApp", "Package name: $packageName")
            }
        } catch (e: Exception){
            Log.d("BlockApp", "Exception: $e")
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "BlockApp Notification Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)
    }
}