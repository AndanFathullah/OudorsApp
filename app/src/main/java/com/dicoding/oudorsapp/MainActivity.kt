package com.dicoding.oudorsapp

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dicoding.oudorsapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    private lateinit var profileCircleImageView: CircleImageView
    private lateinit var userNameView: TextView
    private lateinit var userEmailView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)

        userNameView = navView.getHeaderView(0).findViewById(R.id.userName)

        userEmailView = navView.getHeaderView(0).findViewById(R.id.userEmail)
        userEmailView.text = user?.email

        userReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userNameView.text = snapshot.child("username").value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        profileCircleImageView = navView.getHeaderView(0).findViewById(R.id.userImage)
        if(user != null){
            if (user.photoUrl != null){
                Glide.with(this)
                    .load(user.photoUrl)
                    .placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(profileCircleImageView)
                UserProfileChangeRequest.Builder()
                    .setPhotoUri(user.photoUrl)
                    .build().also {
                        user?.updateProfile(it)
                    }
            }else{
                Glide.with(this)
                    .load(R.drawable.ic_photo)
                    .placeholder(R.drawable.ic_photo)
                    .into(profileCircleImageView)
            }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}