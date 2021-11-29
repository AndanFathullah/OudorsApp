package com.dicoding.oudorsapp.ui.profile

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.databinding.FragmentProfileBinding
import com.dicoding.oudorsapp.form.login.LoginActivity
import com.dicoding.oudorsapp.ui.consultation.ConsulListActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment() {

    private lateinit var fragmentProfileBinding: FragmentProfileBinding
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater, container,false)
        return fragmentProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")


        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)
        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                fragmentProfileBinding.userName.text = snapshot.child("username").value.toString()

                if (snapshot.child("phone").value == null){
                    fragmentProfileBinding.userPhone.text = "No phone number inputed"
                }else{
                    fragmentProfileBinding.userPhone.text = snapshot.child("phone").value.toString()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        if(user != null){
            if (user.photoUrl != null){
                Glide.with(this)
                    .load(user.photoUrl)
                    .placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(fragmentProfileBinding.userImage)
                UserProfileChangeRequest.Builder()
                    .setPhotoUri(user.photoUrl)
                    .build().also {
                        user?.updateProfile(it)
                    }
            }else{
                Glide.with(this)
                    .load(R.drawable.ic_photo)
                    .into(fragmentProfileBinding.userImage)
            }


        }

        fragmentProfileBinding.userEmail.text = user?.email

        fragmentProfileBinding.buttonLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this@ProfileFragment.context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        btnEdit()
    }

    private fun btnEdit(){
        fragmentProfileBinding.buttonEdit.setOnClickListener {
            val intent = Intent(this@ProfileFragment.context, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

}