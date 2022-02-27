package com.andikas.burogu.ui.bookmark

import com.andikas.burogu.data.model.Article

interface BookmarkView {

    fun showAllBookmarkedArticles(articles: List<Article>)

    fun refreshBookmarkedArticles()

}