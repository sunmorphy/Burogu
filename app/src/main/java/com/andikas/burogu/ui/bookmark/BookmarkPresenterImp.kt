package com.andikas.burogu.ui.bookmark

import com.andikas.burogu.data.local.ArticleDatabase
import com.andikas.burogu.data.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BookmarkPresenterImp(
    private val database: ArticleDatabase,
    private val view: BookmarkView,
    override val coroutineContext: CoroutineContext
) : BookmarkPresenter, CoroutineScope {
    override fun loadAllBookmarkedArticles() {
        launch(Dispatchers.Default) {
            val result = database.articleDao().getBookmarkedArticles()
            kotlinx.coroutines.withContext(Dispatchers.Main) {
                view.showAllBookmarkedArticles(result)
            }
        }
    }

    override fun setArticleBookmark(article: Article, newState: Boolean) {
        launch(Dispatchers.Default) {
            article.isBookmarked = newState
            database.articleDao().updateArticle(article)
            withContext(Dispatchers.Main) {
                view.refreshBookmarkedArticles()
            }
        }
    }
}