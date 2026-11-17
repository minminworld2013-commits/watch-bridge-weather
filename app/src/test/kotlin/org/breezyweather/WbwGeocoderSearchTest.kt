/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * Release gate : city search through the device geocoder instead of a
 * shared GeoNames account. Mapping of one geocoder result to a search result.
 */

package org.breezyweather

import org.breezyweather.wbw.WbwGeocoderSearch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class WbwGeocoderSearchTest {

    @Test
    fun usableResultKeepsCoordinatesAndNames() {
        val info = WbwGeocoderSearch.toInfo(
            latitude = 37.5665, longitude = 126.978, countryCode = "KR", countryName = "대한민국",
            adminArea = "서울특별시", subAdminArea = null, locality = "중구", subLocality = null, featureName = "중구"
        )!!
        assertEquals(37.5665, info.latitude)
        assertEquals(126.978, info.longitude)
        assertEquals("KR", info.countryCode)
        assertEquals("서울특별시", info.admin1)
        assertEquals("중구", info.city)
    }

    @Test
    fun cityFallsBackToFeatureNameThenAdminArea() {
        assertEquals(
            "제주도",
            WbwGeocoderSearch.toInfo(33.4, 126.5, "KR", null, "제주특별자치도", null, null, null, "제주도")!!.city
        )
        assertEquals(
            "Oslo",
            WbwGeocoderSearch.toInfo(59.9, 10.7, "NO", null, "Oslo", null, null, null, null)!!.city
        )
    }

    @Test
    fun resultWithoutCountryOrCoordinatesIsDropped() {
        assertNull(WbwGeocoderSearch.toInfo(37.5, 127.0, null, null, null, null, "X", null, null))
        assertNull(WbwGeocoderSearch.toInfo(0.0, 0.0, "KR", null, null, null, "X", null, null))
    }
}
