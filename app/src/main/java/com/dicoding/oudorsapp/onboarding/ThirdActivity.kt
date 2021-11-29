package com.dicoding.oudorsapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.oudorsapp.MainActivity
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.databinding.ActivityThirdBinding
import com.dicoding.oudorsapp.form.login.LoginActivity

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnNext()
        btnSkip()
    }

    private fun btnNext(){
        binding.buttonNext.setOnClickListener {
            val intent = Intent(this, ForthActivity::class.java)
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