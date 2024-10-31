package com.aopr.notes_presentation.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.notes_domain.NotesUseCase
import com.aopr.notes_domain.models.Note
import com.aopr.notes_presentation.view_model.events.NotesEvent
import com.aopr.shared_domain.Responses
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NotesViewModel(private val useCase: NotesUseCase) : ViewModel() {

    private val _tittleOfNote = mutableStateOf("")
    val note: State<String> = _tittleOfNote

    private val _descriptionOfNote = mutableStateOf("")
    val descriptionOfNote: State<String> = _descriptionOfNote

    private val _event = MutableSharedFlow<UiEvents>()
    val uiEvents = _event

    sealed class UiEvents{
        data object NavigateToAllNotesScreen:UiEvents()
    }

    private fun createNote(note: Note) {
        useCase.createNote(note).onEach { result ->
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
        }
    }



    private fun getNoteById(id: Int) {
        useCase.getNoteById(id).onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {

                }
            }

        }
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    deleteNote(event.note)
                }
            }

            NotesEvent.NavigateToAllNotes -> {
              viewModelScope.launch {
                  _event.emit(UiEvents.NavigateToAllNotesScreen)
              }
            }

            is NotesEvent.GetNoteById -> {
                viewModelScope.launch {
                    getNoteById(event.id)
                }
            }

            is NotesEvent.SaveNote -> {
                viewModelScope.launch {
                    val note = Note(
                        tittle = _tittleOfNote.value,
                        description = _descriptionOfNote.value,
                        id = 0
                    )
                    createNote(note)
                }
            }

            is NotesEvent.UpdateDescription -> {
                _descriptionOfNote.value = event.description
            }
            is NotesEvent.UpdateTittle -> {
                _tittleOfNote.value = event.tittle
            }
        }
    }


}