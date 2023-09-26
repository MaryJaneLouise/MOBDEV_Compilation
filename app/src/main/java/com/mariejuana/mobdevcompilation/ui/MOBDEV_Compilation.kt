package com.mariejuana.mobdevcompilation.ui

import android.app.Application
import com.google.android.material.color.DynamicColors

class MOBDEV_Compilation : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}