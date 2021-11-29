package com.dicoding.oudorsapp.ui.consultation

import androidx.lifecycle.ViewModel
import com.dicoding.oudorsapp.data.ConsulEntity
import com.dicoding.oudorsapp.data.Consultant

class ConsultationViewModel: ViewModel() {
    private lateinit var id: String

    fun selectedConsul(id: String){
        this.id = id
    }

    fun getConsultant(): ConsulEntity{
        lateinit var consultant: ConsulEntity
        val consulEntity = Consultant.generateConsultant()
        for (consulEntity in consulEntity){
            if (consulEntity.id == id){
                consultant = consulEntity
            }
        }
        return consultant
    }
}