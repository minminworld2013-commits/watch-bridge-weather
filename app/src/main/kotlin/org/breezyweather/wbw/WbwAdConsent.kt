/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * F6 UMP consent gate: ads are only requested when consent allows.
 */

package org.breezyweather.wbw

import android.app.Activity
import android.util.Log
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import org.breezyweather.BuildConfig

object WbwAdConsent {

    private const val TAG = "WbwAdConsent"

    /** Testable seam: invokes [onCanLoad] only when consent allows. Pure — no Android dependency. */
    internal fun gateLoad(canRequestAds: Boolean, onCanLoad: () -> Unit) {
        if (canRequestAds) onCanLoad()
    }

    /** Testable flag: debug geography is applied only when [isDebug] is true. Pure — caller passes the flag. */
    internal fun shouldUseDebugGeography(isDebug: Boolean): Boolean = isDebug

    /**
     * Runs the UMP consent flow on app start, then invokes [onCanLoad] iff ads are permitted.
     * Weather features are unaffected if consent fails or there is no network.
     */
    fun requestConsentAndLoad(activity: Activity, onCanLoad: () -> Unit) {
        val params = buildParams(activity)
        val info = UserMessagingPlatform.getConsentInformation(activity)
        info.requestConsentInfoUpdate(
            activity,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { formError ->
                    if (formError != null) Log.w(TAG, "consent form: ${formError.message}")
                    gateLoad(info.canRequestAds(), onCanLoad)
                }
            },
            { requestError ->
                Log.w(TAG, "consent update failed: ${requestError.message}")
                gateLoad(info.canRequestAds(), onCanLoad)
            }
        )
    }

    private fun buildParams(activity: Activity): ConsentRequestParameters {
        return if (shouldUseDebugGeography(BuildConfig.DEBUG)) {
            val debug = ConsentDebugSettings.Builder(activity)
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                .build()
            ConsentRequestParameters.Builder()
                .setConsentDebugSettings(debug)
                .build()
        } else {
            ConsentRequestParameters.Builder().build()
        }
    }
}
