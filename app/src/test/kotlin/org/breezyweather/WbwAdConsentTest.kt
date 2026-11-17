/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * F6 UMP consent gate: ads are only requested when consent allows.
 */

package org.breezyweather

import org.breezyweather.wbw.WbwAdConsent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class WbwAdConsentTest {

    // --- gate: onCanLoad callback behaviour ---

    @Test
    fun onCanLoadNotInvokedWhenConsentDenied() {
        var invoked = false
        WbwAdConsent.gateLoad(canRequestAds = false) { invoked = true }
        assertFalse(invoked, "gate must NOT invoke onCanLoad when canRequestAds=false")
    }

    @Test
    fun onCanLoadInvokedExactlyOnceWhenConsentGranted() {
        var count = 0
        WbwAdConsent.gateLoad(canRequestAds = true) { count++ }
        assertEquals(1, count, "gate must invoke onCanLoad exactly once when canRequestAds=true")
    }

    // --- debug geography: flag is passed in, not read from BuildConfig inside the logic ---

    @Test
    fun debugGeographyEnabledWhenIsDebugTrue() {
        assertTrue(
            WbwAdConsent.shouldUseDebugGeography(isDebug = true),
            "debug geography must be enabled when isDebug=true"
        )
    }

    @Test
    fun debugGeographyDisabledWhenIsDebugFalse() {
        assertFalse(
            WbwAdConsent.shouldUseDebugGeography(isDebug = false),
            "debug geography must NOT be enabled in release (isDebug=false)"
        )
    }
}
