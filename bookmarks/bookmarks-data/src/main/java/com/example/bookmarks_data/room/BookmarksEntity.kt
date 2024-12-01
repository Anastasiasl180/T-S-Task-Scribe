package com.example.bookmarks_data.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(
    tableName = "bookmarks", foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,

            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ], indices = [Index(value = ["categoryId"])]
)
data class BookmarksEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tittle: String,
    val url: String? = null,
    val fileUri: String? = null,
    val gptDialog: String? = null,
    val categoryId: Int? = null
)

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tittle: String,
    val listOfBookmarks: List<BookmarksEntity>? = null
)

data class CategoryWithBookMarks(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val bookmarks: List<BookmarksEntity>
)
class Converters {
    @TypeConverter
    fun fromListOfBookmarks(value: List<BookmarksEntity>?): String? {
        return value?.let {
            Gson().toJson(it)
        }
    }

    @TypeConverter
    fun toListOfBookmarks(value: String?): List<BookmarksEntity>? {
        return value?.let {
            val type = object : TypeToken<List<BookmarksEntity>>() {}.type
            Gson().fromJson(it, type)
        }
    }
}


