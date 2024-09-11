package ru.kvs.mangomsngr.models.user

import com.google.gson.annotations.SerializedName
import ru.kvs.mangomsngr.data.local.entities.ProfileEntity

data class Profile(
    @SerializedName("profile_data") val profileData: ProfileData,
)

data class ProfileData(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("birthday") val birthday: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("vk") val vk: String? = null,
    @SerializedName("instagram") val instagram: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("id") val userId: Int,
    @SerializedName("last") val lastSeen: String? = null,
    @SerializedName("online") val isOnline: Boolean,
    @SerializedName("created") val createdAt: String? = null,
    @SerializedName("phone") val phoneNumber: String,
    @SerializedName("completed_task") val completedTask: Int? = null,
    @SerializedName("avatars") val avatarExtended: AvatarExtended? = null,
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

    fun toSendToService(fileName: String? = null, base64: String? = null) = ProfileToChangeBody(
        name = name,
        username = username,
        birthday = birthday,
        city = city,
        vk = vk,
        instagram = instagram,
        status = status,
        avatarToSend =
        if (fileName.isNullOrEmpty() || base64.isNullOrEmpty()) null
        else AvatarToSend(
            fileName = fileName,
            base64 = base64
        )
    )

    fun copyThisField(field: String, value: String) : ProfileData {
        return when(field) {
            "name" -> this.copy(name = value)
            "birthday" -> this.copy(birthday = value)
            "city" -> this.copy(city = value)
            "vk" -> this.copy(vk = value)
            "instagram" -> this.copy(instagram = value)
            "status" -> this.copy(status = value)
            else -> this
        }
    }

    fun getThisField(field: String) : String? {
        return when(field) {
            "name" -> this.name
            "birthday" -> {
                if (this.birthday?.length == 8) {
                    return StringBuilder()
                        .append(this.birthday.substring(0, 2))
                        .append(".")
                        .append(this.birthday.substring(2, 4))
                        .append(".")
                        .append(this.birthday.substring(4, 8))
                        .toString()
                } else {
                    null
                }
            }
            "city" -> this.city
            "vk" -> this.vk
            "instagram" -> this.instagram
            "status" -> this.status
            "username" -> this.username
            "phoneNumber" -> "+" + this.phoneNumber
            else -> null
        }
    }
}

data class AvatarExtended(
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("bigAvatar") val bigAvatar: String,
    @SerializedName("miniAvatar") val miniAvatar: String,
)
