package com.andikas.burogu.ui.detail

import com.andikas.burogu.data.model.Article

interface DetailPresenter {

    fun loadArticleDetails(article: Article)

    fun setArticleBookmark(article: Article, newState: Boolean)

    fun showAlert(article: Article)

}