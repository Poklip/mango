package ru.kvs.mangomsngr.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.kvs.mangomsngr.data.user.UserRepo
import ru.kvs.mangomsngr.models.user.AvatarExtended
import ru.kvs.mangomsngr.models.user.Profile
import ru.kvs.mangomsngr.models.user.ProfileToChangeBody
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepo) : ViewModel() {

    fun getUserData(): LiveData<Profile> {
        return liveData {
            val userDataResponse = repository.getUserData()
            userDataResponse.body()?.let { emit(it) }
        }
    }

    fun changeUserData(dataToRefresh: ProfileToChangeBody): LiveData<AvatarExtended?> {
        return liveData {
            val userDataRefreshedResponse = repository.changeUserData(dataToRefresh = dataToRefresh)
            userDataRefreshedResponse.body()?.let { emit(it.avatars) }
        }
    }
}