package com.dicoding.oudorsapp.ui.consultation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.data.ConsulEntity
import com.dicoding.oudorsapp.databinding.ActivityConsultationBinding
import java.net.URLEncoder

class ConsultationActivity : AppCompatActivity() {
    private lateinit var activityConsultationBinding: ActivityConsultationBinding

    companion object{
        private const val MSG = "Hello"
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityConsultationBinding = ActivityConsultationBinding.inflate(layoutInflater)
        setContentView(activityConsultationBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.title = "Consultation"

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ConsultationViewModel::class.java]
        val extras = intent.extras

        if (extras != null) {
            val id = extras.getString(EXTRA_ID)
            val phone = extras.getString(EXTRA_PHONE)
            if (id != null) {
                viewModel.selectedConsul(id)
                consulInfo(viewModel.getConsultant())
                activityConsultationBinding.btnWhatsapp.setOnClickListener{
                    val message = activityConsultationBinding.message.text.toString()
                    if(message == ""){
                        sendMessage(MSG, phone)

                    }else{
                        sendMessage(message, phone)
                    }
                }

            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun sendMessage(message:String, number: String?){
        val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse("https://api.whatsapp.com/send?phone=$number&text=$message") )
        startActivity(intent)
    }

    private fun consulInfo(consulEntity: ConsulEntity){
        with(activityConsultationBinding){
            consultantName.text = consulEntity.name
            consultantTitle.text = consulEntity.title
        }
    }


}