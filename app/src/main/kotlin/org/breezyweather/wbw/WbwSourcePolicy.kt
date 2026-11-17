/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * F3b : this build shows ads, so only sources whose terms allow commercial
 * use are offered. Single place that decides; SourceManager filters its source list through it.
 */

package org.breezyweather.wbw

object WbwSourcePolicy {
    /** MET Norway: CC BY 4.0, commercial use allowed with attribution and an identifying User-Agent. */
    const val DEFAULT_WEATHER_SOURCE = "metno"

    private val weatherSources = setOf(DEFAULT_WEATHER_SOURCE)

    /**
     * Device geocoder only (no account, no shared quota). GeoNames was used for internal testing with one account
     * shared by every user, which must not ship in a public release (release decision 2026-11-17).
     */
    const val DEFAULT_SEARCH_SOURCE = "nativegeocoder"
    private val locationSearchSources = setOf(DEFAULT_SEARCH_SOURCE)

    /** Offline Natural Earth and the device geocoder. Nominatim is excluded (app default use discouraged). */
    private val reverseGeocodingSources = setOf("naturalearth", "nativegeocoder")

    /** A source with several roles must be allowed in every role it has. */
    fun isAllowed(
        id: String,
        isWeather: Boolean = false,
        isLocationSearch: Boolean = false,
        isReverseGeocoding: Boolean = false,
    ): Boolean = (!isWeather || id in weatherSources) &&
        (!isLocationSearch || id in locationSearchSources) &&
        (!isReverseGeocoding || id in reverseGeocodingSources)
}
