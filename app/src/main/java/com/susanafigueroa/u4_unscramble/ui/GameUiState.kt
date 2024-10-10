package com.susanafigueroa.u4_unscramble.ui

data class GameUiState (
    val currentWordCount: Int = 1,
    val currentScrambledWord: String = "",
    val isGuessedWordWrong: Boolean = false,
    val score: Int = 0,
    val isGameOver: Boolean = false
)