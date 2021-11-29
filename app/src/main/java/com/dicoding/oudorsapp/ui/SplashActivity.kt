package com.dicoding.oudorsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.form.login.LoginActivity
import com.dicoding.oudorsapp.onboarding.FirstActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, FirstActivity::class.java))

            finish()
        }, SPLASH_TIME_OUT)
    }
}