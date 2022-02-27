package com.andikas.burogu.ui.detail

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.andikas.burogu.R
import com.andikas.burogu.data.local.ArticleDatabase
import com.andikas.burogu.data.model.Article
import com.andikas.burogu.databinding.ActivityDetailBinding
import com.andikas.burogu.ui.edit.EditActivity
import com.andikas.burogu.ui.home.HomeActivity
import com.andikas.burogu.utils.Extensions.hideActionBar
import com.andikas.burogu.utils.Extensions.navigateTo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity(), DetailView {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var presenter: DetailPresenterImp
    private var database: ArticleDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()

        database = ArticleDatabase.getInstance(this)
        presenter = DetailPresenterImp(database!!, this, Dispatchers.Main)
        val extraArticle = intent.getParcelableExtra<Article>("extraArticle")!!

        presenter.loadArticleDetails(extraArticle)
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.btnBookmark.setOnClickListener {
            presenter.setArticleBookmark(
                extraArticle,
                !extraArticle.isBookmarked
            )
        }
        binding.btnEdit.setOnClickListener {
            navigateTo(
                EditActivity::class.java,
                "extraArticle",
                extraArticle
            )
        }
        binding.btnDelete.setOnClickListener { presenter.showAlert(extraArticle) }
    }

    override fun showDeleteAlert(article: Article) {
        val textDialogTitle = TextView(this)
        textDialogTitle.text = "Hapus"
        textDialogTitle.textSize = 18f
        textDialogTitle.isAllCaps = true
        textDialogTitle.typeface = ResourcesCompat.getFont(this, R.font.poppins_semi_bold)
        textDialogTitle.setTextColor(ContextCompat.getColor(this, R.color.primaryRed))
        textDialogTitle.setPadding(45, 50, 45, 50)

        val builder = AlertDialog.Builder(this, R.style.CustomDialog)
            .setCustomTitle(textDialogTitle)
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
        if (article.isBookmarked) {
            binding.btnBookmark.setImageResource(R.drawable.ic_bookmark)
        } else {
            binding.btnBookmark.setImageResource(R.drawable.ic_unbookmark)
        }

        if (article.isDummy) {
            Glide.with(this)
                .load(article.imagePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_no_image)
                .centerCrop()
                .into(binding.imgArticle)
        } else {
            Glide.with(this)
                .load(article.imagePath)
                .error(R.drawable.ic_no_image)
                .centerCrop()
                .into(binding.imgArticle)
        }

        binding.txtTitle.text = article.title
        binding.txtCreatedDate.text = article.createdDate
        binding.txtAuthor.text = article.author
        binding.txtContent.text = article.content
    }
}