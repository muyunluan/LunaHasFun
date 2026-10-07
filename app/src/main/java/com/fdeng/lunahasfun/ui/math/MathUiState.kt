package com.fdeng.lunahasfun.ui.math

import com.fdeng.lunahasfun.engine.MathEngine
import com.fdeng.lunahasfun.engine.MathProblem

data class MathUiState(
    val currentProblem: MathProblem = MathEngine.generateAddition(),
    val userInput: String = "",
    val feedbackState: FeedbackState = FeedbackState.IDLE,
    val score: Int = 0
)

enum class FeedbackState {
    IDLE,
    CORRECT,
    INCORRECT
}
