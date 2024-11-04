package com.aopr.notes_presentation.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.notes_domain.NotesUseCase
import com.aopr.notes_domain.models.Note
import com.aopr.notes_presentation.view_model.events.AllNotesEvent
import com.aopr.shared_domain.Responses
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllNotesViewModel(private val useCase: NotesUseCase) : ViewModel() {

    private val _listOfNotes = mutableStateOf<List<Note>?>(null)
    val listOfNotes: State<List<Note>?> = _listOfNotes

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()


    init {
        onEvent(AllNotesEvent.GetAllNotes)
    }

    sealed class UiEvent {
        data class NavigateToCreateNoteScreen(val id: Int?) : UiEvent()
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


    fun onEvent(event: AllNotesEvent) {
        when (event) {
            AllNotesEvent.GetAllNotes -> {
                viewModelScope.launch {
                    getAllNotes()
                }
            }

            is AllNotesEvent.NavigateToCreateNoteScreen -> {
                viewModelScope.launch {
                    _event.emit(UiEvent.NavigateToCreateNoteScreen(event.id))
                }
            }

            is AllNotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    deleteNote(event.note)
                }
            }
        }
    }

}