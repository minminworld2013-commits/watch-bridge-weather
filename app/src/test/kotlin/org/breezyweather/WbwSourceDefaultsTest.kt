/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * F3 acceptance test : new locations must default to
 * commercially usable sources, because this build shows ads.
 */

package org.breezyweather

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class WbwSourceDefaultsTest {

    @Test
    fun defaultWeatherSourceIsMetNorway() {
        // Open-Meteo's free API is non-commercial; MET Norway is CC BY 4.0 (product decision 2026-11-17).
        assertEquals("metno", BuildConfig.DEFAULT_FORECAST_SOURCE)
    }

    @Test
    fun defaultLocationSearchSourceIsTheDeviceGeocoder() {
        // Release gate (release decision 2026-11-17): no GeoNames account shared by every user in a public release.
        // GeoNames was used for internal testing only; the device geocoder needs no account or key.
        assertEquals("nativegeocoder", BuildConfig.DEFAULT_LOCATION_SEARCH_SOURCE)
    }

    @Test
    fun reportIssueContactIsSet() {
        // MET Norway terms require a contact in the User-Agent; MetNoService refuses every location when empty
        // (found on device 2026-11-17: Seoul showed "MET Norway (unavailable)" and no data).
        assertTrue(BuildConfig.REPORT_ISSUE.isNotBlank(), "app.report_issue missing from gradle.properties")
    }
}
