package com.example.bookmarks_data.mapper

import com.example.bookmarks_data.room.BookmarksEntity
import com.example.bookmarks_data.room.CategoryEntity
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_domain.models.Category

internal fun Bookmark.mapToEntity():BookmarksEntity{
    return BookmarksEntity(
        id = this.id,
        tittle = this.tittle,
        url = this.url,
        fileUri = this.fileUri,
        gptDialog = this.gptDialog,
        categoryId = this.categoryId
    )
}
internal fun BookmarksEntity.mapToBookmark():Bookmark{
    return Bookmark(
        id = this.id,
        tittle = this.tittle,
        url = this.url,
        fileUri = this.fileUri,
        gptDialog = this.gptDialog,
        categoryId = this.categoryId
    )
}
internal fun CategoryEntity.mapToCategory():Category{
    return Category(
        id = this.id,
        tittle = this.tittle,
        listOfBookmarks = this.listOfBookmarks
    )
}
internal fun Category.mapToEntity():CategoryEntity{
    return CategoryEntity(
        id = this.id,
        tittle = this.tittle,
        listOfBookmarks = this.listOfBookmarks

    )
}
