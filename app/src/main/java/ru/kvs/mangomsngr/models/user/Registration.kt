package ru.kvs.mangomsngr.models.user

import com.google.gson.annotations.SerializedName

data class RegistrationBody(
    @SerializedName("phone") val phoneNumber: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
)

data class RegistrationResponse(
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("user_id") val userid: Int?,
)
