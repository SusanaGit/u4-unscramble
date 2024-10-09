package com.susanafigueroa.u4_unscramble.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class GameViewModel : ViewModel() {

    // represent the state of the UI
    // MutableStateFlow: emite y guarda el estado actual y notifica a los observadores cada vez que el estado cambia
    // GameUiState(): valor inicial del flujo. Es una clase de datos.
    private val _uiState = MutableStateFlow(GameUiState())

}