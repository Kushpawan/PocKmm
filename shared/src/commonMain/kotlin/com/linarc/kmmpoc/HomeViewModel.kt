package com.linarc.kmmpoc

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class HomeItem(val name: String, val email: String, val dob: String)

class HomeViewModel: ViewModel() {

    private val _homeState: MutableStateFlow<List<HomeItem>> = MutableStateFlow(listOf())
    val homeState: StateFlow<List<HomeItem>> = _homeState

    fun updateList(name: String, email: String, dob: String) {
        val currentList = _homeState.value.toMutableList()
        currentList.add(HomeItem(name, email, dob))
        _homeState.value = currentList
    }
}