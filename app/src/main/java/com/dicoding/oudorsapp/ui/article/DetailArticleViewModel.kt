package com.dicoding.oudorsapp.ui.article

import androidx.lifecycle.ViewModel
import com.dicoding.oudorsapp.data.Article
import com.dicoding.oudorsapp.data.ArticleEntity

class DetailArticleViewModel: ViewModel() {
    private lateinit var id: String

    fun selectedArticle(id: String){
        this.id = id
    }

    fun getArticle(): ArticleEntity{
        lateinit var article: ArticleEntity
        val articleEntity = Article.generateArticle()
        for (articleEntity in articleEntity){
            if (articleEntity.id == id){
                article = articleEntity
            }
        }
        return article
    }
}