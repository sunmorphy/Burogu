package com.andikas.burogu.ui.edit

import com.andikas.burogu.data.local.ArticleDatabase
import com.andikas.burogu.data.model.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class EditPresenterImp(
    private val database: ArticleDatabase,
    private val view: EditView,
    override val coroutineContext: CoroutineContext
) : EditPresenter, CoroutineScope {
    override fun insertArticle(article: Article) {
        launch(Dispatchers.Default) {
            val result = database.articleDao().insertArticle(article)
            withContext(Dispatchers.Main) {
                if (result != 0L) {
                    view.showMessage("Artikel tersimpan")
                    view.closeScreen()
                } else {
                    view.showMessage("Gagal menyimpan artikel")
                }
            }
        }
    }

    override fun updateArticle(article: Article) {
        launch(Dispatchers.Default) {
            database.articleDao().updateArticle(article)
            withContext(Dispatchers.Main) {
                view.showMessage("Berhasil update artikel")
                view.closeScreen()
            }
        }
    }
}