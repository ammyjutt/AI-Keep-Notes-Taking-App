package com.example.keepnotes.presentation.screen.categories


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoriesViewModel : ViewModel() {
    private val _categories = MutableStateFlow(listOf("Work", "Journal", "Schedule", "Password", "Health", "Others"))
    val categories: StateFlow<List<String>> = _categories

    fun onCategorySelected(category: String, navigateToNotes: (String) -> Unit) {
        navigateToNotes(category)
    }
}

