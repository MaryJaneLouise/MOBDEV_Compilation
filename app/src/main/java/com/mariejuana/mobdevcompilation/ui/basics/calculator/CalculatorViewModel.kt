package com.mariejuana.mobdevcompilation.ui.basics.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.math.pow
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {

    private val _expression = MutableLiveData<String>()
    val expression: LiveData<String> = _expression

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    private var currentInput = ""
    private var currentOperator = ""
    private var lastInputWasOperator = false

    fun onDigitClicked(digit: String) {
        if (lastInputWasOperator) {
            currentInput = ""
            lastInputWasOperator = false
        }
        currentInput += digit
        _expression.value = currentInput
    }

    fun onOperatorClicked(operator: String) {
        if (currentInput.isNotEmpty()) {
            currentOperator = operator
            currentInput += operator
            lastInputWasOperator = true
            _expression.value = currentInput
        }
    }

    fun onEqualsClicked() {
        if (currentInput.isNotEmpty() && !lastInputWasOperator) {
            val expression = currentInput
            val result = evaluateExpression(expression)
            currentInput = result // Set the current input to the result
            currentOperator = ""
            lastInputWasOperator = false
            _result.value = result
            _expression.value = currentInput // Update the expression TextView
        }
    }

    fun onClearClicked() {
        currentInput = ""
        currentOperator = ""
        lastInputWasOperator = false
        _result.value = "0"
        _expression.value = ""
    }

    fun onDecimalClicked() {
        if (currentInput.isEmpty()) {
            currentInput = "0."
        } else if (!currentInput.contains(".")) {
            currentInput += "."
        }
        _expression.value = currentInput
    }

    private val calculator = Calculator()
    private fun evaluateExpression(expression: String): String {
        return try {
            val sanitizedExpression = expression.replace("÷", "/").replace("×", "*")
            val result = calculator.evaluateExpression(sanitizedExpression).toString()
            if (result.endsWith(".0")) {
                result.dropLast(2)
            } else {
                result
            }
        } catch (e: Exception) {
            "Error"
        }
    }

}

class Calculator {
    fun evaluateExpression(expression: String): Double {
        try {
            val valueStack = Stack<Double>()
            val operatorStack = Stack<Char>()
            val tokens = expression.trim().split(" ")

            for (token in tokens) {
                when {
                    token.matches(Regex("-?\\d+(\\.\\d+)?")) -> {
                        valueStack.push(token.toDouble())
                    }
                    token == "(" -> {
                        operatorStack.push(token[0])
                    }
                    token == ")" -> {
                        while (operatorStack.isNotEmpty() && operatorStack.peek() != '(') {
                            valueStack.push(applyOperator(operatorStack.pop(), valueStack.pop(), valueStack.pop()))
                        }
                        operatorStack.pop()
                    }
                    token in "+-*/^" -> {
                        while (operatorStack.isNotEmpty() && hasHigherPrecedence(token[0], operatorStack.peek())) {
                            valueStack.push(applyOperator(operatorStack.pop(), valueStack.pop(), valueStack.pop()))
                        }
                        operatorStack.push(token[0])
                    }
                    else -> {
                        throw IllegalArgumentException("Invalid token: $token")
                    }
                }
            }

            while (operatorStack.isNotEmpty()) {
                valueStack.push(applyOperator(operatorStack.pop(), valueStack.pop(), valueStack.pop()))
            }

            return valueStack.pop()
        } catch (e: Exception) {
            e.printStackTrace()
            return Double.NaN
        }
    }
    private fun applyOperator(operator: Char, operand2: Double, operand1: Double): Double {
        return when (operator) {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            '*' -> operand1 * operand2
            '/' -> operand1 / operand2
            '^' -> operand1.pow(operand2)
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }

    private fun hasHigherPrecedence(op1: Char, op2: Char): Boolean {
        val precedenceMap = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2, '^' to 3)
        return precedenceMap[op1] ?: 0 > precedenceMap[op2] ?: 0
    }
}
