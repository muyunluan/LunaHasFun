package com.fdeng.lunahasfun.ui.math

import androidx.lifecycle.ViewModel
import com.fdeng.lunahasfun.engine.MathEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class MathViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MathUiState())
    val uiState: StateFlow<MathUiState> = _uiState.asStateFlow()

    fun onDigitPressed(digit: Int) {
        _uiState.update { state ->
            if (state.feedbackState == FeedbackState.CORRECT || state.isCompleted) {
                return@update state
            }
            if (state.userInput.length < 4) {
                state.copy(userInput = state.userInput + digit, feedbackState = FeedbackState.IDLE)
            } else {
                state
            }
        }
    }

    fun onClearPressed() {
        _uiState.update { state ->
            if (state.isCompleted) return@update state
            state.copy(userInput = "", feedbackState = FeedbackState.IDLE)
        }
    }

    fun onSubmitAnswer() {
        val state = _uiState.value
        if (state.userInput.isBlank() || state.isCompleted) return

        val userNum = state.userInput.toIntOrNull() ?: return
        val isCorrect = userNum == state.currentProblem.expectedAnswer

        if (isCorrect) {
            _uiState.update {
                it.copy(
                    feedbackState = FeedbackState.CORRECT,
                    score = it.score + 1
                )
            }
        } else {
            _uiState.update {
                it.copy(feedbackState = FeedbackState.INCORRECT)
            }
        }
    }

    fun nextProblem() {
        val currentState = _uiState.value
        val nextQNum = currentState.questionNumber + 1

        if (nextQNum > currentState.totalQuestions) {
            _uiState.update { it.copy(isCompleted = true) }
        } else {
            // Dynamically mix addition and subtraction
            val newProblem = if (Random.nextBoolean()) {
                MathEngine.generateAddition()
            } else {
                MathEngine.generateSubtraction()
            }
            _uiState.update {
                it.copy(
                    currentProblem = newProblem,
                    userInput = "",
                    feedbackState = FeedbackState.IDLE,
                    questionNumber = nextQNum
                )
            }
        }
    }

    fun restartQuiz() {
        val firstProblem = if (Random.nextBoolean()) {
            MathEngine.generateAddition()
        } else {
            MathEngine.generateSubtraction()
        }
        _uiState.value = MathUiState(
            currentProblem = firstProblem,
            score = 0,
            questionNumber = 1,
            isCompleted = false
        )
    }
}
