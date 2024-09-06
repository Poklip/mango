package ru.kvs.mangomsngr.models.user

import com.google.gson.annotations.SerializedName

data class CheckAuthBody(
    @SerializedName("phone") val phoneNumber: String,
    @SerializedName("code") val code: String,
)

data class CheckAuthResponse(
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("user_id") val userid: Int?,
    @SerializedName("is_user_exists") val isUserExists: Boolean,
)
