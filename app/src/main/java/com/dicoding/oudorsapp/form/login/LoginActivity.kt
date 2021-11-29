package com.dicoding.oudorsapp.form.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.dicoding.oudorsapp.MainActivity
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.databinding.ActivityLoginBinding
import com.dicoding.oudorsapp.form.regist.RegistActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser !==null){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        login()
        btnDirectRegist()
    }

    private fun login(){
        binding.buttonLogin.setOnClickListener {
            if (TextUtils.isEmpty(binding.inputUserName.text.toString().trim())){
                binding.inputUserName.error = "Please enter your email"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(binding.inputPassword.text.toString().trim())){
                binding.inputPassword.error = "Please enter your password"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(binding.inputUserName.text.toString().trim(), binding.inputPassword.text.toString().trim())
                .addOnCompleteListener{
                    Log.d("tst", "tst", it.exception)
                    if (it.isSuccessful){
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity, "Login failed, please try again!", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun btnDirectRegist(){
        binding.directRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistActivity::class.java )
            startActivity(intent)
        }
    }
}