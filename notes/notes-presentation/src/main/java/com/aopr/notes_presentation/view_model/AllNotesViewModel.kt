package com.aopr.notes_presentation.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.aopr.notes_domain.NotesUseCase
import com.aopr.notes_domain.models.Note
import com.aopr.notes_presentation.R
import com.aopr.notes_presentation.view_model.events.allNotesEvents.AllNotesEvent
import com.aopr.notes_presentation.view_model.events.allNotesEvents.AllNotesUiEvent
import com.aopr.shared_domain.Responses
import com.aopr.shared_ui.util.ViewModelKit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllNotesViewModel(private val useCase: NotesUseCase) : ViewModelKit<AllNotesEvent,AllNotesUiEvent>() {

    private val _listOfNotes = mutableStateOf<List<Note>?>(null)
    val listOfNotes: State<List<Note>?> = _listOfNotes

    private val _pinnedNotes = mutableStateListOf<Note>()
    val pinnedNotes: SnapshotStateList<Note> = _pinnedNotes


    private val _event = MutableSharedFlow<AllNotesUiEvent>()
    val event = _event.asSharedFlow()


    init {
        onEvent(AllNotesEvent.GetAllNotes)
    }



    private fun updateNote(note: Note) {
        useCase.updatedNote(note).onEach { result ->
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

    private fun getAllNotes() {
        useCase.getAllNotes().onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    result.data?.collect() { noteList ->
                        _listOfNotes.value = noteList
                    }

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteNote(note: Note) {
        useCase.deleteNote(note).onEach { result ->
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


    override fun onEvent(event: AllNotesEvent) {
        when (event) {
            AllNotesEvent.GetAllNotes -> {
                viewModelScope.launch {
                    getAllNotes()
                }
            }

            is AllNotesEvent.NavigateToCreateNoteScreen -> {
                viewModelScope.launch {
                    _event.emit(AllNotesUiEvent.NavigateToCreateNoteScreen(event.id))
                }
            }

            is AllNotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    deleteNote(event.note)
                }
            }

            is AllNotesEvent.PinNote -> {
                viewModelScope.launch {
                    updateNote(event.note)
                }
            }

            AllNotesEvent.TapNoteCardMessage -> {
              showLongInfoBar(R.string.PressToPinInfoMessage)
            }
        }
    }

}