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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
                Text(
                    text = "⭐ Score: ${uiState.score}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Coin Display & Question Card
            val cardBg = when (uiState.feedbackState) {
                MoneyFeedbackState.CORRECT -> MintGreen
                MoneyFeedbackState.INCORRECT -> SoftOrange
                MoneyFeedbackState.IDLE -> Color.White
            }

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Count the Coins! 🪙",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF263238),
                        textAlign = TextAlign.Center
                    )

                    // Grid of larger coins (3 columns, 104.dp size with card background)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(uiState.currentProblem.coins) { coin ->
                            Card(
                                modifier = modifier
                                    .size(104.dp),
                                shape = RoundedCornerShape(52.dp), // circular or rounded card
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
                                            .padding(8.dp)
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = "Total Cents: ${uiState.userInput.ifEmpty { "?" }} ¢",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF263238),
                        textAlign = TextAlign.Center
                    )

                    when (uiState.feedbackState) {
                        MoneyFeedbackState.CORRECT -> {
                            Text(
                                text = "🎉 Great Counting!",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            Button(
                                onClick = { viewModel.nextProblem() },
                                colors = ButtonDefaults.buttonColors(containerColor = SunflowerYellow),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(text = "Next Coins ➡️", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                        MoneyFeedbackState.INCORRECT -> {
                            Text(
                                text = "💪 Try Again!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100)
                            )
                        }
                        MoneyFeedbackState.IDLE -> {
                            Text(
                                text = "Use keypad to enter total cents",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            // Keypad
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val buttons = listOf(
                    listOf(1, 2, 3),
                    listOf(4, 5, 6),
                    listOf(7, 8, 9),
                    listOf(-1, 0, -2)
                )
                for (row in buttons) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (btn in row) {
                            KeypadButton(
                                modifier = modifier
                                    .weight(1f)
                                    .height(56.dp),
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
                                            if (uiState.feedbackState == MoneyFeedbackState.CORRECT) {
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
