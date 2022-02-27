package com.andikas.burogu.ui.bookmark

import com.andikas.burogu.data.model.Article

interface BookmarkPresenter {

    fun loadAllBookmarkedArticles()

    fun setArticleBookmark(article: Article, newState: Boolean)

}