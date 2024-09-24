package com.example.keepnotes.presentation.screen.ChatViewModel


import com.example.keepnotes.domain.usecase.getallnoteusecase.GetAllNoteUseCase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepnotes.data.network.OpenAIService
import com.example.keepnotes.domain.model.RealtimeModelResponse
import com.example.keepnotes.domain.model.ResultState
import com.example.keepnotes.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import java.util.regex.Pattern








@HiltViewModel
class ChatViewModel @Inject constructor(
    private val openAIService: OpenAIService,
    private val useCases: UseCases
) : ViewModel() {

    private val _response = MutableStateFlow("")
    val response: StateFlow<String> get() = _response

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _notesState = MutableStateFlow<ResultState<List<RealtimeModelResponse>>>(ResultState.Loading)
    val notesState: StateFlow<ResultState<List<RealtimeModelResponse>>> get() = _notesState

    // Function to parse notes and return a list of formatted strings
    private fun parseNotes(notes: List<RealtimeModelResponse>): List<String> {
        return notes.mapNotNull { response ->
            response.item?.let { item ->
                if (item.title.isNullOrBlank() || item.note.isNullOrBlank() || item.category.isNullOrBlank()) {
                    return@let null
                }
                "Title: ${item.title}\nNote: ${item.note}\nCategory: ${item.category}"
            }
        }
    }

    // Function to build a prompt using the user's query and the parsed notes
// Function to build a JSON-based prompt
    private fun buildPrompt(userQuery: String, notes: List<RealtimeModelResponse>): String {
        val sanitizedQuery = sanitizeInput(userQuery)
        val sanitizedNotes = parseNotes(notes).map { sanitizeInput(it) }

        // Build a JSON structure
        val promptJson = JSONObject().apply {
            put("user_query", sanitizedQuery)
            put("notes", JSONArray(sanitizedNotes))
        }

        return promptJson.toString()
    }



    // Function to fetch response based on user input
    fun fetchResponse(userInput: String) {
        viewModelScope.launch {
            if (_isLoading.value) {
                return@launch
            }

            _isLoading.value = true
            Log.d("ChatViewModel", "Fetching response from OpenAI with input: $userInput")

            try {
                val notesResult = _notesState.value
                val notes = if (notesResult is ResultState.Success) notesResult.data else emptyList()

                val prompt = buildPrompt(userInput, notes)
                Log.d("ChatViewModel", "Generated Prompt: $prompt")

                val result = openAIService.getResponse(prompt)
                Log.d("ChatViewModel", "Result from OpenAI: $result")
                _response.value = result
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error fetching response: ${e.message}")
                _response.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun sanitizeInput(input: String): String {

        return input
            .replace("\\", "")            // Remove backslashes
            .replace("\n", " ")           // Replace newlines with spaces
            .replace("\"", "'")           // Replace double quotes with single quotes
            .replace("\r", " ")           // Replace carriage returns with spaces
            .replace("\t", " ")           // Replace tabs with spaces
            .let { removeHtmlTags(it) }   // Remove HTML tags
            .replace("<", "&lt;")         // Replace remaining angle brackets
            .replace(">", "&gt;")
            .replace("\\n", " ")
    }

    // Function to remove HTML tags
    private fun removeHtmlTags(input: String): String {
        val htmlPattern = Pattern.compile("<.*?>", Pattern.DOTALL)
        return htmlPattern.matcher(input).replaceAll("")
    }


    // Function to fetch all notes from the database
    fun fetchAllNotes() {
        viewModelScope.launch {
            useCases.getAllNoteUseCase().collect { result ->
                _notesState.value = result

                if (result is ResultState.Success) {
                    val notes = result.data
                    Log.d("ChatViewModel", "Fetched Notes: $notes")
                } else if (result is ResultState.Failure) {
                    Log.e("ChatViewModel", "Error fetching notes: ")
                }
            }
        }
    }
}


