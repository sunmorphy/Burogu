package com.andikas.burogu.ui.edit

import com.andikas.burogu.data.model.Article

interface EditPresenter {

    fun setImagePath(path: String)

    fun getImage()

    fun insertArticle(article: Article)

    fun updateArticle(article: Article)

}