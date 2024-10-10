package com.susanafigueroa.u4_unscramble.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.susanafigueroa.u4_unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.susanafigueroa.u4_unscramble.data.MAX_NO_OF_WORDS
import kotlinx.coroutines.flow.update
import com.susanafigueroa.u4_unscramble.data.SCORE_INCREASE

class GameViewModel : ViewModel() {

    // Representa el estado de la UI.
    // MutableStateFlow: emite y guarda el estado actual y notifica a los observadores cada vez
    // que el estado cambia.
    // GameUiState(): valor inicial del flujo. Es una clase de datos.
    private val _uiState = MutableStateFlow(GameUiState())

    // Una vez iniciado, uiState de tipo StateFlow expone un estado que solo puede
    // ser modificado dentro de esta clase.
    // Otros componentes pueden observar el StateFlow.
    // Cada vez que el estado uiState cambia, la vista se actualiza automáticamente.
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Para guardar la palabra desordenada actual.
    // lateinit = variable que no se inicializa en el momento de su declaración sinó luego
    private lateinit var currentWord: String

    // Conjunto mutable para almacenar las palabras usadas en el juego.
    private var usedWords: MutableSet<String> = mutableSetOf()

    var userGuess by mutableStateOf("")
        private set

    // init method
    init {
        resetGame()
    }

    // Elegir palabra aleatoria de la lista y desordenarla
    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    // Desordenar la palabra actual
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    // Inicializa el juego. Para iniciar y reiniciar el juego.
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun checkUserGuess() {

        if (userGuess.equals(currentWord, ignoreCase = true)) {
            // User's guess is correct, increase the score
            // and call updateGameState() to prepare the game for next round
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // Reset user guess
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        // Reset user guess
        updateUserGuess("")
    }

}