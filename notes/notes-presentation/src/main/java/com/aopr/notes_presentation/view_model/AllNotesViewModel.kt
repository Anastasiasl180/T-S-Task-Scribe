package com.aopr.notes_presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aopr.notes_domain.NotesUseCase
import com.aopr.notes_presentation.view_model.events.AllNotesEvent
import com.aopr.shared_domain.Responses
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AllNotesViewModel(private val useCase: NotesUseCase) : ViewModel() {

    init {
        onEvent(AllNotesEvent.GetAllNotes)
    }

    private fun getAllNotes() {
        useCase.getAllNotes().onEach { result ->
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

    fun onEvent(event: AllNotesEvent) {
        when (event) {
            AllNotesEvent.GetAllNotes -> {
                viewModelScope.launch {
                    getAllNotes()
                }
            }
        }
    }

}