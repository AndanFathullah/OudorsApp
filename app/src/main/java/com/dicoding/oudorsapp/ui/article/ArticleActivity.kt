package com.dicoding.oudorsapp.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.oudorsapp.adapter.ArticleAdapter
import com.dicoding.oudorsapp.adapter.ConsultantAdapter
import com.dicoding.oudorsapp.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {
    private lateinit var articleBinding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleBinding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(articleBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.title = "Article"

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ArticleViewModel::class.java]
        val article = viewModel.getArticle()

        val articleAdapter = ArticleAdapter()
        articleAdapter.setArticle(article)
        with(articleBinding.rvArticle){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = articleAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}