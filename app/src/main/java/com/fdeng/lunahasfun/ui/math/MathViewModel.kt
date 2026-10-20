package com.fdeng.lunahasfun.ui.math

import androidx.lifecycle.ViewModel
import com.fdeng.lunahasfun.engine.MathEngine
import com.fdeng.lunahasfun.engine.MathProblem
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
            if (state.mode == MathMode.COMPLETED) return@update state
            if (state.userInput.length < 4) {
                state.copy(userInput = state.userInput + digit)
            } else {
                state
            }
        }
    }

    fun onClearPressed() {
        _uiState.update { state ->
            if (state.mode == MathMode.COMPLETED) return@update state
            state.copy(userInput = "")
        }
    }

    fun onSubmitAnswer() {
        val state = _uiState.value
        if (state.userInput.isBlank() || state.mode == MathMode.COMPLETED) return

        val userNum = state.userInput.toIntOrNull() ?: return
        val isCorrect = userNum == state.currentProblem.expectedAnswer

        when (state.mode) {
            MathMode.QUIZ -> {
                val newScore = if (isCorrect) state.score + 1 else state.score
                val newFailed = if (!isCorrect) state.failedProblems + state.currentProblem else state.failedProblems
                val nextQNum = state.questionNumber + 1

                if (nextQNum > state.totalQuestions) {
                    if (newFailed.isNotEmpty()) {
                        // Start replay mode with failed questions
                        _uiState.update {
                            it.copy(
                                score = newScore,
                                failedProblems = newFailed,
                                mode = MathMode.REPLAY_FAILED,
                                currentProblem = newFailed.first(),
                                replayIndex = 0,
                                userInput = ""
                            )
                        }
                    } else {
                        // Complete quiz
                        _uiState.update {
                            it.copy(
                                score = newScore,
                                mode = MathMode.COMPLETED,
                                userInput = ""
                            )
                        }
                    }
                } else {
                    // Next random question
                    val nextProblem = if (Random.nextBoolean()) {
                        MathEngine.generateAddition()
                    } else {
                        MathEngine.generateSubtraction()
                    }
                    _uiState.update {
                        it.copy(
                            score = newScore,
                            failedProblems = newFailed,
                            questionNumber = nextQNum,
                            currentProblem = nextProblem,
                            userInput = ""
                        )
                    }
                }
            }
            MathMode.REPLAY_FAILED -> {
                val newScore = if (isCorrect) state.score + 1 else state.score
                val nextReplayIndex = state.replayIndex + 1

                if (nextReplayIndex >= state.failedProblems.size) {
                    // Replay round finished, go to completion
                    _uiState.update {
                        it.copy(
                            score = newScore,
                            mode = MathMode.COMPLETED,
                            userInput = ""
                        )
                    }
                } else {
                    // Next failed question to replay
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
            MathMode.COMPLETED -> {}
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
            totalQuestions = 25,
            mode = MathMode.QUIZ,
            failedProblems = emptyList(),
            replayIndex = 0,
            userInput = ""
        )
    }
}
