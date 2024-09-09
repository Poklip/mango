package ru.kvs.mangomsngr.models.user

import com.google.gson.annotations.SerializedName
import ru.kvs.mangomsngr.data.local.entities.ProfileEntity

data class Profile(
    @SerializedName("profile_data") val profileData: ProfileData,
)

data class ProfileData(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("vk") val vk: String?,
    @SerializedName("instagram") val instagram: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("id") val userId: Int,
    @SerializedName("last") val lastSeen: String?,
    @SerializedName("online") val isOnline: Boolean,
    @SerializedName("created") val createdAt: String?,
    @SerializedName("phone") val phoneNumber: String,
    @SerializedName("completed_task") val completedTask: Int?,
    @SerializedName("avatars") val avatarExtended: AvatarExtended?,
) {
    fun toEntity() = ProfileEntity(
        name = name,
        username = username,
        birthday = birthday,
        city = city,
        vk = vk,
        instagram = instagram,
        status = status,
        userId = userId,
        lastSeen = lastSeen,
        isOnline = isOnline,
        createdAt = createdAt,
        phoneNumber = phoneNumber,
        completedTask = completedTask,
        avatar = avatar,
        bigAvatar = avatarExtended?.bigAvatar,
        miniAvatar = avatarExtended?.miniAvatar
    )
}

data class AvatarExtended(
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("bigAvatar") val bigAvatar: String,
    @SerializedName("miniAvatar") val miniAvatar: String,
)
