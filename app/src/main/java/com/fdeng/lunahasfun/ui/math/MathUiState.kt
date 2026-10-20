package com.fdeng.lunahasfun.ui.math

import com.fdeng.lunahasfun.engine.MathEngine
import com.fdeng.lunahasfun.engine.MathProblem

enum class MathMode {
    QUIZ,
    REPLAY_FAILED,
    COMPLETED
}

data class MathUiState(
    val currentProblem: MathProblem = MathEngine.generateAddition(),
    val userInput: String = "",
    val score: Int = 0,
    val questionNumber: Int = 1,
    val totalQuestions: Int = 25,
    val mode: MathMode = MathMode.QUIZ,
    val failedProblems: List<MathProblem> = emptyList(),
    val replayIndex: Int = 0
)
