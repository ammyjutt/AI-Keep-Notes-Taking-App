package com.example.keepnotes.data.network

import com.example.keepnotes.domain.model.Note
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteApiService {
    @GET("notes")
    fun getAllNotes(): Call<List<Note>>

    @GET("notes/{id}")
    fun getNoteById(@Path("id") id: Int): Call<Note>

    @POST("notes")
    fun addNote(@Body note: Note): Call<Void>

    @PUT("notes/{id}")
    fun updateNote(@Path("id") id: Int, @Body note: Note): Call<Void>

    @DELETE("notes/{id}")
    fun deleteNote(@Path("id") id: Int): Call<Void>
}
