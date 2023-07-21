package com.example.composecalculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecalculator.ui.theme.OnPrimary
import com.example.composecalculator.ui.theme.OnSecondary

@Composable
fun Calculator(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp,
    onAction: (CalculatorAction) -> Unit
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(SYMBOL_ONE.toFloat()),
                shape = RoundedCornerShape(bottomEnd = SPACING_30.dp, bottomStart = SPACING_30.dp),
                colors = CardDefaults.cardColors(containerColor = OnSecondary)
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.fillMaxHeight(),
                ) {
                    Text(
                        text = state.numberOne + (state.operation?.symbol
                            ?: EMPTY_STRING) + state.numberTwo,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = SPACING_30.dp,
                                end = SPACING_15.dp
                            ),
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        maxLines = SYMBOL_TWO.toInt(),
                        fontSize = SPACING_60.sp,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SPACING_20.dp),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    repeat(SYMBOL_THREE.toInt()) { index ->
                        CalculatorButton(
                            symbol = when (index) {
                                SYMBOL_ZERO.toInt() -> SYMBOL_AC
                                SYMBOL_ONE.toInt() -> SYMBOL_DEL
                                else -> SYMBOL_DIVIDE
                            },
                            modifier = Modifier
                                .aspectRatio(
                                    if (index == SYMBOL_ZERO.toInt())
                                        SYMBOL_TWO.toFloat()
                                    else
                                        SYMBOL_ONE.toFloat()
                                )
                                .weight(
                                    if (index == SYMBOL_ZERO.toInt())
                                        SYMBOL_TWO.toFloat()
                                    else
                                        SYMBOL_ONE.toFloat()
                                ),
                            onClick = {
                                when (index) {
                                    SYMBOL_ZERO.toInt() -> onAction(CalculatorAction.Clear)
                                    SYMBOL_ONE.toInt() -> onAction(CalculatorAction.Delete)
                                    else -> onAction(
                                        CalculatorAction.Operation(CalculatorOperation.Divide)
                                    )
                                }
                            },
                            color = if (index == SYMBOL_ZERO.toInt()) OnPrimary else OnSecondary
                        )
                    }
                }

                repeat(SYMBOL_THREE.toInt()) { outerIndex ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        repeat(SYMBOL_FOUR.toInt()) { innerIndex ->
                            CalculatorButton(
                                symbol = when (outerIndex) {
                                    SYMBOL_ZERO.toInt() -> {
                                        when (innerIndex) {
                                            SYMBOL_ZERO.toInt() -> SYMBOL_SEVEN
                                            SYMBOL_ONE.toInt() -> SYMBOL_EIGHT
                                            SYMBOL_TWO.toInt() -> SYMBOL_NINE
                                            else -> SYMBOL_MULTIPLY
                                        }
                                    }
                                    SYMBOL_ONE.toInt() -> {
                                        when (innerIndex) {
                                            SYMBOL_ZERO.toInt() -> SYMBOL_FOUR
                                            SYMBOL_ONE.toInt() -> SYMBOL_FIVE
                                            SYMBOL_TWO.toInt() -> SYMBOL_SIX
                                            else -> SYMBOL_MINUS
                                        }
                                    }
                                    else -> {
                                        when (innerIndex) {
                                            SYMBOL_ZERO.toInt() -> SYMBOL_ONE
                                            SYMBOL_ONE.toInt() -> SYMBOL_TWO
                                            SYMBOL_TWO.toInt() -> SYMBOL_THREE
                                            else -> SYMBOL_PLUS
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .aspectRatio(SYMBOL_ONE.toFloat())
                                    .weight(SYMBOL_ONE.toFloat()),
                                onClick = {
                                    when (outerIndex) {
                                        SYMBOL_ZERO.toInt() -> {
                                            when (innerIndex) {
                                                SYMBOL_ZERO.toInt() -> onAction(
                                                    CalculatorAction.Number(
                                                        SYMBOL_SEVEN.toInt()
                                                    )
                                                )
                                                SYMBOL_ONE.toInt() -> onAction(
                                                    CalculatorAction.Number(
                                                        SYMBOL_EIGHT.toInt()
                                                    )
                                                )
                                                SYMBOL_TWO.toInt() -> onAction(
                                                    CalculatorAction.Number(
                                                        SYMBOL_NINE.toInt()
                                                    )
                                                )
                                                else -> onAction(
                                                    CalculatorAction.Operation(
                                                        CalculatorOperation.Multiply
                                                    )
                                                )
                                            }
                                        }
                                        SYMBOL_ONE.toInt() -> {
                                            when (innerIndex) {
                                                SYMBOL_ZERO.toInt() -> onAction(
                                                    CalculatorAction.Number(
                                                        SYMBOL_FOUR.toInt()
                                                    )
                                                )
                                                SYMBOL_ONE.toInt() -> onAction(
                                                    CalculatorAction.Number(
                                                        SYMBOL_FIVE.toInt()
                                                    )
                                                )
                                                SYMBOL_TWO.toInt() -> onAction(
                                                    CalculatorAction.Number(
                                                        SYMBOL_SIX.toInt()
                                                    )
                                                )
                                                else -> onAction(
                                                    CalculatorAction.Operation(
                                                        CalculatorOperation.Subtract
                                                    )
                                                )
                                            }
                                        }
                                        else -> {
                                            when (innerIndex) {
                                                SYMBOL_ZERO.toInt() -> onAction(
                                                    CalculatorAction.Number(
                                                        SYMBOL_ONE.toInt()
                                                    )
                                                )
                                                SYMBOL_ONE.toInt() -> onAction(
                                                    CalculatorAction.Number(
                                                        SYMBOL_TWO.toInt()
                                                    )
                                                )
                                                SYMBOL_TWO.toInt() -> onAction(
                                                    CalculatorAction.Number(
                                                        SYMBOL_THREE.toInt()
                                                    )
                                                )
                                                else -> onAction(
                                                    CalculatorAction.Operation(
                                                        CalculatorOperation.Add
                                                    )
                                                )
                                            }
                                        }
                                    }
                                },
                                color = OnSecondary
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    repeat(SYMBOL_THREE.toInt()) { index ->
                        CalculatorButton(
                            symbol = when (index) {
                                SYMBOL_ZERO.toInt() -> SYMBOL_ZERO
                                SYMBOL_ONE.toInt() -> SYMBOL_DECIMAL
                                else -> SYMBOL_EQUAL
                            },
                            modifier = Modifier
                                .aspectRatio(
                                    if (index == SYMBOL_ZERO.toInt())
                                        SYMBOL_TWO.toFloat()
                                    else
                                        SYMBOL_ONE.toFloat()
                                )
                                .weight(
                                    if (index == SYMBOL_ZERO.toInt())
                                        SYMBOL_TWO.toFloat()
                                    else
                                        SYMBOL_ONE.toFloat()
                                ),
                            onClick = {
                                when (index) {
                                    SYMBOL_ZERO.toInt() -> onAction(
                                        CalculatorAction.Number(
                                            SYMBOL_ZERO.toInt()
                                        )
                                    )

                                    SYMBOL_ONE.toInt() -> onAction(CalculatorAction.Decimal)
                                    else -> onAction(CalculatorAction.Calculate)
                                }
                            },
                            color = OnSecondary
                        )
                    }
                }
            }
        }
    }
}

private const val SYMBOL_AC = "AC"
private const val SYMBOL_DEL = "Del"
private const val SYMBOL_DIVIDE = "/"
private const val SYMBOL_ZERO = "0"
private const val SYMBOL_ONE = "1"
private const val SYMBOL_TWO = "2"
private const val SYMBOL_THREE = "3"
private const val SYMBOL_FOUR = "4"
private const val SYMBOL_FIVE = "5"
private const val SYMBOL_SIX = "6"
private const val SYMBOL_SEVEN = "7"
private const val SYMBOL_EIGHT = "8"
private const val SYMBOL_NINE = "9"
private const val SYMBOL_PLUS = "+"
private const val SYMBOL_MINUS = "-"
private const val SYMBOL_MULTIPLY = "x"
private const val SYMBOL_EQUAL = "="
private const val SYMBOL_DECIMAL = "."
private const val EMPTY_STRING = ""
private const val SPACING_30 = 30
private const val SPACING_60 = 60
private const val SPACING_15 = 15
private const val SPACING_20 = 20