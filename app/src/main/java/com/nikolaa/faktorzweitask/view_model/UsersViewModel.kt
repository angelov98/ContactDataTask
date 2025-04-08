package com.nikolaa.faktorzweitask.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikolaa.faktorzweitask.models.UserModel
import com.nikolaa.faktorzweitask.usecases.GetAllUsersUseCase
import com.nikolaa.faktorzweitask.usecases.GetUserDetailsUseCase
import com.nikolaa.faktorzweitask.usecases.RemoveLocalUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val removeLocalUserUseCase: RemoveLocalUserUseCase
) : ViewModel() {

    private val _userList = MutableStateFlow<List<UserModel?>>(emptyList())
    val userList: StateFlow<List<UserModel?>> = _userList

    private val _userDetails = MutableStateFlow<UserModel?>(null)
    val userDetails: StateFlow<UserModel?> = _userDetails.asStateFlow()

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun fetchAllUsers(forceRefresh: Boolean = false) {
        if (_isRefreshing.value) return
        viewModelScope.launch {
            try {
                _isRefreshing.value = true
                val users = getAllUsersUseCase(forceRefresh)
                _userList.value = users
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isRefreshing.value = false
            }
        }
    }


    fun fetchUserById(userId: Int) {
        if (_isLoading.value) return
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _userDetails.value = null
                val details = getUserDetailsUseCase(userId)
                _userDetails.value = details
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            try {
                removeLocalUserUseCase.invoke(userId) // Just delete the user without returning a UserModel
            } catch (e: Exception) {
                // Handle errors (e.g., show a Snackbar or error message)
                Log.e("UsersViewModel", "Error deleting user", e)
            }
        }
    }
}