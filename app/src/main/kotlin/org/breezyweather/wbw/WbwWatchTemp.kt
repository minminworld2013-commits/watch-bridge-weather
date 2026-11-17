/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * F6 : Gadgetbridge sends Kelvin integers and the watch shows a missing value
 * as 0 K = -273 °C (user report 2026-11-17: "오늘 -273°/5°"). When one half of a day is missing,
 * fill max/min from the temperatures we do know instead of sending nothing.
 */

package org.breezyweather.wbw

import kotlin.math.roundToInt

object WbwWatchTemp {
    /** Returns (max, min) in whole Kelvin; null only when no temperature at all is known. */
    fun maxMin(dayK: Double?, nightK: Double?, currentK: Double?): Pair<Int?, Int?> {
        val known = listOfNotNull(dayK, nightK, currentK)
        val max = dayK ?: known.maxOrNull()
        val min = nightK ?: known.minOrNull()
        return max?.roundToInt() to min?.roundToInt()
    }
}
