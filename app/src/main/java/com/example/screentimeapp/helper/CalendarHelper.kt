package com.example.screentimeapp.helper

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object CalendarHelper {
    fun getDate(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(cal.time)
    }

    fun getDateCon(time: Long):String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.getDateInstance(DateFormat.SHORT).format(cal.time)
    }

    fun getTimeCon(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.getDateInstance(DateFormat.AM_PM_FIELD).format(cal.time)
    }
    
    fun getDateLong(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH).format(cal.time)
    }
    fun getMonthDay(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(cal.time)
    }
    fun toReadableFormat(time: Long): String{
        return when {
            time /1000 < 60 -> "${time /  1000} sec"
            time / 60000 < 60 -> "${time / 60000} min"
            else -> "${time / 3600000}h ${(time % 3600000) / 60000}m"
        }
    }
}