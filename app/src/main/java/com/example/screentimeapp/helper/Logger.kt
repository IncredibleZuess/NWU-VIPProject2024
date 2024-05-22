/**
 * @author Carlo Barnardo
 * @editor Sebastian Klopper
 *
 * Code for the loggers in the app
 */
package com.example.screentimeapp.helper

import android.util.Log
import com.example.screentimeapp.BuildConfig


object Logger{
    /**
     * Debug logger
     */
    fun debug(tag: String, msg:String){
        if(BuildConfig.DEBUG) Log.d(tag, msg)
    }

    /**
     * Error logger
     */
    fun error(tag: String, msg: String){
        Log.e(tag, msg)
    }
}