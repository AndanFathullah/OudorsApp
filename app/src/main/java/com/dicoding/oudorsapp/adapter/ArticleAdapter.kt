package com.dicoding.oudorsapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.oudorsapp.data.ArticleEntity
import com.dicoding.oudorsapp.databinding.ListArticleBinding
import com.dicoding.oudorsapp.ui.article.DetailArticleActivity
import com.dicoding.oudorsapp.ui.consultation.ConsultationActivity

class ArticleAdapter :RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){
    private var listArticle = ArrayList<ArticleEntity>()

    fun setArticle(article: List<ArticleEntity>?){
        if (article==null) return
        this. listArticle.clear()
        this.listArticle.addAll(article)
    }

    class ArticleViewHolder(private val binding: ListArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleEntity){
            with(binding){
                articleTitle.text = article.title
                articleDesc.text = article.desc
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, DetailArticleActivity::class.java)
                    intent.putExtra(DetailArticleActivity.EXTRA_ID, article.id)
                    itemView.context.startActivities(arrayOf(intent))
                }
                Glide.with(itemView.context)
                    .load(article.banner)
                    .apply(RequestOptions().override(3500, 2000))
                    .into(articlePhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val listArticleBinding = ListArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(listArticleBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = listArticle[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = listArticle.size
}