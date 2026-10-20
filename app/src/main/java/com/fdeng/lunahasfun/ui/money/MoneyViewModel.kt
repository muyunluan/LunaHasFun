package com.fdeng.lunahasfun.ui.money

import androidx.lifecycle.ViewModel
import com.fdeng.lunahasfun.engine.MoneyEngine
import com.fdeng.lunahasfun.engine.MoneyProblem
import com.fdeng.lunahasfun.engine.MoneyProblemType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

enum class MoneyMode {
    QUIZ,
    REPLAY_FAILED,
    COMPLETED
}

data class MoneyUiState(
    val currentProblem: MoneyProblem = MoneyEngine.generateCoinCluster(),
    val userInput: String = "",
    val feedbackState: MoneyFeedbackState = MoneyFeedbackState.IDLE,
    val score: Int = 0,
    val questionNumber: Int = 1,
    val totalQuestions: Int = 25,
    val mode: MoneyMode = MoneyMode.QUIZ,
    val failedProblems: List<MoneyProblem> = emptyList(),
    val replayIndex: Int = 0
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
            if (state.mode == MoneyMode.COMPLETED) return@update state
            if (state.userInput.length < 6) {
                state.copy(userInput = state.userInput + digit)
            } else {
                state
            }
        }
    }

    fun onDotPressed() {
        _uiState.update { state ->
            if (state.mode == MoneyMode.COMPLETED) return@update state
            if (!state.userInput.contains(".") && state.userInput.length < 6) {
                val newInput = if (state.userInput.isEmpty()) "0." else state.userInput + "."
                state.copy(userInput = newInput)
            } else {
                state
            }
        }
    }

    fun onClearPressed() {
        _uiState.update { state ->
            if (state.mode == MoneyMode.COMPLETED) return@update state
            state.copy(userInput = "")
        }
    }

    fun onSubmitAnswer() {
        val state = _uiState.value
        if (state.userInput.isBlank() || state.mode == MoneyMode.COMPLETED) return

        val userCents = if (state.userInput.contains(".")) {
            val d = state.userInput.toDoubleOrNull() ?: return
            (d * 100).roundToInt()
        } else {
            val rawInt = state.userInput.toIntOrNull() ?: return
            if (state.currentProblem.problemType == MoneyProblemType.DOLLAR_CONVERSION && rawInt in 1..9 && state.currentProblem.expectedTotalCents >= 100 && state.currentProblem.expectedTotalCents % 100 == 0) {
                rawInt * 100
            } else {
                rawInt
            }
        }

        val isCorrect = userCents == state.currentProblem.expectedTotalCents

        when (state.mode) {
            MoneyMode.QUIZ -> {
                val newScore = if (isCorrect) state.score + 1 else state.score
                val newFailed = if (!isCorrect) state.failedProblems + state.currentProblem else state.failedProblems
                val nextQNum = state.questionNumber + 1

                if (nextQNum > state.totalQuestions) {
                    if (newFailed.isNotEmpty()) {
                        _uiState.update {
                            it.copy(
                                score = newScore,
                                failedProblems = newFailed,
                                mode = MoneyMode.REPLAY_FAILED,
                                currentProblem = newFailed.first(),
                                replayIndex = 0,
                                userInput = ""
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                score = newScore,
                                mode = MoneyMode.COMPLETED,
                                userInput = ""
                            )
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            score = newScore,
                            failedProblems = newFailed,
                            questionNumber = nextQNum,
                            currentProblem = MoneyEngine.generateCoinCluster(),
                            userInput = ""
                        )
                    }
                }
            }
            MoneyMode.REPLAY_FAILED -> {
                val newScore = if (isCorrect) state.score + 1 else state.score
                val nextReplayIndex = state.replayIndex + 1

                if (nextReplayIndex >= state.failedProblems.size) {
                    _uiState.update {
                        it.copy(
                            score = newScore,
                            mode = MoneyMode.COMPLETED,
                            userInput = ""
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            score = newScore,
                            replayIndex = nextReplayIndex,
                            currentProblem = state.failedProblems[nextReplayIndex],
                            userInput = ""
                        )
                    }
                }
            }
            MoneyMode.COMPLETED -> {}
        }
    }

    fun nextProblem() {}

    fun restartQuiz() {
        val currentTotal = _uiState.value.totalQuestions
        _uiState.value = MoneyUiState(
            currentProblem = MoneyEngine.generateCoinCluster(),
            score = 0,
            questionNumber = 1,
            totalQuestions = currentTotal,
            mode = MoneyMode.QUIZ,
            failedProblems = emptyList(),
            replayIndex = 0,
            userInput = ""
        )
    }

    fun setTotalQuestions(count: Int) {
        _uiState.update { it.copy(totalQuestions = count) }
        restartQuiz()
    }
}
