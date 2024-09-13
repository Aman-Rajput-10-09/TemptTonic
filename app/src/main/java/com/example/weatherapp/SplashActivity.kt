package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// SplashActivity displays a splash screen before transitioning to MainActivity
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display for the activity
        enableEdgeToEdge()

        // Set the content view to the splash screen layout
        setContentView(R.layout.activity_splash)

        // Adjust padding to handle system bars using window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Delay for 2000 milliseconds (2 seconds) before transitioning to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Create an intent to start MainActivity
            val intent = Intent(this, MainActivity::class.java)
            // Start MainActivity
            startActivity(intent)
            // Close SplashActivity to prevent returning to it
            finish()
        }, 2000) // 2000 milliseconds delay
    }
}
