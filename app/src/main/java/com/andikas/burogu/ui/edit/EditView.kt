package com.andikas.burogu.ui.edit

import com.andikas.burogu.data.model.Article

interface EditView {

    fun getContentImage()

    fun submitArticle(isUpdate: Boolean)

    fun updateArticle(article: Article?)

    fun showMessage(msg: String)

    fun closeScreen()

}