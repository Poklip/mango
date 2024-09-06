package ru.kvs.mangomsngr.models.user

import com.google.gson.annotations.SerializedName

data class RefreshToken(
    @SerializedName("refresh_token") val refreshToken: String?,
)

data class RefreshedToken(
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("user_id") val userId: Int?,
)
