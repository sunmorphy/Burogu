package com.andikas.burogu.ui.detail

import com.andikas.burogu.data.local.ArticleDatabase
import com.andikas.burogu.data.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DetailPresenterImp(
    private val database: ArticleDatabase,
    private val view: DetailView,
    override val coroutineContext: CoroutineContext
) : DetailPresenter, CoroutineScope {
    override fun loadArticleDetails(article: Article) {
        view.loadDetails(article)
    }

    override fun setArticleBookmark(article: Article, newState: Boolean) {
        launch(Dispatchers.Default) {
            article.isBookmarked = newState
            database.articleDao().updateArticle(article)
            withContext(Dispatchers.Main) {
                view.loadDetails(article)
            }
        }
    }

    override fun showAlert(article: Article) {
        view.showDeleteAlert(article)
    }
}