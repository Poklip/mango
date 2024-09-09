package ru.kvs.mangomsngr.data.local.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import ru.kvs.mangomsngr.models.user.AvatarExtended
import ru.kvs.mangomsngr.models.user.ProfileData

@Entity(tableName = "user_profile")
data class ProfileEntity(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("username") val username: String,
    @ColumnInfo("birthday") val birthday: String?,
    @ColumnInfo("city") val city: String?,
    @ColumnInfo("vk") val vk: String?,
    @ColumnInfo("instagram") val instagram: String?,
    @ColumnInfo("status") val status: String?,
    @ColumnInfo("id") val userId: Int,
    @ColumnInfo("last") val lastSeen: String?,
    @ColumnInfo("online") val isOnline: Boolean,
    @ColumnInfo("created") val createdAt: String?,
    @ColumnInfo("phone") val phoneNumber: String,
    @ColumnInfo("completed_task") val completedTask: Int?,
    @ColumnInfo("avatar") val avatar: String?,
    @ColumnInfo("bigAvatar") val bigAvatar: String?,
    @ColumnInfo("miniAvatar") val miniAvatar: String?,
) {
    fun toDomain() = ProfileData(
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
        avatarExtended = AvatarExtended(
            avatar = avatar,
            bigAvatar = bigAvatar ?: "",
            miniAvatar = miniAvatar ?: "",
        ),
    )
}