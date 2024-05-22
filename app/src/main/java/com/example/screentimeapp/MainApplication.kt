package com.example.screentimeapp

import android.app.Application
import com.example.screentimeapp.helper.ResourceHelper
import com.example.screentimeapp.helper.UsageStatsHelper

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ResourceHelper.setup(applicationContext)
        UsageStatsHelper.setup(applicationContext)
    }
}