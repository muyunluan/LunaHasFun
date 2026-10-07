package com.fdeng.lunahasfun.ui.money

import androidx.lifecycle.ViewModel
import com.fdeng.lunahasfun.engine.MoneyEngine
import com.fdeng.lunahasfun.engine.MoneyProblem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
            if (state.userInput.length < 4) {
                state.copy(userInput = state.userInput + digit, feedbackState = MoneyFeedbackState.IDLE)
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
        val userCents = state.userInput.toIntOrNull() ?: return
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
