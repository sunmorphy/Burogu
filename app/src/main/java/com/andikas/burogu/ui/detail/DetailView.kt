package com.andikas.burogu.ui.detail

import com.andikas.burogu.data.model.Article

interface DetailView {

    fun refreshArticleDetails(article: Article)

}