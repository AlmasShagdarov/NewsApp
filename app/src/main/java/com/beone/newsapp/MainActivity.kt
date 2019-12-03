package com.beone.newsapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.security.ProviderInstaller

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkForTLS()
    }

    private fun checkForTLS() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {
                ProviderInstaller.installIfNeeded(this)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
