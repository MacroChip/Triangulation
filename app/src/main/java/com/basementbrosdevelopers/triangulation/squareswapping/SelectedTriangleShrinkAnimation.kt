package com.basementbrosdevelopers.triangulation.squareswapping

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView

class SelectedTriangleShrinkAnimation(
        private val animationDuration: Long,
        private val triangle: ImageView
) {

    companion object {
        const val SELECTED_TRIANGLE_SCALING = 0.85f
    }

    fun play() {
        AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(triangle, View.SCALE_X, SELECTED_TRIANGLE_SCALING))
                    .with(ObjectAnimator.ofFloat(triangle, View.SCALE_Y, SELECTED_TRIANGLE_SCALING))
            duration = animationDuration
            interpolator = DecelerateInterpolator()
            start()
        }
    }
}