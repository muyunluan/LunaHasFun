package com.fdeng.lunahasfun.ui.math

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

val SkyBlue = Color(0xFF4DB6AC)
val SunflowerYellow = Color(0xFFFFF176)
val MintGreen = Color(0xFF81C784)
val SoftOrange = Color(0xFFFFB74D)

@Composable
fun MathScreen(
    viewModel: MathViewModel,
    onBackToDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SkyBlue)
            .padding(24.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar: Back Button & Score
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onBackToDashboard,
                    colors = ButtonDefaults.buttonColors(containerColor = SunflowerYellow),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = "⬅️ Home", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }

                Text(
                    text = "⭐ Score: ${uiState.score}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Problem & Input Display Card
            val cardBg = when (uiState.feedbackState) {
                FeedbackState.CORRECT -> MintGreen
                FeedbackState.INCORRECT -> SoftOrange
                FeedbackState.IDLE -> Color.White
            }

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val operatorSymbol = if (uiState.currentProblem.isAddition) "+" else "-"
                    Text(
                        text = "${uiState.currentProblem.num1}  $operatorSymbol  ${uiState.currentProblem.num2}",
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF37474F),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = modifier.height(12.dp))

                    Text(
                        text = "= ${uiState.userInput.ifEmpty { "?" }}",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF37474F),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = modifier.height(10.dp))

                    when (uiState.feedbackState) {
                        FeedbackState.CORRECT -> {
                            Text(
                                text = "🎉 Fantastic Job!",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            Spacer(modifier = modifier.height(6.dp))
                            Button(
                                onClick = { viewModel.nextProblem() },
                                colors = ButtonDefaults.buttonColors(containerColor = SunflowerYellow),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(text = "Next Problem ➡️", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                        FeedbackState.INCORRECT -> {
                            Text(
                                text = "💪 Almost! Try Again!",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100)
                            )
                        }
                        FeedbackState.IDLE -> {
                            Text(
                                text = "Type your answer below:",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            // Keypad Grid (1-9, C, 0, ✔)
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val buttons = listOf(
                    listOf(1, 2, 3),
                    listOf(4, 5, 6),
                    listOf(7, 8, 9),
                    listOf(-1, 0, -2) // -1 for Clear (C), -2 for Submit (✔)
                )

                for (row in buttons) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        for (btn in row) {
                            KeypadButton(
                                modifier = modifier
                                    .weight(1f)
                                    .height(64.dp),
                                text = when (btn) {
                                    -1 -> "C"
                                    -2 -> "✔"
                                    else -> btn.toString()
                                },
                                backgroundColor = when (btn) {
                                    -1 -> SoftOrange
                                    -2 -> MintGreen
                                    else -> SunflowerYellow
                                },
                                onClick = {
                                    when (btn) {
                                        -1 -> viewModel.onClearPressed()
                                        -2 -> {
                                            if (uiState.feedbackState == FeedbackState.CORRECT) {
                                                viewModel.nextProblem()
                                            } else {
                                                viewModel.onSubmitAnswer()
                                            }
                                        }
                                        else -> viewModel.onDigitPressed(btn)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeypadButton(
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF263238)
        )
    }
}
