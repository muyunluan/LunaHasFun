package com.fdeng.lunahasfun.engine

import java.time.LocalDate

data class CalendarProblem(
    val questionText: String,
    val baseDate: LocalDate,
    val expectedDate: LocalDate,
    val expectedDayName: String
)

object CalendarEngine {
    /**
     * Generates a calendar math question (e.g., today + 7 days).
     */
    fun generateQuestion(): CalendarProblem {
        val today = LocalDate.now()
        val daysToAdd = listOf(1L, 7L, 14L).random()
        val futureDate = TimeEngine.getFutureDate(today, daysToAdd)

        val question = if (daysToAdd == 7L) {
            "What date is it in 7 days?"
        } else {
            "What date is it in $daysToAdd day(s)?"
        }

        return CalendarProblem(
            questionText = question,
            baseDate = today,
            expectedDate = futureDate,
            expectedDayName = futureDate.dayOfWeek.name
        )
    }
}
