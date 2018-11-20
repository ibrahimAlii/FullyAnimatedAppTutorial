package ali.ibrhim.fullyanimatedapptutorial.fragments


import ali.ibrhim.fullyanimatedapptutorial.R
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.TYPE_KEY
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.FIRST_KEY
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.THIRD_TYPE_VAL
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.FIRST_TYPE_VAL
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.SECOND_TYPE_VAL
import ali.ibrhim.fullyanimatedapptutorial.cons.Constants.Companion.SECOND_KEY
import ali.ibrhim.fullyanimatedapptutorial.utils.Animation.Companion.slideUp
import ali.ibrhim.fullyanimatedapptutorial.utils.ControlKeyboard.Companion.hideKeyboard
import ali.ibrhim.fullyanimatedapptutorial.utils.ControlKeyboard.Companion.showKeyboard
import ali.ibrhim.fullyanimatedapptutorial.utils.FragmentTransaction.Companion.transitionTo


/**
 * A simple [InputDetailsFragment] which represent the inputs.
 *
 */
class InputDetailsFragment : Fragment() {

    private var type: Int = 0
    var handler = Handler()
    var scrollView: ScrollView? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_input_type, container, false)

        val firstType = view.findViewById<Button>(R.id.firstType)
        val secondType = view.findViewById<Button>(R.id.secondType)
        val thirdType = view.findViewById<Button>(R.id.thirdType)
        val firstInputText = view.findViewById<TextView>(R.id.firstInputText)
        val firstInput = view.findViewById<EditText>(R.id.firstInput)
        val secondInputText = view.findViewById<TextView>(R.id.secondInputText)
        val secondInput = view.findViewById<EditText>(R.id.secondInput)
        val secondInputError = view.findViewById<TextView>(R.id.secondInputError)
        val firstInputError = view.findViewById<TextView>(R.id.firstInputError)
        val enter = view.findViewById<Button>(R.id.enter)
        val firstInputIcon = view.findViewById<ImageView>(R.id.firstInputIcon)
        val secondInputIcon = view.findViewById<ImageView>(R.id.secondInputIcon)
        val anIcon = view.findViewById<ImageView>(R.id.Icon)
        scrollView = view.findViewById(R.id.mainScroll)


        handler.postDelayed({
            slideUp(firstInputText, 100, 600f, false)
            slideUp(firstInputIcon, 100, 600f, false)
            slideUp(firstInput, 200, 600f, true)
            slideUp(enter, 500, 600f, true)

            firstInput.requestFocus()
        }, 600)

        type = arguments!!.getInt(TYPE_KEY)

        // High-Light selected button
        highlightSelectedButton(firstType, secondType, thirdType, type)

        var isFirstInput = true

        enter.backgroundTintList = resources.getColorStateList(R.color.colorDimmed)

        // TextChangeListener on dose
        textChangeListener(firstInput, enter, firstInputError)


        // Listen on focus change inorder to change background
        focusListener(firstInput)
        focusListener(secondInput)

        enter.setOnClickListener {
            if (isFirstInput) {
                // hide keyboard
                hideKeyboard(requireContext(), firstInput)
                // hide enter button
                enter.visibility = GONE
                // slide up the sugar text and editText
                slideUp(secondInputText, 500, 400f, false)
                slideUp(secondInputIcon, 500, 400f, false)
                slideUp(secondInput, 600, 400f, true)

                if (secondInput.text.isEmpty()) {
                    enter.isEnabled = false
                    enter.backgroundTintList = resources.getColorStateList(R.color.colorDimmed)
                }

                secondInput.requestFocus()
                slideUp(enter, 1000, 400f, true)


                isFirstInput = false
            } else {
                val resultFragment = ResultFragment()
                val bundle = Bundle()
                bundle.putInt(FIRST_KEY, firstInput.text.toString().toInt())
                bundle.putInt(SECOND_KEY, secondInput.text.toString().toInt())
                bundle.putInt(TYPE_KEY, type)
                resultFragment.arguments = bundle

                val hashMap = HashMap<String, View>()

                when (type) {
                    FIRST_TYPE_VAL -> hashMap["type"] = firstType
                    SECOND_TYPE_VAL -> hashMap["type"] = secondType
                    THIRD_TYPE_VAL -> hashMap["type"] = thirdType
                }


                hashMap["firstInput"] = firstInput
                hashMap["secondInput"] = secondInput
                hashMap["secondInputText"] = secondInputText
                hashMap["firstInputText"] = firstInputText
                hashMap["firstInputIcon"] = firstInputIcon
                hashMap["secondInputIcon"] = secondInputIcon
                hashMap["Icon"] = anIcon

                secondInputText.text = resources.getString(R.string.current_input)
                firstInputText.text = resources.getString(R.string.second_current_input)

                // moving views slightly to write before start animation
                secondInputText.x = secondInputText.x + 140
                firstInputText.x = firstInputText.x + 140

                // moving inputs to the middle to ge the correct behavior
                secondInput.x = (secondInput.width / 2).toFloat()
                firstInput.x = (firstInput.width / 2).toFloat()
                // focus up
                scrollView!!.fullScroll(View.FOCUS_UP)

                handler.postDelayed({
                    transitionTo(resultFragment, requireContext(), hashMap, true)
                }, 200)

            }
        }


        // TextChangeListener on sugarLevel
        textChangeListener(secondInput, enter, secondInputError)

        firstType.setOnClickListener {
            type = FIRST_TYPE_VAL
            highlightButton(firstType)
            dimmedButtons(secondType, thirdType)
        }

        secondType.setOnClickListener {
            type = SECOND_TYPE_VAL
            highlightButton(secondType)
            dimmedButtons(firstType, thirdType)
        }

        thirdType.setOnClickListener {
            type = THIRD_TYPE_VAL
            highlightButton(thirdType)
            dimmedButtons(secondType, firstType)
        }

        return view
    }


    private fun highlightSelectedButton(
            longActing: Button, shortActing: Button, intermediate: Button,
            actingType: Int
    ) {
        when (actingType) {
            FIRST_TYPE_VAL -> highlightButton(longActing)
            SECOND_TYPE_VAL -> highlightButton(shortActing)
            THIRD_TYPE_VAL -> highlightButton(intermediate)
        }
    }

    private fun highlightButton(highlightedButton: Button) {
        highlightedButton.background = resources.getDrawable(R.drawable.but_bg_black)
        highlightedButton.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private fun dimmedButtons(dimmedButton1: Button, dimmedButton2: Button) {
        dimmedButton(dimmedButton1)
        dimmedButton(dimmedButton2)
    }

    private fun dimmedButton(dimmedButton: Button) {
        dimmedButton.background = resources.getDrawable(R.drawable.but_bg)
        dimmedButton.setTextColor(resources.getColor(R.color.colorPrimaryDark))
    }

    private fun textChangeListener(editText: EditText, enter: Button, errorTextView: TextView) {
        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
            ) {

                if (editText.id == R.id.firstInput) {
                    checkForFalseInputs(s, enter, 1, 999, errorTextView, resources.getString(R.string.first_input))
                } else if (editText.id == R.id.secondInput) {
                    checkForFalseInputs(s, enter, 15, 800, errorTextView, resources.getString(R.string.second_input))
                }

            }
        })
    }

    private fun checkForFalseInputs(s: CharSequence, enter: Button, min: Int, max: Int, errorTextView: TextView,
                                    s1: String) {
        if (s.isNotEmpty() && s.toString().toInt() > 0 && s.toString()[0] != '0'
                && s.toString().toInt() >= min && s.toString().toInt() <= max
        ) {
            Log.d("TextWatcher", s.toString().toInt().toString())
            enter.isEnabled = true
            enter.backgroundTintList = resources.getColorStateList(R.color.colorPrimaryDark)
            errorTextView.visibility = GONE
        } else {
            enter.isEnabled = false
            enter.backgroundTintList = resources.getColorStateList(R.color.colorDimmed)
            errorTextView.text = s1.plus(" ").plus(resources.getString(R.string.should)).plus(" ")
                    .plus(resources.getString(R.string.between)).plus(" ")
                    .plus(min).plus(" ").plus(resources.getString(R.string.and)).plus(" ").plus(max)
            errorTextView.visibility = VISIBLE
        }
    }

    private fun focusListener(editText: EditText) {
        editText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(requireContext(), editText)
                editText.background = resources.getDrawable(R.drawable.et_background)
            } else {
                if (editText.id == R.id.secondInput)
                    handler.postDelayed({
                        showKeyboard(requireContext(), editText)

                        handler.postDelayed({
                            scrollView!!.fullScroll(View.FOCUS_DOWN)
                        }, 300)

                    }, 1200)
                else showKeyboard(requireContext(), editText)
                editText.background = resources.getDrawable(R.drawable.et_bg)
            }
        }
    }



    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }



}
