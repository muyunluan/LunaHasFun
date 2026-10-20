package com.fdeng.lunahasfun.ui.math

import com.fdeng.lunahasfun.engine.MathEngine
import com.fdeng.lunahasfun.engine.MathProblem

data class MathUiState(
    val currentProblem: MathProblem = MathEngine.generateAddition(),
    val userInput: String = "",
    val feedbackState: FeedbackState = FeedbackState.IDLE,
    val score: Int = 0,
    val questionNumber: Int = 1,
    val totalQuestions: Int = 25,
    val isCompleted: Boolean = false
)

enum class FeedbackState {
    IDLE,
    CORRECT,
    INCORRECT
}
