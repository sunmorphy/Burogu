package com.andikas.burogu.ui.bookmark

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.andikas.burogu.R
import com.andikas.burogu.adapter.ArticleItem
import com.andikas.burogu.data.local.ArticleDatabase
import com.andikas.burogu.data.model.Article
import com.andikas.burogu.ui.components.MainButton
import com.andikas.burogu.ui.detail.DetailActivity
import com.andikas.burogu.utils.Extensions.Poppins
import com.andikas.burogu.utils.Extensions.hideActionBar
import com.andikas.burogu.utils.Extensions.navigateTo
import kotlinx.coroutines.Dispatchers

class BookmarkActivity : AppCompatActivity(), BookmarkView {
    private lateinit var presenter: BookmarkPresenterImp
    private var database: ArticleDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()

        database = ArticleDatabase.getInstance(this)
        presenter = BookmarkPresenterImp(database!!, this, Dispatchers.Main)
    }

    override fun showAllBookmarkedArticles(articles: List<Article>) {
        setContent {
            MaterialTheme {
                BookmarkContent(
                    articles = articles,
                    onBack = {
                        onBackPressedDispatcher.onBackPressed()
                    },
                    onItemClick = {
                        navigateTo(DetailActivity::class.java, "extraArticle", it)
                    },
                    onIBookmarkClick = {
                        presenter.setArticleBookmark(it, !it.isBookmarked)
                    }
                )
            }
        }
    }

    override fun refreshBookmarkedArticles() {
        presenter.loadAllBookmarkedArticles()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadAllBookmarkedArticles()
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun BookmarkContent(
    articles: List<Article>,
    onBack: () -> Unit,
    onItemClick: (Article) -> Unit,
    onIBookmarkClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 10.dp),
            ) {
                MainButton(
                    icon = Icons.Rounded.ArrowBackIos,
                    contentDescription = "Kembali",
                    onClick = onBack,
                    modifier = Modifier
                )
                Text(
                    text = "Bookmark",
                    fontSize = 32.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.primaryDark),
                    letterSpacing = TextUnit(0F, TextUnitType.Sp),
                    modifier = Modifier
                        .padding(start = 24.dp)
                )
            }
        }
        if (articles.isNotEmpty()) {
            items(articles, key = { it.id as Long }) { article ->
                ArticleItem(
                    id = article.id,
                    imagePath = article.imagePath,
                    title = article.title,
                    content = article.content,
                    createdDate = article.createdDate,
                    author = article.author,
                    isBookmarked = article.isBookmarked,
                    isDummy = article.isDummy,
                    onClick = { onItemClick(it) },
                    onBookmarkClick = { onIBookmarkClick(it) },
                    modifier = Modifier
                        .padding(top = 32.dp)
                )
            }
        }
    }
}

@Preview(
    device = Devices.PIXEL_4_XL,
    showSystemUi = false,
    showBackground = true,
)
@Composable
fun BookmarkContentPreview() {
    MaterialTheme {
        BookmarkContent(
            listOf(Article(1)),
            onBack = { },
            {},
            {}
        )
    }
}