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
        when (uiState.mode) {
            MathMode.COMPLETED -> {
                // Final Score Celebration Screen
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(
                                text = "🏆 Quiz Complete! 🏆",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF263238),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "You finished all questions and review!",
                                fontSize = 18.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )

                            // Score display
                            Card(
                                modifier = modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = SunflowerYellow)
                            ) {
                                Column(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Final Score",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF263238)
                                    )
                                    Spacer(modifier = modifier.height(8.dp))
                                    Text(
                                        text = "${uiState.score} / ${uiState.totalQuestions}",
                                        fontSize = 48.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1B5E20)
                                    )
                                }
                            }

                            val praise = when {
                                uiState.score >= 22 -> "⭐ Amazing Math Superstar! ⭐"
                                uiState.score >= 18 -> "🎉 Great Job! Keep it up!"
                                uiState.score >= 12 -> "👍 Good effort! Practice makes perfect!"
                                else -> "💪 Nice try! Let's play again!"
                            }
                            Text(
                                text = praise,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF37474F),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = modifier.height(8.dp))

                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.restartQuiz() },
                                    modifier = modifier.weight(1f).height(56.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MintGreen),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(text = "🔄 Play Again", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                                Button(
                                    onClick = onBackToDashboard,
                                    modifier = modifier.weight(1f).height(56.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = SoftOrange),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(text = "🏠 Home", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
            MathMode.QUIZ, MathMode.REPLAY_FAILED -> {
                // Active Quiz or Replay Round Screen
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Bar: Back Button, Progress/Mode
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

                        val progressText = if (uiState.mode == MathMode.REPLAY_FAILED) {
                            "🔄 Replay: ${uiState.replayIndex + 1}/${uiState.failedProblems.size}"
                        } else {
                            "Q: ${uiState.questionNumber}/${uiState.totalQuestions}"
                        }

                        Text(
                            text = progressText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Problem & Input Display Card
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (uiState.mode == MathMode.REPLAY_FAILED) {
                                Text(
                                    text = "Review Failed Question",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SoftOrange
                                )
                                Spacer(modifier = modifier.height(8.dp))
                            }

                            val operatorSymbol = if (uiState.currentProblem.isAddition) "+" else "-"
                            Text(
                                text = "${uiState.currentProblem.num1}  $operatorSymbol  ${uiState.currentProblem.num2}",
                                fontSize = 56.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF37474F),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = modifier.height(16.dp))

                            Text(
                                text = "= ${uiState.userInput.ifEmpty { "?" }}",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF37474F),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Keypad Grid (1-9, C, 0) and Submit Button
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val buttons = listOf(
                            listOf("1", "2", "3"),
                            listOf("4", "5", "6"),
                            listOf("7", "8", "9"),
                            listOf("C", "0", "")
                        )

                        for (row in buttons) {
                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                for (btn in row) {
                                    if (btn.isEmpty()) {
                                        Spacer(modifier = modifier.weight(1f))
                                    } else {
                                        KeypadButton(
                                            modifier = modifier
                                                .weight(1f)
                                                .height(60.dp),
                                            text = btn,
                                            backgroundColor = if (btn == "C") SoftOrange else SunflowerYellow,
                                            onClick = {
                                                when (btn) {
                                                    "C" -> viewModel.onClearPressed()
                                                    else -> btn.toIntOrNull()?.let { viewModel.onDigitPressed(it) }
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // Submit Button (✔ SUBMIT)
                        Button(
                            onClick = { viewModel.onSubmitAnswer() },
                            modifier = modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = MintGreen),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                        ) {
                            Text(
                                text = "✔  SUBMIT",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
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
