package com.andikas.burogu.ui.bookmark

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.andikas.burogu.adapter.ArticleAdapter
import com.andikas.burogu.data.local.ArticleDatabase
import com.andikas.burogu.data.model.Article
import com.andikas.burogu.databinding.ActivityBookmarkBinding
import com.andikas.burogu.ui.detail.DetailActivity
import com.andikas.burogu.utils.Extensions.hideActionBar
import com.andikas.burogu.utils.Extensions.navigateTo
import kotlinx.coroutines.Dispatchers

class BookmarkActivity : AppCompatActivity(), BookmarkView {
    private lateinit var binding: ActivityBookmarkBinding
    private lateinit var presenter: BookmarkPresenterImp
    private var database: ArticleDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()

        database = ArticleDatabase.getInstance(this)
        presenter = BookmarkPresenterImp(database!!, this, Dispatchers.Main)

        binding.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun configRecycler(mListArticles: List<Article>) {
        val articleAdapter = ArticleAdapter(mListArticles, {
            navigateTo(DetailActivity::class.java, "extraArticle", it)
        }, {
            presenter.setArticleBookmark(it, !it.isBookmarked)
        })

        binding.rvBookmark.apply {
            layoutManager = GridLayoutManager(this@BookmarkActivity, 2)
            setHasFixedSize(true)
            adapter = articleAdapter
        }
    }

    override fun showAllBookmarkedArticles(articles: List<Article>) {
        configRecycler(articles)
    }

    override fun refreshBookmarkedArticles() {
        presenter.loadAllBookmarkedArticles()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadAllBookmarkedArticles()
    }
}