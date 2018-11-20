package ali.ibrhim.fullyanimatedapptutorial

import ali.ibrhim.fullyanimatedapptutorial.fragments.ChooseTypeFragment
import ali.ibrhim.fullyanimatedapptutorial.utils.FragmentTransaction.Companion.transitionTo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transitionTo(ChooseTypeFragment(), this, null, false)
    }
}
