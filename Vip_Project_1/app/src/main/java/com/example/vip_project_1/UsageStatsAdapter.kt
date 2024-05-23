/**
 * @author Carlo Barnardo
 * @edtior Sebastian Klopper
 */

package com.example.vip_project_1

import android.app.usage.UsageStats
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsageStatsAdapter (private val usageStats: List<UsageStats>) : RecyclerView.Adapter<UsageStatsAdapter.UsageStatsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsageStatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.usage_stats_item, parent, false)
        return UsageStatsViewHolder(view)
    }
    override fun onBindViewHolder(holder: UsageStatsViewHolder, position: Int) {
        val usageStat = usageStats[position]
        holder.bind(usageStat)
    }
    override fun getItemCount(): Int {
        return usageStats.size
    }
    class UsageStatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val appNameTextView: TextView = itemView.findViewById(R.id.app_name)
        private val usageTimeTextView: TextView = itemView.findViewById(R.id.usage_time)

        fun bind(usageStats: UsageStats) {
            appNameTextView.text = usageStats.packageName
            usageTimeTextView.text = formatUsageTime(usageStats.totalTimeInForeground)
        }
        private fun formatUsageTime(timeMillis: Long): String {
            val hours = timeMillis / (1000 * 60 * 60)
            val minutes = (timeMillis / (1000 * 60)) % 60
            val seconds = (timeMillis / 1000) % 60
            return String.format("%02d:%02d:%02d", hours, minutes,seconds)
        }
    }

}