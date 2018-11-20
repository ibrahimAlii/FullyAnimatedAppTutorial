package ali.ibrhim.fullyanimatedapptutorial.fragments


import ali.ibrhim.fullyanimatedapptutorial.R
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.TYPE_KEY
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.FIRST_KEY
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.THIRD_TYPE_VAL
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.FIRST_TYPE_VAL
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.SECOND_TYPE_VAL
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.SECOND_KEY
import ali.ibrhim.fullyanimatedapptutorial.utils.Animation.Companion.slideUp
import ali.ibrhim.fullyanimatedapptutorial.utils.FragmentTransaction.Companion.transitionTo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


/**
 * A simple [ResultFragment] which represent the results and show it to user.
 *
 */
class ResultFragment : Fragment() {
    private var durationOverAll: Long = 200
    private var firstInputVal: Int = 0
    private var secondInputVal: Int = 0
    private var type: Int = 0
    var handler = Handler()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)


        val type = view.findViewById<TextView>(R.id.type)
        val firstInput = view.findViewById<TextView>(R.id.firstInput)
        val secondInput = view.findViewById<TextView>(R.id.secondInput)

        val resultBox = view.findViewById<View>(R.id.result_box)
        val resultIcon = view.findViewById<ImageView>(R.id.sugar_arrow)
        val newTypeText = view.findViewById<TextView>(R.id.newDoseText)
        val units = view.findViewById<TextView>(R.id.units)
        val typeType = view.findViewById<TextView>(R.id.doseType)
        val resultDescription = view.findViewById<TextView>(R.id.resultDescription)
        val recalculate = view.findViewById<Button>(R.id.recalculate)

        firstInputVal = arguments!!.getInt(FIRST_KEY)
        secondInputVal = arguments!!.getInt(SECOND_KEY)
        this.type = arguments!!.getInt(TYPE_KEY)

        // Slide-Up result-container
        slideUp(resultBox, 500, 900f, true)

        type.text = getType(this.type)
        typeType.text = getType(this.type)
        firstInput.text = firstInputVal.toString()
        secondInput.text = secondInputVal.toString()
        val calculatedUnits = 61/*calcUnit(type, firstInputVal, secondInputVal)*/

        // set units text
        units.text = calculatedUnits.toString().plus(" ").plus(resources.getString(R.string.units))

        // set description text
        setDescriptionText(resultDescription, secondInputVal)

        handler.postDelayed({
            slideUp(resultIcon, 300, 100f, true)
            durationOverAll += 150

            handler.postDelayed({
                slideUp(newTypeText, 70, 200f, true)
                durationOverAll -= 90

                handler.postDelayed({
                    slideUp(units, 150, 250f, true)
                    durationOverAll += 100

                    handler.postDelayed({
                        slideUp(typeType, 150, 200f, true)
                        durationOverAll -= 100

                        handler.postDelayed({
                            slideUp(resultDescription, 150, 200f, true)
                            durationOverAll += 100

                            handler.postDelayed({
                                slideUp(recalculate, 250, 200f, true)
                                durationOverAll += 100

                            }, durationOverAll)

                        }, durationOverAll)

                    }, durationOverAll)

                }, durationOverAll)

            }, durationOverAll)

        }, durationOverAll)


        recalculate.setOnClickListener {
            transitionTo(ChooseTypeFragment(), requireContext(), null, false)
        }


        return view
    }

    private fun setDescriptionText(resultDescription: TextView, cost: Int) {
        if (cost > 250)
            resultDescription.text = "May God Bless you! \t May God Bless you! \t\t May God Bless you!"
        else resultDescription.text = "If you fail seven times try one more time!\tIf you fail seven times try one more time!\t" +
                "If you fail seven times try one more time!"
    }

    private fun getType(actingType: Int): String {
        return when (actingType) {
            FIRST_TYPE_VAL -> resources.getString(R.string.first_type)
            SECOND_TYPE_VAL -> resources.getString(R.string.second_type)
            THIRD_TYPE_VAL -> resources.getString(R.string.third_type)
            else -> ""
        }
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }

}
