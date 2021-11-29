package com.dicoding.oudorsapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.oudorsapp.MainActivity
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.databinding.ActivityFirstBinding
import com.dicoding.oudorsapp.form.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser !==null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnNext()
        btnSkip()
    }

    private fun btnNext(){
        binding.buttonNext.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
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