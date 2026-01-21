package com.linarc.kmm_poc

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel: ViewModel() {

    private val _homeState: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
    val homeState: StateFlow<List<String>> = _homeState

    init {
        updateList()
    }

    fun updateList() {
        _homeState.value = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item5")
    }
}