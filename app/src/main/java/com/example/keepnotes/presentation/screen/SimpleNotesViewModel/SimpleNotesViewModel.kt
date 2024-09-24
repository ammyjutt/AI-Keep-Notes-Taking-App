package com.example.keepnotes.presentation.screen.simplenotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepnotes.domain.model.RealtimeModelResponse
import com.example.keepnotes.domain.model.ResultState // Adjusted import based on your setup
import com.example.keepnotes.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

    @HiltViewModel
    class SimpleNotesViewModel @Inject constructor(
        private val useCases: UseCases
    ) : ViewModel() {

        private val _notesState = MutableStateFlow<ResultState<List<RealtimeModelResponse>>>(ResultState.Loading)
        val notesState: StateFlow<ResultState<List<RealtimeModelResponse>>> = _notesState

        init {
            getAllNotes()
        }

        private fun getAllNotes() = viewModelScope.launch {
            useCases.getAllNoteUseCase().collect {
                _notesState.value = it
            }
        }
    }
