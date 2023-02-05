package com.andikas.burogu.ui.edit

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.andikas.burogu.R
import com.andikas.burogu.data.IdentifySharedPref
import com.andikas.burogu.data.local.ArticleDatabase
import com.andikas.burogu.data.model.Article
import com.andikas.burogu.ui.components.MainButton
import com.andikas.burogu.ui.components.MainField
import com.andikas.burogu.utils.Extensions
import com.andikas.burogu.utils.Extensions.hideActionBar
import com.andikas.burogu.utils.Extensions.makeToast
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class EditActivity : AppCompatActivity(), EditView {
    private lateinit var sharedPreference: IdentifySharedPref
    private lateinit var presenter: EditPresenterImp
    private var imagePath: String? = null
    private var database: ArticleDatabase? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val selectedImage: Uri = it.data?.data as Uri
                imagePath = selectedImage.toString()
                presenter.setImagePath(imagePath.toString())
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()

        val extraArticle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("extraArticle", Article::class.java)
        } else {
            intent.getParcelableExtra("extraArticle")
        }

        sharedPreference = IdentifySharedPref(this)
        database = ArticleDatabase.getInstance(this)
        presenter = EditPresenterImp(database!!, this, Dispatchers.Main)

        if (extraArticle?.imagePath != null) presenter.setImagePath(extraArticle.imagePath.toString())
        val imagePathText by presenter.imagePath

        setContent {
            MaterialTheme {
                EditArticleContent(
                    title = extraArticle?.title ?: "",
                    content = extraArticle?.content ?: "",
                    imagePath = imagePathText,
                    author = extraArticle?.author ?: sharedPreference.userName,
                    onSelectImage = {
                        presenter.getImage()
                    },
                    onBack = {
                        onBackPressedDispatcher.onBackPressed()
                    },
                    onSubmit = { title, content, author ->
                        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
                        val createdDate = dateFormat.format(Date())
                        if (extraArticle != null) {
                            val article = Article(
                                id = null,
                                imagePath = imagePathText,
                                title = title,
                                content = content,
                                author = author
                            )

                            presenter.updateArticle(article)
                        } else {
                            val article = Article(
                                id = null,
                                imagePath = imagePathText,
                                title = title,
                                content = content,
                                createdDate = createdDate,
                                author = author
                            )
                            presenter.insertArticle(article)
                        }
                    }
                )
            }
        }
    }

    override fun getContentImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT

        getContent.launch(intent)
    }

    override fun submitArticle(isUpdate: Boolean) {
    }

    override fun updateArticle(article: Article?) {
    }

    override fun showMessage(msg: String) {
        makeToast(msg)
    }

    override fun closeScreen() {
        finish()
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun EditArticleContent(
    title: String,
    content: String,
    imagePath: String,
    author: String,
    onSelectImage: () -> Unit,
    onBack: () -> Unit,
    onSubmit: (
        title: String,
        content: String,
        author: String,
    ) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(20.dp),
    ) {
        var titleText by remember { mutableStateOf(title) }
        var contentText by remember { mutableStateOf(content) }
        var authorText by remember { mutableStateOf(author) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            MainButton(
                icon = Icons.Rounded.ArrowBackIos,
                contentDescription = "Kembali",
                onClick = onBack,
            )
            Text(
                text = "Edit",
                fontSize = 24.sp,
                fontFamily = Extensions.Poppins,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.primaryDark),
                letterSpacing = TextUnit(0F, TextUnitType.Sp),
            )
            IconButton(
                onClick = {
                    onSubmit(
                        titleText,
                        contentText,
                        authorText,
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    tint = colorResource(id = R.color.primaryDark),
                    contentDescription = "Simpan"
                )
            }
        }
        MainField(
            placeholder = "Judul Artikel",
            value = titleText,
            onValueChange = { titleText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
        )
        MainField(
            placeholder = "Deskripsi Artikel",
            value = contentText,
            onValueChange = { contentText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            maxLines = 5,
        )
        MainField(
            placeholder = "Gambar Artikel (Opsional)",
            value = imagePath,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .clickable { onSelectImage() },
            trailingIcon = Icons.Rounded.Add,
            enabled = false,
        )
        MainField(
            placeholder = "User",
            value = authorText,
            onValueChange = { authorText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
        )
    }
}

@Preview
@Composable
fun EditArticleContentPreview() {
    MaterialTheme {
        EditArticleContent(
            "",
            "",
            "",
            "",
            {},
            {},
            { _, _, _ -> },
        )
    }
}