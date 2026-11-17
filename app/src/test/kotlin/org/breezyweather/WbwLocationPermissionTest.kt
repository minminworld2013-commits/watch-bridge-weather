/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * F4 regression (user report 2026-11-17): refreshing the "current location"
 * entry spun forever. The device location source still listed ACCESS_FINE_LOCATION, which F4 removed from
 * the manifest, so it could never be granted: every refresh raised a permission request, and since F4 no
 * longer re-asks, nothing ever answered it.
 */

package org.breezyweather

import android.Manifest
import org.breezyweather.sources.android.AndroidLocationService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WbwLocationPermissionTest {

    @Test
    fun deviceLocationAsksOnlyForApproximateLocation() {
        assertEquals(
            listOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            AndroidLocationService().permissions.toList()
        )
    }
}
