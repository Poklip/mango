package ru.kvs.mangomsngr.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvs.mangomsngr.data.local.user.UserLocalRepo
import ru.kvs.mangomsngr.data.remote.user.UserRemoteRepo
import ru.kvs.mangomsngr.models.user.CheckAuthBody
import ru.kvs.mangomsngr.models.user.CheckAuthResponse
import ru.kvs.mangomsngr.models.user.ProfileData
import ru.kvs.mangomsngr.models.user.RegistrationBody
import ru.kvs.mangomsngr.models.user.RegistrationResponse
import ru.kvs.mangomsngr.models.user.SendAuthBody
import javax.inject.Inject

@HiltViewModel
class EnterViewModel @Inject constructor(private val remoteRepo: UserRemoteRepo, private val localRepo: UserLocalRepo) : ViewModel() {

    private val dbScope = CoroutineScope(Dispatchers.IO)

    fun sendAuth(phoneNumber: String): LiveData<Boolean> {
        return liveData {
            val sendAuthResponse =
                remoteRepo.sendAuth(phoneNumberForCode = SendAuthBody(phoneNumber = phoneNumber))
            Log.d("KVS_DEBUG", sendAuthResponse.body().toString())
            sendAuthResponse.body()?.let { emit(it.isSuccess) }
        }
    }

    fun checkAuth(phoneNumber: String, code: String): LiveData<CheckAuthResponse> {
        return liveData {
            val checkAuthResponse = remoteRepo.checkAuth(
                phoneNumberWithCode = CheckAuthBody(
                    phoneNumber = phoneNumber,
                    code = code
                )
            )
            checkAuthResponse.body()?.let { emit(it) }
        }
    }

    fun registerNewUser(
        phoneNumber: String,
        name: String,
        username: String
    ): LiveData<RegistrationResponse?> {

        return liveData {
            val registerResponse = remoteRepo.registerNewUser(
                registerInfo = RegistrationBody(
                    phoneNumber = phoneNumber,
                    name = name,
                    username = username
                )
            )
            Log.d("KVS_DEBUG", registerResponse.message())
            if (registerResponse.code() != 201) {
                emit(null)
            }
            localRepo.saveProfile(ProfileData(
                name = name,
                username = username,
                phoneNumber = phoneNumber,
                isOnline = true,
                userId = 1//TODO() хардкод, user id не приходил
            ))
            registerResponse.body()?.let { emit(it) }
        }
    }

    fun saveTokens(
        accessToken: String,
        refreshToken: String
    ) {
        dbScope.launch {
            localRepo.saveTokens(accessToken, refreshToken)
        }
    }
}