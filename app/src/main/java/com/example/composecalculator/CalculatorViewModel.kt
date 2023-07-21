package com.example.composecalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction): Any = when (action) {
        is CalculatorAction.Number -> enterNumber(action.number)
        is CalculatorAction.Decimal -> enterDecimal()
        is CalculatorAction.Clear -> state = CalculatorState()
        is CalculatorAction.Operation -> enterOperation(action.operation)
        is CalculatorAction.Calculate -> performCalculation()
        is CalculatorAction.Delete -> performDeletion()
    }

    private fun enterOperation(operation: CalculatorOperation) = state.apply {
        if (numberOne.isNotBlank())
            state = this.copy(operation = operation)
    }

    private fun performDeletion() = state.apply {
        when {
            numberTwo.isNotBlank() -> state = this.copy(
                numberTwo = numberTwo.dropLast(LAST_CHAR)
            )
            operation != null -> state = this.copy(
                operation = null
            )
            numberOne.isNotBlank() -> state = this.copy(
                numberOne = numberOne.dropLast(LAST_CHAR)
            )
        }
    }

    private fun performCalculation() = state.run {
        val number1 = numberOne.toDoubleOrNull()
        val number2 = numberTwo.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result = when (operation) {
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Subtract -> number1 - number2
                is CalculatorOperation.Multiply -> number1 * number2
                is CalculatorOperation.Divide -> number1 / number2
                null -> return
            }
            state = this.copy(
                numberOne = result.toString().take(NUMBER_TAKEN),
                numberTwo = EMPTY_STRING,
                operation = null
            )
        }
    }

    private fun enterDecimal() = state.run {
        if (
            operation == null &&
            !numberOne.contains(SYMBOL_DECIMAL) &&
            numberOne.isNotBlank()
        ) {
            state = this.copy(
                numberOne = "$numberOne$SYMBOL_DECIMAL"
            )
            return
        }

        if (numberTwo.contains(SYMBOL_DECIMAL) && numberTwo.isNotBlank()
        ) {
            state = this.copy(
                numberOne = "$numberTwo$SYMBOL_DECIMAL"
            )
        }
    }

    private fun enterNumber(number: Int? = null) = state.run {
        if (operation == null) {
            if (numberOne.length >= MAX_NUM_LENGTH) {
                return
            }
            state = this.copy(
                numberOne = numberOne + number,

                )
            return
        }
        if (numberTwo.length >= MAX_NUM_LENGTH) {
            return
        }
        state = this.copy(
            numberTwo = numberTwo + number,
        )
    }

    companion object {
        private const val LAST_CHAR = 1
        private const val MAX_NUM_LENGTH = 8
        private const val SYMBOL_DECIMAL = "."
        private const val EMPTY_STRING = ""
        private const val NUMBER_TAKEN = 15
    }
}