package com.andikas.burogu.data.local

import androidx.room.*
import com.andikas.burogu.data.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles ORDER BY id DESC")
    fun getAllArticles(): List<Article>

    @Query("SELECT * FROM articles WHERE bookmarked = 1 ORDER BY id DESC")
    fun getBookmarkedArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article): Long

    @Update
    fun updateArticle(article: Article)

    @Delete
    fun deleteArticle(article: Article)

}