package com.dicoding.oudorsapp.ui.consultation

import androidx.lifecycle.ViewModel
import com.dicoding.oudorsapp.data.ConsulEntity
import com.dicoding.oudorsapp.data.Consultant

class ConsulListViewModel : ViewModel(){
    fun getConsul(): List<ConsulEntity> = Consultant.generateConsultant()
}