/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * MET Norway terms (api.met.no/doc/TermsOfService): coordinates max 4 decimals (5+ may return 403).
 * Also makes nearby refreshes hit the same URL, so the HTTP cache (Expires / If-Modified-Since) works.
 */

package org.breezyweather.wbw

import java.math.BigDecimal
import java.math.RoundingMode

object WbwMetNo {
    fun coord(value: Double): Double = BigDecimal.valueOf(value).setScale(4, RoundingMode.HALF_UP).toDouble()
}
