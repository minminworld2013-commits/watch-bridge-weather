/*
 * Watch Bridge Weather — modified version of Breezy Weather (LGPL-3.0).
 * F5 : one adaptive banner at the bottom of the main screen.
 */

package org.breezyweather.wbw

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import org.breezyweather.BuildConfig

object WbwBannerAd {
    private const val TAG = "WbwBannerAd"

    /** Loads a banner into [container]. A failed load leaves the slot empty and is logged (no silent failure). */
    fun attach(activity: Activity, container: ViewGroup) {
        ViewCompat.setOnApplyWindowInsetsListener(container) { v, insets ->
            v.updatePadding(bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom)
            insets
        }
        MobileAds.initialize(activity) {}
        val widthDp = (activity.resources.displayMetrics.widthPixels / activity.resources.displayMetrics.density).toInt()
        val adView = AdView(activity).apply {
            adUnitId = BuildConfig.WBW_ADMOB_BANNER_ID
            setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, widthDp))
            adListener = object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.w(TAG, "banner failed: code=${error.code} ${error.message}")
                }
            }
        }
        // Keep the main content above the banner so nothing (e.g. the add-location button) sits under the ad.
        container.addOnLayoutChangeListener { v, _, top, _, bottom, _, oldTop, _, oldBottom ->
            if (bottom - top == oldBottom - oldTop) return@addOnLayoutChangeListener
            val parent = v.parent as? ViewGroup ?: return@addOnLayoutChangeListener
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                if (child === v || child is androidx.compose.ui.platform.ComposeView) continue
                (child.layoutParams as? ViewGroup.MarginLayoutParams)?.let { lp ->
                    if (lp.bottomMargin != bottom - top) {
                        lp.bottomMargin = bottom - top
                        child.layoutParams = lp
                    }
                }
            }
        }
        container.removeAllViews()
        container.addView(adView)
        adView.loadAd(AdRequest.Builder().build())
    }
}
