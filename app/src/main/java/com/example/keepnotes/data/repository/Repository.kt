package com.example.keepnotes.data.repository

import com.example.keepnotes.data.network.RemoteDataSource
import com.example.keepnotes.domain.model.Note
import com.example.keepnotes.domain.model.RealtimeModelResponse
import com.example.keepnotes.domain.repository.LocalDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource, // instance of room database
    private val remoteDataSource: RemoteDataSource // instance of MVC server
) {

    fun insertNote(item: RealtimeModelResponse.RealtimeItems) = localDataSource.insertNote(item)

    fun getAllNote() = localDataSource.getAllNote()
    fun getNote(key: String) = localDataSource.getNote(key)
    fun deleteNote(
        key: String,
        categoryToSet: String
    ) = localDataSource.deleteNote(key = key, categoryToSet = categoryToSet)

    fun updateNote(
        res: RealtimeModelResponse
    ) = localDataSource.updateNote(res)


    fun sync_databases() {

        // Fetch the list of modified notes from the local database
        val updatedNotes = localDataSource.getUpdatedNotes()

        // Simulate the syncing process by iterating over each updated note
        for (note in updatedNotes) {
            remoteDataSource.updateNote(note)
        }

    }


}