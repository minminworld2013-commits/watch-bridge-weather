/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * F6 regression (user report 2026-11-17): "오늘 -273°/5°".
 * Gadgetbridge temperatures are Kelvin integers; a missing value reaches the watch as 0 K = -273 °C.
 * A day's min/max must never be sent as missing when any temperature for that day is known.
 */

package org.breezyweather

import org.breezyweather.wbw.WbwWatchTemp
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class WbwWatchTempRangeTest {

    @Test
    fun bothHalvesKnownAreSentAsIs() {
        assertEquals(281 to 278, WbwWatchTemp.maxMin(dayK = 281.2, nightK = 278.1, currentK = 279.0))
    }

    @Test
    fun missingNightFallsBackToKnownValues() {
        val (max, min) = WbwWatchTemp.maxMin(dayK = 278.0, nightK = null, currentK = 276.0)
        assertEquals(278, max)
        assertEquals(276, min)
    }

    @Test
    fun missingDayFallsBackToKnownValues() {
        val (max, min) = WbwWatchTemp.maxMin(dayK = null, nightK = 278.0, currentK = 281.0)
        assertEquals(281, max)
        assertEquals(278, min)
    }

    @Test
    fun nothingKnownStaysMissing() {
        val (max, min) = WbwWatchTemp.maxMin(dayK = null, nightK = null, currentK = null)
        assertNull(max)
        assertNull(min)
    }
}
