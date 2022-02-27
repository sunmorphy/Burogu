package com.andikas.burogu.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "articles")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "image_path")
    var imagePath: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "content")
    var content: String? = null,

    @ColumnInfo(name = "created_date")
    var createdDate: String? = null,

    @ColumnInfo(name = "author")
    var author: String? = null,

    @ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean = false,

    @ColumnInfo(name = "is_dummy")
    var isDummy: Boolean = false

) : Parcelable
