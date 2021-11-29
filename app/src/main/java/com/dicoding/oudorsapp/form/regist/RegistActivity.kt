package com.dicoding.oudorsapp.form.regist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.dicoding.oudorsapp.MainActivity
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.databinding.ActivityRegistBinding
import com.dicoding.oudorsapp.form.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistBinding

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")


        register()
        btnDirectLogin()
    }
    private fun btnDirectLogin(){
        binding.directLogin.setOnClickListener {
            val intent = Intent(this@RegistActivity, LoginActivity::class.java )
            startActivity(intent)
        }
    }

    private fun register(){
        binding.buttonRegister.setOnClickListener{
            val userName: String = binding.inputUserName.text.toString().trim()
            val userEmail: String = binding.inputEmail.text.toString().trim()
            val userPassword: String = binding.inputPassword.text.toString().trim()

            if(TextUtils.isEmpty(userName)){
                binding.inputUserName.error = "Please enter username"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(userEmail)){
                binding.inputEmail.error = "Please enter Email"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(userPassword)) {
                binding.inputPassword.error = "Please fill the Password"
                return@setOnClickListener
            }else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                binding.inputEmail.error = "Please fill with a valid email"
                return@setOnClickListener
            }else if (userPassword.length < 6) {
                binding.inputPassword.error = "Password have to be 6 character at least"
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener{
                    Log.d("tst", "tst", it.exception)
                    if (it.isSuccessful){
                        val currentUser = auth.currentUser
                        val currentUserDb = databaseReference?.child(currentUser?.uid!!)
                        currentUserDb?.child("username")?.setValue(userName)
                        Toast.makeText(this@RegistActivity, "Registration Success!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@RegistActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }else{
                        Toast.makeText(this@RegistActivity, "Registration failed, please try again!", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}