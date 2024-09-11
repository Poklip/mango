package ru.kvs.mangomsngr.data.remote.user

import android.util.Log
import retrofit2.Response
import ru.kvs.mangomsngr.models.user.CheckAuthBody
import ru.kvs.mangomsngr.models.user.Profile
import ru.kvs.mangomsngr.models.user.ProfileChangedResponse
import ru.kvs.mangomsngr.models.user.ProfileToChangeBody
import ru.kvs.mangomsngr.models.user.RefreshToken
import ru.kvs.mangomsngr.models.user.RefreshedToken
import ru.kvs.mangomsngr.models.user.RegistrationBody
import ru.kvs.mangomsngr.models.user.SendAuthBody
import ru.kvs.mangomsngr.models.user.SendAuthResponse
import javax.inject.Inject

class UserRemoteData @Inject constructor(private val service: UserService) {

    suspend fun sendAuth(phoneNumberForCode: SendAuthBody): Response<SendAuthResponse> {
        Log.d("KVS_DEBUG", service.sendAuthCode(body = phoneNumberForCode).body().toString())
        return service.sendAuthCode(body = phoneNumberForCode)
    }

    suspend fun checkAuth(phoneNumberWithCode: CheckAuthBody) =
        service.checkAuthCode(body = phoneNumberWithCode)

    suspend fun registerNewUser(registerInfo: RegistrationBody) =
        service.registerNewUser(body = registerInfo)

    suspend fun getUserProfile(accessToken: String): Response<Profile> {
        return service.getUserData(accessToken = accessToken)
    }

    suspend fun changeUserProfile(accessToken: String, dataToRefresh: ProfileToChangeBody): Response<ProfileChangedResponse> {
        return service.changeUserData(accessToken = accessToken, body = dataToRefresh)
    }

    suspend fun refreshAccessToken(refreshToken: String): Response<RefreshedToken> {
        return service.refreshToken(
            body = RefreshToken(refreshToken = refreshToken)
        )
    }

    suspend fun checkJwt(): Response<String> {
        val userToken = "-" //TODO() вынуть токен из бд и дешифровать
        return service.checkJwt(accessToken = userToken)
    }
}