package com.andikas.burogu.ui.detail

import com.andikas.burogu.data.model.Article

interface DetailView {

    fun loadDetails(article: Article)

    fun showDeleteAlert(article: Article)

}