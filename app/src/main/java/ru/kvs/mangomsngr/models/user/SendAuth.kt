package ru.kvs.mangomsngr.models.user

import com.google.gson.annotations.SerializedName

data class SendAuthBody(
    @SerializedName("phone") val phoneNumber: String,
)

data class SendAuthResponse(
    @SerializedName("is_success") val isSuccess: Boolean,
)
