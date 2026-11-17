/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * Release gate : city search through the device geocoder (no account, no shared quota)
 * instead of a GeoNames account shared by every user. Time zone is filled later by the offline time zone source.
 */

package org.breezyweather.wbw

import breezyweather.domain.location.model.LocationAddressInfo

object WbwGeocoderSearch {
    const val MAX_RESULTS = 10

    /** One geocoder result -> search result, or null when it cannot be used as a location. */
    fun toInfo(
        latitude: Double,
        longitude: Double,
        countryCode: String?,
        countryName: String?,
        adminArea: String?,
        subAdminArea: String?,
        locality: String?,
        subLocality: String?,
        featureName: String?,
    ): LocationAddressInfo? {
        if (countryCode.isNullOrEmpty() || (latitude == 0.0 && longitude == 0.0)) return null
        return LocationAddressInfo(
            latitude = latitude,
            longitude = longitude,
            country = countryName,
            countryCode = countryCode,
            admin1 = adminArea,
            admin2 = subAdminArea,
            city = locality ?: featureName ?: adminArea,
            district = subLocality
        )
    }
}
