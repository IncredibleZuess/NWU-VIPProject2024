/**
 * @author Carlo Barnardo
 * @editor Sebastian Klopper
 *
 * The Main Application function calls
 */

package com.example.screentimeapp

import android.app.Application
import com.example.screentimeapp.helper.ResourceHelper
import com.example.screentimeapp.helper.UsageStatsHelper

/**
 * The main loop of the program that initiates the helpers
 */
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ResourceHelper.setup(applicationContext)
        UsageStatsHelper.setup(applicationContext)
    }
}