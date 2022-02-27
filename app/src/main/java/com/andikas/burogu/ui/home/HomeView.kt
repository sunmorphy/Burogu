package com.andikas.burogu.ui.home

import com.andikas.burogu.data.model.Article

interface HomeView {

    fun showAllArticles(articles: List<Article>)

    fun refreshArticles()

}