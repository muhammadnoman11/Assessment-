package com.example.assessmentfetchapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessmentfetchapi.api.NetworkResult
import com.example.assessmentfetchapi.db.model.UserEntity
import com.example.assessmentfetchapi.model.UserModelItem
import com.example.assessmentfetchapi.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: UsersRepository) : ViewModel() {

    val usersResponseApi: LiveData<NetworkResult<List<UserModelItem>>>
        get() = repository.userResponseApi

    val usersResponseRoomDb: LiveData<List<UserEntity>>
    get() = repository.getUsersFromDb()


    fun getUsersApi() {
        viewModelScope.launch {
            repository.getUsersFromApi()
        }

    }

}

