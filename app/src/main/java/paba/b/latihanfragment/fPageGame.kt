package paba.b.latihanfragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fPageGame.newInstance] factory method to
 * create an instance of this fragment.
 */
class fPageGame : Fragment() {
    // TODO: Rename and change types of parameters
    private var tempScore = 0
    private var options = mutableListOf<Int>()
    private val selectedButtons = mutableListOf<Button>()
    private val matchedPairs = mutableSetOf<Button>()

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f_page_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scoreNum = view.findViewById<TextView>(R.id.tvScore)

        if (arguments != null) {
            val terimaBoundary = arguments?.getString("SETTING")?.toIntOrNull()

            if (terimaBoundary != null) {
                for (i in terimaBoundary until terimaBoundary + 5) {
                    options.add(i)
                    options.add(i)
                }
            } else {
                options = arrayOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5).toMutableList()
            }
        } else {
            options = arrayOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5).toMutableList()
        }

        options.shuffle()
        val buttons = listOf(
            view.findViewById<Button>(R.id.btnNumber1),
            view.findViewById<Button>(R.id.btnNumber2),
            view.findViewById<Button>(R.id.btnNumber3),
            view.findViewById<Button>(R.id.btnNumber4),
            view.findViewById<Button>(R.id.btnNumber5),
            view.findViewById<Button>(R.id.btnNumber6),
            view.findViewById<Button>(R.id.btnNumber7),
            view.findViewById<Button>(R.id.btnNumber8),
            view.findViewById<Button>(R.id.btnNumber9),
            view.findViewById<Button>(R.id.btnNumber10)
        )

//        val _arrayView = view.findViewById<TextView>(R.id.array)
//        _arrayView.text = options.toString()

        buttons.forEachIndexed { index, button ->
            button.tag = options[index]
            button.setOnClickListener {
                // Ignore if button is already matched or selected
                if (button in matchedPairs || button in selectedButtons) return@setOnClickListener

                // Reveal the number
                button.text = button.tag.toString()
                selectedButtons.add(button)

                // Check for a match if two buttons are selected
                if (selectedButtons.size == 2) {
                    val (firstButton, secondButton) = selectedButtons

                    if (firstButton.tag == secondButton.tag) {
                        // It's a match
                        tempScore += 10
                        scoreNum.text = "$tempScore"
                        matchedPairs.addAll(selectedButtons)
                        firstButton.isEnabled = false
                        secondButton.isEnabled = false
                        selectedButtons.clear()

                        if (matchedPairs.size == buttons.size) {
                            // All cards are revealed, navigate to the next fragment
                            val mFragmentManager = parentFragmentManager
                            val nextFragment = pageScore()
                            val mBundle = Bundle()
                            mBundle.putString("SCORE", scoreNum.text.toString())
                            nextFragment.arguments = mBundle
                            mFragmentManager.beginTransaction().apply {
                                replace(R.id.frameContainer, nextFragment, pageScore::class.java.simpleName)
                                addToBackStack(null)
                                commit()
                            }
                        }
                    } else {
                        // No match - hide numbers after a delay
                        tempScore -= 5
                        scoreNum.text = "$tempScore"
                        Handler(Looper.getMainLooper()).postDelayed({
                            firstButton.text = ""
                            secondButton.text = ""
                            selectedButtons.clear()
                        }, 500)
                    }
                }
            }
        }

        val mFragmentManager = parentFragmentManager
        mFragmentManager.findFragmentByTag(pageScore::class.java.simpleName)
        val mBundle = Bundle()

        val _btnGiveUp = view.findViewById<Button>(R.id.btnGiveUp)
        _btnGiveUp.setOnClickListener {

            val mfScore = pageScore()
            mBundle.putString("SCORE", scoreNum.text.toString())
            mfScore.arguments = mBundle
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mfScore, pageScore::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fPageSatu.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fPageGame().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}