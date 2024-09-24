package com.example.keepnotes.data.network


import com.example.keepnotes.domain.model.Note
import com.example.keepnotes.domain.model.RealtimeModelResponse
import com.example.keepnotes.domain.model.ResultState
import com.example.keepnotes.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException



class RemoteDataSource(
    private val apiService: NoteApiService
) {

    fun insertNote(item: RealtimeModelResponse.RealtimeItems): Flow<ResultState<String>> = flow {
        try {
            emit(ResultState.Loading)

            val note = Note(
                id = 0,
                title = item.title ?: "",
                note = item.note ?: "",
                category = item.category ?: ""
            )

            apiService.addNote(note).execute()
            emit(ResultState.Success("Note added successfully"))
        } catch (e: HttpException) {
            emit(ResultState.Failure(e))
        } catch (e: Exception) {
            emit(ResultState.Failure(e))
        }
    }


    fun getAllNote(): Flow<ResultState<List<RealtimeModelResponse>>> = flow {
        try {
            emit(ResultState.Loading)

            val response = apiService.getAllNotes().execute()
            if (response.isSuccessful) {
                val notes = response.body()?.map { note: Note ->
                    RealtimeModelResponse(
                        item = RealtimeModelResponse.RealtimeItems(
                            title = note.title,
                            note = note.note,
                            category = note.category
                        ),
                        key = note.id.toString() // or another unique identifier
                    )
                } ?: emptyList()

                emit(ResultState.Success(notes))
            } else {
                emit(ResultState.Failure(Exception("Failed to fetch notes")))
            }
        } catch (e: HttpException) {
            emit(ResultState.Failure(e))
        } catch (e: Exception) {
            emit(ResultState.Failure(e))
        }
    }

    fun getNote(key: String): Flow<ResultState<RealtimeModelResponse>> = flow {
        try {
            emit(ResultState.Loading)

            val response = apiService.getNoteById(key.toInt()).execute()
            if (response.isSuccessful) {
                val note = response.body()?.let {
                    RealtimeModelResponse(
                        item = RealtimeModelResponse.RealtimeItems(
                            title = it.title,
                            note = it.note,
                            category = it.category
                        ),
                        key = key
                    )
                }
                emit(ResultState.Success(note!!)) // Ensure the note is not null
            } else {
                emit(ResultState.Failure(Exception("Failed to fetch note")))
            }
        } catch (e: HttpException) {
            emit(ResultState.Failure(e))
        } catch (e: Exception) {
            emit(ResultState.Failure(e))
        }
    }

    fun deleteNote(
        key: String,
        categoryToSet: String
    ): Flow<ResultState<String>> = flow {
        try {
            emit(ResultState.Loading)

            apiService.deleteNote(key.toInt()).execute()
            emit(ResultState.Success("Note deleted successfully"))
        } catch (e: HttpException) {
            emit(ResultState.Failure(e))
        } catch (e: Exception) {
            emit(ResultState.Failure(e))
        }
    }

    fun updateNote(
        res: RealtimeModelResponse
    ): Flow<ResultState<String>> = flow {
        try {
            emit(ResultState.Loading)

            val note = Note(
                id = res.item?.userId?.toInt() ?: 0, // Ensure you pass the correct ID
                title = res.item?.title ?: "",
                note = res.item?.note ?: "",
                category = res.item?.category ?: ""
            )

            apiService.updateNote(note.id, note).execute()
            emit(ResultState.Success("Note updated successfully"))
        } catch (e: HttpException) {
            emit(ResultState.Failure(e))
        } catch (e: Exception) {
            emit(ResultState.Failure(e))
        }
    }

    fun archiveNote(
        key: String
    ): Flow<ResultState<String>> = flow {
        try {
            // Implement archiving logic here if needed
            emit(ResultState.Success("Note archived successfully"))
        } catch (e: Exception) {
            emit(ResultState.Failure(e))
        }
    }
}
