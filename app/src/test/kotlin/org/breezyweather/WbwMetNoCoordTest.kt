/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * MET Norway terms (api.met.no/doc/TermsOfService): "truncate all coordinates to max 4 decimals";
 * 5+ decimals may return 403. The app was sending full-precision doubles .
 */

package org.breezyweather

import org.breezyweather.wbw.WbwMetNo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WbwMetNoCoordTest {

    @Test
    fun coordinatesAreSentWithAtMostFourDecimals() {
        assertEquals("59.9139", WbwMetNo.coord(59.91386901234567).toString())
        assertEquals("10.7522", WbwMetNo.coord(10.75224500987654).toString())
        assertEquals("-33.8688", WbwMetNo.coord(-33.86882).toString())
        assertEquals("10.5", WbwMetNo.coord(10.5).toString())
    }
}
