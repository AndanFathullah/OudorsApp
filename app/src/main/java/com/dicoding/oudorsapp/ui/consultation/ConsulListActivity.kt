package com.dicoding.oudorsapp.ui.consultation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.oudorsapp.adapter.ConsultantAdapter
import com.dicoding.oudorsapp.databinding.ActivityConsulListBinding

class ConsulListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsulListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsulListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.title = "Consultation"

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ConsulListViewModel::class.java]
        val consul = viewModel.getConsul()

        val consulAdapter = ConsultantAdapter()
        consulAdapter.setConsul(consul)
        with(binding.rvConsul){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = consulAdapter
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}