package com.example.composecalculator

data class CalculatorState(
    val numberOne: String = "",
    val numberTwo: String = "",
    val operation: CalculatorOperation? = null
)
