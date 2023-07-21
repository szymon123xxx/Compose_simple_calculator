package com.example.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composecalculator.ui.theme.ComposeCalculatorTheme
import com.example.composecalculator.ui.theme.OnBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCalculatorTheme {
                val viewModel = viewModel<CalculatorViewModel>()
                val state = viewModel.state

                Calculator(
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(OnBackground),
                    buttonSpacing = BUTTON_SPACING.dp,
                    onAction = viewModel::onAction
                )
            }
        }
    }

    companion object {
        private const val BUTTON_SPACING = 8
    }
}