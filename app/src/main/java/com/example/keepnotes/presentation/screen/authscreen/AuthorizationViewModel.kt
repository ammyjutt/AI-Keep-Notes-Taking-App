package com.example.keepnotes.presentation.screen.authscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import android.util.Log

@HiltViewModel
class AuthorizationViewModel @Inject constructor() : ViewModel() {

    var registeredEmail by mutableStateOf("")
        private set
    var registeredPassword by mutableStateOf("")
        private set

    // Function to handle login and return success/failure
    fun login(enteredEmail: String, enteredPassword: String, onLoginResult: (Boolean) -> Unit) {
        onLoginResult(true)

        viewModelScope.launch {
            Log.d("AuthViewModel", "Attempting to log in with: $enteredEmail")
            Log.d("AuthViewModel", "Registered mail : $registeredEmail")

            // Check if the entered email and password match the registered credentials
            if (enteredEmail == "amir@gmail.com" && enteredPassword == "amir1234") {
                Log.d("AuthViewModel", "Login successful")
                onLoginResult(true)
            } else {
                Log.d("AuthViewModel", "Login failed")
                onLoginResult(false)
            }
        }
    }

    // Function to handle signup (register email and password)
    fun signUp(newEmail: String, newPassword: String, onSignUpSuccess: () -> Unit) {
        viewModelScope.launch {
            registeredEmail = newEmail
            registeredPassword = newPassword
            Log.d("AuthViewModel", "User registered with: $newEmail")
            onSignUpSuccess()
        }
    }
}

