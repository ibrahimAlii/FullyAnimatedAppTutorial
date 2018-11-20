package ali.ibrhim.fullyanimatedapptutorial.utils

import ali.ibrhim.fullyanimatedapptutorial.R
import ali.ibrhim.fullyanimatedapptutorial.fragments.ChooseTypeFragment
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.transition.Fade
import android.util.Log
import android.view.View
import ali.ibrhim.fullyanimatedapptutorial.transition.MyTransition


class FragmentTransaction {


    companion object {

        /**
         * Helper method to navigate between fragments
         *
         * @param fragment which represent the fragment to navigate to
         * @param context which represent the current context
         * @param sharedElements which represent the sharedElements to transition [Value = view, Key = key].
         *
         */
        fun transitionTo(
                fragment: Fragment, context: Context, sharedElements: Map<String, View>?,
                addToStack: Boolean
        ) {

            val activity = context as FragmentActivity
            val manager = activity.supportFragmentManager
            val ft = manager.beginTransaction().replace(R.id.fragment, fragment)

            // Clear stack if the fragment is [ChooseTypeFragment]
            if (fragment is ChooseTypeFragment)
                for (i in 0 until manager.backStackEntryCount)
                    manager.popBackStack()

            fragment.sharedElementEnterTransition = MyTransition()
            fragment.enterTransition = Fade()
            fragment.exitTransition = Fade()
            fragment.sharedElementReturnTransition = MyTransition()

            sharedElements?.forEach { (key, value) ->
                Log.d("Transaction", key)
                ft.addSharedElement(value, key)
            }

            if (addToStack)
                ft.addToBackStack(fragment.tag).commit()
            else ft.commit()

        }
    }
}