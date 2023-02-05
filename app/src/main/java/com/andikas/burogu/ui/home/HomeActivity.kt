package com.andikas.burogu.ui.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material.icons.rounded.ShortText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andikas.burogu.R
import com.andikas.burogu.adapter.ArticleItem
import com.andikas.burogu.data.IdentifySharedPref
import com.andikas.burogu.data.local.ArticleDatabase
import com.andikas.burogu.data.model.Article
import com.andikas.burogu.ui.components.MainField
import com.andikas.burogu.ui.detail.DetailActivity
import com.andikas.burogu.ui.edit.EditActivity
import com.andikas.burogu.ui.profile.ProfileActivity
import com.andikas.burogu.utils.Extensions.Poppins
import com.andikas.burogu.utils.Extensions.hideActionBar
import com.andikas.burogu.utils.Extensions.navigateTo
import kotlinx.coroutines.Dispatchers

class HomeActivity : AppCompatActivity(), HomeView {
    private lateinit var sharedPreference: IdentifySharedPref
    private lateinit var presenter: HomePresenterImp
    private var database: ArticleDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()

        sharedPreference = IdentifySharedPref(this)
        database = ArticleDatabase.getInstance(this)
        presenter = HomePresenterImp(database!!, this, Dispatchers.Main)

        presenter.insertDummyArticles()
    }

    override fun showAllArticles(articles: List<Article>) {
        val query by presenter.query

        setContent {
            MaterialTheme {
                HomeContent(
                    articles = articles,
                    name = sharedPreference.userName,
                    query = query,
                    onQueryChange = presenter::onQueryChange,
                    onProfileClick = {
                        navigateTo(ProfileActivity::class.java)
                    },
                    onItemClick = {
                        navigateTo(DetailActivity::class.java, "extraArticle", it)
                    },
                    onBookmarkClick = {
                        presenter.setArticleBookmark(it, !it.isBookmarked)
                    },
                    onAddClick = {
                        navigateTo(EditActivity::class.java)
                    })
            }
        }
    }

    override fun refreshArticles() {
        presenter.loadAllArticles()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadAllArticles()
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}

@Composable
fun HomeContent(
    articles: List<Article>,
    name: String,
    query: String,
    onQueryChange: (String) -> Unit,
    onProfileClick: () -> Unit,
    onItemClick: (Article) -> Unit,
    onBookmarkClick: (Article) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var setGrid by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Halo $name",
                    fontSize = 22.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.primaryDark),
                    textDecoration = TextDecoration.Underline,
                )
                IconButton(
                    onClick = { onProfileClick() },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PersonOutline,
                        tint = colorResource(id = R.color.primaryDark),
                        contentDescription = "Profil"
                    )
                }
            }
            Text(
                text = "Jelajahi Artikelmu!",
                fontSize = 18.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.primaryDark),
                modifier = Modifier
                    .padding(top = 20.dp),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                MainField(
                    placeholder = "Cari artikel",
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .weight(3f)
                )
                IconButton(
                    onClick = {
                        setGrid = !setGrid
                    },
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Icon(
                        imageVector = if (setGrid) Icons.Rounded.GridView else Icons.Rounded.ShortText,
                        tint = colorResource(id = R.color.primaryDark),
                        contentDescription = null
                    )
                }
            }
            if (setGrid) LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier
                    .padding(top = 32.dp)
            ) {
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
                        onBookmarkClick = { onBookmarkClick(it) },
                        modifier = Modifier
                            .padding(top = 32.dp)
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier
                        .padding(top = 32.dp)
                ) {
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
                            onBookmarkClick = { onBookmarkClick(it) },
                            modifier = Modifier
                                .padding(top = 32.dp)
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            containerColor = colorResource(id = R.color.primaryDark),
            contentColor = colorResource(id = R.color.white),
            onClick = { onAddClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Tambah artikel"
            )
        }
    }
}

@Preview(
    device = Devices.PIXEL_4_XL,
    showSystemUi = false,
    showBackground = true,
)
@Composable
fun HomeContentPreview() {
    MaterialTheme {
        HomeContent(
            listOf(Article(1)),
            "name",
            "",
            {},
            {},
            {},
            {},
            {}
        )
    }
}