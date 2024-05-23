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

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsageStatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.usage_stats_item, parent, false)
        return UsageStatsViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: UsageStatsViewHolder, position: Int) {
        val usageStat = usageStats[position]
        holder.bind(usageStat)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int {
        return usageStats.size
    }

    /**
     * ViewHolder for the usage stats item.
     */
    class UsageStatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val appNameTextView: TextView = itemView.findViewById(R.id.app_name)
        private val usageTimeTextView: TextView = itemView.findViewById(R.id.usage_time)

        /**
         * Binds the usage stats data to the view holder.
         */
        fun bind(usageStats: UsageStats) {
            appNameTextView.text = usageStats.packageName
            usageTimeTextView.text = formatUsageTime(usageStats.totalTimeInForeground)
        }

        /**
         * Formats the usage time in hours, minutes, and seconds.
         */
        private fun formatUsageTime(timeMillis: Long): String {
            val hours = timeMillis / (1000 * 60 * 60)
            val minutes = (timeMillis / (1000 * 60)) % 60
            val seconds = (timeMillis / 1000) % 60
            return String.format("%02d:%02d:%02d", hours, minutes,seconds)
        }
    }

}