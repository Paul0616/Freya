package com.softtehnica.freya.configs

import android.R
import android.app.Activity
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window


class KeyboardUtils {
    //TODO: unused
    interface KeyboardVisibilityListener {
        fun onKeyboardVisibilityChanged(keyboardVisible: Boolean)
    }

    fun setKeyboardVisibilityListener(
        activity: Activity,
        keyboardVisibilityListener: KeyboardVisibilityListener
    ) {
        val contentView: View = activity.findViewById(android.R.id.content)
        contentView.getViewTreeObserver()
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                private var mPreviousHeight = 0
                override fun onGlobalLayout() {
                    val newHeight: Int = contentView.getHeight()
                    if (mPreviousHeight != 0) {
                        if (mPreviousHeight > newHeight) { // Height decreased: keyboard was shown
                            keyboardVisibilityListener.onKeyboardVisibilityChanged(true)
                        } else if (mPreviousHeight < newHeight) { // Height increased: keyboard was hidden
                            keyboardVisibilityListener.onKeyboardVisibilityChanged(false)
                        } else { // No change
                        }
                    }
                    mPreviousHeight = newHeight
                }
            })
    }


}