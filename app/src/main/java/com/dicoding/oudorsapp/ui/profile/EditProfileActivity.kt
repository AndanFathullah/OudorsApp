package com.dicoding.oudorsapp.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    lateinit var bitmap: Bitmap

    private lateinit var imageUri: Uri

    companion object{
        private const val IMAGE_PICKCODE = 1000
        private const val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.title = "Edit Profile"


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val user = auth.currentUser

        if(user != null){
            if (user.photoUrl != null){
                Glide.with(this)
                    .load(user.photoUrl)
                    .placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.userImage)
            }else{
                Glide.with(this)
                    .load(R.drawable.ic_photo)
                    .into(binding.userImage)
            }

            binding.userEmail.text = user.email

        }


        binding.buttonSave.setOnClickListener {
            val image = when{
                ::imageUri.isInitialized -> imageUri
                user?.photoUrl == null -> Uri.parse("R.drawable.ic_photo")
                else -> user.photoUrl
            }
            UserProfileChangeRequest.Builder()
                .setPhotoUri(image)
                .build().also {
                    user?.updateProfile(it)
                }

            databaseReference = database?.getReference("profile")
            val userName: String = binding.userName.text.toString().trim()
            val userPhone: String = binding.userPhone.text.toString().trim()

            if(TextUtils.isEmpty(userName)){
                binding.userName.error = "Please enter username"
                return@setOnClickListener
            }

            val currentUser = auth.currentUser
            val currentUserDb = databaseReference?.child(currentUser?.uid!!)
            currentUserDb?.child("username")?.setValue(userName)
            currentUserDb?.child("phone")?.setValue(userPhone)?.addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }


        }
        btnChangePhoto()

    }

    private fun btnChangePhoto(){
        Log.d("help", "help")
        binding.changePhoto.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                }else{
                    selectImage()
                }
            }else{
                selectImage()
            }
        })
    }

    private fun selectImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICKCODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    selectImage()
                }else{
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICKCODE){
            val uri: Uri? = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            val imgBitmap = bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap){
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img/${FirebaseAuth.getInstance().currentUser?.uid}")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val image = baos.toByteArray()

        ref.putBytes(image)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener{
                        it.result?.let {
                            imageUri = it
                            binding.userImage.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}