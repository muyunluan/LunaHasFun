package com.fdeng.lunahasfun.engine

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.LocalDate

class TimeEngineTest {

    @Test
    fun testGetTodayInfo() {
        val todayInfo = TimeEngine.getTodayInfo()
        assertNotNull(todayInfo.date)
        assertNotNull(todayInfo.dayOfWeek)
        assertEquals(LocalDate.now(), todayInfo.date)
        assertEquals(LocalDate.now().dayOfWeek, todayInfo.dayOfWeek)
    }

    @Test
    fun testGetFutureDate_plusSevenDays() {
        val expected = LocalDate.now().plusDays(7)
        val actual = TimeEngine.getFutureDate(7)
        assertEquals(expected, actual)
    }

    @Test
    fun testGetFutureDate_withBaseDate() {
        val baseDate = LocalDate.of(2026, 1, 1)
        val expected = LocalDate.of(2026, 1, 8)
        val actual = TimeEngine.getFutureDate(baseDate, 7)
        assertEquals(expected, actual)
    }
}
