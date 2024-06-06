/**
 * @author Carlo Barnardo
 * @editor Sebastian Klopper
 */

package com.example.vip_project_1

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private fun displayUsageStats() {

        val usageStatsManager = activity?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val currentTime = System.currentTimeMillis()
        val stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime- 1000*60*60*24, currentTime)
        if (stats != null) {
            val sortedStats = stats.sortedByDescending { it.totalTimeInForeground }
            setupRecyclerView(sortedStats)
        }
    }
    private fun setupRecyclerView(usageStats: List<UsageStats>) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = UsageStatsAdapter(usageStats)
    }
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
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val sharedPreferencesData = activity?.getSharedPreferences("ShowData", Context.MODE_PRIVATE)
        val showData = sharedPreferencesData?.getBoolean("ShowData", false)
        if (showData == true) {
            if (!hasUsageStatsPermission()) {
                startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
            } else {
                displayUsageStats()
            }
        }
        // Register broadcast receiver to listen for SHOW_DATA_CHANGED intent
        val filter = IntentFilter("com.example.vip_project_1.SHOW_DATA_CHANGED")
        activity?.registerReceiver(dataChangeReceiver, filter)



        return view
    }
    // BroadcastReceiver to handle data change events
    private val dataChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.example.vip_project_1.SHOW_DATA_CHANGED") {
                val sharedPreferencesData = activity?.getSharedPreferences("ShowData", Context.MODE_PRIVATE)
                val showData = sharedPreferencesData?.getBoolean("ShowData", false) ?: false
                if (showData) {
                    displayUsageStats()
                } else {
                    recyclerView.adapter = null
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Unregister the broadcast receiver when the view is destroyed
        activity?.unregisterReceiver(dataChangeReceiver)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}