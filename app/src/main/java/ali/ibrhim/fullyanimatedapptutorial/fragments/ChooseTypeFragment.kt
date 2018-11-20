package ali.ibrhim.fullyanimatedapptutorial.fragments


import ali.ibrhim.fullyanimatedapptutorial.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.TYPE_KEY
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.THIRD_TYPE_VAL
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.FIRST_TYPE_VAL
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.SECOND_TYPE_VAL
import ali.ibrhim.fullyanimatedapptutorial.utils.Application.Companion.changeLanguage
import ali.ibrhim.fullyanimatedapptutorial.utils.FragmentTransaction.Companion.transitionTo
import android.os.Build
import java.util.*


/**
 * A simple [ChooseTypeFragment] subclass.
 * and then continue...
 *
 */
class ChooseTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chose_type, container, false)


        val typeText = view.findViewById<TextView>(R.id.chooseTypeText)
        val firstType = view.findViewById<Button>(R.id.firstType)
        val secondType = view.findViewById<Button>(R.id.secondType)
        val thirdType = view.findViewById<Button>(R.id.thirdType)
        val language = view.findViewById<TextView>(R.id.language)
        val andIcon = view.findViewById<ImageView>(R.id.Icon)


        val readWriteMap = hashMapOf(
            "firstType" to firstType, "secondType" to secondType,
            "thirdType" to thirdType, "TypeText" to typeText,
            "Icon" to andIcon
        )
        val sharedElements: Map<String, View> = HashMap(readWriteMap)
        val bundle = Bundle()

        val sugarDetailsFragment = InputDetailsFragment()

        firstType.setOnClickListener {
            bundle.putInt(TYPE_KEY, FIRST_TYPE_VAL)
            sugarDetailsFragment.arguments = bundle
            transitionTo(sugarDetailsFragment, requireContext(), sharedElements, true)
        }
        secondType.setOnClickListener {
            bundle.putInt(TYPE_KEY, SECOND_TYPE_VAL)
            sugarDetailsFragment.arguments = bundle
            transitionTo(sugarDetailsFragment, requireContext(), sharedElements, true)
        }
        thirdType.setOnClickListener {
            bundle.putInt(TYPE_KEY, THIRD_TYPE_VAL)
            sugarDetailsFragment.arguments = bundle
            transitionTo(sugarDetailsFragment, requireContext(), sharedElements, true)
        }

        language.setOnClickListener {

            val lang: String = if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    resources.configuration.locales[0].language == "en"
                } else {
                    resources.configuration.locale.language == "en"
                }
            )
                "ar"
            else "en"

            changeLanguage(requireContext(), lang)

            activity?.recreate()

        }

        return view
    }


}
