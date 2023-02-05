package com.andikas.burogu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.andikas.burogu.R
import com.andikas.burogu.data.model.Article
import com.andikas.burogu.databinding.ItemArticleBinding
import com.andikas.burogu.utils.Extensions.Poppins

class ArticleAdapter(
    private val articles: List<Article>,
    private val onClick: (Article) -> Unit,
    private val onBookmarkClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick, onBookmarkClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = articles.size

    class ViewHolder(
        private var binding: ItemArticleBinding,
        private val onClick: (Article) -> Unit,
        private val onBookmarkClick: (Article) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.composeView.setContent {
                MaterialTheme {
                    ArticleItem(
                        article.id,
                        article.imagePath,
                        article.title,
                        article.content,
                        article.createdDate,
                        article.author,
                        article.isBookmarked,
                        article.isDummy,
                        onClick,
                        onBookmarkClick,
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    id: Long?,
    imagePath: String?,
    title: String?,
    content: String?,
    createdDate: String?,
    author: String?,
    isBookmarked: Boolean,
    isDummy: Boolean,
    onClick: (Article) -> Unit,
    onBookmarkClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    val article = Article(id, imagePath, title, content, createdDate, author, isBookmarked, isDummy)

    ConstraintLayout(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onClick(article)
            }
    ) {
        val (articleImage, stackImage, titleText, authorText, bookmarkButton) = createRefs()

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath)
                .error(R.drawable.ic_no_image)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .constrainAs(articleImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        AsyncImage(
            model = R.drawable.bg_stack,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(stackImage) {
                    start.linkTo(articleImage.start)
                    top.linkTo(articleImage.top)
                    end.linkTo(articleImage.end)
                    bottom.linkTo(articleImage.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Text(
            text = title ?: "",
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            maxLines = 2,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.primaryLight),
            modifier = Modifier
                .constrainAs(titleText) {
                    start.linkTo(articleImage.start, margin = 16.dp)
                    end.linkTo(bookmarkButton.start, margin = 30.dp)
                    bottom.linkTo(authorText.top)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = author ?: "",
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            maxLines = 1,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.primaryLight),
            modifier = Modifier
                .constrainAs(authorText) {
                    start.linkTo(titleText.start)
                    end.linkTo(titleText.end)
                    bottom.linkTo(bookmarkButton.bottom)
                    width = Dimension.fillToConstraints
                }
        )

        IconButton(
            onClick = {
                onBookmarkClick(article)
            },
            modifier = Modifier
                .size(24.dp)
                .constrainAs(bookmarkButton) {
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                }
        ) {
            Icon(
                imageVector = if (isBookmarked) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                tint = colorResource(id = R.color.primaryLight),
                contentDescription = "Tambahkan ke bookmark"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
    MaterialTheme {
        ArticleItem(
            id = 1,
            imagePath = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:bb2a73c0f97d1d5c4292af18d2334e5020221025171536.jpeg",
            title = "lorem ipsum dolor sit amet",
            content = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:bb2a73c0f97d1d5c4292af18d2334e5020221025171536.jpeghttps://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:bb2a73c0f97d1d5c4292af18d2334e5020221025171536.jpeghttps://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/academy/dos:bb2a73c0f97d1d5c4292af18d2334e5020221025171536.jpeg",
            createdDate = "Daw",
            author = "dawdawdaw",
            isBookmarked = false,
            isDummy = false,
            onClick = { },
            onBookmarkClick = { }
        )
    }
}