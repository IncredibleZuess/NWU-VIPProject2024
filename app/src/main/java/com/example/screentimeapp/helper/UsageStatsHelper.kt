package com.example.screentimeapp.helper

import android.app.usage.UsageEvents
import android.app.usage.UsageEvents.Event.ACTIVITY_PAUSED
import android.app.usage.UsageEvents.Event.ACTIVITY_RESUMED
import android.app.usage.UsageEvents.Event.MOVE_TO_FOREGROUND
import android.app.usage.UsageStatsManager
import android.content.Context
import com.example.screentimeapp.model.UsageRecord
import java.lang.ref.WeakReference

object UsageStatsHelper {
    data class Result(val records: List<UsageRecord> = listOf(), val lastEndTime: Long = 0, val foregroundPackageName: String? = null)

    private var context : WeakReference<Context>? = null
    fun setup(c: Context){
        context = WeakReference(c)
    }

    const val HOUR_24 = 1000 * 60 * 60 * 24L
    private var mLastForegroundEvent: String? = null
    private var mLastTimeStamp: Long = 0

    fun getLatestEvent(usageStatsManager: UsageStatsManager,startTime: Long, endTime: Long): Result {
        val records = arrayListOf<UsageRecord>()
        val usageEvents = usageStatsManager.queryEvents(startTime,endTime)
        val event = UsageEvents.Event()
        var lastEndTime: Long =0

        while(usageEvents.hasNextEvent()){
            usageEvents.getNextEvent(event)
            val packageName = event.packageName
            val timeStamp = event.timeStamp
            val eventType = event.eventType

            if (eventType == ACTIVITY_PAUSED && mLastForegroundEvent == packageName && timeStamp > mLastTimeStamp){
                mLastForegroundEvent?.let {
                    recordUsage(it, mLastTimeStamp,timeStamp- mLastTimeStamp)
                    records.add(UsageRecord(packageName, mLastTimeStamp,timeStamp- mLastTimeStamp))
                }
                mLastForegroundEvent = null
                lastEndTime = timeStamp
            } else if (eventType == ACTIVITY_RESUMED){
                mLastForegroundEvent = packageName
                mLastTimeStamp = timeStamp
            }
        }
        return Result(records,lastEndTime, mLastForegroundEvent)
    }
    private fun recordUsage(packageName: String, startTime: Long, duration: Long){
        val context = context?.get() ?: return
        Logger.d("recordUsage", "$packageName from ${CalendarHelper.getDate(startTime)} - ${duration/1000}s")
        val filename = CalendarHelper.getDateCon(startTime)
        val path = File(context.filesDir.path+ "/" + filename)

    }
}