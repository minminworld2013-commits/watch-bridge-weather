/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * F5 acceptance tests : one banner ad on the main screen.
 */

package org.breezyweather

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class WbwAdsConfigTest {

    private val googleTestBanner = "ca-app-pub-3940256099942544/6300978111"

    @Test
    fun bannerUnitIdIsAnAdMobId() {
        assertTrue(
            Regex("""ca-app-pub-\d{16}/\d{10}""").matches(BuildConfig.WBW_ADMOB_BANNER_ID),
            "WBW_ADMOB_BANNER_ID is not an AdMob ad unit id: ${BuildConfig.WBW_ADMOB_BANNER_ID}"
        )
    }

    @Test
    fun debugBuildsAlwaysUseGoogleTestAds() {
        // Clicking real ads from our own test devices is invalid traffic; debug must never serve real ads.
        if (BuildConfig.DEBUG) assertEquals(googleTestBanner, BuildConfig.WBW_ADMOB_BANNER_ID)
    }
}
