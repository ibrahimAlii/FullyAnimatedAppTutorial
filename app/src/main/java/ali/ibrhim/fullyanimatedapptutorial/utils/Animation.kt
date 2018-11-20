package ali.ibrhim.fullyanimatedapptutorial.utils

import android.view.View
import android.view.animation.TranslateAnimation
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.animation.SpringForce.*
import android.view.animation.Animation


class Animation {


    companion object {

        fun slideUp(view: View, duration: Long, fromYDelta: Float, haveSpringAnimation: Boolean) {
            view.visibility = View.VISIBLE
            val animate = TranslateAnimation(
                0f, // fromXDelta
                0f, // toXDelta
                fromYDelta, // fromYDelta
                0f
            ) // toYDelta
            animate.duration = duration
            animate.fillAfter = true
            view.startAnimation(animate)

            if (haveSpringAnimation)
                animate.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        createSpringAnim(
                            view,
                            DynamicAnimation.TRANSLATION_Y
                        ).animateToFinalPosition(-30f)
                    }

                    override fun onAnimationStart(p0: Animation?) {

                    }

                })
        }


        fun createSpringAnim(
            view: View,
            property: DynamicAnimation.ViewProperty
        ): SpringAnimation {
            return SpringAnimation(view, property).setSpring(SpringForce().apply {
                stiffness = STIFFNESS_LOW
                dampingRatio = DAMPING_RATIO_HIGH_BOUNCY

            })
        }
    }
}