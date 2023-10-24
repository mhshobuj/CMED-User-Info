package com.mhs.userinfo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhs.userinfo.repository.Repository
import com.mhs.userinfo.response.UserDetailsResponse
import com.mhs.userinfo.response.UserListResponse
import com.mhs.userinfo.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiRepository: Repository) : ViewModel() {

    //getUserList
    private val _userList = MutableLiveData<DataStatus<List<UserListResponse.UserListResponseItem>>>()
    val usersList: LiveData<DataStatus<List<UserListResponse.UserListResponseItem>>> get() = _userList

    fun getUserList() = viewModelScope.launch {
        apiRepository.getUserList().collect {
            _userList.value = it
        }
    }

    //getUserDetails
    private val _userDetails = MutableLiveData<DataStatus<UserDetailsResponse>>()
    val userDetails: LiveData<DataStatus<UserDetailsResponse>> get() = _userDetails

    fun getUserDetails(id: String) = viewModelScope.launch {
        apiRepository.getUserDetails(id).collect {
            _userDetails.value = it
        }
    }
}