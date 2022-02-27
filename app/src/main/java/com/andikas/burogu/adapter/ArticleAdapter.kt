package com.andikas.burogu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andikas.burogu.R
import com.andikas.burogu.data.model.Article
import com.andikas.burogu.databinding.ItemArticleBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ArticleAdapter(
    private val articles: List<Article>,
    private val onClick: (Article) -> Unit,
    private val onBookmarkClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]

        if (article.isDummy) {
            Glide.with(holder.itemView)
                .load(article.imagePath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_no_image)
                .centerCrop()
                .into(holder.binding.imgArticle)
        } else {
            Glide.with(holder.itemView)
                .load(article.imagePath)
                .error(R.drawable.ic_no_image)
                .centerCrop()
                .into(holder.binding.imgArticle)
        }

        if (article.isBookmarked) {
            holder.binding.btnBookmark.setImageResource(R.drawable.ic_bookmark)
        } else {
            holder.binding.btnBookmark.setImageResource(R.drawable.ic_unbookmark)
        }

        holder.binding.txtTitle.text = article.title
        holder.binding.txtAuthor.text = article.author
        holder.binding.btnBookmark.setOnClickListener { onBookmarkClick(article) }
        holder.itemView.setOnClickListener { onClick(article) }
    }

    override fun getItemCount(): Int = articles.size

    inner class ViewHolder(var binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)
}