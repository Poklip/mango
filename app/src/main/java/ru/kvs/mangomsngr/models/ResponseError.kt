package ru.kvs.mangomsngr.models

import com.google.gson.annotations.SerializedName

data class ResponseError (
    @SerializedName("detail") val details: List<ErrorDetail>
)

data class ErrorDetail(
    @SerializedName("loc") val loc: List<String>?,
    @SerializedName("msg") val message: String?,
    @SerializedName("type") val type: String?
)