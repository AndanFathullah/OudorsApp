package com.dicoding.oudorsapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.oudorsapp.data.ConsulEntity
import com.dicoding.oudorsapp.databinding.ListConsulBinding
import com.dicoding.oudorsapp.ui.consultation.ConsultationActivity

class ConsultantAdapter: RecyclerView.Adapter<ConsultantAdapter.ConsultantViewHolder>() {
    private var listConsul = ArrayList<ConsulEntity>()

    fun setConsul(consul: List<ConsulEntity>?){
        if (consul==null) return
        this. listConsul.clear()
        this.listConsul.addAll(consul)
    }

    class ConsultantViewHolder(private val binding: ListConsulBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(consul: ConsulEntity){
            with(binding){
                consultantName.text = consul.name
                consultantTitle.text = consul.title
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, ConsultationActivity::class.java)
                    intent.putExtra(ConsultationActivity.EXTRA_PHONE, consul.phone)
                    intent.putExtra(ConsultationActivity.EXTRA_ID, consul.id)
                    itemView.context.startActivities(arrayOf(intent))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultantViewHolder {
        val listConsulBinding = ListConsulBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConsultantViewHolder(listConsulBinding)
    }

    override fun onBindViewHolder(holder: ConsultantViewHolder, position: Int) {
        val consul = listConsul[position]
        holder.bind(consul)
    }

    override fun getItemCount(): Int = listConsul.size

}