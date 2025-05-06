package com.harrikirik.rescheck.util

import android.view.View
import android.view.Window
import androidx.annotation.VisibleForTesting
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

object EdgeToEdgeUtil {

    fun applyPaddings(targetView: View, spec: EdgeToEdgeSpec, consume: Boolean) {
        ViewCompat.setOnApplyWindowInsetsListener(targetView) { view, insets ->

            val typeMask = generateWindowInsetMaskFromSpec(spec)
            val systemPaddings = insets.getInsets(
                typeMask,
            )
            applySystemPaddingsBySpec(ViewPaddings.fromView(view), systemPaddings, spec) { paddings ->
                view.setPadding(paddings.left, paddings.top, paddings.right, paddings.bottom)
            }
            if (consume) {
                // Return CONSUMED if you don't want the window insets to keep passing down to descendant views.
                WindowInsetsCompat.CONSUMED
            } else {
                // Pass down
                insets
            }
        }
    }

    fun setLightStatusBarIcons(window: Window) {
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
    }

    @VisibleForTesting
    internal fun applySystemPaddingsBySpec(
        currentViewPaddings: ViewPaddings,
        systemPaddings: Insets,
        spec: EdgeToEdgeSpec,
        applyParams: (paddings: ViewPaddings) -> Unit,
    ) {
        val paddings = ViewPaddings(
            if (spec.setLeft) {
                systemPaddings.left
            } else {
                currentViewPaddings.left
            },
            if (spec.setTop) {
                systemPaddings.top
            } else {
                currentViewPaddings.top
            },
            if (spec.setRight) {
                systemPaddings.right
            } else {
                currentViewPaddings.right
            },
            if (spec.setBottom) {
                systemPaddings.bottom
            } else {
                currentViewPaddings.bottom
            },
        )
        applyParams(paddings)
    }

    @VisibleForTesting
    internal fun generateWindowInsetMaskFromSpec(spec: EdgeToEdgeSpec): Int {
        require(spec.avoidBars or spec.avoidCutout) { "At least one avoid type must be true in spec $spec" }

        val typeMask = if (spec.avoidBars && spec.avoidCutout) {
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
        } else if (spec.avoidBars) {
            WindowInsetsCompat.Type.systemBars()
        } else {
            WindowInsetsCompat.Type.displayCutout()
        }
        return typeMask
    }
}

data class EdgeToEdgeSpec(
    val avoidBars: Boolean = true,
    val avoidCutout: Boolean = true,
    val setLeft: Boolean = true,
    val setTop: Boolean,
    val setRight: Boolean = true,
    val setBottom: Boolean,
) {
    companion object {
        val AVOID_BAR_AND_CUTOUT_SET_ALL = EdgeToEdgeSpec(
            avoidBars = true,
            avoidCutout = true,
            setLeft = true,
            setTop = true,
            setRight = true,
            setBottom = true,
        )
    }
}

internal data class ViewPaddings(val left: Int, val top: Int, val right: Int, val bottom: Int) {
    companion object {
        fun fromView(view: View): ViewPaddings {
            return ViewPaddings(
                left = view.paddingLeft,
                top = view.paddingTop,
                right = view.paddingRight,
                bottom = view.paddingBottom
            )
        }
    }
}
