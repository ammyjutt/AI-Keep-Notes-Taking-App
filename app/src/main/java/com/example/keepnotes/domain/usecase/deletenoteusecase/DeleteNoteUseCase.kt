package com.example.keepnotes.domain.usecase.deletenoteusecase

import com.example.keepnotes.data.repository.Repository
import com.example.keepnotes.domain.model.Note

class DeleteNoteUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(key:String, categoryToSet: String = "deleted") = repository.deleteNote(key, categoryToSet)
}