package com.dicoding.oudorsapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.oudorsapp.MainActivity
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.databinding.ActivityForthBinding
import com.dicoding.oudorsapp.form.login.LoginActivity

class ForthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnOke()
    }

    private fun btnOke(){
        binding.buttonOke.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}