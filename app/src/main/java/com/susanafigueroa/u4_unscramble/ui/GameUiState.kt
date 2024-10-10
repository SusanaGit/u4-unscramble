package com.susanafigueroa.u4_unscramble.ui

data class GameUiState (
    val currentScrambledWord: String = "",
    val isGuessedWordWrong: Boolean = false,
)