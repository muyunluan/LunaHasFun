package com.fdeng.lunahasfun.ui.money

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fdeng.lunahasfun.engine.MoneyProblemType
import com.fdeng.lunahasfun.ui.math.KeypadButton
import com.fdeng.lunahasfun.ui.math.MintGreen
import com.fdeng.lunahasfun.ui.math.SkyBlue
import com.fdeng.lunahasfun.ui.math.SoftOrange
import com.fdeng.lunahasfun.ui.math.SunflowerYellow

@Composable
fun MoneyScreen(
    viewModel: MoneyViewModel,
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
            MoneyMode.COMPLETED -> {
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
                                text = "🏆 Coin Counter Complete! 🏆",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF263238),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "You finished all coin problems and review!",
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
                                uiState.score >= 22 -> "⭐ Master Coin Counter! ⭐"
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
            MoneyMode.QUIZ, MoneyMode.REPLAY_FAILED -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Bar
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

                        val progressText = if (uiState.mode == MoneyMode.REPLAY_FAILED) {
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

                    // Coin Display & Question Card
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (uiState.mode == MoneyMode.REPLAY_FAILED) {
                                Text(
                                    text = "🔄 Review Failed Coins",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SoftOrange
                                )
                            }

                            Text(
                                text = "Calculate the value of the following coins:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF263238),
                                textAlign = TextAlign.Center
                            )

                            // Grid of coins with labels underneath
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                modifier = modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(4.dp)
                            ) {
                                items(uiState.currentProblem.coins) { coin ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Card(
                                            modifier = modifier.size(72.dp),
                                            shape = RoundedCornerShape(36.dp),
                                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                        ) {
                                            Box(
                                                modifier = modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(id = coin.drawableResId),
                                                    contentDescription = coin.displayName,
                                                    modifier = modifier
                                                        .fillMaxSize()
                                                        .padding(4.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "${coin.displayName}\n(${coin.centValue}¢)",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF263238),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 13.sp
                                        )
                                    }
                                }
                            }

                            val isDollar = uiState.currentProblem.problemType == MoneyProblemType.DOLLAR_CONVERSION
                            val promptText = if (isDollar) {
                                "Total Value: \$${uiState.userInput.ifEmpty { "?" }}"
                            } else {
                                "Total Value: ${uiState.userInput.ifEmpty { "?" }}¢"
                            }

                            Text(
                                text = promptText,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF263238),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = if (isDollar) "Type amount in dollars (e.g. 1.25)" else "Use keypad to enter total cents",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    // Keypad (1-9, C, 0, ., and Submit ✔)
                    Column(
                        modifier = modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val rows = listOf(
                            listOf("1", "2", "3"),
                            listOf("4", "5", "6"),
                            listOf("7", "8", "9"),
                            listOf("C", "0", ".")
                        )
                        for (row in rows) {
                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                for (btn in row) {
                                    KeypadButton(
                                        modifier = modifier
                                            .weight(1f)
                                            .height(50.dp),
                                        text = btn,
                                        backgroundColor = when (btn) {
                                            "C" -> SoftOrange
                                            "." -> SunflowerYellow
                                            else -> SunflowerYellow
                                        },
                                        onClick = {
                                            when (btn) {
                                                "C" -> viewModel.onClearPressed()
                                                "." -> viewModel.onDotPressed()
                                                else -> btn.toIntOrNull()?.let { viewModel.onDigitPressed(it) }
                                            }
                                        }
                                    )
                                }
                            }
                        }

                        // Submit Button (✔ SUBMIT)
                        Button(
                            onClick = { viewModel.onSubmitAnswer() },
                            modifier = modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = MintGreen),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                        ) {
                            Text(
                                text = "✔  SUBMIT",
                                fontSize = 20.sp,
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
