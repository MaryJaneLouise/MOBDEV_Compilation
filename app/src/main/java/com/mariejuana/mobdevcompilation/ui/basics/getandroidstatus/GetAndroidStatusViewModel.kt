package com.mariejuana.mobdevcompilation.ui.basics.getandroidstatus

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.os.Build.VERSION
import androidx.lifecycle.AndroidViewModel

class GetAndroidStatusViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        // Get the device model (e.g., "Samsung Galaxy S21")
        val deviceModel = Build.MODEL

        // Get the device manufacturer (e.g., "Samsung")
        val deviceManufacturer = Build.MANUFACTURER

        // Get the Android version (e.g., "Android 11")
        val androidVersion = "Android " + Build.VERSION.RELEASE

        // Get the device name
        val deviceName = getDeviceName()

        // Get the battery status
        val batteryStatus = getBatteryStatus(application)

        // Create a formatted string with the device information
        val info = "Device: $deviceManufacturer $deviceModel\n" +
                "Android Version: $androidVersion\n" +
                "Device Name: $deviceName\n" +
                "Battery Status: $batteryStatus"

        value = info
    }

    val text: LiveData<String> = _text

    // Function to get the device name
    private fun getDeviceName(): String {
        try {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            if (model.startsWith(manufacturer)) {
                return model.capitalize()
            }
            return manufacturer.capitalize() + " " + model
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "Unknown"
    }

    // Function to get the battery status
    private fun getBatteryStatus(context: Context): String {
        try {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val batteryPercentage = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            return "$batteryPercentage%"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "Unknown"
    }
}
