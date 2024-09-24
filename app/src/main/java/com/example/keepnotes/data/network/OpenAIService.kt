package com.example.keepnotes.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class OpenAIService {
    private val client = OkHttpClient()
    private val apiKey = "" // Paste your key here 
    private val url = "https://api.openai.com/v1/chat/completions" // Ensure this is the correct endpoint

    // Function to get response from OpenAI based on user input
    suspend fun getResponse(userInput: String): String {
        return withContext(Dispatchers.IO) {
            val mediaType = "application/json; charset=utf-8".toMediaType()

            // Construct the JSON body using JSONObject for proper formatting and escaping
            // Custom instructions for the model
            val systemMessage = "You are a helpful assistant that answers questions based on the user's notes. Always refer to the notes provided and be concise."

            // Construct the JSON body with the system message
            val jsonBody = JSONObject().apply {
                put("model", "gpt-4o-mini")
                put("messages", JSONArray().apply {
                    // Add the system message first
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", systemMessage)
                    })
                    // Then add the user's input
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", userInput)
                    })
                })
                put("max_tokens", 50)
            }.toString()

            val requestBody = jsonBody.toRequestBody(mediaType)

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .build()

            try {
                // Log the final JSON body being sent
                Log.d("OpenAIService", "Making API call to OpenAI: $url with input: $jsonBody")

                val response = client.newCall(request).execute()

                // Log the HTTP status code to help with debugging
                Log.d("OpenAIService", "HTTP Status Code: ${response.code}")

                val responseBody = response.body?.string() ?: return@withContext "No response"

                Log.d("OpenAIService", "API response received: $responseBody")

                // Parse the response JSON and handle errors if present
                val json = JSONObject(responseBody)
                if (json.has("error")) {
                    val errorMessage = json.getJSONObject("error").getString("message")
                    Log.e("OpenAIService", "Error from API: $errorMessage")
                    return@withContext "Error: $errorMessage"
                }

                val choices = json.getJSONArray("choices")
                val message = choices.getJSONObject(0).getJSONObject("message").getString("content")

                Log.d("OpenAIService", "Parsed message from API: $message")

                return@withContext message.trim()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("OpenAIService", "Error making API call: ${e.message}")
                return@withContext "Error: ${e.message}"
            }
        }
    }

}
