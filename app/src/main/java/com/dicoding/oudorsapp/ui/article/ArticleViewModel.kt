package com.dicoding.oudorsapp.ui.article

import androidx.lifecycle.ViewModel
import com.dicoding.oudorsapp.data.Article
import com.dicoding.oudorsapp.data.ArticleEntity

class ArticleViewModel: ViewModel() {
    fun getArticle(): List<ArticleEntity> = Article.generateArticle()
}