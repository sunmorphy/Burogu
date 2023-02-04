package com.andikas.burogu.ui.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.andikas.burogu.R
import com.andikas.burogu.data.local.ArticleDatabase
import com.andikas.burogu.data.model.Article
import com.andikas.burogu.databinding.ActivityDetailBinding
import com.andikas.burogu.ui.edit.EditActivity
import com.andikas.burogu.ui.home.HomeActivity
import com.andikas.burogu.utils.Extensions.Poppins
import com.andikas.burogu.utils.Extensions.Roboto
import com.andikas.burogu.utils.Extensions.hideActionBar
import com.andikas.burogu.utils.Extensions.navigateTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity(), DetailView {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var presenter: DetailPresenterImp
    private var database: ArticleDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        hideActionBar()

        database = ArticleDatabase.getInstance(this)
        presenter = DetailPresenterImp(database!!, this, Dispatchers.Main)
        val extraArticle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("extraArticle", Article::class.java)!!
        } else {
            intent.getParcelableExtra("extraArticle")!!
        }
        presenter.loadArticleDetails(extraArticle)
    }

    @SuppressLint("SetTextI18n")
    override fun showDeleteAlert(article: Article) {
        val builder = AlertDialog.Builder(this, R.style.CustomDialog)
            .setTitle("Hapus")
            .setMessage("Anda yakin ingin menghapus artikel?")
            .setCancelable(true)
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch(Dispatchers.Default) {
                    database?.articleDao()?.deleteArticle(article)
                    withContext(Dispatchers.Main) {
                        navigateTo(HomeActivity::class.java)
                        finish()
                    }
                }
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    override fun loadDetails(article: Article) {
        setContent {
            MaterialTheme {
                ArticleDetailContent(
                    imagePath = article.imagePath,
                    title = article.title,
                    content = article.content,
                    createdDate = article.createdDate,
                    author = article.author,
                    isBookmarked = article.isBookmarked,
                    onBack = {
                        onBackPressedDispatcher.onBackPressed()
                    },
                    onBookmarkClick = {
                        presenter.setArticleBookmark(
                            article,
                            !article.isBookmarked
                        )
                    },
                    onEditClick = {
                        navigateTo(
                            EditActivity::class.java,
                            "extraArticle",
                            article
                        )
                    },
                    onDeleteClick = {
                        presenter.showAlert(article)
                    }
                )
            }
        }
    }
}

@Composable
fun ArticleDetailContent(
    imagePath: String?,
    title: String?,
    content: String?,
    createdDate: String?,
    author: String?,
    isBookmarked: Boolean,
    onBack: () -> Unit,
    onBookmarkClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White)
            .padding(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 160.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Button(
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primaryDark)),
                    onClick = { onBack() },
                    modifier = Modifier,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIos,
                        tint = Color.White,
                        contentDescription = "Kembali",
                    )
                }
                IconButton(
                    onClick = {
                        onBookmarkClick()
                    },
                    modifier = Modifier
                        .size(24.dp)
                ) {
                    androidx.compose.material.Icon(
                        imageVector = if (isBookmarked) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                        tint = colorResource(id = R.color.primaryDark),
                        contentDescription = "Tambahkan ke bookmark"
                    )
                }
            }
            Text(
                text = title ?: "",
                fontSize = 24.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.primaryDark),
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
            )
            Text(
                text = author ?: "",
                fontSize = 14.sp,
                fontFamily = Roboto,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.neutralLight),
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Text(
                text = createdDate ?: "",
                fontSize = 14.sp,
                fontFamily = Roboto,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.neutralLight),
                modifier = Modifier
            )
            AsyncImage(
                model = imagePath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .padding(top = 30.dp)
            )
            Text(
                text = content ?: "",
                fontSize = 16.sp,
                fontFamily = Roboto,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.neutralDark),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 32.dp),
        ) {
            FloatingActionButton(
                containerColor = colorResource(id = R.color.primaryDark),
                contentColor = Color.White,
                onClick = { onEditClick() },
                modifier = Modifier,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "Sunting"
                )
            }
            FloatingActionButton(
                containerColor = colorResource(id = R.color.primaryDark),
                contentColor = Color.White,
                onClick = { onDeleteClick() },
                modifier = Modifier
                    .padding(top = 12.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Hapus"
                )
            }
        }
    }
}

@Preview(
    device = Devices.PIXEL_4_XL,
    showSystemUi = false,
)
@Composable
fun ArticleDetailContentPreview() {
    MaterialTheme {
        ArticleDetailContent(
            "",
            "title",
            "content",
            "created date",
            "author",
            true,
            {},
            {},
            {},
            {}
        )
    }
}