package com.aopr.notes_presentation.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.aopr.notes_domain.NotesUseCase
import com.aopr.notes_domain.models.Note
import com.aopr.notes_presentation.view_model.events.notesEvents.NotesEvent
import com.aopr.notes_presentation.view_model.events.notesEvents.NotesUiEvents
import com.aopr.shared_domain.Responses
import com.aopr.shared_ui.util.ViewModelKit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NotesViewModel(private val useCase: NotesUseCase) :
    ViewModelKit<NotesEvent, NotesUiEvents>() {

    private val _tittleOfNote = mutableStateOf("")
    val tittleOfNote: State<String> = _tittleOfNote

    private val _descriptionOfNote = mutableStateOf("")
    val descriptionOfNote: State<String> = _descriptionOfNote

    private val _tittleOfTask = mutableStateOf("")
    val tittleOfTask: State<String> = _tittleOfTask

    private val _descriptionOfTask = mutableStateOf("")
    val descriptionOfTask: State<String> = _descriptionOfTask

    private val _event = MutableSharedFlow<NotesUiEvents>()
    val uiEvents = _event


    private fun createNote(note: Note) {
        useCase.createNote(note).onEach { result ->
            when (result) {
                is Responses.Error -> {
                    hideInfoBar()
                    result.message?.let { showShortInfoBar(it, 2) }
                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                }
            }

        }.launchIn(viewModelScope)
    }


    override fun onEvent(event: NotesEvent) {
        when (event) {

            NotesEvent.NavigateToAllNotes -> {
                viewModelScope.launch {
                    _event.emit(NotesUiEvents.NavigateToAllNotesScreen)
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

            is NotesEvent.UpdateDescriptionOfNote -> {
                _descriptionOfNote.value = event.description
            }

            is NotesEvent.UpdateTittleOfNote -> {
                _tittleOfNote.value = event.tittle
            }

            NotesEvent.NavigateToAllTasks -> {
                viewModelScope.launch {
                    _event.emit(NotesUiEvents.NavigateToAllTasks)
                }
            }

            NotesEvent.SaveTask -> {

            }
            is NotesEvent.UpdateDescriptionOfTask -> {
                _tittleOfTask.value = event.description
            }

            is NotesEvent.UpdateTittleOFTask -> {
                _tittleOfTask.value = event.tittle
            }
        }
    }


}