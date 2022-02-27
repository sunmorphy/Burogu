package com.andikas.burogu.ui.home

import com.andikas.burogu.data.model.Article

interface HomePresenter {

    fun insertDummyArticles()

    fun loadAllArticles()

    fun setArticleBookmark(article: Article, newState: Boolean)

}