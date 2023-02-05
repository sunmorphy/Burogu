package com.andikas.burogu.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.andikas.burogu.data.local.ArticleDatabase
import com.andikas.burogu.data.model.Article
import com.andikas.burogu.utils.DataDummy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class HomePresenterImp(
    private val database: ArticleDatabase,
    private val view: HomeView,
    override val coroutineContext: CoroutineContext
) : HomePresenter, CoroutineScope {

    private var result = listOf<Article>()

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    override fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        launch(Dispatchers.Default) {
            result = database.articleDao().findArticles(_query.value)
            withContext(Dispatchers.Main) {
                view.showAllArticles(result)
            }
        }
    }

    override fun insertDummyArticles() {
        launch(Dispatchers.Default) {
            val articles = database.articleDao().getAllArticles()
            if (articles.isEmpty()) {
                database.articleDao().insertArticle(DataDummy.generateDummyArticles()[0])
                database.articleDao().insertArticle(DataDummy.generateDummyArticles()[1])
            }
            withContext(Dispatchers.Main) {
                view.refreshArticles()
            }
        }
    }

    override fun loadAllArticles() {
        launch(Dispatchers.Default) {
            if (_query.value.isEmpty()) result = database.articleDao().getAllArticles()
            withContext(Dispatchers.Main) {
                view.showAllArticles(result)
            }
        }
    }

    override fun setArticleBookmark(article: Article, newState: Boolean) {
        launch(Dispatchers.Default) {
            article.isBookmarked = newState
            database.articleDao().updateArticle(article)
            withContext(Dispatchers.Main) {
                view.refreshArticles()
            }
        }
    }
}