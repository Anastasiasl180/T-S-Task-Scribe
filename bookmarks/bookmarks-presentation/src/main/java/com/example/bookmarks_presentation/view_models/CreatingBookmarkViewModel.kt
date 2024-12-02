package com.example.bookmarks_presentation.view_models

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.shared_domain.Responses
import com.example.bookmarks_domain.interactors.BookmarksUseCase
import com.example.bookmarks_domain.models.Bookmark
import com.example.bookmarks_presentation.events.creating_bookmark_events.CreatingBookmarkEvents
import com.example.bookmarks_presentation.events.main_events.MainEvents
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CreatingBookmarkViewModel(private val bookmarksUseCase: BookmarksUseCase) : ViewModel() {

    private val _tittleOfBookmark = mutableStateOf("")
    val tittle: State<String> = _tittleOfBookmark

    private val _contentUrl = mutableStateOf<String?>(null)
    val contentUrl: State<String?> = _contentUrl

    private val _fileUri = mutableStateOf<String?>(null)
    val fileUri: State<String?> = _fileUri


    private fun createBookmark(bookmark: Bookmark) {
        bookmarksUseCase.createBookmark(bookmark).onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {

                }
            }

        }.launchIn(viewModelScope)
    }


    fun oEvent(events: CreatingBookmarkEvents) {
        when (events) {
            is CreatingBookmarkEvents.GetBookmarkById -> {

            }

            CreatingBookmarkEvents.SaveBookmark -> {
                viewModelScope.launch {
                    val bookmark = Bookmark(
                        tittle = _tittleOfBookmark.value,
                        fileUri = _fileUri.value,
                        url = _contentUrl.value
                    )
                    createBookmark(bookmark)
                }
            }

            is CreatingBookmarkEvents.UpdateTittleOfBookmark -> {
                _tittleOfBookmark.value = events.tittle
            }

            is CreatingBookmarkEvents.AddFile -> {
                _fileUri.value = events.uri
            }
            is CreatingBookmarkEvents.AddLink -> {
                _contentUrl.value = events.url
            }
            CreatingBookmarkEvents.CancelFile -> {
                _fileUri.value = null
            }
            CreatingBookmarkEvents.CancelLink -> {
                _contentUrl.value = null
            }
            is CreatingBookmarkEvents.OpenFile -> TODO()
            is CreatingBookmarkEvents.OpenLink -> TODO()
        }
    }


}
