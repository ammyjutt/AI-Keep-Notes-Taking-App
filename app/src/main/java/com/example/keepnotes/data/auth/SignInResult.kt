package com.example.keepnotes.data.auth


data class SignInResult(
    val data: GoogleUser?,
    val errorMessage: String?
)

data class UserData(
    val userId: String? = "",
    val username: String? = "",
    val profilePictureUrl: String? = "",
    val mail: String? = "",
    var bio: String? = "",
)