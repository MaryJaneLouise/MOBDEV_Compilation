package com.mariejuana.mobdevcompilation.ui.activities.randomquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mariejuana.mobdevcompilation.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, MainFragment()).commit()
    }
}