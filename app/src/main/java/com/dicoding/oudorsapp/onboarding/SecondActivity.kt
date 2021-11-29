package com.dicoding.oudorsapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.oudorsapp.MainActivity
import com.dicoding.oudorsapp.databinding.ActivitySecondBinding
import com.dicoding.oudorsapp.form.login.LoginActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnNext()
        btnSkip()
    }

    private fun btnNext(){
        binding.buttonNext.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun btnSkip(){
        binding.buttonSkip.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}