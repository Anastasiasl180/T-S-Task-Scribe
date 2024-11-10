package com.aopr.notes_presentation.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.notes_domain.NotesUseCase
import com.aopr.notes_domain.models.Note
import com.aopr.notes_presentation.R
import com.aopr.notes_presentation.view_model.events.CreatingNoteEvents.CreatingNoteEvent
import com.aopr.notes_presentation.view_model.events.CreatingNoteEvents.CreatingNoteUiEvents
import com.aopr.shared_domain.Responses
import com.aopr.shared_ui.util.ViewModelKit
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CreatingNoteViewModel(private val useCase: NotesUseCase) :
    ViewModelKit<CreatingNoteEvent, CreatingNoteUiEvents>() {

    private val _existingNote = MutableStateFlow<Note?>(null)
    val existingNote: StateFlow<Note?> = _existingNote

    private val _tittleOfNote = MutableStateFlow("")
    val tittleOfNote: StateFlow<String> = _tittleOfNote

    private val _descriptionOfNote = MutableStateFlow("")
    val descriptionOfNote: StateFlow<String> = _descriptionOfNote

    private val _isNotePinned = mutableStateOf(false)
    val isNotePinned: State<Boolean> = _isNotePinned

    private val _isAlreadyCreateNote = mutableStateOf(false)
    val isAlreadyCreateNote: State<Boolean> = _isAlreadyCreateNote

    private val _event = MutableSharedFlow<CreatingNoteUiEvents>()
    val event = _event.asSharedFlow()


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
                    _isAlreadyCreateNote.value = true

                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getNoteById(id: Int) {
        useCase.getNoteById(id).onEach { result ->
            when (result) {
                is Responses.Error -> {

                }

                is Responses.Loading -> {

                }

                is Responses.Success -> {
                    result.data?.collect() { note ->
                        _existingNote.value = note
                        _tittleOfNote.value = note.tittle
                        _descriptionOfNote.value = note.description
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: CreatingNoteEvent) {
        when (event) {
            is CreatingNoteEvent.GetNoteById -> {
                event.id?.let { getNoteById(it) }
            }

            CreatingNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    val noteId = _existingNote.value?.id ?: 0
                    val note = Note(
                        tittle = _tittleOfNote.value,
                        description = _descriptionOfNote.value,
                        id = noteId, isPinned = _isNotePinned.value
                    )
                    createNote(note)
                    delay(500)
                }
            }

            is CreatingNoteEvent.UpdateDescription -> {
                _descriptionOfNote.value = event.description
            }

            is CreatingNoteEvent.UpdateTittle -> {
                _tittleOfNote.value = event.tittle
            }

            CreatingNoteEvent.NavigateToBack -> {
                viewModelScope.launch {
                    _event.emit(CreatingNoteUiEvents.NavigateBack)
                }
            }

            CreatingNoteEvent.PinNote -> {
                _isNotePinned.value = !_isNotePinned.value
            }
        }
    }
}