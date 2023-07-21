package com.example.composecalculator

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ButtonState { Pressed, Idle }

fun Modifier.bounceClick(onClick: () -> Unit) =
    composed {
        composed {
            var buttonState by remember { mutableStateOf(ButtonState.Idle) }
            val scale by animateFloatAsState(
                if (buttonState == ButtonState.Pressed)
                    BUTTON_PRESSED.toFloat()
                else
                    BUTTON_RELEASED.toFloat()
            )

            this
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onClick() }
                )
                .pointerInput(buttonState) {
                    awaitPointerEventScope {
                        buttonState = if (buttonState == ButtonState.Pressed) {
                            waitForUpOrCancellation()
                            ButtonState.Idle
                        } else {
                            awaitFirstDown(false)
                            ButtonState.Pressed
                        }
                    }
                }
        }
    }

@Composable
fun CalculatorButton(
    symbol: String,
    modifier: Modifier,
    onClick: () -> Unit,
    textStyle: TextStyle = TextStyle(),
    color: Color = Color.White,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .bounceClick(onClick)
            .clip(RoundedCornerShape(CORNER_SHAPE.dp))
            .background(color)
            .then(modifier)
    ) {
        Text(
            text = symbol,
            style = textStyle,
            color = Color.White,
            fontSize = FONT_SIZE.sp
        )
    }
}

private const val FONT_SIZE = 20
private const val CORNER_SHAPE = 100
private const val BUTTON_PRESSED = 0.7
private const val BUTTON_RELEASED = 1