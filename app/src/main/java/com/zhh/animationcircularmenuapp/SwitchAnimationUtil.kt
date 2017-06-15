package com.zhh.animationcircularmenuapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator

/**

 * 菜单动画加载类
 */
class SwitchAnimationUtil {
    private var mOrderIndex = 0
    private val mDelay = 80
    private val mDuration = 500

    fun startAnimation(root: View, type: AnimationType) {
        bindAnimation(root, 0, type)
    }

    private fun bindAnimation(view: View, depth: Int, type: AnimationType) {

        if (view is ViewGroup) {
            val group = view

            for (i in 0..group.childCount - 1) {
                bindAnimation(group.getChildAt(i), depth + 1, type)
            }

        } else {
            runAnimation(view, (mDelay * mOrderIndex).toLong(), type)
            mOrderIndex++
        }
    }

    private fun runAnimation(view: View, delay: Long, type: AnimationType) {
        when (type) {
            SwitchAnimationUtil.AnimationType.ROTATE -> runRotateAnimation(view, delay)
            SwitchAnimationUtil.AnimationType.ALPHA -> runAlphaAnimation(view, delay)
            else -> {
            }
        }
    }

    private fun runAlphaAnimation(view: View, delay: Long) {
        view.alpha = 0f
        val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        objectAnimator.startDelay = delay
        objectAnimator.duration = mDuration.toLong()
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.start()
    }

    private fun runRotateAnimation(view: View, delay: Long) {
        view.alpha = 0f
        val set = AnimatorSet()
        val objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 0f)
        val objectAnimator2 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
        val objectAnimator3 = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)
        val objectAnimator4 = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)

        objectAnimator2.interpolator = AccelerateInterpolator(1.0f)
        objectAnimator3.interpolator = AccelerateInterpolator(1.0f)

        set.duration = mDuration.toLong()
        set.playTogether(objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4)
        set.startDelay = delay
        set.start()
    }

    enum class AnimationType {
        ALPHA, ROTATE
    }
}
