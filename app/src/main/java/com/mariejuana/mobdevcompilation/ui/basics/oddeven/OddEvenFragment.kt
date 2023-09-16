package com.mariejuana.mobdevcompilation.ui.basics.oddeven

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mariejuana.mobdevcompilation.databinding.FragmentOddevenBinding

class OddEvenFragment : Fragment() {

    private var _binding: FragmentOddevenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val oddEvenViewModel =
            ViewModelProvider(this).get(OddEvenViewModel::class.java)

        _binding = FragmentOddevenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textOddEven
        val numberInput: EditText = binding.numberInput
        val checkButton: Button = binding.checkButton

        checkButton.setOnClickListener {
            val numberText = numberInput.text.toString()

            try {
                val number = numberText.toInt()
                val result = if (number % 2 == 0) {
                    "even"
                } else {
                    "odd"
                }
                textView.text = "$number is $result."
            } catch (e: NumberFormatException) {
                textView.text = "'$numberText' is not valid input. Please enter a valid number."
            }
        }


        oddEvenViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}