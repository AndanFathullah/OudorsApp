package com.dicoding.oudorsapp.ui.games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.oudorsapp.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var activityGameBinding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityGameBinding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(activityGameBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.title = "Games"

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}