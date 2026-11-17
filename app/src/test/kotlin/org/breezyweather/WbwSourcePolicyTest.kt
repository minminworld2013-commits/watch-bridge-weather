/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * F3b acceptance tests : this build shows ads, so only sources whose terms
 * allow commercial use may be offered. Found in testing 2026-11-17: Open-Meteo was selected.
 */

package org.breezyweather

import org.breezyweather.wbw.WbwSourcePolicy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class WbwSourcePolicyTest {

    @Test
    fun onlyMetNorwayIsOfferedAsWeatherSource() {
        assertTrue(WbwSourcePolicy.isAllowed("metno", isWeather = true))
        listOf("openmeteo", "accu", "openweather", "pirateweather", "nws", "china", "infoplaza").forEach {
            assertFalse(WbwSourcePolicy.isAllowed(it, isWeather = true), "$it must be hidden")
        }
    }

    @Test
    fun locationSearchUsesTheDeviceGeocoderOnly() {
        // Release gate (release decision 2026-11-17): no shared GeoNames account in a public release.
        assertTrue(
            WbwSourcePolicy.isAllowed("nativegeocoder", isLocationSearch = true, isReverseGeocoding = true)
        )
        assertFalse(WbwSourcePolicy.isAllowed("geonames", isLocationSearch = true))
        assertFalse(WbwSourcePolicy.isAllowed("openmeteo", isWeather = true, isLocationSearch = true))
    }

    @Test
    fun defaultLocationSearchSourceMatchesBuildDefault() {
        assertEquals(BuildConfig.DEFAULT_LOCATION_SEARCH_SOURCE, WbwSourcePolicy.DEFAULT_SEARCH_SOURCE)
    }

    @Test
    fun reverseGeocodingAvoidsNominatim() {
        // Nominatim policy strongly discourages app default use.
        assertTrue(WbwSourcePolicy.isAllowed("naturalearth", isReverseGeocoding = true))
        assertTrue(WbwSourcePolicy.isAllowed("nativegeocoder", isReverseGeocoding = true))
        assertFalse(WbwSourcePolicy.isAllowed("nominatim", isReverseGeocoding = true))
    }

    @Test
    fun otherRolesUntouched() {
        assertTrue(WbwSourcePolicy.isAllowed("native")) // device location
        assertTrue(WbwSourcePolicy.isAllowed("gadgetbridge")) // broadcast to Watch Bridge
    }

    @Test
    fun defaultWeatherSourceMatchesBuildDefault() {
        assertEquals(BuildConfig.DEFAULT_FORECAST_SOURCE, WbwSourcePolicy.DEFAULT_WEATHER_SOURCE)
    }
}
