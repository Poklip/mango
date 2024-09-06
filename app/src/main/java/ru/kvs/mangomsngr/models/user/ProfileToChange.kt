package ru.kvs.mangomsngr.models.user

import com.google.gson.annotations.SerializedName


data class ProfileToChangeBody(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("vk") val vk: String?,
    @SerializedName("instagram") val instagram: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("avatar") val avatarToSend: AvatarToSend?,
)

data class AvatarToSend(
    @SerializedName("filename") val fileName: String,
    @SerializedName("base_64") val base64: String,
)

data class ProfileChangedResponse(
    @SerializedName("avatars") val avatars: AvatarExtended?
)