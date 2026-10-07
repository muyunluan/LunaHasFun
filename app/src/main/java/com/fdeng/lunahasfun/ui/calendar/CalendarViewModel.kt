package com.fdeng.lunahasfun.ui.calendar

import androidx.lifecycle.ViewModel
import com.fdeng.lunahasfun.engine.CalendarEngine
import com.fdeng.lunahasfun.engine.CalendarProblem
import com.fdeng.lunahasfun.engine.DateInfo
import com.fdeng.lunahasfun.engine.TimeEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CalendarUiState(
    val currentProblem: CalendarProblem = CalendarEngine.generateQuestion(),
    val todayInfo: DateInfo = TimeEngine.getTodayInfo(),
    val userInput: String = "",
    val feedbackState: CalendarFeedbackState = CalendarFeedbackState.IDLE,
    val score: Int = 0
)

enum class CalendarFeedbackState {
    IDLE,
    CORRECT,
    INCORRECT
}

class CalendarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    fun onDigitPressed(digit: Int) {
        _uiState.update { state ->
            if (state.feedbackState == CalendarFeedbackState.CORRECT) return@update state
            if (state.userInput.length < 3) {
                state.copy(userInput = state.userInput + digit, feedbackState = CalendarFeedbackState.IDLE)
            } else {
                state
            }
        }
    }

    fun onClearPressed() {
        _uiState.update { state ->
            state.copy(userInput = "", feedbackState = CalendarFeedbackState.IDLE)
        }
    }

    fun onSubmitAnswer() {
        val state = _uiState.value
        if (state.userInput.isBlank()) return
        val userDayNum = state.userInput.toIntOrNull() ?: return
        val isCorrect = userDayNum == state.currentProblem.expectedDate.dayOfMonth

        if (isCorrect) {
            _uiState.update { it.copy(feedbackState = CalendarFeedbackState.CORRECT, score = it.score + 1) }
        } else {
            _uiState.update { it.copy(feedbackState = CalendarFeedbackState.INCORRECT) }
        }
    }

    fun nextProblem() {
        _uiState.update {
            it.copy(
                currentProblem = CalendarEngine.generateQuestion(),
                todayInfo = TimeEngine.getTodayInfo(),
                userInput = "",
                feedbackState = CalendarFeedbackState.IDLE
            )
        }
    }
}
