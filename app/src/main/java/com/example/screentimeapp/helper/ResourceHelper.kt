/**
 * @author Carlo Barnardo
 * @editor Sebastian Klopper
 *
 * Contains all the helper functions for the resource handling
 */
package com.example.screentimeapp.helper

import android.content.Context
import android.graphics.Typeface
import android.util.SparseArray
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import java.lang.ref.WeakReference


object ResourceHelper {
    /**
     * Holds a weak reference to the context
     */
    private var context: WeakReference<Context>? = null

    /**
     * Sets the context
     */
    fun setup(c: Context) {
        context = WeakReference(c)
    }

    /**
     * Converts dp to px
     */

    // TODO: DP? PX? Make more verbose
    fun dp(value: Float): Int {
        val c = context?.get() ?: return 0
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, c.resources.displayMetrics))
    }

    /**
     * Converts to float
     */
    fun dp(value: Int): Int = dp(value.toFloat())

    /**
     * Returns the dimensions of the resource
     */
    fun dimen(@DimenRes resId: Int): Int {
        return context?.get()?.resources?.getDimensionPixelOffset(resId) ?: 0
    }

    /**
     * Returns the color
     */
    fun color(@ColorRes resId: Int): Int {
        val c = context?.get() ?: return 0
        return ContextCompat.getColor(c,resId)
    }

    /**
     * Defines the font cache
     */
    private var sFontCache: SparseArray<Typeface> = SparseArray()

    /**
     * Returns the font from the cache or the resource
     */
    fun font(@FontRes resId: Int): Typeface? {
        return if (sFontCache.indexOfKey(resId) >= 0){
            sFontCache.get(resId)
        } else{
            val con = context?.get()
            val cache = if (con != null) ResourcesCompat.getFont(con,resId) else null
            if (cache != null) sFontCache.put(resId,cache)
            cache
        }
    }
}