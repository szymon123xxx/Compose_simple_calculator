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
            CreateCardWithText(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(SYMBOL_ONE.toFloat()),
                state = state
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SPACING_20.dp),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CreateBoxOfButtons(buttonSpacing = buttonSpacing, onAction = onAction)
            }
        }
    }
}

@Composable
fun CreateCardWithText(
    modifier: Modifier,
    state: CalculatorState,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(bottomEnd = SPACING_30.dp, bottomStart = SPACING_30.dp),
        colors = CardDefaults.cardColors(containerColor = OnSecondary),
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = state.numberOne + (state.operation?.symbol ?: EMPTY_STRING) + state.numberTwo,
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
}

@Composable
fun CreateBoxOfButtons(
    buttonSpacing: Dp,
    onAction: (CalculatorAction) -> Unit,
) {
    repeat(SYMBOL_FIVE.toInt()) { index ->
        CreateRowOfButtons(
            buttonSpacing = buttonSpacing,
            onAction =  when (index) {
                SYMBOL_ZERO.toInt() -> listOf(
                    { onAction(CalculatorAction.Clear) },
                    { onAction(CalculatorAction.Delete) },
                    { onAction(CalculatorAction.Operation(CalculatorOperation.Divide)) }
                )
                SYMBOL_ONE.toInt() -> listOf(
                    { onAction(CalculatorAction.Number(SYMBOL_SEVEN.toInt())) },
                    { onAction(CalculatorAction.Number(SYMBOL_EIGHT.toInt())) },
                    { onAction(CalculatorAction.Number(SYMBOL_NINE.toInt())) },
                    { onAction(CalculatorAction.Operation(CalculatorOperation.Multiply)) }
                )
                SYMBOL_TWO.toInt() -> listOf(
                    { onAction(CalculatorAction.Number(SYMBOL_FOUR.toInt())) },
                    { onAction(CalculatorAction.Number(SYMBOL_FIVE.toInt())) },
                    { onAction(CalculatorAction.Number(SYMBOL_SIX.toInt())) },
                    { onAction(CalculatorAction.Operation(CalculatorOperation.Subtract)) }
                )
                SYMBOL_THREE.toInt() -> listOf(
                    { onAction(CalculatorAction.Number(SYMBOL_ONE.toInt())) },
                    { onAction(CalculatorAction.Number(SYMBOL_TWO.toInt())) },
                    { onAction(CalculatorAction.Number(SYMBOL_THREE.toInt())) },
                    { onAction(CalculatorAction.Operation(CalculatorOperation.Add)) }
                )
                else -> listOf(
                    { onAction(CalculatorAction.Number(SYMBOL_ZERO.toInt())) },
                    { onAction(CalculatorAction.Decimal) },
                    { onAction(CalculatorAction.Calculate) }
                )
            },
            symbols = when (index) {
                SYMBOL_ZERO.toInt() -> listOf(SYMBOL_AC, SYMBOL_DEL, SYMBOL_DIVIDE)
                SYMBOL_ONE.toInt() -> listOf(SYMBOL_SEVEN, SYMBOL_EIGHT, SYMBOL_NINE, SYMBOL_MULTIPLY)
                SYMBOL_TWO.toInt() -> listOf(SYMBOL_FOUR, SYMBOL_FIVE, SYMBOL_SIX, SYMBOL_MINUS)
                SYMBOL_THREE.toInt() -> listOf(SYMBOL_ONE, SYMBOL_TWO, SYMBOL_THREE, SYMBOL_PLUS)
                else -> listOf(SYMBOL_ZERO, SYMBOL_DECIMAL, SYMBOL_EQUAL)
            },
            repeat = when (index) {
                SYMBOL_ZERO.toInt(), SYMBOL_FOUR.toInt() -> SYMBOL_THREE.toInt()
                else -> SYMBOL_FOUR.toInt()
            },
            aspectRatio = when (index) {
                SYMBOL_ZERO.toInt(), SYMBOL_FOUR.toInt() -> true
                else -> false
            },
            isOtherColor = index == SYMBOL_ZERO.toInt()
        )
    }
}

@Composable
fun CreateRowOfButtons(
    buttonSpacing: Dp,
    onAction: List<() -> Unit>,
    symbols: List<String>,
    repeat: Int,
    aspectRatio: Boolean,
    isOtherColor: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        repeat(repeat) { index ->
            CalculatorButton(
                symbol = symbols[index],
                modifier = Modifier
                    .aspectRatio(
                        if (index == SYMBOL_ZERO.toInt() && aspectRatio)
                            SYMBOL_TWO.toFloat()
                        else
                            SYMBOL_ONE.toFloat()
                    )
                    .weight(
                        if (index == SYMBOL_ZERO.toInt() && aspectRatio)
                            SYMBOL_TWO.toFloat()
                        else
                            SYMBOL_ONE.toFloat()
                    ),
                onClick = onAction[index],
                color = if (isOtherColor && index == SYMBOL_ZERO.toInt()) OnPrimary else OnSecondary
            )
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