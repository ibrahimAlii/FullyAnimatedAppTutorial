package ali.ibrhim.fullyanimatedapptutorial.transition

import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet

class MyTransition : TransitionSet() {


    init {
        init()
    }


    private fun init() {
        ordering = TransitionSet.ORDERING_TOGETHER
        addTransition(ChangeBounds()).addTransition(ChangeTransform()).addTransition(ChangeImageTransform()).duration = 750
    }
}