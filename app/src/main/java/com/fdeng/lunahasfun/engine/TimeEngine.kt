package com.fdeng.lunahasfun.engine

import java.time.DayOfWeek
import java.time.LocalDate

data class DateInfo(
    val date: LocalDate,
    val dayOfWeek: DayOfWeek,
    val formattedDateString: String
)

object TimeEngine {
    /**
     * Returns current date and day of week info.
     */
    fun getTodayInfo(): DateInfo {
        val today = LocalDate.now()
        return DateInfo(
            date = today,
            dayOfWeek = today.dayOfWeek,
            formattedDateString = today.toString()
        )
    }

    /**
     * Calculates future date by adding specified days (e.g., 7 days) from today.
     */
    fun getFutureDate(daysToAdd: Long): LocalDate {
        return LocalDate.now().plusDays(daysToAdd)
    }

    /**
     * Calculates future date given a base date.
     */
    fun getFutureDate(baseDate: LocalDate, daysToAdd: Long): LocalDate {
        return baseDate.plusDays(daysToAdd)
    }
}
