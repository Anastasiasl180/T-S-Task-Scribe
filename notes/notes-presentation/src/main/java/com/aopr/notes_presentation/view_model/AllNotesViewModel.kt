package com.aopr.notes_presentation.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.aopr.notes_domain.interactors.NotesUseCase
import com.aopr.notes_domain.models.Note
import com.aopr.notes_presentation.R
import com.aopr.notes_presentation.view_model.events.all_notes_events.AllNotesEvent
import com.aopr.notes_presentation.view_model.events.all_notes_events.AllNotesUiEvent
import com.aopr.shared_domain.Responses
import com.aopr.shared_ui.util.ViewModelKit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllNotesViewModel(private val useCase: NotesUseCase) :
    ViewModelKit<AllNotesEvent, AllNotesUiEvent>() {

    private val _listOfNotes = mutableStateOf<List<Note>?>(null)
    val listOfNotes: State<List<Note>?> = _listOfNotes

    private val _pinnedNotes = mutableStateListOf<Note>()
    val pinnedNotes: SnapshotStateList<Note> = _pinnedNotes

    private val _isInSelectionMode = MutableStateFlow(false)
    val isInSelectedMode: StateFlow<Boolean> = _isInSelectionMode

    private val _selectedNotesToDelete = mutableStateListOf<Note>()
    val selectedNotesToDelete: List<Note>  = _selectedNotesToDelete

    private val _event = MutableSharedFlow<AllNotesUiEvent>()
    val event = _event.asSharedFlow()


    init {
        onEvent(AllNotesEvent.GetAllNotes)
    }

    fun addNoteToDelete(note: Note) {
        _selectedNotesToDelete.add(note)
    }

    fun removeNoteFromDelete(note: Note) {
        _selectedNotesToDelete.remove(note)
    }
    fun cancelNoteFromDelete() {
        _selectedNotesToDelete.clear()
        _isInSelectionMode.value = false
    }

    fun deleteSelectedNotes() {
        onEvent(AllNotesEvent.DeleteNote(_selectedNotesToDelete))
        _selectedNotesToDelete.clear()
        _isInSelectionMode.value = false
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

    private fun deleteNote(note: List<Note>) {
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
                hideInfoBar()
                showLongInfoBar(R.string.PressToPinInfoMessage, time = 2)

            }

            AllNotesEvent.TurnOnSelectionModeForDelete -> {
                _isInSelectionMode.value = !_isInSelectionMode.value
            }

            AllNotesEvent.NavigateBack -> {
                viewModelScope.launch {
                    _event.emit(AllNotesUiEvent.NavigateBack)
                }
            }
        }
    }

}