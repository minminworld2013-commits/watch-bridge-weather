/*
 * This file is part of Breezy Weather.
 *
 * Breezy Weather is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, version 3 of the License.
 *
 * Breezy Weather is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Breezy Weather. If not, see <https://www.gnu.org/licenses/>.
 */

package org.breezyweather.sources.android

import android.content.Context
import android.location.Geocoder
import breezyweather.domain.location.model.LocationAddressInfo
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.rx3.rxObservable
import org.breezyweather.common.exceptions.LocationSearchException
import org.breezyweather.common.extensions.currentLocale
import org.breezyweather.wbw.WbwGeocoderSearch
import javax.inject.Inject

class AndroidGeocoderService @Inject constructor() : AndroidGeocoderServiceStub() {

    override fun requestNearestLocation(
        context: Context,
        latitude: Double,
        longitude: Double,
    ): Observable<List<LocationAddressInfo>> {
        val geocoder = Geocoder(context, context.currentLocale)
        return rxObservable {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)

            val locationList = mutableListOf<LocationAddressInfo>()
            addresses?.getOrNull(0)?.let {
                locationList.add(
                    LocationAddressInfo(
                        city = it.locality,
                        district = it.subLocality,
                        admin1 = it.adminArea,
                        admin2 = it.subAdminArea,
                        country = it.countryName,
                        countryCode = it.countryCode
                    )
                )
            }
            send(locationList)
        }
    }

    // WBW: city search through the device geocoder
    override fun requestLocationSearch(
        context: Context,
        query: String,
    ): Observable<List<LocationAddressInfo>> {
        if (!Geocoder.isPresent()) throw LocationSearchException()
        val geocoder = Geocoder(context, context.currentLocale)
        return rxObservable {
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocationName(query, WbwGeocoderSearch.MAX_RESULTS) ?: emptyList()
            send(
                addresses.filter { it.hasLatitude() && it.hasLongitude() }.mapNotNull {
                    WbwGeocoderSearch.toInfo(
                        latitude = it.latitude,
                        longitude = it.longitude,
                        countryCode = it.countryCode,
                        countryName = it.countryName,
                        adminArea = it.adminArea,
                        subAdminArea = it.subAdminArea,
                        locality = it.locality,
                        subLocality = it.subLocality,
                        featureName = it.featureName
                    )
                }
            )
        }
    }
}
