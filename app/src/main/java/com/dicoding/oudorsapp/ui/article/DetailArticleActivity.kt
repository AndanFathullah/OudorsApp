package com.dicoding.oudorsapp.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.oudorsapp.R
import com.dicoding.oudorsapp.data.ArticleEntity
import com.dicoding.oudorsapp.data.ConsulEntity
import com.dicoding.oudorsapp.databinding.ActivityDetailArticleBinding
import com.dicoding.oudorsapp.ui.consultation.ConsultationActivity
import com.dicoding.oudorsapp.ui.consultation.ConsultationViewModel

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding

    companion object{
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.title = "Article"

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailArticleViewModel::class.java]
        val extras = intent.extras

        if (extras != null) {
            val id = extras.getString(EXTRA_ID)
            if (id != null) {
                viewModel.selectedArticle(id)
                articleInfo(viewModel.getArticle())
            }
        }
    }

    private fun articleInfo(articleEntity: ArticleEntity){
        with(binding){
            articleTitle.text = articleEntity.title
            articleDesc.text = articleEntity.desc
        }
        Glide.with(this)
            .load(articleEntity.banner)
            .apply(RequestOptions().override(3500, 2000))
            .into(binding.articleImage)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}