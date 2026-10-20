package com.fdeng.lunahasfun.ui.money

import androidx.lifecycle.ViewModel
import com.fdeng.lunahasfun.engine.MoneyEngine
import com.fdeng.lunahasfun.engine.MoneyProblem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

data class MoneyUiState(
    val currentProblem: MoneyProblem = MoneyEngine.generateCoinCluster(),
    val userInput: String = "",
    val feedbackState: MoneyFeedbackState = MoneyFeedbackState.IDLE,
    val score: Int = 0
)

enum class MoneyFeedbackState {
    IDLE,
    CORRECT,
    INCORRECT
}

class MoneyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MoneyUiState())
    val uiState: StateFlow<MoneyUiState> = _uiState.asStateFlow()

    fun onDigitPressed(digit: Int) {
        _uiState.update { state ->
            if (state.feedbackState == MoneyFeedbackState.CORRECT) return@update state
            if (state.userInput.length < 6) {
                state.copy(userInput = state.userInput + digit, feedbackState = MoneyFeedbackState.IDLE)
            } else {
                state
            }
        }
    }

    fun onDotPressed() {
        _uiState.update { state ->
            if (state.feedbackState == MoneyFeedbackState.CORRECT) return@update state
            if (!state.userInput.contains(".") && state.userInput.length < 6) {
                val newInput = if (state.userInput.isEmpty()) "0." else state.userInput + "."
                state.copy(userInput = newInput, feedbackState = MoneyFeedbackState.IDLE)
            } else {
                state
            }
        }
    }

    fun onClearPressed() {
        _uiState.update { state ->
            state.copy(userInput = "", feedbackState = MoneyFeedbackState.IDLE)
        }
    }

    fun onSubmitAnswer() {
        val state = _uiState.value
        if (state.userInput.isBlank()) return

        val userCents = if (state.userInput.contains(".")) {
            val d = state.userInput.toDoubleOrNull() ?: return
            (d * 100).roundToInt()
        } else {
            state.userInput.toIntOrNull() ?: return
        }

        val isCorrect = userCents == state.currentProblem.expectedTotalCents

        if (isCorrect) {
            _uiState.update { it.copy(feedbackState = MoneyFeedbackState.CORRECT, score = it.score + 1) }
        } else {
            _uiState.update { it.copy(feedbackState = MoneyFeedbackState.INCORRECT) }
        }
    }

    fun nextProblem() {
        _uiState.update {
            it.copy(
                currentProblem = MoneyEngine.generateCoinCluster(),
                userInput = "",
                feedbackState = MoneyFeedbackState.IDLE
            )
        }
    }
}
