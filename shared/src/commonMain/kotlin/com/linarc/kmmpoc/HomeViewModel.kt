package com.linarc.kmmpoc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class HomeItem(val name: String, val email: String, val dob: String)

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}

class HomeViewModel: ViewModel() {

    private val ktorClient = KtorClient()

    private val _homeState = MutableStateFlow<List<HomeItem>>(listOf())
    val homeState: StateFlow<List<HomeItem>> = _homeState.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun updateList(name: String, email: String, dob: String) {
        _homeState.value = _homeState.value + HomeItem(name, email, dob)
    }

    fun observeHomeState(onUpdate: (List<HomeItem>) -> Unit) {
        homeState.onEach { onUpdate(it) }.launchIn(viewModelScope)
    }

    fun observeLoginState(onUpdate: (LoginState) -> Unit) {
        loginState.onEach { onUpdate(it) }.launchIn(viewModelScope)
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = ktorClient.login(LoginRequest(username, password))
                _loginState.value = LoginState.Success(response)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
