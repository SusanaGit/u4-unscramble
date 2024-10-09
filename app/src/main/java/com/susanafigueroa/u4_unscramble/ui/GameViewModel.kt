package com.susanafigueroa.u4_unscramble.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class GameViewModel : ViewModel() {

    // represent the state of the UI
    private val _uiState = MutableStateFlow(GameUiState())

}