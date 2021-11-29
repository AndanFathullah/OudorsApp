package com.dicoding.oudorsapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.databinding.FragmentHomeBinding
import com.dicoding.oudorsapp.ui.article.ArticleActivity
import com.dicoding.oudorsapp.ui.consultation.ConsulListActivity
import com.dicoding.oudorsapp.ui.consultation.ConsultationActivity
import com.dicoding.oudorsapp.ui.games.GameActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return fragmentHomeBinding.root
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
                fragmentHomeBinding.userName.text = snapshot.child("username").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        buttonArticle()
        buttonConsul()
        buttonGames()
    }

    private fun buttonConsul(){
        fragmentHomeBinding.btnConsultation.setOnClickListener{
            val intent = Intent(this@HomeFragment.context, ConsulListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buttonArticle(){
        fragmentHomeBinding.btnArticle.setOnClickListener{
            val intent = Intent(this@HomeFragment.context, ArticleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buttonGames(){
        fragmentHomeBinding.btnGames.setOnClickListener{
            val intent = Intent(this@HomeFragment.context, GameActivity::class.java)
            startActivity(intent)
        }
    }

}