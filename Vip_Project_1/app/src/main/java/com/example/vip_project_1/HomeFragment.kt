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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    // Initiate the usage stats manager and query the usage stats for the last 24 hours and filter by user installed apps
    private fun displayUsageStats() {
        val usageStatsManager = activity?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val currentTime = System.currentTimeMillis()
        // Query the usage stats for the last 24 hours and filter by user installed apps
        val stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime- 1000*60*60*24, currentTime)
        val userInstalledApps = activity?.packageManager?.queryIntentActivities(Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER), 0)
        // Sort the usage stats by total time in foreground and setup the recycler view
        if (stats != null) {
            val sortedStats = stats.sortedByDescending { it.totalTimeInForeground }
            val filteredStats = sortedStats.filter { userInstalledApps?.any { app -> app.activityInfo.packageName == it.packageName } == true }
            setupRecyclerView(filteredStats)
        }
    }
    // Setup the recycler view with the usage stats
    private fun setupRecyclerView(usageStats: List<UsageStats>) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = UsageStatsAdapter(usageStats)
    }
    // Check if the user has granted the usage stats permission
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun hasUsageStatsPermission(): Boolean {
        val appOps = activity?.getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
        val mode = activity?.packageName?.let {
            appOps.unsafeCheckOpNoThrow("android:get_usage_stats", android.os.Process.myUid(),
                it
            )
        }
        return mode == android.app.AppOpsManager.MODE_ALLOWED
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        // Check if the user has granted the usage stats permission else run the request
        if (!hasUsageStatsPermission()) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        } else {
            displayUsageStats()
        }
        return view
    }
}