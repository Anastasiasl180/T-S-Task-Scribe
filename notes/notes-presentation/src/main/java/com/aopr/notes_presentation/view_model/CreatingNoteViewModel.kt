package com.aopr.notes_presentation.view_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.notes_domain.NotesUseCase
import com.aopr.notes_domain.models.Note
import com.aopr.notes_presentation.view_model.events.CreatingNoteEvent
import com.aopr.shared_domain.Responses
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CreatingNoteViewModel(private val useCase: NotesUseCase) : ViewModel() {

    private val _existingNote = MutableStateFlow<Note?>(null)
    val existingNote: StateFlow<Note?> = _existingNote

    private val _tittleOfNote = MutableStateFlow("")
    val tittleOfNote: StateFlow<String> = _tittleOfNote

    private val _descriptionOfNote = MutableStateFlow("")
    val descriptionOfNote: StateFlow<String> = _descriptionOfNote


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

    fun onEvent(event: CreatingNoteEvent) {
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
                        id = noteId
                    )
                    createNote(note)
                }
            }

            is CreatingNoteEvent.UpdateDescription -> {
                _descriptionOfNote.value = event.description
            }

            is CreatingNoteEvent.UpdateTittle -> {
                _tittleOfNote.value = event.tittle
            }
        }
    }
}