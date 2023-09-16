package com.mariejuana.mobdevcompilation.ui.basics.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mariejuana.mobdevcompilation.R
import com.mariejuana.mobdevcompilation.databinding.FragmentAndroidStatusBinding
import com.mariejuana.mobdevcompilation.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
    private var _binding: FragmentAndroidStatusBinding? = null

    private lateinit var binding: FragmentCalculatorBinding
    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val expressionTextView: TextView = binding.expressionTextView
        val resultTextView: TextView = binding.resultTextView


        binding.apply {
            // Set up click listeners for digit buttons
            button0.setOnClickListener { viewModel.onDigitClicked("0") }
            button1.setOnClickListener { viewModel.onDigitClicked("1") }
            button2.setOnClickListener { viewModel.onDigitClicked("2") }
            button3.setOnClickListener { viewModel.onDigitClicked("3") }
            button4.setOnClickListener { viewModel.onDigitClicked("4") }
            button5.setOnClickListener { viewModel.onDigitClicked("5") }
            button6.setOnClickListener { viewModel.onDigitClicked("6") }
            button7.setOnClickListener { viewModel.onDigitClicked("7") }
            button8.setOnClickListener { viewModel.onDigitClicked("8") }
            button9.setOnClickListener { viewModel.onDigitClicked("9") }

            // Set up click listeners for operator buttons
            buttonPlus.setOnClickListener { viewModel.onOperatorClicked("+") }
            buttonMinus.setOnClickListener { viewModel.onOperatorClicked("-") }
            buttonMultiply.setOnClickListener { viewModel.onOperatorClicked("*") }
            buttonDivide.setOnClickListener { viewModel.onOperatorClicked("/") }

            // Set up click listeners for other buttons
            buttonClear.setOnClickListener { viewModel.onClearClicked() }
            buttonEquals.setOnClickListener { viewModel.onEqualsClicked() }
            buttonDecimal.setOnClickListener { viewModel.onDecimalClicked() }
        }

        // Observe the expression LiveData from the ViewModel and update the expression TextView
        viewModel.expression.observe(viewLifecycleOwner) { expression ->
            expressionTextView.text = expression
        }

        // Observe the result LiveData from the ViewModel and update the result TextView
        viewModel.result.observe(viewLifecycleOwner) { result ->
            resultTextView.text = result
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}