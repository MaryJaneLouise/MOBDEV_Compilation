package com.mariejuana.mobdevcompilation.ui.basics.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.text.SpannableStringBuilder
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mariejuana.mobdevcompilation.R
import com.mariejuana.mobdevcompilation.databinding.FragmentAndroidStatusBinding
import com.mariejuana.mobdevcompilation.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView
    private var isOperator = false
    private var hasOperator = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_calculator, container, false)
        expressionTextView = rootView.findViewById(R.id.expressionTextView)
        resultTextView = rootView.findViewById(R.id.resultTextView)

        rootView.findViewById<Button>(R.id.button0).setOnClickListener { numberButtonClicked("00") }
        rootView.findViewById<Button>(R.id.button0).setOnClickListener { numberButtonClicked("0") }
        rootView.findViewById<Button>(R.id.button1).setOnClickListener { numberButtonClicked("1") }
        rootView.findViewById<Button>(R.id.button2).setOnClickListener { numberButtonClicked("2") }
        rootView.findViewById<Button>(R.id.button3).setOnClickListener { numberButtonClicked("3") }
        rootView.findViewById<Button>(R.id.button4).setOnClickListener { numberButtonClicked("4") }
        rootView.findViewById<Button>(R.id.button5).setOnClickListener { numberButtonClicked("5") }
        rootView.findViewById<Button>(R.id.button6).setOnClickListener { numberButtonClicked("6") }
        rootView.findViewById<Button>(R.id.button7).setOnClickListener { numberButtonClicked("7") }
        rootView.findViewById<Button>(R.id.button8).setOnClickListener { numberButtonClicked("8") }
        rootView.findViewById<Button>(R.id.button9).setOnClickListener { numberButtonClicked("9") }

        rootView.findViewById<Button>(R.id.buttonPlus).setOnClickListener { operatorButtonClicked("+") }
        rootView.findViewById<Button>(R.id.buttonMinus).setOnClickListener { operatorButtonClicked("-") }
        rootView.findViewById<Button>(R.id.buttonMultiply).setOnClickListener { operatorButtonClicked("x") }
        rootView.findViewById<Button>(R.id.buttonDivide).setOnClickListener { operatorButtonClicked("/") }
        rootView.findViewById<Button>(R.id.buttonModulo).setOnClickListener { operatorButtonClicked("%") }

        rootView.findViewById<Button>(R.id.buttonDecimal).setOnClickListener { dotButtonClicked() }
        rootView.findViewById<Button>(R.id.buttonClear).setOnClickListener { clearButtonClicked() }
        //rootView.findViewById<Button>(R.id.btn_back).setOnClickListener { backButtonClicked() }
        rootView.findViewById<Button>(R.id.buttonEquals).setOnClickListener { resultButtonClicked() }

        return rootView
    }

    private fun numberButtonClicked(number: String) {
        if (isOperator) {
            expressionTextView.append(" ")
        }
        isOperator = false

        val expressionText = expressionTextView.text.split(" ")

        expressionTextView.append(number)
        resultTextView.text = calculateExpression()
    }

    private fun operatorButtonClicked(operator: String) {
        if (expressionTextView.text.isEmpty()) {
            return
        }

        when {
            isOperator -> {
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1) + operator
            }
            hasOperator -> {
                Toast.makeText(requireContext(), "An operator can only be used once.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                expressionTextView.append(" $operator")
            }
        }
        val ssb = SpannableStringBuilder(expressionTextView.text)

        expressionTextView.text = ssb
        isOperator = true
        hasOperator = true
    }

    private fun resultButtonClicked() {
        val expressionTexts = expressionTextView.text.split(" ")
        if (expressionTextView.text.isEmpty() || expressionTexts.size == 1) {
            return
        }
        if (expressionTexts.size != 3 && hasOperator) {
            Toast.makeText(requireContext(), "Complete the formula first!", Toast.LENGTH_SHORT).show()
            return
        }
        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
            return
        }
        val expressionText = expressionTextView.text.toString()
        val resultText = calculateExpression()

        resultTextView.text = ""
        expressionTextView.text = resultText

        isOperator = false
        hasOperator = false
    }

    private fun calculateExpression(): String {
        val expressionTexts = expressionTextView.text.split(" ")

        if (hasOperator.not() || expressionTexts.size != 3) {
            return ""
        } else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return ""
        }

        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when (op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "x" -> (exp1 * exp2).toString()
            "%" -> (exp1 % exp2).toString()
            "/" -> (exp1 / exp2).toString()
            else -> ""
        }
    }

    private fun dotButtonClicked() {
        if (!hasOperator) {
            if (expressionTextView.text.isEmpty()) {
                expressionTextView.append("0.")
            } else if (!expressionTextView.text.toString().contains(".")) {
                expressionTextView.append(".")
            }
        } else {if (resultTextView.text.isEmpty()) {
            resultTextView.append("0.")
        } else if (resultTextView.text.isNotEmpty() && resultTextView.text.toString().contains(".")) {
            resultTextView.append(".")
        }
        }
    }

    private fun clearButtonClicked() {
        expressionTextView.text = ""
        resultTextView.text = ""
        isOperator = false
        hasOperator = false
    }

    private fun backButtonClicked() {
        expressionTextView.text = expressionTextView.text.dropLast(1)
        isOperator = false
        hasOperator = false
    }

    fun String.isNumber(): Boolean {
        return try {
            this.toBigInteger()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}

